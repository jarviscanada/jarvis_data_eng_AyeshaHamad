package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  private Controller controller;
  private TwitterDao dao;
  private TwitterService service;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String accessSecret = System.getenv("accessSecret");

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,accessToken, accessSecret);
    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(this.dao);
    this.controller = new TwitterController(this.service);
  }

  @Test
  public void postTweet() {
    String text = "Tweet_Text_" +System.currentTimeMillis();
    String arg = "post " + text + " 40:50";
    String []args = arg.split(" ");
    String[] coord =  args[2].split(":");
    Float lat = Float.parseFloat(coord[0]);
    Float lon = Float.parseFloat(coord[1]);
    Tweet responseTweet = controller.postTweet(args);

    assertEquals(args[1], responseTweet.getText());
    assertNotNull(responseTweet.getCoordinates());
    assertEquals(2,responseTweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, responseTweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, responseTweet.getCoordinates().getCoordinates()[1]);
  }

  @Test
  public void showTweet() {
    String arg = "show 1459279723159330825 text,coordinateS";
    String []args = arg.split(" ");
    Tweet responseTweet = controller.showTweet(args);

    try {
      Log.logger.info(JsonParser.toJson(responseTweet, true, false));
    } catch (JsonProcessingException e) {
      Log.logger.error("Unable to parse Json string", e);
    }

    assertNotNull(responseTweet.getText());
    assertNull(responseTweet.getCreated_at());
    assertNull(responseTweet.getId());
  }

  @Test
  public void deleteTweet() {
    String arg = "delete 1457589082746413057";
    String []args = arg.split(" ");

    List<Tweet> deletedList = controller.deleteTweet(args);

    try {
      for(Tweet tweet: deletedList){
        Log.logger.info(JsonParser.toJson(tweet, true, false));
      }
    } catch (JsonProcessingException e) {
      Log.logger.error("Unable to parse Json string", e);
    }

    assertNotNull(deletedList.get(0).getText());
    assertNotNull(deletedList.get(0).getCoordinates());
    assertEquals(args[1],deletedList.get(0).getId_str());
    assertEquals(2,deletedList.get(0).getCoordinates().getCoordinates().length);
  }
}