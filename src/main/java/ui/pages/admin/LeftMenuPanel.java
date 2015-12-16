package ui.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;
import utils.LeftBarOptions;

/**
 * User: JorgeAvila
 * Date: 12/7/15
 */
public class LeftMenuPanel extends BasePageObject{

    @FindBy(xpath = "//div[@class='panel panel-default']")
    WebElement buttonsPanel;

    WebElement menuButton;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(buttonsPanel));
    }

    public LeftMenuPanel(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public ConferenceRoomsPage clickOnConferenceRooms(){
        By xpathButton = constructXpathButton(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        clickLeftMenuButton(xpathButton);
        return new ConferenceRoomsPage();
    }

    public LocationPage clickOnLocationPage(){
        By xpathButton = constructXpathButton(LeftBarOptions.LOCATIONS.getToPage());
        clickLeftMenuButton(xpathButton);
        return new LocationPage();
    }

    public EmailServerPage clickOnEmailServerPage(){
        By xpathButton = constructXpathButton(LeftBarOptions.EMAIL_SERVER.getToPage());
        clickLeftMenuButton(xpathButton);
        return new EmailServerPage();
    }

    /**
     * construct the web element given a By object
     * @param xpathButton By element created fot finding the web element
     */
    private void clickLeftMenuButton(By xpathButton) {
        menuButton = buttonsPanel.findElement(xpathButton);
        menuButton.click();
    }

    /**
     * Constructs a by object given a name
     * @param buttonName name of the button to construct the By object given a xpath
     * @return the By Object to find
     */
    private By constructXpathButton(String buttonName){
        By button = By.xpath("//ul/li/a[contains(., '"+ buttonName +"')]");
        return button;
    }
}
