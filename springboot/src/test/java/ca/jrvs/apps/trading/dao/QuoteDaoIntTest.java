package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
//@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao ;

  private Quote quoteSaved = new Quote();

  @Before
  public void insertOne() {
    quoteSaved = new Quote();
    quoteSaved.setAskPrice(10d);
    quoteSaved.setAskSize(10);
    quoteSaved.setBidPrice(20.2d);
    quoteSaved.setBidSize(10);
    quoteSaved.setId("FRC");
    quoteSaved.setLastPrice(10.5);
    quoteDao.save(quoteSaved);
  }

  @After
  public void deleteOne() {
    //working
    //tested
    //quoteDao.deleteById(quoteSaved.getId());
    //quoteDao.deleteAll();
  }

  @Test
  public void test() {
    assertEquals(true, quoteDao.existsById(quoteSaved.getId()));
    assertEquals(6, quoteDao.count());
    List<Quote> list = (List<Quote>) quoteDao.findAll();
    for(Quote quote : list) {
      System.out.println(quote.getId());
    }
  }
}