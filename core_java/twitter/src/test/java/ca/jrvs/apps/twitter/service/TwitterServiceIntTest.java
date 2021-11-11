package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
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

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET + "\n" + ACCESS_TOKEN);

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);

    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(this.dao);
  }

  @Test
  public void postTweet() {
    String hashTag = "#abc #test";
    String text = "@Hamad @Jon Testing - API " + hashTag ;
    Float lat = 49.706486f;
    Float lon = -89.992193f;
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");
    Tweet postTweet = new Tweet(text, coordinates);
    System.out.println("post tweet object ");
    try {
      System.out.println(JsonParser.toJson(postTweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Tweet tweet = service.postTweet(postTweet);

    System.out.println("response ");
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(tweet.getText());
    System.out.println(tweet.getCoordinates().getCoordinates().length);
    System.out.println(tweet.getCoordinates().getCoordinates()[0]);
    System.out.println(tweet.getCoordinates().getCoordinates()[1]);
  }

  @Test
  public void showTweet() {
    String id = "1457261968285372418";
    Tweet tweet = service.showTweet(id,null);
    System.out.println("response for reading a tweet by id");
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void deleteTweets() {
    String[] id = {"1457580135956221956"};
    List<Tweet> deletedList = service.deleteTweets(id);

    System.out.println("response for deleting tweet ");
    try {
      for(Tweet tweet: deletedList){
        System.out.println(JsonParser.toJson(tweet, true, false));
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}