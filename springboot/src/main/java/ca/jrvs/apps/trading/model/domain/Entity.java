package ca.jrvs.apps.trading.model.domain;

public interface Entity<ID> {
  void setId(ID id);
  ID getId();

  //void setId(Integer id);
}
