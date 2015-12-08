package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import ui.pages.EmailServerPage;
import ui.pages.HomePage;
import ui.pages.LocationPage;
import ui.pages.LocationSettingsPage;

import static junit.framework.Assert.assertTrue;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class Location {

    private LocationPage locationPage;
    private LocationSettingsPage locationSettingPage;
    private HomePage homePage = new HomePage();

    @Given("^I go to the \"(.*?)\" page$")
    public void goToAPage(String namePage){


        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(namePage);

    }

    @When("^I create a Location with Name \"(.*?)\" and Display Name \"(.*?)\"$")
    public void createLocation(String locationName, String displayName){
        locationSettingPage = locationPage.clickAddLocation();
        locationSettingPage.fillFormSuccessfully(locationName, displayName);
    }

    @Then("^the Location \"(.*?)\" should be displayed in the Location page$")
    public void isTheLocationDisplayedInTheLocationPage(String locationName){
        Assert.assertTrue("this location was created successfully", locationPage.isLocationNameExists(locationName));
    }

    @And("^the Location \"(.*?)\" should be obtained by API request$")
    public void isTheLocationObtainedByAPI(String locationName){

    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){

    }

    @When("^I associate the Room \"(.*?)\" with the Location \"(.*?)\"$")
    public void associateRoomToLocation(String roomName, String locationName){

    }

    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Location page$")
    public void isTheRoomDisplayInTheLocation(String roomName, String locationName){

    }

    @And("^the Location \"(.*?)\" should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheRoomAObtainedByAPI(String locationName, String roomName){

    }
    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){

    }
    @And("^I open the Location \"(.*?)\" and I select the \"(.*?)\" tab$")
    public void openLocation(String customName, String tablocations){

    }
    @When("^I dis-associate the Room \"(.*?)\" of the Location \"(.*?)\"$")
    public void disAssociateRoomToLocation(String roomName, String customName){}
    @Then("^The Room \"(.*?)\" should be displayed in the column of \"(.*?)\"$")
    public void isRoomDisplayedAssocite(String roomName){}
    @And("^the Location \"(.*?)\" obtained by API request should not contains the Room \"(.*?)\"$")
    public void isContainsRoomInLocation(String customName, String roomName){}
}
