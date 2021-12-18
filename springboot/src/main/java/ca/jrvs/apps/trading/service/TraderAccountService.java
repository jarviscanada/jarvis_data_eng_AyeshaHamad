package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private static final Logger logger = LoggerFactory.getLogger(TraderAccountService.class);

  private SecurityOrderDao securityOrderDao;
  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;

  public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao,
      SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
    logger.info("TraderAccountService init");
  }

  /**
   * Create a new trader and initialize a new account with 0 amount.
   * - validate user input (all fields must be non-empty)
   * - create a trader
   * - create an account
   * - create, setup, and return a new TraderAccountView
   *
   * Assumption: to simplify the logic, each trader has only one account with traderId == accountId
   *
   * @param trader
   * @return
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    if (trader.getId() == null){
      throw new DataRetrievalFailureException("Invalid trader");
    }

    Account account = new Account();
    account.setTraderId(trader.getId());
    account.setAmount(0d);

    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setTrader(traderDao.save(trader));
    traderAccountView.setAccount(accountDao.save(account));

    return traderAccountView;
  }

  /**
   * A trader can be deleted if it has no open position and 0 cash balance
   * - validate tradeId
   * - get trader account by traderId and check account balance
   * - get positions by accountId and check positions
   * - delete all securityOrders, account, trader (in this order)
   *
   * @param traderId must not be null
   * @throws IllegalArgumentException if tradeId is null or not found or unable to delete
   */
  public void deleteTraderById(Integer traderId) {
    if(traderId == null) {
      throw new IllegalArgumentException("Trader Id is null");
    }

    Trader trader = traderDao.findById(traderId).orElseThrow(() -> new IllegalArgumentException("Trader does not exist"));
    Account account = accountDao.findById(traderId).orElseThrow(() -> new IllegalArgumentException("Account does not exist"));

    if(account.getAmount() != 0) {
      throw new IllegalArgumentException("Trader cannot be deleted, account have a non-zero balance");
    }

    Optional<Position> position = positionDao.findById(account.getId());

    if(position.isPresent()) {
      throw new IllegalArgumentException("Trader has open positions");
    }

    securityOrderDao.deleteById(account.getId());
    accountDao.deleteById(account.getId());
    traderDao.deleteById(trader.getId());
  }

  /**
   * Deposit a fund to an account by traderId
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountById
   * @param traderId must not be null
   * @param fund mustbe greater than 0
   * @return updated Account
   */
  public Account deposit(Integer traderId, Double fund){
    if (fund <= 0){
      throw new IllegalArgumentException("fund must be greater than 0");
    }
    Account account = accountDao.findById(traderId).get();
    account.setAmount(account.getAmount() + fund);
    accountDao.updateOne(account);
    return account;
  }

  /**
   * Withdraw a fund to an account by traderId
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountById
   * @param traderId
   * @param fund ;8must be greater than 0
   * @return updated Account
   */
  public Account withdraw(Integer traderId, Double fund){
    if (fund <= 0){
      throw new IllegalArgumentException("fund must be greater than 0");
    }
    Account account = accountDao.findById(traderId).get();
    account.setAmount(account.getAmount() - fund);
    accountDao.updateOne(account);
    return account;
  }
}
