package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//Testted through controller// but implement this test class
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
//@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

  @Test
  public void findIexQuoteByTicker() {
  }

  @Test
  public void findAllIexQuoteByTicker() {
  }

  @Test
  public void updateMarketData() {
  }

  @Test
  public void saveQuotes() {
  }

  @Test
  public void saveQuote() {
  }

  @Test
  public void testSaveQuote() {
  }

  @Test
  public void findAllQuotes() {
  }
}