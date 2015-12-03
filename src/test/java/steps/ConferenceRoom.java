package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class ConferenceRoom {

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName){

    }

    @When("^I assign the Room \"(.*?)\" to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String roomName, String locationName){

    }

    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Conference Room page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){

    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){

    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){

    }
}
