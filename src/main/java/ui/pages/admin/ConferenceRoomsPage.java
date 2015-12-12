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
         By resourcesLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]/..");
        WebElement resourcesButton = driver.findElement(resourcesLocator);
        resourcesButton.click();
        return new ConferenceRoomsPage();
    }

    public ConferenceRoomsPage makeSureResourcesIsSelect(Resource resource) {
        By resourceNameLocator = By.xpath("//div[@class='ngHeaderContainer']//div[contains(text(),'" + resource.getCustomName() + "')]");
        if (!UIMethods.isElementPresent(resourceNameLocator)){
            System.out.println("ENTRAAAAAAAAA");
            By resourceButtonLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]");
            WebElement resourceButton = driver.findElement(resourceButtonLocator);
            resourceButton.click();
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(resourceNameLocator));
        }
        return new ConferenceRoomsPage();
    }

    public boolean isTheResourceCorrect(Resource resource, ConferenceRoom conferenceRoom) {
        By iconResourceLocator = By.xpath("//div[.//span[contains(.,'" + conferenceRoom.getCustomDisplayName() + "')] and @class='ng-scope ngRow odd']//div[contains(@class,'animate-if')]/span");
        System.out.println("es el icono del recurso " + resource.getFontIcon());
        try {
            WebElement iconResources = driver.findElement(iconResourceLocator);
            System.out.println("++++==========" + iconResources.getAttribute("class"));
            return iconResources.getAttribute("class").contains(resource.getFontIcon());
        }catch (NoSuchElementException e){
            System.out.println("Resource not found");
            return false;
        }
    }


    public boolean isTheSameQuantityOfResources(Resource resource, ConferenceRoom conferenceRoom) {
        By iconResourceLocator = By.xpath("//div[.//span[contains(.,'" + conferenceRoom.getCustomDisplayName() + "')] and @class='ng-scope ngRow odd']//div[contains(@class,'animate-if')]/span/following::span");
        System.out.println("la cantidad +++++++++++++" + resource.getQuantity());
        try {
            WebElement iconResources = driver.findElement(iconResourceLocator);
            System.out.println("+++++++++++++++++++"+iconResources.getText());
            return iconResources.getText().contains(resource.getQuantity());
        }catch (NoSuchElementException e){
            System.out.println("Resource not found");
            return false;
        }
    }
}
