package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import io.swagger.annotations.ApiOperation;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
public class TraderAccountController {

  private TraderAccountService service;

  @Autowired
  public TraderAccountController(TraderAccountService service) {
    this.service = service;
  }

  @ApiOperation(value = "Create a Trader and an Account")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(
      path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
  )
  public TraderAccountView createTrader(
      @PathVariable String firstname,
      @PathVariable String lastname,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
      @PathVariable String country,
      @PathVariable String email
  ) {
    try{
      Trader trader = new Trader();
      trader.setFirstName(firstname);
      trader.setLastName(lastname);
      trader.setEmail(email);
      trader.setCountry(country);
      trader.setDob(Date.valueOf(dob));
      return service.createTraderAndAccount(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Create a Trader and Account with DTO")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public TraderAccountView createTrader(@RequestBody Trader trader) {
    try {
      return service.createTraderAndAccount(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Delete a Trader by ID")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @DeleteMapping(path = "/traderId/{traderId}")
  public void deleteTrader(@PathVariable Integer traderId) {
    try {
      service.deleteTraderById(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Deposit a fund")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(path = "deposit/traderId/{traderId}/amount/{amount}")
  public Account depositFund(@PathVariable Integer traderId, @PathVariable Double amount) {
    try {
      return service.deposit(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Withdraw a fund")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @PutMapping(path = "/withdraw/traderId/{traderId}/amount/{amount}")
  public Account withdrawFund(@PathVariable Integer traderId, @PathVariable Double amount) {
    try {
      return service.withdraw(traderId, amount);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
