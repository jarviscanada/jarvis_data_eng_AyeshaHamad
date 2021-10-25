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
    JDBCExecutor.logger.debug("Hello, Learning JDBC");

    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost",
        "hplussport",
        "postgres",
        "password");
    try{
      Connection connection = dcm.getConnection();

      //jdbc.findCustomerByIdExample(connection);
      jdbc.updateCustomerExample(connection);
      //jdbc.createCustomerExample(connection);
      //jdbc.deleteCustomerExample(connection);

    }catch (SQLException e){
      JDBCExecutor.logger.debug("Unable to create connection " + e);
    }
  }

  /**
   * Simple function for displaying table without DTO and DAO implementation
   * @param connection  : db connection
   * @throws SQLException : SQL Exception
   */
  private void displayDataFromCustomerTableExample(Connection connection) throws SQLException{
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
    while (resultSet.next()){
      logger.debug("Total count = " + resultSet.getInt(1) );
    }
  }

  /**
   * Inserting into database (INSERT CLAUSE)
   * @param connection : db connection
   */
  private void createCustomerExample(Connection connection){
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
  }

  /**
   * Reading database (SELECT CLAUSE)
   * @param connection : db connection
   */
  private void findCustomerByIdExample(Connection connection){
    CustomerDAO customerDAO = new CustomerDAO(connection);
    Customer customer = customerDAO.findById(10007);
    logger.debug("Result from database\n" + customer.getFirstName() + " " + customer.getLastName());
  }

  /**
   * Update Customer (UPDATE CLAUSE)
   * @param connection : db connection
   */
  private void updateCustomerExample(Connection connection){
    CustomerDAO customerDAO = new CustomerDAO(connection);
    Customer customer = customerDAO.findById(10006);
    logger.debug("Result from database that you want to modify\n" + customer.getFirstName() + " " + customer.getLastName());
    customer.setFirstName("Maaida");
    customer = customerDAO.update(customer);
    logger.debug("Result from database after modification\n" + customer.getFirstName() + " " + customer.getLastName());
  }

  /**
   * Delate Customer (DELETE CLAUSE)
   * @param connection : db connection
   */
  private void deleteCustomerExample(Connection connection){
    CustomerDAO customerDAO = new CustomerDAO(connection);
    customerDAO.delete(10004);
  }
}
