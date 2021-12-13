package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//Implement this Test Class
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
//@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {

  @Autowired
  TraderDao traderDao;

  Trader savedTrader;

  @Before
  public void insertOne() throws Exception {
    savedTrader = new Trader();
    //savedTrader.setId(1);
    savedTrader.setFirstName("Hamad");
    savedTrader.setLastName("Asif");
    savedTrader.setDob(Date.valueOf("1989-01-01"));
    savedTrader.setCountry("Canada");
    savedTrader.setEmail("ayesha@gmail.com");
    traderDao.save(savedTrader);
  }

  @After
  public void deleteOne() throws Exception {
    traderDao.deleteById(savedTrader.getId());
  }

  @Test
  public void findAllById() {
    Optional<Trader> traders = traderDao.findById(savedTrader.getId()) ;
    assertEquals(savedTrader.getCountry(), traders.get().getCountry());
  }

  @Test
  public void count() {
    assertEquals(3,traderDao.count());
  }

  @Test
  public void findAll() {
    List<Trader> traders = traderDao.findAll();
    assertEquals(3, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }
}