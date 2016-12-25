package pac.transactions;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pac.DemoApplicationTests;
import pac.entities.Greeting;
import pac.services.GreetingService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by macbookair on 27.11.16.
 */
@Transactional // эта аннотация размещенная над классом, который экстендит или я вляется главным или абстрактным  Тествовым классом
public class GreetingServiceTest extends DemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService service;

    @Before
    public void setUp() {
        service.evictCache();
    }

    @After
    public void tearDown() {
        //After calling each test
    }

    @Test
    public void findAll() {
        Collection<Greeting> all = service.findAll();
        all.forEach(System.out::println);
        Assert.assertNotNull("The collection is not null", all);
        Assert.assertNotEquals("The size is not same ", 9, all.size());
    }

    @Test
    public void findOneById() {
        Greeting greeting = service.findOne((long) 1);
        Assert.assertNotNull(greeting);
        Assert.assertEquals((long) 1, (long) greeting.getId());

    }

    public static void main(String[] args) {
        List<Double> list = new LinkedList<>();

        double sum = list.stream().reduce((x, y) -> x + y).orElse(-1.0);
        System.out.println(sum);
    }

    @Test
    public void createOne(){
        Greeting greeting = new Greeting();
        String str = "new creation in test";
        greeting.setText(str);
        greeting = service.create(greeting);
        Assert.assertNotNull(greeting);
        Assert.assertNotNull(greeting.getId());
        Assert.assertEquals(str, greeting.getText());
    }

    @Test
    public void updateOne() {
        Long id = new Long(1);
        Greeting greeting = service.findOne(id);

        Assert.assertNotNull(greeting);

        String st = "new tesxt for 1";
        greeting.setText(st);
        greeting = service.update(greeting);

        Assert.assertEquals(st, greeting.getText());
    }

    @Test
    public void deleteOne(){
        Long id = new Long(2);
        service.delete(id);
        Greeting greeting = service.findOne(id);

        Assert.assertNull(greeting);
    }
    @Test
    public void mytestForMethod() {
//		greetingBatchBean.getScheduledGreeting();
    }

}


















