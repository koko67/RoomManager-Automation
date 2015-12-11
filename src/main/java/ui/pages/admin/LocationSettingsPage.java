package ui.pages.admin;

import entities.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import ui.pages.admin.LocationAssociationsPage;
import ui.pages.admin.LocationPage;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/7/15
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationSettingsPage extends BasePageObject{
    @FindBy(xpath = "//span[contains(text(),'Save')]/..")
    WebElement saveButton;
    @FindBy(id = "location-add-name")
    WebElement locationNameTextField;
    @FindBy(id = "location-add-display-name")
    WebElement locationDisplayNameTextField;
    @FindBy(xpath = "//a[@class='ng-binding ng-scope' and contains(text(),'Location Associations')]")
    WebElement locationAssociateTab;

    public LocationSettingsPage(){
        PageFactory.initElements(driver,this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(saveButton));
    }

    /**
     * this method write the field required for the location
     * @param locationName is the name of location
     * @param displayName
     */
    public void writeInformationLocation(String locationName, String displayName) {
        locationNameTextField.clear();
        locationNameTextField.sendKeys(locationName);
        locationDisplayNameTextField.clear();
        locationDisplayNameTextField.sendKeys(displayName);
    }

    /**
     * press button for save the changes of location
     * @return the new location page.
     */
    public void saveLocation() {
        saveButton.click();
    }
    public LocationPage fillFormSuccessfully(Location location){
        By messageLocationAddedLocator = By.xpath("//div[@class='ng-binding ng-scope' and contains(text(),'Location successfully added')]");
        writeInformationLocation(location.getName(), location.getDisplayName());
        saveLocation();
        WebElement messageLocationAdded = driver.findElement(messageLocationAddedLocator);
        driverWait.until(ExpectedConditions.visibilityOf(messageLocationAdded));
        return new LocationPage();
    }

    /**
     * method that doing click in the location associate tab.
     * @return the page with list of rooms Available and non-Available
     */
    public LocationAssociationsPage goLocationAssociationTab() {
        locationAssociateTab.click();
        return new LocationAssociationsPage();
    }
}
