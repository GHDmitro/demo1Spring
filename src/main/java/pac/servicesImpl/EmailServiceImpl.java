package pac.servicesImpl;

import pac.entities.Greeting;
import pac.services.EmailService;
import pac.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by macbookair on 24.11.16.
 */

@Service
public class EmailServiceImpl implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean send(Greeting greeting) {
        logger.info("> send ");

        Boolean success = Boolean.FALSE;

        long pause = 5000;

        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("This method is do in " + pause / 1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info("> sendAsync");
        try {
            send(greeting);
        } catch (Exception e) {
            logger.warn("Exception in async method ", e);
        }

        logger.info("< send");
    }

    @Async
    @Override
    public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
        logger.info("> sendAsyncWithResult");

        AsyncResponse<Boolean> response = new AsyncResponse<>();

        try{
            Boolean success = send(greeting);
            response.complete(success);
        }catch (Exception e){
            logger.warn("Exception in asyncWithResult mail ", e);
            response.completeExceptionally(e);
        }

        logger.info("< sendAsyncWithResult");
        return response;

    }
}
































