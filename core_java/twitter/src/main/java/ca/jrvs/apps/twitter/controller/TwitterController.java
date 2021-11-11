package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

  private Service service;

  private static final String COOR_SEP = ":";
  private static final String COMA = ",";

  @Autowired
  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"Tweet_Text\" \"latitude:longitude\"");
    }

    String tweet_text = args[1];
    String coord = args[2];
    String[] coordArray = coord.split(COOR_SEP);
    if(tweet_text.length() == 0  || coordArray.length != 2){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"Tweet_Text\" \"latitude:longitude\"");
    }

    Float lat = Float.parseFloat(coordArray[0]);
    Float lon = Float.parseFloat(coordArray[1]);

    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new Float[]{lon,lat});

    Tweet postTweet = new Tweet(tweet_text, coordinates);
    return service.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length < 2 || args.length > 3 || args[1].length() == 0) {
      throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"[field1,fields2,...]\"");
    }
    String id = args[1];
    String field ;
    String[] fieldList = null;
    if(args.length == 3) {
      field = args[2];
      fieldList = field.split(COMA);
    }

    return service.showTweet(id,fieldList);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[id1,id2,..]\"");
    }
    String id = args[1];
    String[] ids = id.split(COMA);

    return service.deleteTweets(ids);
  }
}
