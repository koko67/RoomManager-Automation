package ui.pages.tablet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/10/15
 */
public class StatusPageTablet extends BasePageObject {

    @FindBy(xpath = "//div[@type='button']")
    WebElement roomDropDownButton;

    @FindBy(xpath = "//input[@type='text']")
    WebElement  searchRoomInput;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement startNowButton;

    @FindBy(xpath = "//div[@class=ng-binding ng-scope and contains(., 'Connection setting was updated')]")
    WebElement connectionInfo;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=ng-binding ng-scope and contains(., 'Connection setting was updated')]")));
    }

    public StatusPageTablet(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public HomePageTablet selectRoomSuccessfully(String roomName){
        roomDropDownButton.click();
        searchRoomInput.clear();
        searchRoomInput.sendKeys(roomName);
        WebElement selectRoom = driver.findElement(By.xpath("//strong[contains(., '" + roomName + "')]"));
        selectRoom.click();
        startNowButton.click();
        return new HomePageTablet();
    }
}
