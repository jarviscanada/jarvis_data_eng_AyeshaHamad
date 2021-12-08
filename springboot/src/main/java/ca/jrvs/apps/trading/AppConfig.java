package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao", "ca.jrvs.apps.trading.service"})
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig config = new MarketDataConfig();
    config.setToken(System.getenv("TOKEN"));
    config.setHost("https://cloud.iexapis.com/v1");
    return config;
  }

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }
}