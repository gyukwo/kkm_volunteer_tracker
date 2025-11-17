package kkm.model;

import java.time.LocalDateTime;

public class EventSignup {
    private int volunteerId;
    private int eventId;
    private String volunteerName;
    private LocalDateTime eventSignupStartTime;
    private LocalDateTime eventSignupEndTime;
    private String eventName;
    private String eventLocation;

    public EventSignup(int volunteerId, int eventId, LocalDateTime eventSignupStartTime,LocalDateTime eventSignupEndTime) {
        this.volunteerId = volunteerId;
        this.eventId = eventId;
        this.eventSignupStartTime = eventSignupStartTime;
        this.eventSignupEndTime = eventSignupEndTime;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }
    
    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getEventSignupStartTime() {
        return eventSignupStartTime;
    }

    public void setEventSignupStartTime(LocalDateTime eventSignupStartTime) {
        this.eventSignupStartTime = eventSignupStartTime;
    }

    public LocalDateTime getEventSignupEndTime() {
        return eventSignupEndTime;
    }

    public void setEventSignupEndTime(LocalDateTime eventSignupEndTime) {
        this.eventSignupEndTime = eventSignupEndTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
