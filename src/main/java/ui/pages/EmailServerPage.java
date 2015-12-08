package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class EmailServerPage extends BasePageObject{
    @FindBy(xpath = "//a[contains(@class,'list-group-item')]")
    WebElement mailServer;

    private LeftMenuPanel leftMenuBar = new LeftMenuPanel();

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(mailServer));
    }
    public LocationPage goToLocationPage(String namePage) {
        leftMenuBar.clickOnLocationPage(namePage);
        return new LocationPage();
    }
}
