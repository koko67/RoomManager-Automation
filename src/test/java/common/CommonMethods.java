package common;

import framework.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.PageTransporter;
import utils.ConfigFileReader;

/**
 * User: RonaldButron
 * Date: 11/20/15
 */
public class CommonMethods {

    private static WebDriver driver = DriverManager.getInstance().getWebDriver();

    /**
     * This method verify if is in the main page then Log out
     */
    public static void logOut(){

        if (!isItInTheLoginPage()){
        }

    }

    /**
     * navigate to the Login Page
     */
    public static void navigateLogIn(){

       PageTransporter.getInstance().toLoginPage();
    }

    /**
     * Verify if is in the Login Page
     * @return true is in the Login Page
     */
    public static Boolean isItInTheLoginPage(){

        ConfigFileReader reader = new ConfigFileReader();
        String URLLogin = reader.getPropertiesValues("URLLogin");
        return driver.getCurrentUrl().equalsIgnoreCase(URLLogin);
    }

    /**
     * High light elements
     * @param element element to high light
     */
    public static void elementHighlight(WebElement element) {

        WebDriver driver = DriverManager.getInstance().getWebDriver();
        for (int i = 0; i < 3; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: red; border: 4px solid red;");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }


}
