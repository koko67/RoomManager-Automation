package common;

import framework.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.PageTransporter;
import ui.pages.admin.HomePage;
import utils.CredentialManager;

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
            new HomePage().getHeader().logOut();
        }
    }

    /**
     * navigate to the Login Page
     */
    public static void navigateLogIn(){
       PageTransporter.getInstance().toLoginPage();
    }

    /**
     * Verify if the current URL is in the Login Page
     * @return true if the current URL is in the Login Page
     */
    public static Boolean isItInTheLoginPage(){
        String LoginURL = CredentialManager.getInstance().getAdminURL();
        return driver.getCurrentUrl().equalsIgnoreCase(LoginURL);
    }

    /**
     * This method verify if the current URL is in the Login Page of the tablet version
     * @return true if the current URL is in the Tablet login page
     */
    public static Boolean isItInTheLoginPageTablet(){
        String LoginTabletURL = CredentialManager.getInstance().getTabletURL();
        return driver.getCurrentUrl().equalsIgnoreCase(LoginTabletURL);
    }

    /**
     * This method verify if the current URL is in the Home Page of the Tablet
     * @return true if the current URL is in the home page of the tablet version
     */
    public static Boolean isItInTheHomePageTablet(){
        String HomeTabletURL = CredentialManager.getInstance().getHometabletURL();
        return driver.getCurrentUrl().equalsIgnoreCase(HomeTabletURL);
    }

    /**
     * High light elements
     * @param element element to high light
     */
    public static void elementHighlight(WebElement element) {
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
