package pac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pac.entities.Greeting;
import pac.services.EmailService;
import pac.services.GreetingService;

import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Created by macbookair on 01.11.16.
 */

@RestController
public class GreetingController extends BaseController {


    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/deleteGreeting/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> deletemethod(@PathVariable("id") Long id) {
        greetingService.delete(id);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getObj() {
        Collection<Greeting> m = greetingService.findAll();
        return new ResponseEntity<Collection<Greeting>>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "/getGreeting/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getSpecificObj(@PathVariable("id") Long id) {
        Greeting greeting = greetingService.findOne(id);
        if (greeting != null) {
            return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
        } else return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> createObj(@RequestBody Greeting obj) {
        Greeting greeting = greetingService.create(obj);
        return new ResponseEntity<Greeting>(greeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateGreeting/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> updateObj(@RequestBody Greeting greeting) {
        Greeting simpleDemo = greetingService.update(greeting);
        if (simpleDemo == null) {
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else return new ResponseEntity<Greeting>(simpleDemo, HttpStatus.OK);
    }


    @RequestMapping(value = "/sendGreeting/{id}/send")    //симуляция отправки сообщений с помощью Async в
    public ResponseEntity<Greeting> send(@PathVariable(value = "id") Long id,          //параллельных потоках, ассинхронно
                                         @RequestParam(value = "wait", defaultValue = "false")
                                                 boolean waitAsyncMethod) {
        logger.info("> sedGreeting");

        Greeting greeting = null;

        try {

            greeting = greetingService.findOne(id);

            if (greeting == null) {
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if (waitAsyncMethod) {
                Future<Boolean> booleanFuture = emailService.sendAsyncWithResult(greeting);
                boolean isSuccessSending = booleanFuture.get();

                logger.info("send by asynhwith result " + isSuccessSending);

            } else {
                emailService.sendAsync(greeting);
                logger.info("send by async method");
            }


        } catch (Exception e) {

            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< sedGreeting");

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
















