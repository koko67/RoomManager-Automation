package ui.pages.admin;

import commons.FrameworkUtils;
import entities.ConferenceRoom;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/10/15
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationAssociationsPage extends BasePageObject{
    @FindBy(xpath = "//h4[contains(text(),'Associated')]")
    WebElement associateLabel;
    @FindBy(xpath = "//span[contains(text(),'Save')]/..")
    WebElement saveButton;

    public LocationAssociationsPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }
    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(associateLabel));
    }

    /***
     * this method that call at method that doing click on the button + o -,
     * when want associated or disassociate a room of location. Only build the locator of room
     * @param roomName is the name of the room that been associate or dis-associate
     *                 of location.
     * @return call of the method that realize click on button.
     */
    public LocationPage clickOnRoomAssociateToLocation(String roomName) {
        By addRoomLocator = By.xpath("//div[contains(text(),'"+ roomName +"')]/following-sibling::div/button");
        return associateRoom(addRoomLocator);
    }
    public void saveLocation() {
        saveButton.click();
    }

    /**
     * method that associate or di-associate a room
     * @param addRoomLocator receive a locator of room to associate or disassociate
     * @return the location page.
     */
    public LocationPage associateRoom(By addRoomLocator) {
        By messageLocationAddedLocator = By.xpath("//div[@class='ng-binding ng-scope' and contains(text(),'Location successfully added')]");
        WebElement iconAssociateRoom = driver.findElement(addRoomLocator);
        iconAssociateRoom.click();
        saveLocation();
        WebElement messageLocationAdded = driver.findElement(messageLocationAddedLocator);
        driverWait.until(ExpectedConditions.visibilityOf(messageLocationAdded));
        return new LocationPage();
    }

    /**
     * verify if a conference room is associated with the location
     * @param conferenceRoom is the room to find in the associated rooms
     * @return true if exist the conference room associated
     */
    public boolean existsRoomAssociated(ConferenceRoom conferenceRoom) {
        By confRoom = By.xpath("//following-sibling::div[.//div[contains(.,'" + conferenceRoom.getDisplayName() + "')]]");
        try {
            FrameworkUtils.elementHighlight(associateLabel.findElement(confRoom));
            return associateLabel.findElement(confRoom) != null;
        } catch(NoSuchElementException e){
            e.printStackTrace();
            return false;
        }
    }
}
