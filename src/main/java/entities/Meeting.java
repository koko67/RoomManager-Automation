package entities;

/**
 * User: RonaldButron
 * Date: 12/11/15
 */
public class Meeting {
    private String deleteSubject;
    private String subject;
    private String attendees;
    private String hourFrom;
    private String hourTo;
    private String organizer;
    private String body;
    private String roomName;

    public void setRoomName(String roomName){
        this.roomName = roomName;
    }

    public void setDeleteSubject(String subject){
        this.deleteSubject = subject;
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

    public String getDeleteSubject(){
        return deleteSubject;
    }

    public String getRoomName(){
        return roomName;
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
