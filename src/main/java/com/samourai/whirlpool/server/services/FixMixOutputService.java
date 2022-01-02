package com.samourai.whirlpool.server.services;

import com.samourai.http.client.JavaHttpClient;
import com.samourai.whirlpool.server.persistence.to.MixOutputTO;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
CREATE TABLE `mix_output_processed` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `address` varchar(90) NOT NULL,
        `created` datetime DEFAULT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `mix_output_processed_uniq` (`address`)
        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
*/
public class FixMixOutputService implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private JavaHttpClient httpClient;
  private DbService dbService;

  public FixMixOutputService(JavaHttpClient httpClient, DbService dbService) {
    this.httpClient = httpClient;
    this.dbService = dbService;
  }

  @Override
  public void run() {
    Iterable<MixOutputTO> mixOutputTOS = dbService.getMixOutputs();
    for (MixOutputTO mixOutputTO : mixOutputTOS) {
      log.info(
          "still "
              + dbService.countMixOutputs()
              + " mixOutputs remaining, "
              + dbService.countMixOutputsProcessed()
              + " processed, created="
              + mixOutputTO.getCreated());
      try {
        String address = mixOutputTO.getAddress();
        if (!dbService.hasMixOutputProcessed(address)) {
          boolean unused = isUnusedAddress(address);
          if (unused) {
            log.info("deleting unused " + address);
            dbService.deleteMixOutput(address);
          } else {
            log.info("keeping used " + address);
          }
          dbService.saveMixOutputProcessed(address);

          try {
            Thread.sleep(500);
          } catch (Exception e) {
          }
        }
      } catch (Exception e) {
        log.error("", e);
      }
    }
  }

  private boolean isUnusedAddress(String address) throws Exception {
    String url = "https://blockstream.info/api/address/" + address;
    String result = httpClient.getJson(url, String.class, null);

    if (!result.contains("\"tx_count\":")) {
      throw new Exception("invalid response for " + url + ": " + result);
    }
    return result.contains("\"tx_count\":0},") && result.contains("\"tx_count\":0}}");
  }
}
