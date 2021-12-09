package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
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


}