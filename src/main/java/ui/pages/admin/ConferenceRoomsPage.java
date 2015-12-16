package ui.pages.admin;

import entities.ConferenceRoom;
import entities.Resource;
import framework.UIMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    /**
     * method that click on the resource button on the header of conference room page
     * @param resource is the resource name
     * @return return conference room page with new elements visible in the DOM
     */
    public ConferenceRoomsPage clickOnResourcesDisplayButton(Resource resource) {
        By resourcesLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]");
        WebElement resourcesButton = driver.findElement(resourcesLocator);
        resourcesButton.click();
        return new ConferenceRoomsPage();
    }

    /**
     * this method is for sure that the resource is displayed in the table
     * @param resource receive the resource that should be displayed in the table
     * @return
     */
    public ConferenceRoomsPage makeSureResourcesIsSelect(Resource resource) {
        By resourceNameLocator = By.xpath("//div[@class='ngHeaderContainer']//div[contains(text(),'" + resource.getCustomName() + "')]");
        if (!UIMethods.isElementPresent(resourceNameLocator)){
            By resourceButtonLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]");
            WebElement resourceButton = driver.findElement(resourceButtonLocator);
            resourceButton.click();
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(resourceNameLocator));
        }
        return new ConferenceRoomsPage();
    }

    /**
     * this method verify that the resources assigned are same that the displayed
     * @param resource receive the resource that was assigned
     * @param conferenceRoom the room where is verified
     * @return
     */
    public boolean isTheResourceCorrect(Resource resource, ConferenceRoom conferenceRoom) {
        By iconResourceLocator = By.xpath("//span[contains(.,'" + conferenceRoom.getCustomDisplayName() + "')]/../../../following-sibling::div[contains(@class,'col3')]//span");
        try {
            WebElement iconResources = driver.findElement(iconResourceLocator);
            return iconResources.getAttribute("class").contains(resource.getFontIcon());
        }catch (NoSuchElementException e){
            System.out.println("Resource not found");
            return false;
        }
    }

    /**
     * this method verify the quantity resources assigned are same that the displayed
     * @param resource receive the resource that was assigned
     * @param conferenceRoom the room where is verified
     * @return
     */
    public boolean isTheSameQuantityOfResources(Resource resource, ConferenceRoom conferenceRoom) {
        By quantityResourceLocator = By.xpath("//span[contains(.,'" + conferenceRoom.getCustomDisplayName() + "')]/../../../following-sibling::div[contains(@class,'col3')]//span/following::span");
        try {
            WebElement iconResources = driver.findElement(quantityResourceLocator);
            return iconResources.getText().contains(resource.getQuantity());
        }catch (NoSuchElementException e){
            System.out.println("Resource not found");
            return false;
        }
    }

    /**
     * this method verify that room is disable
     * @param conferenceRoom receive the room that was disable
     * @return
     */
    public boolean isRoomEnable(ConferenceRoom conferenceRoom) {
        By roomEnableButtonLocator = By.xpath("//span[contains(.,'Floor1-Room11')]/../../../preceding-sibling::div[contains(@class,'centeredColumn')]//span[contains(@class,'gray')]");
        String colorButton = null;
        try {
            WebElement roomEnableButton = driver.findElement(roomEnableButtonLocator);
            if (conferenceRoom.isEnabled()){
                colorButton = "green";
                System.out.println(colorButton);
            }else{
                colorButton = "gray";
                System.out.println(colorButton);
            }
            return roomEnableButton.getAttribute("class").contains(colorButton);
        }catch (NoSuchElementException e){
            System.out.println("Element not found");
            return false;
        }
    }
}
