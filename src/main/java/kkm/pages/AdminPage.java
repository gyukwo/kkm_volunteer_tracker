package kkm.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kkm.MainFrame;
import kkm.Session;

public class AdminPage {
    private static final String BACKGROUND_COLOR = "#E5F3FD";
    private static final String BLUE = "#7BB6FF";
    private static final String RED =  "D30000";

    public static void showAdminPage(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        String userName = Session.getUserName();
        LocalDateTime signInTime = Session.getSignInTime();

        Text welcomeText = new Text("Welcome, " + (userName == null ? "" : userName) + "!");
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        String formatted;
        if (signInTime == null) {
            formatted = "-";
        } else {
            formatted = signInTime.format(formatter);
        }

        Text statusText = new Text("You are currently signed in since " + formatted);
        statusText.setStyle("-fx-font-size: 14px;");

        Button volunteerList = new Button("Volunteer List");
        volunteerList.setOnAction(e -> VolunteerListPage.showVolunteerList(stage));
        styleButton(volunteerList, BLUE);

        Button addEventsButton = new Button("Add Events");
        addEventsButton.setOnAction(e -> AddEventPage.showAddEvent(stage));
        styleButton(addEventsButton, BLUE);

        Button seeEventsButton = new Button("See Events");
        seeEventsButton.setOnAction(e -> SeeEventsPage.showDailyEvents(stage));
        styleButton(seeEventsButton, BLUE);

        Button signOutButton = new Button("Sign Out");
        signOutButton.setOnAction(e -> {
            Session.signOut();
            MainFrame.loadMenu(stage);
        });
        styleButton(signOutButton, RED);

        vbox.getChildren().addAll(welcomeText, statusText, volunteerList, addEventsButton, seeEventsButton,
                signOutButton);
        vbox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");


        stage.setScene(new Scene(vbox, 800, 600));
        stage.setTitle("Admin Page");
        stage.show();
    }

    private static void styleButton(Button btn, String color) {
        btn.setFont(new Font("Arial", 16));
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-background-radius: 999px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: " + color + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 999px;");

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 999px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: " + color + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 999px;"));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-background-radius: 999px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: " + color + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 999px;"));
    }
}
