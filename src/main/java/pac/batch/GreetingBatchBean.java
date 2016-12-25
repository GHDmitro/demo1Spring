package pac.batch;

import pac.entities.Greeting;
import pac.services.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by macbookair on 22.11.16.
 */

@Profile(value = "batch")
@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(cron = "${batch.greeting.cron}")      //0,30 * * * * * //каждые 30 сек кажды день каждый месяц каждый год
    public void getScheduledGreeting3(){
        logger.info("> start");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are greeting size() "+ greetings.size() );
        logger.info("< finish");
    }
//param to annotation we get from application-batch.properties
    @Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedRateString = "${batch.greeting.fixedrate}")     //initialDelay = 5000, fixedRate = 15000
    public void getScheduledGreeting(){
        logger.info("> start");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are greeting size() "+ greetings.size() );
        logger.info("< finish");
    }

    @Scheduled(initialDelayString = "${batch.greeting.initialdelay}", fixedDelayString = "${batch.greeting.fixeddelay}")    //после 5с инициализации класса и каждые 15 сек
    public void getScheduledGreeting1(){                     // происходит выполнение этого метода
        logger.info("> start");

        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are greeting size() "+ greetings.size() );
        logger.info("< finish");
    }




}
