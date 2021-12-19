package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
    "symbols",
    "urls"
})
public class Entities {

  private List<HashTag> hashtags;
  private List<UserMention> user_mentions;

  public List<HashTag> getHashtags() {
    return hashtags;
  }

  public void setHashtags(List<HashTag> hashtags) {
    this.hashtags = hashtags;
  }

  public List<UserMention> getUser_mentions() {
    return user_mentions;
  }

  public void setUser_mentions(List<UserMention> user_mentions) {
    this.user_mentions = user_mentions;
  }
}
