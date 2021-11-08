package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterCLIApp {

  private Controller controller;

  public static final String USAGE = "Usage: Util.TwitterCLIApp post|show|delete [options]";

  private static String CONSUMER_KEY = System.getenv("consumerKey");
  private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static String ACCESS_TOKEN = System.getenv("accessToken");
  private static String ACCESS_SECRET = System.getenv("accessSecret");

  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  //test this function
  public  static void main(String[] args) throws URISyntaxException, IOException {

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET + "\n" + ACCESS_TOKEN);
    TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);

    CrdDao dao = new TwitterDao(httpHelper);
    Service service = new TwitterService(dao);
    Controller controller = new TwitterController(service);
    TwitterCLIApp app = new TwitterCLIApp(controller);

    app.run(args);
  }

  private void run(String[] args){
    if (args.length == 0) {
      throw new IllegalArgumentException("Improper arguments!\n" + USAGE);
    }
    switch (args[0].toLowerCase()) {
      case "post":
        printTweet(controller.postTweet(args));
        break;

      case "show":
        printTweet(controller.showTweet(args));
        break;

      case "delete":
        controller.deleteTweet(args).forEach(this::printTweet);
        break;

      default:
        throw new IllegalArgumentException("Invalid usage\n" + USAGE);
    }
  }

  private void printTweet(Tweet tweet){
    try {
      System.out.println(JsonParser.toJson(tweet, true, false));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
