package ca.jrvs.apps.trading.dao;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoIntTest {

  MarketDataDao dao;

  @Before
  public void setUp() throws Exception {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);

    MarketDataConfig config = new MarketDataConfig();
    config.setHost("https://cloud.iexapis.com/v1");
    config.setToken(System.getenv("TOKEN"));

    dao = new MarketDataDao(cm, config);
  }

  @Test
  public void findById() {
    String ticker = "FRC";
    Optional<IexQuote> iexQuoteOptional = dao.findById(ticker);
    IexQuote iexQuote = iexQuoteOptional.get();
    assertEquals(ticker, iexQuote.getSymbol());

    JsonParser.print(iexQuote);
  }

  @Test
  public void findAllById() {
    //happy path
    List<IexQuote> iexQuoteList = (List<IexQuote>) dao.findAllById(Arrays.asList("FRC","AAPL"));
    assertEquals(2, iexQuoteList.size());
    assertEquals("FRC", iexQuoteList.get(0).getSymbol());
    JsonParser.print(iexQuoteList.get(0));
    JsonParser.print(iexQuoteList.get(1));
    //sad path
    try {
      dao.findAllById(Arrays.asList("FRC", "AAPL"));
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void save() {
  }
}