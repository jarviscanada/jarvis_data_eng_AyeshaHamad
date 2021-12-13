package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity> implements CrudRepository<T, Integer> {

  private static Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  public abstract JdbcTemplate getJdbcTemplate();
  public abstract SimpleJdbcInsert getSimpleJdbcInsert();
  public abstract String getTableName();
  public abstract String getIDColumnName();
  public abstract Class<T> getEntityClass();

  /**
   * Saves an entity and update auto generated entity ID
   */
  @Override
  public <S extends T> S save(S entity) {
    if (existsById((Integer) entity.getId())) {
      if(updateOne(entity) != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    }
    else {
      addOne(entity);
    }
    return entity;
  }

  /**
   * helper method that saves one quote
   */
  private <S extends T> void addOne(S entity) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(entity);
    Number newID = getSimpleJdbcInsert().executeAndReturnKey(source);
    entity.setId(newID.intValue());
  }

  /**
   * helper function
   */
  abstract public  <S extends T> int updateOne(S entity);

  @Override
  public <S extends T> List<S> saveAll(Iterable<S> iterable) {
    for (S quote : iterable) {
      this.save(quote);
    }
    return (List<S>) iterable;
  }

  //check it
  @Override
  public Optional<T> findById(Integer id) {
    String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIDColumnName() + " = ?";
    T quote = null;
    try {
      quote = getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(getEntityClass()), id);
    } catch(Exception e) {
      logger.error("Exception: record not found, so insert it as a new record", e);
      return Optional.empty();
    }

    if(quote == null) {
      logger.info("object is null");
      return Optional.empty();
    } else {
      logger.info("object is not null");
      return Optional.of(quote);
    }
  }

  @Override
  public boolean existsById(Integer id) {
    return findById(id).isPresent();
  }

  @Override
  public List <T> findAll() {
    String sql = "SELECT * FROM " + getTableName();
    List<T> object = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(getEntityClass()));
    return (List<T>) object;
  }

  @Override
  public long count() {
    String sql = "SELECT COUNT(*) FROM " + getTableName();
    return getJdbcTemplate().queryForObject(sql,Long.class);
  }

  @Override
  public void deleteById(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("Invalid id");
    }

    String sql = "DELETE FROM " + getTableName() + " WHERE " + getIDColumnName() + " =?";
    int check = getJdbcTemplate().update(sql, id);

    if (check == 0) {
      logger.error(id + " - unable to delete");
    }
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(sql);
  }


  @Override
  public void delete(T entity) {
    this.deleteById((Integer) entity.getId());
  }

}
