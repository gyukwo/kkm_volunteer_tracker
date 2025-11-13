package kkm.model;

import java.time.LocalDateTime;

public class EventSignup {
    private int eventSignupId;
    private int volunteerId;
    private int eventId;
    private LocalDateTime eventSignupStartTime;
    private LocalDateTime eventSignupEndTime;
    private int eventSignupStatus; 

    public EventSignup(int eventSignupId, int volunteerId, int eventId, 
                       LocalDateTime eventSignupStartTime, LocalDateTime eventSignupEndTime, 
                       int eventSignupStatus) {
        this.eventSignupId = eventSignupId;
        this.volunteerId = volunteerId;
        this.eventId = eventId;
        this.eventSignupStartTime = eventSignupStartTime;
        this.eventSignupEndTime = eventSignupEndTime;
        this.eventSignupStatus = eventSignupStatus;
    }

    public EventSignup(int volunteerId, int eventId, 
                       LocalDateTime eventSignupStartTime, LocalDateTime eventSignupEndTime, 
                       int eventSignupStatus) {
        this.eventSignupId = -1; 
        this.volunteerId = volunteerId;
        this.eventId = eventId;
        this.eventSignupStartTime = eventSignupStartTime;
        this.eventSignupEndTime = eventSignupEndTime;
        this.eventSignupStatus = eventSignupStatus;
    }

    // Getters and setters for all fields
    public int getEventSignupId() {
        return eventSignupId;
    }

    public void setEventSignupId(int eventSignupId) {
        this.eventSignupId = eventSignupId;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
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

    public int getEventSignupStatus() {
        return eventSignupStatus;
    }

    public void setEventSignupStatus(int eventSignupStatus) {
        this.eventSignupStatus = eventSignupStatus;
    }

    public boolean isSignedIn() {
        return this.eventSignupStatus == 1;
    }
}
