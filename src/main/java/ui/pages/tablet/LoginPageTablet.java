package ui.pages.tablet;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import utils.CredentialManager;

/**
 * User: Ronald Butron
 * Date: 12/10/15
 */
public class LoginPageTablet extends BasePageObject {

    @FindBy(id = "service-url-input" )
    WebElement serviceURLInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "username")
    WebElement userNameInput;

    @FindBy(xpath = "//div[@type='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//i[@class='fa fa-check']")
    WebElement validateService;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(submitButton));
    }

    public LoginPageTablet(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * This method sign in successfully
     * @param userName the name of the user
     * @return new status page
     */
    public StatusPageTablet signInSuccessfully(String userName) {
        setServiceURL();
        setUsername(userName);
        setPassword();
        clickSignIn();
        return new StatusPageTablet();
    }

    /**
     * Insert the service URL
     */
    private void setServiceURL(){
        serviceURLInput.clear();
        serviceURLInput.sendKeys(CredentialManager.getInstance().getRoomManagerService());
        driverWait.until(ExpectedConditions.visibilityOf(validateService));
    }

    /**
     * Insert the name of the user
     * @param userName the name of user
     */
    private void setUsername(String userName){
        userNameInput.clear();
        userNameInput.sendKeys(userName);
    }

    /**
     * insert the password of the user
     */
    private void setPassword(){
        passwordInput.clear();
        passwordInput.sendKeys(CredentialManager.getInstance().getPasswordAdmin());
    }

    /**
     *  Click on the button Sign in
     */
    public void clickSignIn(){
        submitButton.click();

    }


}
