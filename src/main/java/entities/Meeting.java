package entities;

/**
 * User: RonaldButron
 * Date: 12/11/15
 */
public class Meeting {

    private String subject;
    private String attendees;
    private String hourFrom;
    private String hourTo;
    private String organizer;
    private String body;

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setAttendees(String attendees){
        this.attendees = attendees;
    }

    public void setHourFrom(String hourFrom){
        this.hourFrom = hourFrom;
    }

    public void setHourTo(String hourTo){
        this.hourTo = hourTo;
    }

    public void setOrganizer(String organizer){
        this.organizer = organizer;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getSubject(){
        return subject;
    }

    public String getAttendees(){
        return attendees;
    }

    public String getHourFrom(){
        return hourFrom;
    }

    public String getHourTo(){
        return hourTo;
    }

    public String getOrganizer(){
        return organizer;
    }

    public String getBody(){
        return  body;
    }

    public void setAllForm(String organizer, String subject, String hourFrom, String hourTo, String attendees, String body){
        this.subject = subject;
        this.organizer = organizer;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.attendees = attendees;
        this.body = body;
    }

    public void setUpdateForm(String subject, String from, String to, String body) {
        this.subject = subject;
        this.hourFrom = from;
        this.hourTo = to;
        this.body = body;
    }
}
