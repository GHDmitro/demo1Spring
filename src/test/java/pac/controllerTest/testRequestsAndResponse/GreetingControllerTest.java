package pac.controllerTest.testRequestsAndResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pac.controllerTest.AbstractControllerTest;
import pac.entities.Greeting;
import pac.services.GreetingService;

/**
 * Created by macbookair on 27.11.16.
 */
@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

    @Autowired
    private GreetingService service;

    @Before
    public void setUp(){
        super.setUp();
        service.evictCache();
    }
    @Test
    public void getAllTest() throws Exception {
        String uri = "/getAll";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failler -> response 200", 200, status);
        Assert.assertTrue("failer -> content length > 0 ",content.trim().length() > 0);
    }

    @Test
    public void getGreetingById() throws Exception {
        String uri = "/getGreeting/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failler -> response 200", 200, status);
        Assert.assertTrue("failer -> content length > 0 ",content.trim().length() > 0);
    }

    @Test
    public void getGreetingNotFound() throws Exception {
        String uri = "/getGreeting/{id}";
        Long id =Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertNotEquals("failler -> response 200", 200, status);
        Assert.assertFalse("failer -> content length > 0 ",content.trim().length() > 0);
    }
    @Test
    public void createGreetingTest() throws Exception {
        String uri = "/createGreeting";
        Greeting greeting = new Greeting();
        greeting.setText("testGreeting");

        String inputJson = mapToJson(greeting);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure -> not 201 status ", 201, status);
        Assert.assertTrue("failure -> content is null", content.trim().length() > 0);

        Greeting greeting1 = mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure -> greeting is NULL" ,greeting1);

        Assert.assertNotNull("failure -> id is null", greeting1.getId());

        Assert.assertEquals("failure -> text is null" , "testGreeting", greeting1.getText());
    }

    @Test
    public void updateGreetingTest() throws Exception {
        String uri = "/updateGreeting/{id}";
        Long id = new Long(1);
        Greeting greeting = service.findOne(id);
        greeting.setText("updateGreeting");

        String inputJson = mapToJson(greeting);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure -> not 200 status ", 200, status);
        Assert.assertTrue("failure -> content is null", content.trim().length() > 0);

        Greeting greeting1 = mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure -> greeting is NULL" ,greeting);

        Assert.assertNotNull("failure -> id is null", greeting.getId());

        Assert.assertEquals("failure -> text is null" , "updateGreeting", greeting.getText());
    }

}

























