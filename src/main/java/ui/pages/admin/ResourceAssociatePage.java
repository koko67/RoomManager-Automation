package ui.pages.admin;

import entities.Resource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/12/15
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceAssociatePage extends BasePageObject{
    @FindBy(xpath = "//legend[contains(text(),'Available')]")
    WebElement availableLabel;

    @FindBy(xpath = "//button[.//span[contains(.,'Save')]]")
    WebElement saveButton;

    public ResourceAssociatePage(){
        PageFactory.initElements(driver,this);
        waitUntilPageObjectIsLoaded();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(availableLabel));
    }

    public ResourceAssociatePage clickOnAddResources(Resource resource) {
        By addResourcesLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]/..//following-sibling::div//i[contains(@class,'fa-plus')]/..");
        WebElement addResourceButton = availableLabel.findElement(addResourcesLocator);
        addResourceButton.click();
        return new ResourceAssociatePage();
    }

    public ResourceAssociatePage typeQuantityResources(Resource resource) {
        By quantityResourcesLocator = By.xpath("//span[contains(text(),'" + resource.getCustomName() + "')]/ancestor::div[@ng-repeat='associatedResource in roomResources']//input");
        if (Integer.parseInt(resource.getQuantity()) > 1){
            WebElement typeQuantityResource = driver.findElement(quantityResourcesLocator);
            typeQuantityResource.clear();
            typeQuantityResource.sendKeys(resource.getQuantity());
        }
        return this;
    }

    public ConferenceRoomsPage clickOnSaveButton() {
        saveButton.click();
        return new ConferenceRoomsPage();
    }
}
