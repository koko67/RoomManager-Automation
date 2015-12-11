package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Location;
import org.junit.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationSettingsPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import ui.pages.admin.LocationAssociationsPage;
import ui.pages.admin.LocationPage;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class LocationSteps {

    private LocationPage locationPage;
    private LocationSettingsPage locationInfoPage;
    private HomePage homePage;
    private LocationAssociationsPage locationAssociationsPage;
    private ConferenceRoomsPage conferenceRoomPage;

    private Location location;
    private RoomSettingsPage roomInfoPage;

    public LocationSteps(Location location){
        homePage = new HomePage();
        this.location = location;
    }
    @Given("^I go to the \"(.*?)\" page$")
    public void goToAPage(String namePage){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(namePage);
    }

    @When("^I create a Location with Name \"(.*?)\" and Display Name \"(.*?)\"$")
    public void createLocation(String locationName, String displayName){
        location.setName(locationName);
        location.setDisplayName(displayName);
        locationInfoPage = locationPage.clickAddLocation();
        locationInfoPage.fillFormSuccessfully(location);

    }
    /***
     * the method is for workaround for the missing the update feature
     * this step should be removed when the update feature were implement
     */
    @And("^I refresh the page and come back \"(.*?)\"$")
    public void workAround(String namePage){

        homePage.getLeftMenuPanel().clickOnEmailServerPage("Email Servers");
        homePage.getLeftMenuPanel().clickOnLocationPage(namePage);

    }
    @Then("^the Location  should be displayed in the Location page$")
    public void isTheLocationDisplayedInTheLocationPage(){
        Assert.assertTrue("this location was created successfully", locationPage.isLocationNameExists(location.getName()));
    }

    @And("^the Location \"(.*?)\" should be obtained by API request$")
    public void isTheLocationObtainedByAPI(String locationName){

    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage("Locations");
        location.setName(locationName);
        location.setDisplayName(locationName);
        locationPage
                .clickAddLocation()
                .fillFormSuccessfully(location);
        homePage.getLeftMenuPanel().clickOnEmailServerPage("Email Servers");
        homePage.getLeftMenuPanel().clickOnLocationPage("Locations");
        locationInfoPage = locationPage.clickEditLocation(location);
    }

    @When("^I associate the Room \"(.*?)\" with the Location$")
    public void associateRoomToLocation(String roomName){
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
        locationPage = locationAssociationsPage.clickOnRoomAssociateToLocation(roomName);
    }

    @Then("^the Room \"(.*?)\" should be associated to the Location in the Conference Rooms page$")
    public void isTheRoomDisplayInTheLocation(String roomName){
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms("Conference Rooms");
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        Assert.assertTrue("the room has assigned to the location", roomInfoPage.isLocationPresent(location));
    }

    @And("^the Location \"(.*?)\" should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheRoomAObtainedByAPI(String locationName, String roomName){

    }
    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){

    }
    @And("^I open the Location and I select the Locations Associations tab$")
    public void openLocation(){

    }
    @When("^I dis-associate the Room \"(.*?)\" of the Location \"(.*?)\"$")
    public void disAssociateRoomToLocation(String roomName, String customName){}
    @Then("^The Room \"(.*?)\" should be displayed in the column of \"(.*?)\"$")
    public void isRoomDisplayedAssocite(String roomName){}
    @And("^the Location \"(.*?)\" obtained by API request should not contains the Room \"(.*?)\"$")
    public void isContainsRoomInLocation(String customName, String roomName){}
}
