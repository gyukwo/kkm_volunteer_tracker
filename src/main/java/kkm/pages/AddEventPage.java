package kkm.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;

public class AddEventPage {

    public static void showAddEvent(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Create New Event");
        title.setFont(MainFrame.PAGE_HEADING_FONT);

        GridPane form = new GridPane();
        form.setAlignment(Pos.TOP_CENTER);
        form.setHgap(12);
        form.setVgap(12);

        Label nameL = new Label("Event Name:");
        nameL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField nameF = new TextField();

        Label locL = new Label("Location:");
        locL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField locF = new TextField();

        Label startL = new Label("Start (yyyy-MM-dd HH:mm:ss):");
        startL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField startF = new TextField();
        startF.setPromptText("e.g. 2025-11-16 10:00:00");

        Label endL = new Label("End (yyyy-MM-dd HH:mm:ss):");
        endL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField endF = new TextField();
        endF.setPromptText("e.g. 2025-11-16 12:00:00");

        Label volsL = new Label("# Volunteers Needed:");
        volsL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField volsF = new TextField();
        volsF.setPromptText("e.g. 10");

        Label descL = new Label("Description:");
        descL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextArea descF = new TextArea();
        descF.setPrefRowCount(3);
        descF.setWrapText(true);

        int row = 0;
        form.add(nameL, 0, row);
        form.add(nameF, 1, row++);
        form.add(locL, 0, row);
        form.add(locF, 1, row++);
        form.add(startL, 0, row);
        form.add(startF, 1, row++);
        form.add(endL, 0, row);
        form.add(endF, 1, row++);
        form.add(volsL, 0, row);
        form.add(volsF, 1, row++);
        form.add(descL, 0, row);
        form.add(descF, 1, row++);

        Label error = new Label();
        error.setTextFill(Color.RED);
        error.setFont(MainFrame.TABLE_BODY_FONT);

        Button btCreate = new Button("Create Event");
        Button btBack = new Button("Back");

        btCreate.setOnAction(e -> {
            String eventName = nameF.getText().trim();
            String eventLocation = locF.getText().trim();
            String eventStart = startF.getText().trim();
            String eventEnd = endF.getText().trim();
            String volsText = volsF.getText().trim();
            String eventDescription = descF.getText().trim();

            if (eventName.isEmpty() || eventLocation.isEmpty() ||
                eventStart.isEmpty() || eventEnd.isEmpty() ||
                volsText.isEmpty()) {
                error.setText("All fields except description are required.");
                error.setTextFill(Color.RED);
                return;
            }

            int eventVolunteers;
            try {
                eventVolunteers = Integer.parseInt(volsText);
            } catch (NumberFormatException ex) {
                error.setText("Number of volunteers must be an integer.");
                error.setTextFill(Color.RED);
                return;
            }

            try {
                DB.insertEvent(eventName, eventLocation, eventStart, eventEnd,
                               eventVolunteers, eventDescription);
                error.setTextFill(Color.GREEN);
                error.setText("Event created successfully!");

                nameF.clear();
                locF.clear();
                startF.clear();
                endF.clear();
                volsF.clear();
                descF.clear();

            } catch (Exception ex) {
                error.setTextFill(Color.RED);
                error.setText("Error creating event. Check console for details.");
            }
        });

        btBack.setOnAction(e -> {
            AdminPage.showAdminPage(stage);
            MainFrame.loadMenu(stage);
        });

        HBox actions = new HBox(15, btCreate, btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(title, form, error, actions);

        Scene scene = new Scene(root, 600, 420);
        stage.setScene(scene);
        stage.setTitle("Add Event");
        stage.show();
    }
}