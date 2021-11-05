package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class TwitterApiTest {

  private static String CONSUMER_KEY = System.getenv("consumerKey");
  private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static String ACCESS_TOKEN = System.getenv("accessToken");
  private static String ACCESS_SECRET = System.getenv("accessSecret");

  public static void main(String[] args) {

    System.out.println("Twitter app");

    //set oauth with keys
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_SECRET);

    //http post request
    String status = "Its a lovely day";
    PercentEscaper escaper = new PercentEscaper("", false);
    HttpPost request = new HttpPost(
        "https://api.twitter.com/1.1/statuses/update.json?status=" + escaper.escape(status));

    //sign the request
    try {
      consumer.sign(request);
    } catch (OAuthMessageSignerException e) {
      e.printStackTrace();
    } catch (OAuthExpectationFailedException e) {
      e.printStackTrace();
    } catch (OAuthCommunicationException e) {
      e.printStackTrace();
    }

    //print header
    System.out.println("Header of the Http POst request");
    Arrays.stream(request.getAllHeaders()).forEach(System.out::println);
    System.out.println(request.getURI().toString());

    //send request
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpResponse httpResponse = null;
    try {
      httpResponse = httpClient.execute(request);
      System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
