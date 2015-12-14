package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import framework.UIMethods;
import org.junit.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationSettingsPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import ui.pages.admin.LocationAssociationsPage;
import ui.pages.admin.LocationPage;
import utils.LeftBarOptions;


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
    private RoomSettingsPage roomInfoPage;

    private Location location;
    private ConferenceRoom conferenceRoom;

    public LocationSteps(Location location){
        homePage = new HomePage();
        this.location = location;
        conferenceRoom = new ConferenceRoom();
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
        UIMethods.switchPages(LeftBarOptions.LOCATIONS.getToPage());
    }
    @Then("^the Location  should be displayed in the Location page$")
    public void isTheLocationDisplayedInTheLocationPage(){
        Assert.assertTrue("this location was created successfully", locationPage.isLocationNameExists(location.getDisplayName()));
    }

    @And("^the Location \"(.*?)\" should be obtained by API request$")
    public void isTheLocationObtainedByAPI(String locationName){

    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(LeftBarOptions.LOCATIONS.getToPage());
        location.setName(locationName);
        location.setDisplayName(locationName);
        locationPage
                .clickAddLocation()
                .fillFormSuccessfully(location);
        UIMethods.switchPages(LeftBarOptions.LOCATIONS.getToPage());
        locationInfoPage = locationPage.clickEditLocation(location);
    }

    @When("^I associate the Room \"(.*?)\" with the Location$")
    public void associateRoomToLocation(String roomName){
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
        locationAssociationsPage.clickOnRoomAssociateToLocation(roomName).saveLocation();
    }

    @Then("^the Room \"(.*?)\" should be associated to the Location in the Conference Rooms page$")
    public void isTheRoomDisplayInTheLocation(String roomName){
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }

    @And("^the Location \"(.*?)\" should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheRoomAObtainedByAPI(String locationName, String roomName){

    }
    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(LeftBarOptions.LOCATIONS.getToPage());
        location.setName(customName);
        location.setDisplayName(displayName);
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setLocation(location);
    }
    @And("^I open the Location and I select the Locations Associations tab$")
    public void openLocation(){
        locationInfoPage = locationPage.clickEditLocation(location);
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
    }
    @When("^I dis-associate the Room \"(.*?)\" of the Location$")
    public void disAssociateRoomToLocation(String roomName){
        locationAssociationsPage.clickOnRoomDisassociateLocation(roomName);
    }
    @Then("^The Room \"(.*?)\" should be displayed in the column of \"(.*?)\"$")
    public void isRoomDisplayedAssocite(String roomName, String state){
        boolean isLocationAvailable = locationAssociationsPage.searchRoomInAvailableColumn(roomName, state);
        locationAssociationsPage.saveLocation();
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = !roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();
        Assert.assertTrue("The location is displayed in the Available Column", isLocationAvailable);
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }
    @And("^the Location \"(.*?)\" obtained by API request should not contains the Room \"(.*?)\"$")
    public void isContainsRoomInLocation(String customName, String roomName){}
}
