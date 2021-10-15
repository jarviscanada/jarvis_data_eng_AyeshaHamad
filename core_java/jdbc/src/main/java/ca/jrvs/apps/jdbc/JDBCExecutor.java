package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    JDBCExecutor jdbc = new JDBCExecutor();
    jdbc.logger.debug("Hello, Learning JDBC");

    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost",
        "hplussport",
        "postgres",
        "password");
    try{
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      //5deleting
      Customer customer = new Customer();
      customerDAO.delete(10001);

      //4updating
      /*
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(10000);
      logger.debug("Result from database that you want to modify\n" + customer.getFirstName() + " " + customer.getLastName());
      customer.setLastName("Hamad");
      customer = customerDAO.update(customer);
      logger.debug("Result from database after modification\n" + customer.getFirstName() + " " + customer.getLastName());
      */

      //3reading database
      /*
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(10000);
      logger.debug("REsult from database\n" + customer.getFirstName() + " " + customer.getLastName());
      */

      //2inserting database
      /*
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = new Customer();
      customer.setFirstName("Hamad");
      customer.setLastName("Asif");
      customer.setEmail("ayesha@gmail.com");
      customer.setPhone("204-000-1234");
      customer.setAddress("1228 winnipeg");
      customer.setCity("Winnipeg");
      customer.setState("MB");
      customer.setZipCode("123123");
      customerDAO.create(customer);
      */

      //1
      /*
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
      while (resultSet.next()){
        jdbc.logger.debug("Total count = " + resultSet.getInt(1) );
      }
      */
    }catch (SQLException e){
      jdbc.logger.debug("Unable to create connection " + e);
    }
  }
}
