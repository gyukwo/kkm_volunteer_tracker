package kkm;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kkm.model.EventSignup;

public final class Session {
    private static boolean signedIn = false;
    private static LocalDateTime signInTime = null;
    private static int userId = -1;
    private static String userName = "";
    
    private Session() {
    } 

    public static boolean isSignedIn() {
        return signedIn;
    }

    public static void setSignedIn(boolean v) {
        signedIn = v;
    }

    public static LocalDateTime getSignInTime() {
        return signInTime;
    }

    public static void setSignInTime(LocalDateTime t) {
        signInTime = t;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int id) {
        userId = id;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String n) {
        userName = n;
    }

    public static void signIn(int id, String name, LocalDateTime time) {
        userId = id;
        userName = name;
        signInTime = time;
        signedIn = true;
    }

    public static void signOut() {
        signedIn = false;
        signInTime = null;
        userId = -1;
        userName = "";
    }

    private static Map<Integer, Boolean> userEventSignups = new HashMap<>();

    public static void setUserEventSignup(int eventId, boolean isSignedUp) {
        userEventSignups.put(eventId, isSignedUp);
    }

    public static boolean isUserSignedUpForEvent(int eventId) {
        return userEventSignups.getOrDefault(eventId, false);
    }

    public static double computeHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null)
            return 0.0;
        if (end.isBefore(start))
            return 0.0;
        double minutes = Duration.between(start, end).toMinutes();
        return minutes / 60.0;
    }

    public static double getTotalHours(int userId) {
        ArrayList<EventSignup> pastEventSignups = DB.loadPastEventSignupsForUser(userId);
        double totalHours = 0;

        for (EventSignup signup : pastEventSignups) {
            totalHours += computeHours(signup.getEventSignupStartTime(), signup.getEventSignupEndTime());
        }

        return totalHours;
    }

}