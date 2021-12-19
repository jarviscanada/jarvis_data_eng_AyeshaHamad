package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

  @Autowired
  private TraderDao traderDao;
  private Trader savedTrader;
  @Autowired
  private AccountDao accountDao;
  private Account savedAccount;
  @Autowired
  private QuoteDao quoteDao;
  private Quote savedQuote;
  @Autowired
  private SecurityOrderDao securityOrderDao;
  private SecurityOrder securityOrder;

  @Before
  public void setUp() throws Exception {
    savedTrader = new Trader();
    savedTrader.setId(1);
    savedTrader.setFirstName("Hamad");
    savedTrader.setLastName("Asif");
    savedTrader.setDob(Date.valueOf("1989-01-01"));
    savedTrader.setCountry("Canada");
    savedTrader.setEmail("ayesha@gmail.com");
    traderDao.save(savedTrader);

    savedAccount = new Account();
    savedAccount.setId(1);
    savedAccount.setTraderId(1);
    savedAccount.setAmount(100d);
    accountDao.save(savedAccount);

    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("AAPL");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);

    securityOrder = new SecurityOrder();
    securityOrder.setId(1);
    securityOrder.setAccountId(1);
    securityOrder.setTicker("AAPL");
    securityOrder.setPrice(10.9d);
    securityOrder.setSize(10);
    securityOrder.setStatus("Closed");
    securityOrder.setNotes("Add Notes");
    securityOrderDao.save(securityOrder);
  }

  @After
  public void tearDown() throws Exception {
    securityOrderDao.deleteById(1);
  }

  @Test
  public void count(){
    assertEquals(1,securityOrderDao.count());
  }

  @Test
  public void findAll(){
    assertEquals(1,securityOrderDao.findAll().size());
  }

}