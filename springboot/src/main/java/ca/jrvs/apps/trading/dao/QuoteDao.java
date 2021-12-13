package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  //make this class extend JdbcCrudDao
  //update later
  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  @Override
  public <S extends Quote> S save(S quote) {
    if( existsById(quote.getId())) {
      int updatedRowNum = updateOne(quote);
      if (updatedRowNum != 1) {
        throw new DataRetrievalFailureException("Unable to update row");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private void addOne(Quote quote) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(quote);
    int check = simpleJdbcInsert.execute(source);
    if(check != 1) {
      throw new IncorrectResultSizeDataAccessException("Unable to insert",1,check);
    }
  }

  private int updateOne(Quote quote) {
    String sql = "UPDATE " + TABLE_NAME + " SET "
        + "last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=? ";
    return jdbcTemplate.update(sql, makeUpdateValues(quote));
  }

  private Object[] makeUpdateValues (Quote quote) {
    Object[] objects = {
        quote.getLastPrice(),
        quote.getBidPrice(),
        quote.getBidSize(),
        quote.getAskPrice(),
        quote.getAskSize(),
        quote.getTicker()
    };
    return objects;
  }

  //not tested yet
  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> iterable) {
    for (Quote quote : iterable) {
      save(quote);
    }
    return iterable;
  }

  @Override
  public Optional<Quote> findById(String id) {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";//"'" + id + "'";
    Quote quote = null;
    try {
      quote = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Quote.class), id);
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
  public boolean existsById(String id) {
    return findById(id).isPresent();
  }

  @Override
  public Iterable<Quote> findAll() {
    String sql = "SELECT * FROM " + TABLE_NAME;
    List<Quote> quotes = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Quote.class));
    return quotes;
  }

  @Override
  public long count() {
    String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    return jdbcTemplate.queryForObject(sql,Long.class);
  }

  @Override
  public void deleteById(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Invalid ticker id");
    }

    String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    int check = jdbcTemplate.update(sql, id);

    if (check == 0) {
      logger.error(id + " - uncle to delete");
    }
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM " + TABLE_NAME;
    jdbcTemplate.update(sql);
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Method not supported");
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Method not supported");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Method not supported");
  }
}
