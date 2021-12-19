package ca.jrvs.apps.twitter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Log {

  public static final Logger logger = LoggerFactory.getLogger(Log.class);

  @Autowired
  public Log() {
  }

}
