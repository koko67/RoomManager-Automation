package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class Header extends BasePageObject {

    @FindBy(xpath = "//a[@ng-click='removeSession()']")
    WebElement logOutBtn;

    @Override
    public void waitUntilPageObjectIsLoaded() {

        driverWait.until(ExpectedConditions.visibilityOf(logOutBtn));
    }

    public Header(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * This method LogOut the main Page
     * @return a Login Page
     */
    public LoginPage logOut(){

        logOutBtn.click();
        return new LoginPage();
    }
}
