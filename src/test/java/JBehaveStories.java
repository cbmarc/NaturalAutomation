import com.fullstackmarc.selenium.config.AcceptanceTest;
import com.fullstackmarc.selenium.config.AcceptanceTestsConfiguration;
import com.fullstackmarc.selenium.jbehave.AbstractSpringJBehaveStories;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AcceptanceTestsConfiguration.class)
@AcceptanceTest
public class JBehaveStories extends AbstractSpringJBehaveStories {


}