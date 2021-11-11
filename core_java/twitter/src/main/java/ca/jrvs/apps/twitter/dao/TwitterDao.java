package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.Log;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Repository
public class TwitterDao implements CrdDao<Tweet, String>{

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DEL_PATH = "/1.1/statuses/destroy/id.json";

  //URI symbols
  private static final String QUERY_SYMBOL = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet entity) {
    URI uri = getPostURI(entity);
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet findById(String s) {
    URI uri = getReadURI(s);
    HttpResponse response = httpHelper.httpGet(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri = getDeleteURI(s);
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  private URI getReadURI(String id){
    String uri_string = API_BASE_URI + SHOW_PATH + QUERY_SYMBOL + "id" + EQUAL + id;
    URI uri;
    try {
      uri = new URI(uri_string);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to create URI - Invalid Tweet Input ", e);
    }
    return uri;
  }

  private URI getDeleteURI(String id){
    String uri_base = API_BASE_URI + DEL_PATH;
    String uri_string = uri_base.replace("id", id);
    URI uri;
    try {
      uri = new URI(uri_string);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to create URI - Invalid Tweet Input ", e);
    }
    return uri;
  }

  private URI getPostURI(Tweet entity){
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String status = percentEscaper.escape( entity.getText() );
    String uri_string = API_BASE_URI + POST_PATH + QUERY_SYMBOL + "status" + EQUAL + status
        + AMPERSAND + "long" + EQUAL + entity.getCoordinates().getCoordinates()[0]
        + AMPERSAND + "lat" + EQUAL + entity.getCoordinates().getCoordinates()[1];
    URI uri;
    try {
      uri = new URI(uri_string);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to create URI - Invalid Tweet Input ", e);
    }
    return uri;
  }

  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
    //check response code
    int statusCode = response.getStatusLine().getStatusCode();
    if( statusCode != expectedStatusCode ) {
      /*try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no Entity" + e);
      }*/
      throw new RuntimeException("Invalid Status Code " + statusCode);
    }

    if(response.getEntity() == null) {
      throw new RuntimeException("Empty Response Body");
    }

    //Convert response Entity to String
    String jsonString = null;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      Log.logger.error("Response has no Entity ", e);
    }

    Tweet tweet = null;
    try {
      tweet = JsonParser.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      Log.logger.error("Unable to convert JSON string to Object", e);
    }

    return tweet;
  }
}
