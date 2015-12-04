package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class Location {

    @Given("^I go to the \"(.*?)\" page$")
    public void goToAPage(String namePage){

    }

    @When("^I create a Location with Name \"(.*?)\" and Display Name \"(.*?)\"$")
    public void createLocation(String locationName, String displayName){

    }

    @Then("^the location \"(.*?)\" should be displayed in the Location page$")
    public void isTheLocationDisplayedInTheLocationPage(String locationName){

    }

    @And("^the location \"(.*?)\" should be obtained by API request$")
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
}
