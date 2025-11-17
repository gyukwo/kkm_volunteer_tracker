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

public class AdminPage {
    public static void showAdminPage(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        String userName = Session.getUserName();
        LocalDateTime signInTime = Session.getSignInTime();

        Text welcomeText = new Text("Welcome, " + (userName == null ? "" : userName) + "!");
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        String formatted = (signInTime == null) ? "-" : signInTime.format(formatter);

        Text statusText = new Text("You are currently signed in since " + formatted);
        statusText.setStyle("-fx-font-size: 14px;");

        Button volunteerList = new Button("Volunteer List");
        volunteerList.setOnAction(e -> EventsPage.showDailyEvents(stage));

        Button addEventsButton = new Button("Add Events");
        addEventsButton.setOnAction(e -> AddEventPage.showAddEvent(stage));

        Button seeEventsButton = new Button("See Events");
        seeEventsButton.setOnAction(e -> EventsPage.showDailyEvents(stage));

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(e -> {
            Session.signOut();
            MainFrame.loadMenu(stage);
        });

        vbox.getChildren().addAll(welcomeText, statusText, volunteerList, addEventsButton, seeEventsButton, signOutButton);

        stage.setScene(new Scene(vbox, 400, 300));
        stage.setTitle("Admin Page");
        stage.show();
    }
}
