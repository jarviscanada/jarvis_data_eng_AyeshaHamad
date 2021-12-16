package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Account savedAccount;
  private Trader savedTrader;

  @Before
  public void initAddOne() throws Exception {
    savedTrader = new Trader();
    //savedTrader.setId(1);
    savedTrader.setFirstName("Hamad");
    savedTrader.setLastName("Asif");
    savedTrader.setDob(Date.valueOf("1989-01-01"));
    savedTrader.setCountry("Canada");
    savedTrader.setEmail("ayesha@gmail.com");
    //traderDao.save(savedTrader);

    savedAccount = new Account();
    savedAccount.setId(1);
    savedAccount.setTraderId(1);
    savedAccount.setAmount(100d);
    accountDao.save(savedAccount);
  }

  @After
  public void deleteById() throws Exception {
    accountDao.deleteById(savedAccount.getId());
  }

  @Test
  public void count() {
    assertEquals(1, accountDao.count());
  }

  @Test
  public void findAll() {
    assertEquals(1, accountDao.findAll().size());
  }
}