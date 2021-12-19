package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader>{

  private static Logger logger = LoggerFactory.getLogger(TraderDao.class);
  private static final String TABLE_NAME = "trader";
  private static final String ID_COLUMN_NAME = "id";
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public TraderDao(DataSource dataSource) {
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
  public Class<Trader> getEntityClass() {
    return Trader.class;
  }

  private Object[] makeUpdateValues (Trader entity) {
    Object[] objects = {
        entity.getFirstName(),
        entity.getLastName(),
        entity.getDob(),
        entity.getCountry(),
        entity.getEmail(),
        entity.getId()
    };
    return objects;
  }

  // not implemented yet
  @Override
  public Iterable<Trader> findAllById(Iterable<Integer> iterable) {
    return null;
  }

  //following are not implemented
  @Override
  public <S extends Trader> int updateOne(S entity) {
    /*String sql = "UPDATE " + TABLE_NAME + " SET "
        + " first_name=?, last_name=?, dob=?, country=?, email=? WHERE " + ID_COLUMN_NAME + "=?";
    return jdbcTemplate.update(sql, makeUpdateValues(entity));*/
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public void deleteAll(Iterable<? extends Trader> iterable) {
    throw new UnsupportedOperationException("Not implemented.");
  }
}
