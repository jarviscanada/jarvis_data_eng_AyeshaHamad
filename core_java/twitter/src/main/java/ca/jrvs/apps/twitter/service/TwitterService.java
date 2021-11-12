package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.Log;
import com.fasterxml.jackson.core.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;
  private static final int TEXT_MAX_LENGTH = 140;
  private static final int ID_MAX_LENGTH = 19;
  private static final float LAT_MAX = 90F;
  private static final float LAT_MIN = -90F;
  private static final float LONG_MAX = 180F;
  private static final float LONG_MIN = -180F;
  private String[] jsonFields = new String[]{"created_at", "id", "id_str", "text", "entities",
      "coordinates",
      "retweet_count", "favorite_count", "favorited", "retweeted"};

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
    validateFields(fields);
    Tweet tweet = (Tweet) dao.findById(id);
    markNullTweetFields(tweet, fields);
    return tweet;
  }

  private void markNullTweetFields(Tweet tweet, String[] fields) {
    HashSet<String> setNullFields = new HashSet<>();
    List list = Arrays.stream(fields).map(s -> s.toLowerCase()).collect(Collectors.toList());
    HashSet<String> fieldSet = new HashSet<>(list);

    //collect null fields
    for (String field : jsonFields) {
      if (!fieldSet.contains(field)) {
        setNullFields.add(field);
      }
    }
    //check if user entered fields are already null/empty
    //if its null display original tweet object
    boolean flag = false;
    for (String field : fieldSet) {
      if (isAttributeNull(tweet, field) == true) {
        flag = true;
      }
    }
    if (flag == false) {
      //replace null fields to null
      for (String field : setNullFields) {
        switch (field.toLowerCase()) {
          case "created_at":
            tweet.setCreated_at(null);
            break;
          case "id":
            tweet.setId(null);
            break;
          case "id_str":
            tweet.setId_str(null);
            break;
          case "text":
            tweet.setText(null);
            break;
          case "entities":
            tweet.setEntitles(null);
            break;
          case "coordinates":
            tweet.setCoordinates(null);
            break;
          case "retweet_count":
            tweet.setRetweet_count(null);
            break;
          case "favorite_count":
            tweet.setFavorite_count(null);
            break;
          case "favorited":
            tweet.setFavorited(null);
            break;
          case "retweeted":
            tweet.setRetweeted(null);
            break;
        }
      }
    }
  }

  private boolean isAttributeNull(Tweet tweet, String field) {

    boolean flag = false;
    switch (field) {
      case "created_at":
        if (tweet.getCreated_at().length() == 0) {
          flag = true;
        }
        break;
      case "id":
        if (tweet.getId().toString().length() == 0) {
          flag = true;
        }
        break;
      case "id_str":
        if (tweet.getId_str().length() == 0) {
          flag = true;
        }
        break;
      case "text":
        if (tweet.getText().length() == 0) {
          flag = true;
        }
        break;
      case "entities":
        if (tweet.getEntitles().getHashtags().size() == 0 &&
            tweet.getEntitles().getUser_mentions().size() == 0) {
          flag = true;
        }
        break;
      case "coordinates":
        if (tweet.getCoordinates().getCoordinates().length == 0) {
          flag = true;
        }
        break;
      case "retweet_count":
        if (tweet.getRetweet_count() == 0) {
          flag = true;
        }
        break;
      case "favorite_count":
        if (tweet.getFavorite_count() == 0) {
          flag = true;
        }
        break;
      case "favorited":
        if (tweet.isFavorited() == null) {
          flag = true;
        }
        break;
      case "retweeted":
        if (tweet.isRetweeted() == null) {
          flag = true;
        }
        break;
    }
    return flag;
  }

  private void validateFields(String[] fields) {
    if (fields != null) {
      Arrays.sort(jsonFields);
      for (String field : fields) {
        if (Arrays.binarySearch(jsonFields, field.toLowerCase()) < 0) {
          Log.logger.error("Invalid field(s)");
          throw new IllegalArgumentException();
        }
      }
    }
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> deletedList = new ArrayList<>();
    for (String id : ids) {
      validateID(id);
      deletedList.add((Tweet) dao.deleteById(id));
    }
    return deletedList;
  }

  private void validatePostTweet(Tweet tweet) {
    float lon = tweet.getCoordinates().getCoordinates()[0];
    float lat = tweet.getCoordinates().getCoordinates()[1];

    if (tweet.getText().length() > TEXT_MAX_LENGTH) {
      throw new IllegalArgumentException("Invalid text input!");
    }

    if (!(lon >= LONG_MIN && lon <= LONG_MAX)) {
      throw new IllegalArgumentException("The longitude is out of range!");
    }

    if (!(lat >= LAT_MIN && lat <= LAT_MAX)) {
      throw new IllegalArgumentException("The latitude is out of range!");
    }

  }

  private void validateID(String id) {
    if (!(id.matches("^[0-9]*$"))) {
      throw new IllegalArgumentException("id should only consist of digits!");
    }
    if (id.length() < ID_MAX_LENGTH) {
      throw new IllegalArgumentException("id length out of range!");
    }

  }
}
