package com.samourai.whirlpool.server.beans;

import com.samourai.soroban.client.endpoint.meta.typed.SorobanEndpointTyped;
import com.samourai.soroban.client.endpoint.meta.typed.SorobanItemTyped;
import com.samourai.soroban.protocol.payload.SorobanErrorMessage;
import com.samourai.wallet.bip47.rpc.Bip47Encrypter;
import com.samourai.wallet.util.AsyncUtil;
import com.samourai.wallet.util.RandomUtil;
import com.samourai.whirlpool.protocol.soroban.WhirlpoolApiCoordinator;
import com.samourai.whirlpool.protocol.soroban.payload.registerInput.RegisterInputRequest;
import com.samourai.whirlpool.server.services.RegisterInputService;
import com.samourai.whirlpool.server.utils.Utils;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputPoolQueue extends InputPool {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final AsyncUtil asyncUtil = AsyncUtil.getInstance();
  private static final RandomUtil randomUtil = RandomUtil.getInstance();

  private Pool pool;
  private boolean liquidity;
  private WhirlpoolApiCoordinator whirlpoolApiCoordinator;
  private List<SorobanItemTyped> sorobanInputs;

  public InputPoolQueue(
      Pool pool, boolean liquidity, WhirlpoolApiCoordinator whirlpoolApiCoordinator) {
    super();
    this.pool = pool;
    this.liquidity = liquidity;
    this.whirlpoolApiCoordinator = whirlpoolApiCoordinator;
    this.sorobanInputs = new LinkedList<>();
  }

  public boolean hasInputsClassicOrSoroban() {
    return super.hasInputs() || !sorobanInputs.isEmpty();
  }

  public void refreshSorobanInputs() throws Exception {
    // refresh
    this.sorobanInputs =
        asyncUtil.blockingGet(
            whirlpoolApiCoordinator.registerInputFetchRequests(pool.getPoolId(), liquidity));
    Collections.shuffle(sorobanInputs);
  }

  public synchronized Optional<RegisteredInput> removeRandomClassicOrSoroban(
      Predicate<RegisteredInput> filter, RegisterInputService registerInputService) {
    if (randomUtil.nextInt(10) % 2 == 0) {
      // find soroban input
      Optional<RegisteredInput> inputOpt = removeRandomSorobanInput(filter, registerInputService);
      if (inputOpt.isPresent()) {
        return inputOpt;
      }
    }

    // find classic input
    return removeRandom(filter);
  }

  private synchronized Optional<RegisteredInput> removeRandomSorobanInput(
      Predicate<RegisteredInput> filter, RegisterInputService registerInputService) {
    for (SorobanItemTyped sorobanItemTyped : sorobanInputs) {
      try {
        RegisteredInput registeredInput =
            validateSorobanInput(sorobanItemTyped, registerInputService);

        // filter
        if (filter.test(registeredInput)) {
          return Optional.of(registeredInput);
        }
      } catch (Exception e) {
        log.error("Error reading soroban input: " + e.getMessage());
      }
    }
    return Optional.empty();
  }

  private RegisteredInput validateSorobanInput(
      SorobanItemTyped sorobanItemTyped, RegisterInputService registerInputService)
      throws Exception {
    Bip47Encrypter encrypter =
        whirlpoolApiCoordinator.getRpcSession().getRpcWallet().getBip47Encrypter();
    SorobanEndpointTyped endpointReply = sorobanItemTyped.getEndpointReply(encrypter);
    try {
      RegisterInputRequest req = sorobanItemTyped.read(RegisterInputRequest.class);
      SorobanInput sorobanInput = new SorobanInput(sorobanItemTyped.getMetaSender(), endpointReply);

      return registerInputService.validateRegisterInputRequest(
          pool,
          // use Soroban sender (which is a temporary identity) as username
          sorobanItemTyped.getMetaSender().toString(),
          req.signature,
          req.utxoHash,
          req.utxoIndex,
          liquidity,
          null, // we never know if user is using Tor with Soroban
          req.blockHeight,
          sorobanInput);

    } catch (Exception e) {
      // reply error
      SorobanErrorMessage sorobanErrorMessage = Utils.computeSorobanErrorMessage(e);
      log.warn("REGISTER_INPUT ERROR SOROBAN_REPLY -> " + sorobanErrorMessage.toString());
      asyncUtil.blockingAwait(
          whirlpoolApiCoordinator
              .getRpcSession()
              .withSorobanClient(
                  sorobanClient -> endpointReply.send(sorobanClient, sorobanErrorMessage)));
      throw e;
    }
  }

  public Collection<RegisteredInput> _getInputsSoroban(RegisterInputService registerInputService) {
    Collection<RegisteredInput> inputs = new LinkedList<>();

    for (SorobanItemTyped sorobanItemTyped : sorobanInputs) {
      try {
        RegisteredInput registeredInput =
            validateSorobanInput(sorobanItemTyped, registerInputService);
        inputs.add(registeredInput);
      } catch (Exception e) {
        log.error("Error reading soroban input: " + e.getMessage());
      }
    }
    return inputs;
  }
}
