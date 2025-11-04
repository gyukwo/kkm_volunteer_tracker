package kkm;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class Session {
    private static boolean signedIn = false;
    private static LocalDateTime signInTime = null;
    private static int userId = -1;
    private static String userName = "";

    private Session() {
    } // no instances

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

    // A map to store event sign-up statuses for the user
    private static Map<Integer, Boolean> userEventSignups = new HashMap<>();

    // Method to set the sign-up status for a user for a specific event
    public static void setUserEventSignup(int eventId, boolean isSignedUp) {
        userEventSignups.put(eventId, isSignedUp);
    }

    // Method to get the sign-up status for a specific event
    public static boolean isUserSignedUpForEvent(int eventId) {
        return userEventSignups.getOrDefault(eventId, false);
    }

}