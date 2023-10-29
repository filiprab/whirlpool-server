package com.samourai.whirlpool.server.controllers.web;

import com.samourai.whirlpool.server.beans.RegisteredInput;
import com.samourai.whirlpool.server.utils.Utils;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

// used in thymeleaf templates
@Component
public class TemplateUtil {
  public BigDecimal satoshisToBtc(long sats) {
    return Utils.satoshisToBtc(sats);
  }

  public String registeredInputsToString(Collection<RegisteredInput> registeredInputs) {
    String result = registeredInputs.size() + " inputs";
    if (registeredInputs.size() > 0) {
      result +=
          ":\n"
              + registeredInputs.stream()
                  .map(
                      input ->
                          input.getOutPoint().toKey()
                              + " "
                              + (input.isSoroban() ? "soroban" : "classic")
                              + (input.isQuarantine() ? ": " + input.getQuarantineReason() : ""))
                  .collect(Collectors.joining("\n"));
    }
    return result;
  }

  public String durationFromNow(long ms) {
    int seconds = (int) (System.currentTimeMillis() - ms) / 1000;
    return duration(seconds, true);
  }

  public String duration(int seconds) {
    return duration(seconds, true);
  }

  public String duration(int seconds, boolean withSeconds) {
    StringBuffer sb = new StringBuffer();
    if (seconds > 60) {
      int minutes = (int) Math.floor(seconds / 60);

      if (minutes > 60) {
        int hours = (int) Math.floor(minutes / 60);
        sb.append(hours + "h");
        minutes -= hours * 60;
      }

      sb.append(minutes + "m");
      seconds -= minutes * 60;
    }
    if (withSeconds) {
      sb.append(seconds + "s");
    }
    return sb.toString();
  }
}
