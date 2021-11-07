package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterDao implements CrdDao<Tweet, String>{

  //URI constants
  //post https://api.twitter.com/1.1/statuses/update.json?status=Delete this tweet
  //read https://api.twitter.com/1.1/statuses/show.json?id=1456875054395019265
  //read all https://api.twitter.com/1.1/statuses/user_timeline.json?user_id=1445786203182153736
  //del https://api.twitter.com/1.1/statuses/destroy/1453391600940306434.json
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DEL_PATH = "/1.1/statuses/destroy";

  //URI symbols
  private static final String QUERY_SYMBOL = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

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

    return null;
  }

  @Override
  public Tweet deleteById(String s) {
    return null;
  }

  private URI getPostURI(Tweet entity){
    PercentEscaper escaper = new PercentEscaper("", false);
    String status = escaper.escape( entity.getText() );
    String uri_string = API_BASE_URI + POST_PATH + QUERY_SYMBOL + "status" + EQUAL + status
        + AMPERSAND + "lat" + EQUAL + entity.getCoordinates().getCoordinates()[0]
        + AMPERSAND + "long" + EQUAL + entity.getCoordinates().getCoordinates()[1];
    System.out.println(uri_string);
    URI uri = null;
    try {
      uri = new URI(uri_string);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to create URI - Invalid Tweet Input " + e);
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
      throw new RuntimeException("Empty Response Body ");
    }

    //Convert response Entity to String
    String jsonString = null;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      System.out.println("Response has no Entity " + e);
    }

    Tweet tweet = null;
    try {
      tweet = JsonParser.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      System.out.println("String to convert is : " + jsonString);
      System.out.println("Unable to convert JSON string to Object" + e);
    }

    return tweet;
  }
}
