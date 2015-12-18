package ui.pages.admin;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * User: RonaldButron
 * Date: 12/4/15
 */
public class HomePage extends BasePageObject {

    @FindBy(xpath = "//a[@class='navbar-brand' and contains(text(), 'Room Manager')]")
    WebElement nameWebPage;

    private LeftMenuPanel leftMenuPanel;
    private Header header;

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(nameWebPage));
    }

    public HomePage(){
        leftMenuPanel = new LeftMenuPanel();
        header = new Header();
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public Header getHeader(){
        return header;
    }

    public LeftMenuPanel getLeftMenuPanel(){
        return leftMenuPanel;
    }
}
