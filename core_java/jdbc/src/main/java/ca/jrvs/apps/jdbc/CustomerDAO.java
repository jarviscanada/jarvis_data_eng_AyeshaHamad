package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {

  private static final String INSERT = "INSERT INTO customer ("
                                                      + "first_name, "
                                                      + "last_name, "
                                                      + "email, "
                                                      + "phone, "
                                                      + "address, "
                                                      + "city, "
                                                      + "state, "
                                                      + "zipCode) "
                                                      + "VALUES (?,?,?,?,?,?,?,?)";

  private static final String GET_ONE = "SELECT "
                                              + "customer_id, "
                                              + "first_name, "
                                              + "last_name, "
                                              + "email, "
                                              + "phone, "
                                              + "address, "
                                              + "city, "
                                              + "state, "
                                              + "zipCode "
                                        + "FROM customer "
                                        + "WHERE customer_id=?";

  private static final String UPDATE = "UPDATE customer SET first_name = ?, last_name = ?, "
      + " email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

  private static final String DELETE = "DELETE FROM customer WHERE customer_id = ?";

  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(long id) {
    Customer customer = new Customer();
    try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE)){
      statement.setLong(1,id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        customer.setId(resultSet.getLong("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        customer.setAddress(resultSet.getString("address"));
        customer.setCity(resultSet.getString("city"));
        customer.setState(resultSet.getString("state"));
        customer.setZipCode(resultSet.getString("zipcode"));
        JDBCExecutor.logger.debug("Data read from table successfully");
      }
    }catch (SQLException e){
      JDBCExecutor.logger.debug("Unable to read from database " + e);
    }
    return customer;
  }

  @Override
  public List<Customer> findAll() {
    return null;
  }

  @Override
  public Customer update(Customer dto) {
    setAutoCommit(false);
    Customer customer = null;
    try(PreparedStatement statement = this.connection.prepareStatement(UPDATE)){
      statement.setString(1,dto.getFirstName());
      statement.setString(2,dto.getLastName());
      statement.setString(3,dto.getEmail());
      statement.setString(4,dto.getPhone());
      statement.setString(5,dto.getAddress());
      statement.setString(6,dto.getCity());
      statement.setString(7,dto.getState());
      statement.setString(8,dto.getZipCode());
      statement.setLong(9, dto.getId());
      statement.execute();

      customer = this.findById(dto.getId());
      commit();

      JDBCExecutor.logger.debug("Updating record successfully");

    }catch(SQLException e){
      rollback();
      JDBCExecutor.logger.debug("Unable to update record " + e);
    }

    return customer;
  }

  @Override
  public Customer create(Customer dto) {
    try(PreparedStatement statement = this.connection.prepareStatement(INSERT);){
      statement.setString(1,dto.getFirstName());
      statement.setString(2,dto.getLastName());
      statement.setString(3,dto.getEmail());
      statement.setString(4,dto.getPhone());
      statement.setString(5,dto.getAddress());
      statement.setString(6,dto.getCity());
      statement.setString(7,dto.getState());
      statement.setString(8,dto.getZipCode());
      statement.execute();

      JDBCExecutor.logger.debug("Data inserted successfully with no exception");

      int id = this.getLastVal(CUSTOMER_SEQUENCE);
      return this.findById(id);

    }catch (SQLException e){
      JDBCExecutor.logger.debug("Unable to insert into database " + e);
    }
    return null;
  }

  @Override
  public void delete(long id) {
    try(PreparedStatement statement = this.connection.prepareStatement(DELETE)){
      statement.setLong(1,id);
      int verifyDelete = statement.executeUpdate();
      if(verifyDelete != 0)
      JDBCExecutor.logger.debug("Record deleted successfully. Id = " + id);
      else
        JDBCExecutor.logger.debug("Unable to delete record with Id = " + id);
    }catch (SQLException e){
      JDBCExecutor.logger.debug("Unable to delete record " + e);
    }
  }

  private void commit(){
    try {
      this.connection.commit();
      JDBCExecutor.logger.debug("CustomerDAO: Data commit is done successfully.");
    }catch (SQLException e){
      JDBCExecutor.logger.debug("CustomerDAO: Unable to commit data. " + e);
    }
  }

  private void rollback(){
    try{
      this.connection.rollback();
      JDBCExecutor.logger.debug("CustomerDAO: Data rollback is done successfully.");
    }catch (SQLException e){
      JDBCExecutor.logger.debug("CustomerDAO: Unable to rollback data. " + e);
    }
  }

  private void setAutoCommit(boolean commit){
    try{
      this.connection.setAutoCommit(commit);
    }catch(SQLException e){
      JDBCExecutor.logger.debug("CustomerDAO: Auto commit = " + commit);
    }
  }

}
