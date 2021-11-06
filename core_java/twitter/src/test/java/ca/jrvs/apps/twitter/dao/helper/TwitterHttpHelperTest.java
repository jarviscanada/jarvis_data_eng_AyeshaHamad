package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  @Test
  public void httpPost() throws URISyntaxException, IOException {

    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String ACCESS_SECRET = System.getenv("accessSecret");

    System.out.println("Keys\n" + CONSUMER_KEY + "\n" + CONSUMER_SECRET + "\n" + ACCESS_SECRET + "\n" + ACCESS_TOKEN);

    /*TwitterHttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET);
    String status = "Testing Twitter API ";
    PercentEscaper escaper = new PercentEscaper("", false);
    String uri = "https://api.twitter.com/1.1/statuses/update.json?status=" + escaper.escape(status);
    HttpResponse response = httpHelper.httpPost(new URI(uri));
    System.out.println(EntityUtils.toString(response.getEntity()));*/
  }

  @Test
  public void httpGet() {
  }
}