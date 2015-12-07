package runner;

/**
 * User: RonaldButron
 * Date: 11/20/15
 */

import common.CommonMethods;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import framework.DriverManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;



@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        glue={"steps"},
        features = {"src/test/resources/features"},
        monochrome = true)

public class RunCukesTest extends AbstractTestNGCucumberTests {


    private static Logger log = Logger.getLogger("RunCukesTest");

    @BeforeTest
    public void beforeExecution(){
        try{
            CommonMethods.navigateLogIn();
        }catch (Exception e){
            log.error("Unable to navigate to the Login Page" + e);
        }
    }

    @AfterTest
    public void afterExecution() {
        try {
            CommonMethods.logOut();
        } catch (Exception e) {
            log.error("Unable to logout after execution" + e);
        } finally {
            DriverManager.getInstance().getWebDriver().quit();
        }
    }





}