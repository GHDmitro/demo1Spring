package pac.controllerTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pac.DemoApplicationTests;
import pac.controllers.BaseController;

import java.io.IOException;

/**
 * Created by macbookair on 27.11.16.
 */
@WebAppConfiguration
public abstract class AbstractControllerTest extends DemoApplicationTests{

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    protected void setUp(BaseController controller){
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException,  IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(json, clazz);
    }



}
