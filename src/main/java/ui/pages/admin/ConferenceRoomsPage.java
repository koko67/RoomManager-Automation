package ui.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: Jorge Avila
 * Date: 12/4/15
 */
public class ConferenceRoomsPage extends BasePageObject{

    @FindBy(id = "roomsGrid")
    WebElement roomsTable;

    public ConferenceRoomsPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public RoomSettingsPage openConferenceRoomSettings(String roomName) {
        WebElement room = roomsTable.findElement(By.xpath("//span[contains(., '" + roomName + "') ]/following-sibling::span"));
        Actions action = new Actions(driver);
        action.moveToElement(room).doubleClick().perform();
        return new RoomSettingsPage();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(roomsTable));
    }
}
