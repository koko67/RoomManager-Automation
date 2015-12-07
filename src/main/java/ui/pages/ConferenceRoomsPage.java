package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class ConferenceRoomsPage extends BasePageObject{

    @FindBy(id = "roomsGrid")
    WebElement roomsTable;

    public RoomSettingsPage openConferenceRoomSettings(String roomName){
        roomsTable.findElement(By.xpath("//span[contains(., '" + roomName + "') ]")).click();
        return new RoomSettingsPage();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {

    }
}
