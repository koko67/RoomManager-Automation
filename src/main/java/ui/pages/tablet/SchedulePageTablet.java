package ui.pages.tablet;

import common.CommonMethods;
import commons.DomainAppConstants;
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
public class SchedulePageTablet extends BasePageObject {


    @FindBy(id = "txtOrganizer")
    WebElement organizerInput;

    @FindBy(id = "txtSubject")
    WebElement subjectInput;

    @FindBy(xpath = "//button[@class='clean item item-btn']")
    WebElement createButton;

    @FindBy(xpath = "//input[@ng-change='endTimeChanged()']")
    WebElement endTimeInput;

    @FindBy(xpath = "//input[@ng-change='startTimeChanged()']")
    WebElement startTimeInput;

    @FindBy(id = "_value")
    WebElement attendeesInput;

    @FindBy(id = "txtBody")
    WebElement bodyInput;

    @FindBy(xpath = "//div[contains(@class, select-row]")
    WebElement selectAttendees;

    @FindBy(id = "_dropdown")
    WebElement attendeesDropDown;

    @FindBy(id = "go-home")
    WebElement goHomeButton;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(createButton));
    }

    public SchedulePageTablet(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * Insert the subject
     * @param subject of the meeting
     */
    public void setSubject(String subject){
        subjectInput.clear();
        subjectInput.sendKeys(subject);
    }

    /**
     * Insert the organizer
     * @param organizer of the meeting
     */
    public void setOrganizer(String organizer){
        organizerInput.clear();
        organizerInput.sendKeys(organizer);
    }

    /**
     * Insert the attendees
     * @param attendees of the meeting
     */
    public void setAttendees(String attendees){
        attendeesInput.clear();
        attendeesInput.sendKeys(attendees);
        //CommonMethods.elementHighlight(attendeesDropDown);
    }

    /**
     * Insert the start hour
     * @param hourFrom start hour
     */
    public void setHourFrom(String hourFrom){
        startTimeInput.clear();
        startTimeInput.sendKeys(hourFrom);
    }

    /**
     * Insert the end hour
     * @param hourTo end hour
     */
    public void setHourTo(String hourTo){
        endTimeInput.clear();
        endTimeInput.sendKeys(hourTo);
    }

    /**
     * Make click over Create button
     */
    public void createMetting(){
        createButton.click();
    }

    /**
     * Insert the body of the meeting
     * @param body contains
     */
    public void setBodyInput(String body){
        bodyInput.clear();
        bodyInput.sendKeys(body);
    }

    /**
     * Verify if the Config Message is displayed
     * @return
     */
    public Boolean isDisplayedTheConfigMessage(){
        return UIMethods.waitElementIsPresent(3, By.xpath("//div[contains(@class, 'ng-binding') and contains(text(), '" + DomainAppConstants.MEETING_SUCCESSFULLY_CREATED + "')]"));
    }

    /**
     * This method create meeting
     * @param meeting information
     * @return Exchange Credentials page
     */
    public ExchangeCredentialsPage createSuccessfullyAMeeting(Meeting meeting) {
        setOrganizer(meeting.getOrganizer());
        setSubject(meeting.getSubject());
        setHourFrom(meeting.getHourFrom());
        setHourTo(meeting.getHourTo());
        setAttendees(meeting.getAttendees());
        setBodyInput(meeting.getBody());
        createMetting();
        return  new ExchangeCredentialsPage();
    }

    /**
     * Verify if the Meeting is displayed in the Schedule Bar
     * @param meeting information
     * @return true or false
     */
    public boolean isTheMeetingDisplayedInTheScheduleBar(Meeting meeting) {
        return UIMethods.waitElementIsPresent(5, By.xpath("//span[@class='vis-item-content' and contains(text(), '" + meeting.getSubject() + "')]"));
    }

    /**
     * Go to the Tablet Home page
     * @return a new Home page
     */
    public HomePageTablet goHome(){
        goHomeButton.click();
        return new HomePageTablet();
    }
}
