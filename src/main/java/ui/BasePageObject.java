package ui;

import entities.Meeting;
import framework.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * User: RonaldButron
 * Date: 12/03/15
*/
public abstract class BasePageObject {
     protected WebDriver driver;
     protected WebDriverWait driverWait;

    public BasePageObject(){
        this.driver = DriverManager.getInstance().getWebDriver();
        this.driverWait = DriverManager.getInstance().getWait();
        PageFactory.initElements(driver, this);
    }

    public abstract void waitUntilPageObjectIsLoaded();

}
