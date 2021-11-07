package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;
  @Before
  public void setUp() throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String ACCESS_SECRET = System.getenv("accessSecret");

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET + "\n" + ACCESS_TOKEN);

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);

    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    Tweet postTweet = new Tweet();
    String hashTag = "#abc #test";
    String text = "@Hamad @Jon Testing Twitter - - API " + hashTag ;
    Float lat = 49.706486f;
    Float lon = -96.992193f;
    //Double [] coord = new Double[]{lat, lon};
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lat, lon});
    coordinates.setType("Point");
    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);
    System.out.println("post tweet object ");
    try {
      System.out.println(JsonParser.toJson(postTweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Tweet tweet = dao.create(postTweet);

    System.out.println("response ");
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(tweet.getText());
    /*System.out.println(tweet.getCoordinates().getCoordinates().length);
    System.out.println(tweet.getCoordinates().getCoordinates()[0]);
    System.out.println(tweet.getCoordinates().getCoordinates()[1]);*/
  }

  @Test
  public void findById() {
  }

  @Test
  public void deleteById() {
  }
}