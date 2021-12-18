package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
    //scanBasePackages = "ca.jrvs.apps.trading",
    exclude = {JdbcTemplateAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
public class Application implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(Application.class);

  //@Value("${app.init.dailyList}")
  //private String[] initDailyList;

  //why ? it should be controller
  @Autowired
  //private QuoteController quoteController;
  private QuoteService quoteService;

  //@Autowired
  //public Application(QuoteController quoteController) {
  //  this.quoteController = quoteController;
  //}

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(Application.class);
    springApplication.run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("In Run Method");
  }
}
