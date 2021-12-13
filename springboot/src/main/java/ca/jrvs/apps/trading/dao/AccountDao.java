package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account>{

  private static Logger logger = LoggerFactory.getLogger(AccountDao.class);
  private static final String TABLE_NAME = "account";
  private static final String ID_COLUMN_NAME = "id";
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN_NAME);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIDColumnName() {
    return ID_COLUMN_NAME;
  }

  @Override
  public Class<Account> getEntityClass() {
    return Account.class;
  }

  @Override
  public <S extends Account> int updateOne(S entity) {
    String sql = "UPDATE " + TABLE_NAME + " SET "
        + " trader_id=?, amount=? WHERE " + ID_COLUMN_NAME + "=?";
    return jdbcTemplate.update(sql, makeUpdateValues(entity));
  }

  private Object[] makeUpdateValues (Account entity) {
    Object[] objects = {
        entity.getTraderId(),
        entity.getAmount(),
        entity.getId()
    };
    return objects;
  }

  @Override
  public Iterable<Account> findAllById(Iterable<Integer> iterable) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> iterable) {
    throw new UnsupportedOperationException("Not implemented.");
  }
}
