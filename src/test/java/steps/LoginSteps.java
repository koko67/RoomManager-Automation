package steps;

import common.CommonMethods;
import cucumber.api.java.en.Given;
import ui.pages.admin.HomePage;
import ui.pages.admin.LoginPage;


/**
 * User: Ronald Butron
 * Date: 12/3/15
 */
public class LoginSteps {

    private LoginPage login;
    private HomePage homePage;
    public LoginSteps(){
        login = new LoginPage();
    }

    @Given("^I log in successfully as \"(.*?)\" with password \"(.*?)\"$")
    public void loginSuccessfully(String userName, String userPassword){

        if(CommonMethods.isItInTheLoginPage()){
            homePage = login.loginPageSuccessfully(userName, userPassword);
        }
    }

}
