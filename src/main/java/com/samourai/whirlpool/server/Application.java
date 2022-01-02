package com.samourai.whirlpool.server;

import com.samourai.http.client.HttpUsage;
import com.samourai.http.client.JavaHttpClient;
import com.samourai.javaserver.config.ServerConfig;
import com.samourai.javaserver.run.ServerApplication;
import com.samourai.javaserver.utils.LogbackUtils;
import com.samourai.javaserver.utils.ServerUtils;
import com.samourai.whirlpool.server.config.WhirlpoolServerConfig;
import com.samourai.whirlpool.server.services.DbService;
import com.samourai.whirlpool.server.services.FixMixOutputService;
import com.samourai.whirlpool.server.services.JavaHttpClientService;
import com.samourai.whirlpool.server.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value = "com.samourai.whirlpool.server.config.filters")
public class Application extends ServerApplication {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Autowired private ServerUtils serverUtils;

  @Autowired private WhirlpoolServerConfig serverConfig;

  @Autowired private JavaHttpClientService httpClientService;

  @Autowired private DbService dbService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void runServer() throws Exception {
    // server starting...
    JavaHttpClient httpClient = new JavaHttpClient(10000, null, HttpUsage.BACKEND);
    new FixMixOutputService(httpClient, dbService).run();
  }

  @Override
  protected ServerConfig getServerConfig() {
    return serverConfig;
  }

  @Override
  protected void setLoggerDebug() {
    Utils.setLoggerDebug();

    // skip noisy logs
    LogbackUtils.setLogLevel(
        "org.springframework.web.socket.config.WebSocketMessageBrokerStats",
        Level.ERROR.toString());
  }
}
