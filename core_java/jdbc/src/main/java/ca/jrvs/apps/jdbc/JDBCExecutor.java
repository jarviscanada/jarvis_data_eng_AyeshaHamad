package ca.jrvs.apps.jdbc;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    JDBCExecutor jdbc = new JDBCExecutor();
    jdbc.logger.debug("Hello, Learning JDBC");
  }
}
