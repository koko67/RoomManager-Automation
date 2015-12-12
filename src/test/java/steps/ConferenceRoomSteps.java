package steps;

import api.APILibrary;
import api.EndPoints;
import com.mongodb.DBObject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import mongodb.DataBaseDriver;
import org.json.JSONObject;
import org.testng.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;

/**
 * Author: JorgeAvila
 * Date: 12/3/15
 */
public class ConferenceRoomSteps {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomInfoPage;
    private APILibrary apiLibrary;
    private DataBaseDriver dataBaseDriver;

    private ConferenceRoom conferenceRoom;

    public ConferenceRoomSteps(ConferenceRoom conferenceRoom){
        homePage = new HomePage();
        this.conferenceRoom = conferenceRoom;
        this.apiLibrary = new APILibrary();
        dataBaseDriver = new DataBaseDriver();
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                                      .clickOnConferenceRooms("Conference Rooms");

        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(roomName);
    }

    @When("^I assign the Room \"(.*?)\" to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String roomName, String locationName){
        roomInfoPage.expandLocations()
                .selectLocationByName(locationName)
                .clickOnSaveButton();

    }


    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Locations page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){
        homePage.getLeftMenuPanel()
                .clickOnLocationPage("Locations");
    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){
        conferenceRoom.setCustomDisplayName(displayName);
        conferenceRoom.setCode(roomCode);
        conferenceRoom.setCapacity(roomCapacity);
        roomInfoPage.fillForm(conferenceRoom);

        dataBaseDriver.createConnectionToDB("172.20.208.241:27017");
        DBObject location = dataBaseDriver.getADBObjectFromACollection("rooms", "customDisplayName", displayName);

        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGG" + location.get("_id"));
        String endpoint = EndPoints.LOCATION_BY_ID.replace("#id#", location.get("_id").toString());
        JSONObject response = apiLibrary.getById(endpoint);

        Assert.assertEquals(conferenceRoom.getCustomDisplayName(), response.get("customDisplayName"));
        Assert.assertEquals(conferenceRoom.getCode(), response.get("code"));
        Assert.assertEquals(conferenceRoom.getCapacity(), response.get("capacity"));
    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){

    }
}
