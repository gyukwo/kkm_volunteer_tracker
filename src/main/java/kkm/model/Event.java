package kkm.model;

import java.time.LocalDateTime;

public class Event {
    private int eventId;
    private String eventName;
    private String eventLocation;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private int eventVolunteers;
    private String eventDescription;

    public Event(int eventId, String eventName, String eventLocation, LocalDateTime eventStart, LocalDateTime eventEnd,
            int eventVolunteers, String eventDescription) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventVolunteers = eventVolunteers;
        this.eventDescription = eventDescription;
    }

    public Event(String eventName, String eventLocation, LocalDateTime eventStart, LocalDateTime eventEnd,
            int eventVolunteers, String eventDescription) {
        this.eventId = -1; // default placeholder
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventVolunteers = eventVolunteers;
        this.eventDescription = eventDescription;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public int getEventVolunteers() {
        return eventVolunteers;
    }

    public void setEventVolunteers(int eventVolunteers) {
        this.eventVolunteers = eventVolunteers;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

}
