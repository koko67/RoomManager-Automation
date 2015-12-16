package framework;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import utils.ConfigFileReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by RonaldButron on 12/03/2015.
 */
public class DriverManager {

    private static DriverManager instance = null;
    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigFileReader reader = new ConfigFileReader();
    private String timeOutImplicitWait = reader.getPropertiesValues("timeOutImplicitWait");
    private String waitTimeOut = reader.getPropertiesValues("waitTimeOut");
    private String waitTimeOutSleep = reader.getPropertiesValues("waitTimeOutSleep");
    private String browserName;
    private String chromedriverPath = reader.getPropertiesValues("driver.chrome");

    protected DriverManager(){
        init();
    }

    public static DriverManager getInstance(){
        if (instance == null){
            instance = new DriverManager();
        }

        return instance;
    }

    private void init(){
        browserName = System.getProperty("browserName");
        if(browserName == null || browserName.isEmpty() || browserName.equals("firefox")){
            driver = new FirefoxDriver();
        } else if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", chromedriverPath);
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Long.parseLong(timeOutImplicitWait), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Long.parseLong(waitTimeOut), Long.parseLong(waitTimeOutSleep));

    }

    public WebDriver getWebDriver(){
        return driver;
    }

    public WebDriverWait getWait(){
        return wait;
    }
}
