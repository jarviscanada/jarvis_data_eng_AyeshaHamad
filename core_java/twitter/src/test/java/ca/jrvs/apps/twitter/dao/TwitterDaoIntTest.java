package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;
  private String testId = "1458710115889164296";
  @Before
  public void setUp() throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String ACCESS_SECRET = System.getenv("accessSecret");

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n"
        + ACCESS_SECRET + "\n" + ACCESS_TOKEN);

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);
    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    Tweet postTweet = new Tweet();
    String hashTag = "#abc #test";
    String text = "Testing Twitter DAO @Test " + hashTag + System.currentTimeMillis();

    Float lat = 49.706486f;
    Float lon = -86.992193f;
    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon, lat});
    coordinates.setType("Point");

    postTweet.setText(text);
    postTweet.setCoordinates(coordinates);

    Tweet tweet = dao.create(postTweet);
    testId = tweet.getId_str();
    System.out.println(testId);
    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2,tweet.getCoordinates().getCoordinates().length);
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0]);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1]);
  }

  @Test
  public void findById() {
    String id = testId;
    System.out.println(testId);
    Tweet tweet = dao.findById(id);

    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2,tweet.getCoordinates().getCoordinates().length);

  }

  @Test
  public void deleteById() {
    String id = testId;
    System.out.println(testId);
    Tweet tweet = dao.deleteById(id);

    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2,tweet.getCoordinates().getCoordinates().length);
  }
}