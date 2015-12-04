package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class Login {

    @Given("^I log in successfully as \"(.*?)\" with password \"(.*?)\"$")
    public void loginSuccessfully(String userName, String userPassword){

    }
    @Then("^I should log in successfully$")
    public void loginSuccessfully(){}
}
