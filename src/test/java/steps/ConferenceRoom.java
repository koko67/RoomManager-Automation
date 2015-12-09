package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ui.pages.ConferenceRoomsPage;
import ui.pages.HomePage;
import ui.pages.RoomSettingsPage;

/**
 * Author: JorgeAvila
 * Date: 12/3/15
 */
public class ConferenceRoom {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomSettingsPage;

    public ConferenceRoom(){
        homePage = new HomePage();
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                .clickOnConferenceRooms("Conference Rooms");

        roomSettingsPage = conferenceRoomsPage.openConferenceRoomSettings(roomName);
    }

    @When("^I assign the Room \"(.*?)\" to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String roomName, String locationName){

    }

    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Conference Room page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){

    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){
        roomSettingsPage.fillForm(displayName, roomCode, roomCapacity);
    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){

    }
}
