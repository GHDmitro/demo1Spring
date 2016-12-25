package pac.servicesImpl;

import org.springframework.boot.actuate.metrics.CounterService;
import pac.entities.Greeting;
import pac.repository.GreetingRepository;
import pac.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by macbookair on 20.11.16.
 */
@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    //this feature allow as to get count of each methods invokes, this we can take to the Aspect class to After annotation
    @Autowired
    private CounterService counterService;


    @Override
    public Collection<Greeting> findAll() {
        counterService.increment("GreetingServiceBean.findAll");

        Collection<Greeting> geetings = greetingRepository.findAll();
        return geetings;
    }

    @Override
    @CachePut(value = "greetings", key = "#id")
    public Greeting findOne(Long id) {
        counterService.increment("GreetingServiceBean.findOne");

        Greeting one =  greetingRepository.findOne(id);
        return one;
    }

    @Override
    @CachePut(value = "greetings", key = "#result.id")  //в кеш после создания
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public Greeting create(Greeting greeting) {
        counterService.increment("GreetingServiceBean.create");

        if (greeting.getId() != null){
            return null;
        }
        Greeting m = greetingRepository.save(greeting);
        return m;
    }

    @Override
    @CachePut(value = "greetings", key = "#greeting.id")
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public Greeting update(Greeting greeting) {
        counterService.increment("GreetingServiceBean.update");

        Greeting greetingPersistence = greetingRepository.findOne(greeting.getId());
        if (greetingPersistence == null){
            return null;
        }
        Greeting updatingPensistence = greetingRepository.save(greeting);
        return updatingPensistence;
    }



    @Override
    @CacheEvict(value = "greetings", key = "#id")   //вытесняет из кеша инфу по  id
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    public void delete(Long id) {
        counterService.increment("GreetingServiceBean.delete");

        greetingRepository.delete(id);
    }


    @Override
    @CacheEvict(value = "greetings", allEntries = true)
    public void evictCache() {

    }
}
