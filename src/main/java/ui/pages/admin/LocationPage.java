package ui.pages.admin;

import framework.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class LocationPage extends BasePageObject{
    @FindBy(xpath = "//h1[contains(text(),'Locations')]")
    WebElement locationLabel;
    @FindBy(xpath = "//span[contains(text(),'Add')]/..")
    WebElement addLocationButton;

    public LocationPage(){
        PageFactory.initElements(driver,this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(locationLabel));
    }

    /**
     * press button add location
     * @return the dialog with form to registry the new location
     */
    public LocationSettingsPage clickAddLocation() {
        addLocationButton.click();
        return new LocationSettingsPage();
    }

    /**
     * This method return the name of location.
     * @param locationName, name of location
     * @return the string for compare with the expected result
     */

    public boolean isLocationNameExists(String locationName) {
        By locationNameLocator = By.xpath("//div[contains(text(),'" + locationName + "')]");
        return UIMethods.waitElementIsPresent(2, locationNameLocator);
    }
}