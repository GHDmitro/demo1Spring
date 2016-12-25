package pac.controllerTest.testMockitoAndMockObjects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pac.controllerTest.AbstractControllerTest;
import pac.controllers.GreetingController;
import pac.entities.Greeting;
import pac.services.EmailService;
import pac.services.GreetingService;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

/**
 * Created by macbookair on 03.12.16.
 */

@Transactional
public class GreetingControllerMocksTest extends AbstractControllerTest {

    @Mock
    private EmailService emailService;
    @Mock
    private GreetingService greetingService;

    @InjectMocks
    private GreetingController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUp(controller);
    }


    @Test
    public void testGetGreeting() throws Exception {

        when(greetingService.findAll()).thenReturn(getCollections());

        String uri = "/getAll";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(greetingService, times(1)).findAll();

        Assert.assertEquals("failure -> expected status 200 ", 200, status);
        Assert.assertTrue("failure -> expected content ", content.trim().length() > 0);


    }

    @Test
    public void testGreetingNotFound() throws Exception {

        long id = Long.MAX_VALUE;

        String uri = "/getGreeting/{id}";

        when(greetingService.findOne(id)).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(greetingService, times(1)).findOne(id);

        Assert.assertEquals("failure -> that status is  not 404", 404, status);
        Assert.assertTrue("failure -> that content is null", content.trim().length() == 0);

    }

    @Test
    public void testOne() throws Exception {

        Long id = new Long(0);
        String uri = "/getGreeting/{id}";

        when(greetingService.findOne(id)).thenReturn(getNewGreeting());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).
                accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(greetingService, times(1)).findOne(id);

        Assert.assertEquals("failure -> status is not 200", 200, status);
        Assert.assertTrue("failure -> content.length = 0 ", content.trim().length() > 0);

    }

    @Test
    public void testCreate() throws Exception {
        Greeting g = getNewGreeting();
//        Greeting g = new Greeting();
//        g.setText("sdlfnvjsdlnv");

        when(greetingService.create(any(Greeting.class))).thenReturn(g);

        String uri = "/createGreeting";

        String jsonStr = mapToJson(g);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).content(jsonStr)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(greetingService, times(1)).create(any(Greeting.class));

        Assert.assertEquals("failure -> status not 201", 201, status);
        Assert.assertTrue("failure -> content is empty", content.trim().length() > 0);

        Greeting newGreeting = mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure -> Greeting is null", newGreeting);
        Assert.assertNotNull("failure -> id is null", newGreeting.getId());
        Assert.assertEquals("failure -> text is not equals", g.getText(), newGreeting.getText());
    }

    @Test
    public void testUpdate() throws Exception {
        Long id = new Long(0);
        Greeting g = getNewGreeting();
        Assert.assertNotNull(g);

        when(greetingService.update(any(Greeting.class))).thenReturn(g);
        g.setText("another");

        String uri = "/updateGreeting/{id}";

        String jsonStr = super.mapToJson(g);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                content(jsonStr)).
                andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(greetingService, times(1)).update(any(Greeting.class));

        Assert.assertEquals("failure -> status not 200", 200, status);
        Assert.assertTrue("failure -> content is empty", content.trim().length() > 0);

        Greeting newGreeting = super.mapFromJson(content, Greeting.class);
        Assert.assertNotNull("failure -> Greeting is null", newGreeting);
        Assert.assertNotNull("failure -> id is null", newGreeting.getId());
        Assert.assertEquals("failure -> text is not equals", "another", newGreeting.getText());
    }


    private Collection<Greeting> getCollections() {
        Collection<Greeting> c = new LinkedList<>(Arrays.asList(getNewGreeting()));

        return c;
    }

    private Greeting getNewGreeting() {
        Greeting g = new Greeting();
        g.setId(1L);
        g.setText("sdjhbvjshdb");
        return g;
    }


}




































