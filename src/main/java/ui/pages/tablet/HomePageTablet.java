package ui.pages.tablet;

import entities.Meeting;
import framework.UIMethods;
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
public class HomePageTablet extends BasePageObject {


    @FindBy(xpath = "//div[contains(@class,'room-name')]/span[contains(@class,'room-label')]")
    WebElement roomName;

    @FindBy(xpath = "//div[@class='tile-label' and contains(text(), 'Schedule')]")
    WebElement scheduleButton;

    @FindBy(xpath = "//div[@class='meeting-title ng-binding' and contains(text(), 'Available')]")
    WebElement availableButton;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(roomName));
    }

    public HomePageTablet(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * Go to the Schedule page
     * @return a new Schedule page
     */
    public SchedulePageTablet goToSchedulePage() {
        availableButton.click();
        return new SchedulePageTablet();
    }

    /**
     * Verify if the next meeting is displayed
     * @param meeting obtain the meeting name
     * @return true or false
     */
    public Boolean isDisplayedTheNextMeeting(Meeting meeting){
        return UIMethods.waitElementIsPresent(3, By.xpath("//span[@class='vis-item-content' and contains(text(), '" + meeting.getSubject() + "')]"));
    }


}

