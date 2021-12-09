package ca.jrvs.apps.trading.model.domain;

public interface Entity<ID> {
  void setID(ID id);
  ID getID();
}
