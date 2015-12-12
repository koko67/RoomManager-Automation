package ui.pages.tablet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import utils.CredentialManager;

/**
 * User: RonaldButron
 * Date: 12/10/15
 */
public class ExchangeCredentialsPage extends BasePageObject {

    @FindBy(xpath = "//input[@placeholder='username']")
    WebElement userNameInput ;

    @FindBy(xpath = "//input[@placeholder='password']")
    WebElement userPasswordInput ;

    @FindBy(xpath = "//button[@ng-click='dialog.ok()']")
    WebElement okButton;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.elementToBeClickable(okButton));
    }

    public ExchangeCredentialsPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * Click over the button Ok
     * @return a new Schedule page
     */
    public SchedulePageTablet saveExchangeCredentials(){
        okButton.click();
        return new SchedulePageTablet();
    }

    /**
     *  Insert the user name
     */
    public void setUserName(){
        userNameInput.clear();
        userNameInput.sendKeys(CredentialManager.getInstance().getUserExchange());
    }

    /**
     *  Insert the Exchange Password
     */
    public void setUserPassword(){
        userPasswordInput.clear();
        userPasswordInput.sendKeys(CredentialManager.getInstance().getPasswordExchange());
    }

    /**
     * This method fill the Credentials Exchange form
     * @return return a Schedule page
     */
    public SchedulePageTablet fillExchangeCredentialsForm() {
        setUserName();
        setUserPassword();
        return saveExchangeCredentials();
    }

    /**
     * This method fill the Exchange password
     * @return a new Exchange Credentials Page
     */
    public SchedulePageTablet fillTheExchangePasswordToRemoveAMeeting(){
        setUserPassword();
        return saveExchangeCredentials();
    }
}
