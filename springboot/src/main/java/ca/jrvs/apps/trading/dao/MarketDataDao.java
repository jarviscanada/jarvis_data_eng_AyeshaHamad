package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Market DAO is responsible for getting quotes from IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private String IEX_BATCH_URL;
  private final Integer HTTP_OK = 200;
  private final Integer HTTP_REQUEST_NOT_FOUND = 404;

  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager manager, MarketDataConfig config) {
    this.httpClientConnectionManager = manager;
    IEX_BATCH_URL = config.getHost() + IEX_BATCH_PATH + config.getToken();
  }

  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuoteOptional;
    List<IexQuote> iexQuoteList = (List<IexQuote>) findAllById(Collections.singletonList(ticker));

    if(iexQuoteList.size() == 0) {
      return Optional.empty();
    } else if (iexQuoteList.size() == 1) {
      iexQuoteOptional = Optional.of(iexQuoteList.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuoteOptional;
  }

  @Override
  public Iterable<IexQuote> findAllById(Iterable<String> tickers) {

    if(Iterables.size(tickers) == 0) {
      throw new IllegalArgumentException("Ticker Input is Empty");
    }

    String uri = String.format(IEX_BATCH_URL, String.join(",", tickers));

    //HTTP response
    String response = executeHttpGet(uri).orElseThrow(() -> new IllegalArgumentException("Invalid ticker"));

    //Array of JSON documents
    JSONObject IexQuotesJson = new JSONObject(response);

    //Get number of documents
    if (IexQuotesJson.length() == 0) {
      throw new IllegalArgumentException("Invalid ticker");
    }

    //now deserialize json using JSONParser
    List<IexQuote> iexQuoteList = new ArrayList<>(Iterables.size(tickers));
    for(String ticker : tickers) {
      if (IexQuotesJson.has(ticker)) {
        try {
          iexQuoteList.add(JsonParser.toObjectFromJson(IexQuotesJson.getJSONObject(ticker).get("quote").toString(), IexQuote.class));
        } catch (IOException e) {
          logger.error("Unable to parse JsonObject", e);
        }
      }
    }

    return iexQuoteList;
  }

  private Optional<String> executeHttpGet(String url) {
    CloseableHttpClient httpClient = getHttpClient();
    HttpGet httpRequest = new HttpGet(url);
    HttpResponse httpResponse = null;
    try {
      httpResponse = httpClient.execute(httpRequest);
    } catch (IOException e) {
      logger.error("Unable to execute http request", e);
    }
    return parseResponseBody(httpResponse, HTTP_OK);
  }

  private Optional<String> parseResponseBody(HttpResponse response, Integer expectedStatusCode) {

    //check response code
    int statusCode = response.getStatusLine().getStatusCode();
    if( statusCode == HTTP_REQUEST_NOT_FOUND ) {
      return Optional.empty();
    }
    else if( statusCode != HTTP_OK) {
      throw new DataRetrievalFailureException("Data not retrieved");
    }

    if(response.getEntity() == null) {
      throw new RuntimeException("Empty Response Body");
    }

    //Convert response Entity to String
    String jsonString = null;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      logger.error("Response has no Entity ", e);
    }

    return Optional.of(jsonString);
  }

  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
