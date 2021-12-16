package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder>{

  private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
  private static final String TABLE_NAME = "security_order";
  private static final String ID_COLUMN_NAME = "id";
  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
        withTableName(TABLE_NAME).
        usingGeneratedKeyColumns(ID_COLUMN_NAME);
    logger.info("init Security Dao");
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
  public Class getEntityClass() {
    return SecurityOrder.class;
  }

  //check it // test it
  @Override
  public <S extends SecurityOrder> int updateOne(S entity) {
    String sql = "UPDATE " + TABLE_NAME + " SET "
        + "account_id=?, status=?, ticker=?, size=?, price=?, notes=? WHERE " + ID_COLUMN_NAME + "=?";
    return jdbcTemplate.update(sql, makeUpdateValues(entity));

  }

  private Object[] makeUpdateValues (SecurityOrder entity) {
    return new Object[]{
        entity.getAccountId(),
        entity.getStatus(),
        entity.getTicker(),
        entity.getSize(),
        entity.getPrice(),
        entity.getNotes(),
        entity.getId()
    };
  }

  @Override
  public Iterable<SecurityOrder> findAllById(Iterable<Integer> iterable) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  @Override
  public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
    throw new UnsupportedOperationException("Not implemented.");
  }
}
