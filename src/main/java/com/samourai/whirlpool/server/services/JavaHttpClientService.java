package com.samourai.whirlpool.server.services;

import com.samourai.http.client.JavaHttpClient;
import com.samourai.whirlpool.cli.utils.CliUtils;
import com.samourai.whirlpool.protocol.WhirlpoolProtocol;
import org.eclipse.jetty.client.HttpClient;
import org.springframework.stereotype.Service;

@Service
public class JavaHttpClientService extends JavaHttpClient {
  private static final String USER_AGENT = "whirlpool-server " + WhirlpoolProtocol.PROTOCOL_VERSION;

  public JavaHttpClientService() {
    super();
  }

  protected HttpClient computeHttpClient(boolean isRegisterOutput) throws Exception {
    HttpClient httpClient = CliUtils.computeHttpClient(null, USER_AGENT);
    return httpClient;
  }
}
