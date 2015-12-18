package steps;

import api.APILibrary;
import api.APILocationMethods;
import api.APIRoomMethods;
import api.EndPoints;
import commons.DomainAppConstants;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import framework.UIMethods;
import mongodb.DataBaseRoomMethods;
import mongodb.DataBaseLocationMethods;
import org.json.JSONObject;
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
    @Given("^I go to the Location page$")
    public void goToAPage(){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage();
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

    @And("^the Location should be obtained by API request$")
    public void isTheLocationObtainedByAPI(){
        String id = DataBaseLocationMethods.obtainId(location);
        location.setId(id);
        JSONObject response = APILocationMethods.getLocationByID(location);

        Assert.assertEquals("the location name is the same that was assigned", response.getString(DomainAppConstants.KEY_NAME), location.getName());
        Assert.assertEquals("the location display name is the same that was assigned", response.getString(DomainAppConstants.KEY_CUSTOM_NAME), location.getDisplayName());
    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){
        location.setName(locationName);
        location.setDisplayName(locationName);

        JSONObject response = APILocationMethods.postLocation(location);

        location.setId(response.getString(DomainAppConstants.KEY_ID));
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage();

        UIMethods.refreshPage();
        locationInfoPage = locationPage.clickEditLocation(location);
    }

    @When("^I associate the Room \"(.*?)\" with the Location$")
    public void associateRoomToLocation(String roomName){
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
        locationAssociationsPage.clickOnRoomAssociateToLocation(roomName).saveLocation();
    }

    @Then("^the Room \"(.*?)\" should be associated to the Location in the Conference Rooms page$")
    public void isTheRoomDisplayInTheLocation(String roomName){
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms();
        conferenceRoom.setDisplayName(roomName);
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(conferenceRoom.getDisplayName());

        boolean isLocationInRoomInfo = roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();

        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }

    @And("^the current Room obtained by API should contains the reference of the Location$")
    public void isTheRoomAObtainedByAPI(){
        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);

        JSONObject room = APIRoomMethods.obtainRoom(conferenceRoom);
        Assert.assertEquals("the location id is the same",room.get(DomainAppConstants.KEY_LOCATION_ID), location.getId());
    }

    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){
        location.setName(customName);
        location.setDisplayName(displayName);

        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoom.setLocation(location);

        JSONObject responseLocation = APILocationMethods.postLocation(location);
        location.setId(responseLocation.getString(DomainAppConstants.KEY_ID));

        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);

        APIRoomMethods.associateLocationToRoom(location, conferenceRoom);

        UIMethods.refreshPage();
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage();
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

        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms();
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = !roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();

        Assert.assertTrue("The location is displayed in the Available Column", isLocationAvailable);
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }
    @And("^the current Room obtained by API should not contains the reference of the Location$")
    public void isContainsRoomInLocation(){
        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);

        JSONObject room = APIRoomMethods.obtainRoom(conferenceRoom);
        Assert.assertEquals("The room has not location id ", room.get(DomainAppConstants.KEY_LOCATION_ID), null);
    }
    @After(value = "@LocationForUI")
    public void deleteLocation(){
        String locationId = DataBaseLocationMethods.obtainId(location);
        APILocationMethods.deleteLocation(locationId);
    }
    @After(value = "@AssociateLocation")
    public void deleteLocationAssociate(){
        APILocationMethods.deleteLocation(location.getId());
    }
    @After(value = "@DisassociateLocation")
    public void deleteLocationDisAssociate(){
        APILocationMethods.deleteLocation(location.getId());
    }
}
