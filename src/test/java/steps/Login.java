package steps;

import cucumber.api.java.en.Given;
import ui.pages.admin.LoginPage;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class Login {

    private LoginPage login = new LoginPage();

    @Given("^I log in successfully as \"(.*?)\" with password \"(.*?)\"$")
    public void loginSuccessfully(String userName, String userPassword){

        login.loginPageSuccessfully(userName, userPassword);
    }

}
