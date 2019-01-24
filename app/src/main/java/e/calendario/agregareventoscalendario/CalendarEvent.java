package e.calendario.agregareventoscalendario;

public class CalendarEvent {
    private String title;
    private String descr;
    private String location;
    private long startTime;
    private long endTime;
    private String idCalendar;

    public CalendarEvent(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setIdCalendar(String idCalendar) {
        this.idCalendar = idCalendar;
    }

    public String getTitle(){
        return title;
    }

    public String getDescr() {
        return descr;
    }

    public String getLocation() {
        return location;
    }

    public String getIdCalendar() {
        return idCalendar;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
