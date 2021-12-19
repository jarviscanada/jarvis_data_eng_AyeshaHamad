package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.google.common.collect.Iterables;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private MarketDataDao marketDataDao;
  private QuoteDao quoteDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.marketDataDao = marketDataDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Find an IexQuote
   *
   * @param ticker id
   * @return IexObject
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker).
        orElseThrow(() -> new IllegalArgumentException("Invalid Ticker input: " + ticker));
  }

  public List<IexQuote> findAllIexQuoteByTicker(Iterable<String> tickers) {
    if(Iterables.size(tickers) == 0) {
      throw new IllegalArgumentException("Ticker Input is Empty");
    }
    return (List<IexQuote>) marketDataDao.findAllById(tickers);
  }

  //QuoteDao implementation
  /**
   * Update quote table against IEX source
   * - get all quotes from the db
   * - foreach ticker get iexQuote
   * - convert iexQuote to quote entity
   * - persist quote to db
   *
   * @throws IllegalStateException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void updateMarketData() {
    List<Quote> quoteList = findAllQuotes();
    for(Quote quoteIterator : quoteList) {
      IexQuote iexQuote = findIexQuoteByTicker(quoteIterator.getId());
      Quote quote = buildQuoteFromIexQuote(iexQuote);
      saveQuote(quote);
    }
  }

  /**
   * Helper method. Map a IEXQuote to a Quote entity.
   * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
   * Default values are set for fields that are missing.
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setId(iexQuote.getSymbol());
    quote.setTicker(iexQuote.getSymbol());
    quote.setLastPrice(iexQuote.getLatestPrice());
    quote.setAskPrice(Double.valueOf(iexQuote.getIexAskPrice()));
    quote.setBidPrice(Double.valueOf(iexQuote.getIexBidPrice()));
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setAskSize(iexQuote.getIexAskSize());
    return quote;
  }

  /**
   * Validate against IEX and save given tickers to quote table.
   *
   * - Get IexQuote(s)
   * - convert each IexQuote to Quote entity
   * - Persist the quote to db
   *
   * @param tickers list of tickers/symbols
   * @return
   */
  public List<Quote> saveQuotes(List<String> tickers) {
    return null;
  }

  /**
   * helper function
   */
  public Quote saveQuote(String ticker) {
    IexQuote iexQuote = findIexQuoteByTicker(ticker);
    return saveQuote(buildQuoteFromIexQuote(iexQuote));
  }

  /**
   * Update a given Quote to quote table without Validation
   * @param quote
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  /**
   * Find All Quotes from Quote Table
   * @return
   */
  public List<Quote> findAllQuotes() {
    return (List<Quote>) quoteDao.findAll();
  }
}