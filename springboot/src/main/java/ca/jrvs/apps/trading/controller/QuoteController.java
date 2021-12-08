package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  private QuoteService quoteService;

  @Autowired
  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @GetMapping(path = "/iex/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ApiOperation(value = "Show IexQuote")
  public IexQuote getQuotes(@PathVariable String ticker) {
    try {
      return quoteService.findIexQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @GetMapping(path = "/iex/tickers/{tickers}" )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ApiOperation("Show multiple IexQuotes")
  public List<IexQuote> findAllIexQuoteByTicker(@PathVariable String[] tickers) {
    try {
      return quoteService.findAllIexQuoteByTicker(Arrays.asList(tickers));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
