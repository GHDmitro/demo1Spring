package pac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//it is deprecated variant because it used for Spring 3 version
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = DemoApplication.class)

@RunWith(SpringRunner.class)    //have inside all methods from SpringJUnit4ClassRunner and it used for spring 4
@SpringBootTest(classes = DemoApplication.class)
public abstract class DemoApplicationTests {

	@Test
	public void contextLoads() throws InitializationError {

	}

}
