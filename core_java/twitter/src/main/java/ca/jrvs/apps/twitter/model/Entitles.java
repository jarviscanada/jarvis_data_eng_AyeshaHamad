package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entitles {

  private List<HashTag> hashTags;
  private List<UserMention> userMentions;

  public List<HashTag> getHashTags() {
    return hashTags;
  }

  public void setHashTags(List<HashTag> hashTags) {
    this.hashTags = hashTags;
  }

  public List<UserMention> getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(List<UserMention> userMentions) {
    this.userMentions = userMentions;
  }
}
