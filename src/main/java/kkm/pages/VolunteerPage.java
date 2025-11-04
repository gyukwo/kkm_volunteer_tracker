package kkm.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kkm.MainFrame;
import kkm.Session;

public class VolunteerPage {

    public static void showVolunteerPage(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        // From Session
        String userName = Session.getUserName();
        LocalDateTime signInTime = Session.getSignInTime();

        Text welcomeText = new Text("Welcome, " + (userName == null ? "" : userName) + "!");
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        String formatted = (signInTime == null) ? "-" : signInTime.format(formatter);

        // TODO right now this is for signed in to account, but I wanted it to be since
        // u signed in for an event
        String signInStatus = Session.isSignedIn()
                ? "You are currently signed in since " + formatted
                : "You are currently NOT signed in for an event";
        Text statusText = new Text(signInStatus);
        statusText.setStyle("-fx-font-size: 14px;");

        Button dailyEventsButton = new Button("Daily Events");
        dailyEventsButton.setOnAction(e -> EventsPage.showDailyEvents(stage));

        Button totalHoursButton = new Button("Total Hours");
        totalHoursButton.setOnAction(e -> HoursPage.showUserHours(stage));

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(e -> {
            Session.signOut();
            MainFrame.loadMenu(stage);
        });

        vbox.getChildren().addAll(welcomeText, statusText,
                dailyEventsButton, totalHoursButton, signOutButton);

        stage.setScene(new Scene(vbox, 400, 300));
        stage.setTitle("Volunteer Page");
        stage.show();
    }
}