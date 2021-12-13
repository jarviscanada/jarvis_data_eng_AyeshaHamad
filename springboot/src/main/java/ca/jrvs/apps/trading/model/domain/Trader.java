package ca.jrvs.apps.trading.model.domain;

import java.util.Date;

public class Trader implements Entity<Integer> {

  private Integer id;
  private String firstName;
  private String lastName;
  private String country;
  private String email;
  private Date dob;

  public Trader() {

  }

  public Trader(Integer id, String firstName, String lastName, String country, String email,
      Date dob) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
    this.email = email;
    this.dob = dob;
  }

  public Trader(Trader trader) {
    this.id = trader.getId();
    this.firstName = trader.getFirstName();
    this.lastName = trader.getLastName();
    this.dob = trader.getDob();
    this.country = trader.getCountry();
    this.email = trader.getEmail();
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }
}
