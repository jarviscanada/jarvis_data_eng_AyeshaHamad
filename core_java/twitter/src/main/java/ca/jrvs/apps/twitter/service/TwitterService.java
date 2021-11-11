package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service{

  private CrdDao dao;
  private static final int TEXT_MAX_LENGTH = 140;
  private static final int ID_MAX_LENGTH = 19;
  private static final float LAT_MAX = 90F;
  private static final float LAT_MIN = -90F;
  private static final float LONG_MAX = 180F;
  private static final float LONG_MIN = -180F;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateID(id);
    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedList = new ArrayList<>();
    for(String id : ids){
      validateID(id);
      deletedList.add((Tweet) dao.deleteById(id));
    }
    return deletedList;
  }

  private void validatePostTweet(Tweet tweet){
    float lon = tweet.getCoordinates().getCoordinates()[0];
    float lat = tweet.getCoordinates().getCoordinates()[1];

    if(tweet.getText().length() > TEXT_MAX_LENGTH) {
      throw new IllegalArgumentException("Invalid text input!");
    }

    if( !(lon >= LONG_MIN && lon <= LONG_MAX) ) {
      throw new IllegalArgumentException("The longitude is out of range!");
    }

    if( !(lat >= LAT_MIN && lat <= LAT_MAX) ) {
      throw new IllegalArgumentException("The latitude is out of range!");
    }

  }

  private void validateID(String id){
    if ( !(id.matches("^[0-9]*$")) ) {
      throw new IllegalArgumentException("id should only consist of digits!");
    }
    if (id.length() < ID_MAX_LENGTH) {
      throw new IllegalArgumentException("id length out of range!");
    }

  }
}
