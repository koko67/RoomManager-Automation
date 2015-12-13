package commons;

import framework.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.PageTransporter;
import ui.pages.admin.HomePage;
import utils.CredentialManager;

/**
 * User: Jorge Avila
 * Date: 11/20/15
 */
public class FrameworkUtils {

    private static WebDriver driver = DriverManager.getInstance().getWebDriver();

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
