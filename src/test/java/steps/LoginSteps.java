package steps;

import cucumber.api.java.en.Given;
import ui.pages.LoginPage;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class LoginSteps {

    private LoginPage login = new LoginPage();

    public LoginSteps(){
        login = new LoginPage();
    }

    @Given("^I log in successfully as \"(.*?)\" with password \"(.*?)\"$")
    public void loginSuccessfully(String userName, String userPassword){
        login.loginPageSuccessfully(userName, userPassword);
    }

}
