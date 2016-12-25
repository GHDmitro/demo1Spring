package pac.services;

import pac.entities.Greeting;

import java.util.Collection;

/**
 * Created by macbookair on 20.11.16.
 */
public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting update(Greeting greeting);

    Greeting create(Greeting greeting);

    void delete(Long id);

    void evictCache();
}
