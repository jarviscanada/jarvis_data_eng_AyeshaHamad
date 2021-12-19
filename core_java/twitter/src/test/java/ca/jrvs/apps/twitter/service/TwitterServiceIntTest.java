package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  private TwitterDao dao;
  private TwitterService service;

  @Before
  public void setUp() throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String ACCESS_SECRET = System.getenv("accessSecret");

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET
        + "\n" + ACCESS_TOKEN);

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);
    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(this.dao);
  }

  @Test
  public void postTweet() {
    String hashTag = "#abc #test";
    String text = "Testing Twitter Service @Test " + hashTag + System.currentTimeMillis();

    Float lat = 49.706486f;
    Float lon = -86.992193f;
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon, lat});
    //coordinates.setType("Point");

    Tweet postTweet = new Tweet(text, coordinates);
    Tweet responseTweet = service.postTweet(postTweet);

    assertEquals(text, responseTweet.getText());
    assertNotNull(responseTweet.getCoordinates());
    assertEquals(2,responseTweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, responseTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, responseTweet.getCoordinates().getCoordinates()[1]);

    try {
      Log.logger.info(JsonParser.toJson(responseTweet, true, false));
    } catch (JsonProcessingException e) {
      Log.logger.error("Unable to parse Json string", e);
    }
  }

  @Test
  public void showTweet() {
    String id = "1459279723159330825";
    String[] fields = new String[]{"id", "created_at", "text"};
    Tweet responseTweet = service.showTweet(id, fields);

    try {
      Log.logger.info(JsonParser.toJson(responseTweet, true, false));
    } catch (JsonProcessingException e) {
      Log.logger.error("Unable to parse Json string", e);
    }

    assertNotNull(responseTweet.getText());
    assertNotNull(responseTweet.getCreated_at());
    assertNotNull(responseTweet.getId());
    assertEquals(id,responseTweet.getId().toString());

  }

  @Test
  public void deleteTweets() {
    String[] id = {"1459282658148687880"};
    List<Tweet> deletedList = service.deleteTweets(id);

    try {
      for(Tweet tweet: deletedList){
        Log.logger.info(JsonParser.toJson(tweet, true, false));
      }
    } catch (JsonProcessingException e) {
      Log.logger.error("Unable to parse Json string", e);
    }

    assertNotNull(deletedList.get(0).getText());
    assertNotNull(deletedList.get(0).getCoordinates());
    assertEquals(id[0],deletedList.get(0).getId_str());
    assertEquals(2,deletedList.get(0).getCoordinates().getCoordinates().length);
  }
}