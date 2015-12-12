package utils;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/11/15
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LeftBarOptions {
    EMAIL_SERVER ("Email Servers"),
    CONFERENCE_ROOMS("Conference Rooms"),
    LOCATIONS("Locations");

    public final String namePage;

    private LeftBarOptions(String namePage) {
        this.namePage = namePage;
    }

    public String getToPage(){
        return namePage;
    }
}
