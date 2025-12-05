package kkm.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.model.EventSignup;

public class VolunteerListPage {

    private static final String BACKGROUND_COLOR = "#E5F3FD";
    private static final String RED =  "D30000";
    private static final String BLUE = "#7BB6FF";

    public static void showVolunteerList(Stage stage) {
        ArrayList<Integer> volunteerIds = DB.loadVolunteerIds();

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Label title = new Label("Volunteer List");
        title.setFont(MainFrame.PAGE_HEADING_FONT);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        int row = 0;
        Label nameH = new Label("Volunteer");
        nameH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(nameH, 0, row);

        Label hoursH = new Label("Total Hours");
        hoursH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(hoursH, 1, row);

        Label lastDateH = new Label("Last Date Volunteered");
        lastDateH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(lastDateH, 2, row);

        Label actionH = new Label("Action");
        actionH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(actionH, 3, row);

        row++;

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (volunteerIds == null || volunteerIds.isEmpty()) {
            Label none = new Label("No volunteers found.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 4, 1);
        } else {
            for (Integer volunteerId : volunteerIds) {
                if (volunteerId == null) {
                    continue;
                }

                String volunteerName = DB.getUserNameByUserId(volunteerId);
                if (volunteerName == null || volunteerName.isEmpty()) {
                    volunteerName = "User #" + volunteerId;
                }

                ArrayList<EventSignup> signups = DB.loadPastEventSignupsForUser(volunteerId);

                double totalHours = 0.0;
                LocalDate lastDate = null;

                if (signups != null && !signups.isEmpty()) {
                    for (EventSignup es : signups) {
                        LocalDateTime start = es.getEventSignupStartTime();
                        LocalDateTime end = es.getEventSignupEndTime();

                        totalHours += computeHours(start, end);

                        if (start != null) {
                            LocalDate d = start.toLocalDate();
                            if (lastDate == null || d.isAfter(lastDate)) {
                                lastDate = d;
                            }
                        }
                    }
                }

                String lastDateStr;
                if (lastDate == null) {
                    lastDateStr = "-";
                } else {
                    lastDateStr = lastDate.format(dateFmt);
                }

                Label nameL = new Label(volunteerName);
                nameL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(nameL, 0, row);

                Label hoursL = new Label(String.format("%.2f", totalHours));
                hoursL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(hoursL, 1, row);

                Label lastDateL = new Label(lastDateStr);
                lastDateL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(lastDateL, 2, row);

                Button viewBt = new Button("View");
                viewBt.setOnAction(e -> {
                    PDFPage.createPDF(stage, volunteerId);
                });
                styleButton(viewBt, BLUE);
                gp.add(viewBt, 3, row);

                row++;
            }
        }

        Button btBack = new Button("Back");
        btBack.setOnAction(e -> AdminPage.showAdminPage(stage));
        styleButton(btBack, RED);

        HBox actions = new HBox(btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(15, 0, 0, 0));

        root.getChildren().addAll(title, gp, actions);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Volunteer List");
        stage.show();
    }

    private static double computeHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0.0;
        }
        if (end.isBefore(start)) {
            return 0.0;
        }
        double minutes = Duration.between(start, end).toMinutes();
        return minutes / 60.0;
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