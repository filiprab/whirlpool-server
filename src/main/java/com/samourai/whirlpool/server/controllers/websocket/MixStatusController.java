package com.samourai.whirlpool.server.controllers.websocket;

import com.samourai.whirlpool.protocol.WhirlpoolProtocol;
import com.samourai.whirlpool.server.beans.Pool;
import com.samourai.whirlpool.server.services.MixService;
import com.samourai.whirlpool.server.services.PoolService;
import com.samourai.whirlpool.server.services.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.security.Principal;

@Controller
public class MixStatusController extends AbstractWebSocketController {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private MixService mixService;
  private PoolService poolService;

  @Autowired
  public MixStatusController(MixService mixService, PoolService poolService, WebSocketService webSocketService) {
    super(webSocketService);
    this.mixService = mixService;
    this.poolService = poolService;
  }

  @SubscribeMapping(WhirlpoolProtocol.SOCKET_SUBSCRIBE_USER_PRIVATE + WhirlpoolProtocol.SOCKET_SUBSCRIBE_USER_REPLY)
  public void mixStatusOnSubscribe(Principal principal, StompHeaderAccessor headers) throws Exception {
    validateHeaders(headers);

    String username = principal.getName();
    if (log.isDebugEnabled()) {
      log.info("[controller] subscribe:"+ WhirlpoolProtocol.SOCKET_SUBSCRIBE_USER_PRIVATE + WhirlpoolProtocol.SOCKET_SUBSCRIBE_USER_REPLY + ": username=" + username);
    }

    // validate poolId
    String headerPoolId = headers.getFirstNativeHeader(WhirlpoolProtocol.HEADER_POOL_ID);
    Pool pool = poolService.getPool(headerPoolId); // exception if not found

    try {
      Thread.sleep(1000); // wait to make sure client subscription is ready
      String mixId = pool.getCurrentMix().getMixId();
      getWebSocketService().sendPrivate(username, mixService.computeMixStatusNotification(mixId));
    }
    catch(Exception e) {
      log.error("", e);
    }
  }

  @MessageExceptionHandler
  public void handleException(Exception exception, Principal principal) {
    super.handleException(exception, principal);
  }

}