package pac.services;

import pac.entities.Greeting;

import java.util.concurrent.Future;

/**
 * Created by macbookair on 23.11.16.
 */
public interface EmailService {

    boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);

}
