package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
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
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String ACCESS_SECRET = System.getenv("accessSecret");

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET + "\n" + ACCESS_TOKEN);

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);

    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(this.dao);
    this.controller = new TwitterController(this.service);

  }

  @Test
  public void postTweet() {
    String arg = "post Tweet_Text 40:50";
    String []args = arg.split(" ");
    Tweet tweet = controller.postTweet(args);

    System.out.println("response ");
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void showTweet() {//1457587199310970880
    String arg = "show 1457589082746413057";
    String []args = arg.split(" ");

    Tweet tweet = controller.showTweet(args);

    System.out.println("response ");
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void deleteTweet() {
    String arg = "delete 1457589082746413057";
    String []args = arg.split(" ");

    List<Tweet> tweetList = controller.deleteTweet(args);

    System.out.println("response ");
    try {
      for(Tweet tweet: tweetList) {
        System.out.println(JsonParser.toJson(tweet, true, false));
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}