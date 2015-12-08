package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * Created by jorgeavila on 12/7/2015.
 */
public class RoomSettingsPage extends BasePageObject {

    @FindBy(xpath = "//form/div[2]/input")
    WebElement displayNameInput;

    @FindBy(xpath = "//form/div[3]/input")
    WebElement codeInput;

    @FindBy(xpath = "//form/div[4]/input")
    WebElement capacityInput;

    @FindBy(xpath = "//button[.//span[contains(.,'Save')]]")
    WebElement saveButton;

    public RoomSettingsPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public RoomSettingsPage typeDisplayNameInput(String displayName){
        displayNameInput.clear();
        displayNameInput.sendKeys(displayName);
        return this;
    }

    public RoomSettingsPage typeCodeInput(String code){
        codeInput.clear();
        codeInput.sendKeys(code);
        return this;
    }

    public RoomSettingsPage typeCapacityInput(String capacity){
        capacityInput.clear();
        capacityInput.sendKeys(capacity);
        return this;
    }

    public ConferenceRoomsPage clickOnSaveButton(){
        saveButton.click();
        return new ConferenceRoomsPage();
    }

    public ConferenceRoomsPage fillForm(String displayName, String code, String capacity){
        typeDisplayNameInput(displayName);
        typeCodeInput(code);
        typeCapacityInput(capacity);
        return clickOnSaveButton();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(saveButton));
    }
}
