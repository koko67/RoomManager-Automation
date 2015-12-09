package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: Ronald Butron
 * Date: 12/3/15
 */
public class LoginPage extends BasePageObject {

    @FindBy(id = "loginUsername")
    @CacheLookup
    WebElement userNameInput;

    @FindBy(id = "loginPassword")
    @CacheLookup
    WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    @CacheLookup
    WebElement submitButton;

    @Override
    public void waitUntilPageObjectIsLoaded() {

        driverWait.until(ExpectedConditions.visibilityOf(submitButton));
    }

    public LoginPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * This method insert the user name
     * @param userName user name to set
     * @return the same page
     */

    public LoginPage setUserName(String userName){
        userNameInput.clear();
        userNameInput.sendKeys(userName);
        return this;
    }

    /**
     * This method insert the password of the user
     * @param userPassword user password
     * @return the same page
     */

    public LoginPage setPassword(String userPassword){
        passwordInput.clear();
        passwordInput.sendKeys(userPassword);
        return  this;
    }

    /**
     * This method login to the page successfully
     * @param userName name of the user
     * @param userPassword  credentials of the end user
     * @return a new Home Page
     */

    public HomePage loginPageSuccessfully(String userName, String userPassword){

        setUserName(userName);
        setPassword(userPassword);
        submitButton.click();
        return  new HomePage();


    }






}