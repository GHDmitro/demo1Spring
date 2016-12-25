package pac.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import pac.entities.Greeting;
import pac.services.GreetingService;

import java.util.Collection;

/**
 * Created by macbookair on 06.12.16.
 */

@Component
public class GreetingHealthIndicator implements HealthIndicator {

    @Autowired
    private GreetingService greetingService;

    @Override
    public Health health() {

        Collection<Greeting> collection = greetingService.findAll();

        if (collection == null || collection.size() == 0){
            return Health.down().withDetail("count -> ", 0).build();
        }
        return Health.up().withDetail("count -> ", collection.size()).build();

    }
}
