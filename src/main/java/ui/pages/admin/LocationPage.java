package ui.pages.admin;

import entities.ConferenceRoom;
import entities.Location;
import framework.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class LocationPage extends HomePage {

    @FindBy(xpath = "//span[contains(text(),'Add')]/..")
    WebElement addLocationButton;

    public LocationPage(){
        super();
        PageFactory.initElements(driver,this);
    }
    @Override
    public void waitUntilPageObjectIsLoaded() {

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

    /**
     * method that doing double click in the location.
     * @param location recieve this parameter for the know where doing double click
     * @return the page with information of the location
     */
    public LocationSettingsPage clickEditLocation(Location location) {
        By locationNameLocator = By.xpath("//div[contains(text(),'" + location.getDisplayName() + "')]");
        WebElement locationName = driver.findElement(locationNameLocator);
        Actions action =  new Actions(driver);
        action.moveToElement(locationName).doubleClick().perform();
        return new LocationSettingsPage();
    }

    public boolean verifyIfExistLocationAssociation(Location location, ConferenceRoom conferenceRoom){
        By locationBy = By.xpath("//div[contains(text(),'" + location.getDisplayName() + "')]");
        if(UIMethods.waitElementIsPresent(3, locationBy)){
            return clickEditLocation(location)
                    .goLocationAssociationTab()
                    .existsRoomAssociated(conferenceRoom);
        }
        return false;
    }
}