package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
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

  @Bean
  public DataSource DataSource(){
    String jdbcUrl ="jdbc:postgresql://" +
        System.getenv("PSQL_HOST") + ":" +
        System.getenv("PSQL_PORT") + "/" +
        System.getenv("PSQL_DB");
    String user = System.getenv("PSQL_USER");
    String password = System.getenv("PSQL_PASSWORD");

    //Never log your credentials/secrets. Use IDE debugger instead
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(jdbcUrl);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(password);
    return basicDataSource;
  }
}