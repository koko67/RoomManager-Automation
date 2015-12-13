package steps;

import api.APILibrary;
import api.EndPoints;
import com.mongodb.DBObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import mongodb.DataBaseDriver;
import org.json.JSONObject;
import org.testng.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;

/**
 * Author: Jorge Avila
 * Date: 12/3/15
 */
public class ConferenceRoomSteps {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomInfoPage;
    private LocationPage locationPage;
    private APILibrary apiLibrary;
    private DataBaseDriver dataBaseDriver;

    private ConferenceRoom conferenceRoom;
    private Location location;

    public ConferenceRoomSteps(ConferenceRoom conferenceRoom){
        homePage = new HomePage();
        this.conferenceRoom = conferenceRoom;
        this.location = new Location();
        this.apiLibrary = new APILibrary();
        dataBaseDriver = new DataBaseDriver();
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                                      .clickOnConferenceRooms("Conference Rooms");

        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(roomName);
    }

    @When("^I assign the current Room to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String locationName){
        location.setName(locationName);
        location.setDisplayName(locationName);
        roomInfoPage.expandLocations()
                .expandDefaultLocation()
                .selectLocationByName(locationName)
                .clickOnSaveButton();

    }


    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Locations page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){
        locationPage = homePage.getLeftMenuPanel()
                .clickOnLocationPage("Locations");
        boolean existAssociated = locationPage.clickEditLocation(location)
                .goLocationAssociationTab()
                .existsRoomAssociated(conferenceRoom);
        Assert.assertTrue(existAssociated);

    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){
        conferenceRoom.setCustomDisplayName(displayName);
        conferenceRoom.setCode(roomCode);
        conferenceRoom.setCapacity(roomCapacity);
        roomInfoPage.fillForm(conferenceRoom);
    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){

    }
}
