package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);
  private static final String TABLE_NAME = "position";
  private static final String ID_COLUMN_NAME = "account_id";
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Optional<Position> findById(Integer id) {

    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
    Position position = null;
    try {
      position = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Position.class), id);
    } catch(Exception e) {
      logger.error("Exception: Position record not found. Position id = " +id, e);
      return Optional.empty();
    }

    if(position == null) {
      logger.info("object is null");
      return Optional.empty();
    } else {
      logger.info("object is not null");
      return Optional.of(position);
    }
  }

  public List<Position> findAll() {
    String sql = "SELECT * FROM " + TABLE_NAME;
    List<Position> entities = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class));
    return entities;
  }

  public boolean existsById(Integer id) {
    return findById(id).isPresent();
  }

  public long count() {
    String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    return jdbcTemplate.queryForObject(sql,Long.class);
  }
}
