package kkm.pages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;
import kkm.model.EventSignup;

public class HoursPage {

    public static void showUserHours(Stage stage) {
        int userId = Session.getUserId();
        String userName = Session.getUserName();
        String displayName = (userName == null || userName.isEmpty())
                ? ("User #" + userId)
                : userName;

        ArrayList<EventSignup> pastEventSignups = DB.loadPastEventSignupsForUser(userId);

        double totalHours = Session.getTotalHours(userId);


        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label pageTitle = new Label("Volunteer Hours");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0, 0, 20, 0));

        GridPane header = new GridPane();
        header.setAlignment(Pos.TOP_CENTER);
        header.setHgap(20);
        header.setVgap(10);

        Label nameH = new Label("Volunteer:");
        nameH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(nameH, 0, 0);

        Label nameV = new Label(displayName);
        nameV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(nameV, 1, 0);

        Label totalH = new Label("Total Hours:");
        totalH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(totalH, 0, 1);

        Label totalV = new Label(String.format("%.2f", totalHours));
        totalV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(totalV, 1, 1);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);
        gp.setPadding(new Insets(20, 0, 0, 0));

        int row = 0;
        Label dateH = new Label("Date");
        dateH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(dateH, 0, row);

        Label nameEH = new Label("Event");
        nameEH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(nameEH, 1, row);

        Label locH = new Label("Location");
        locH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(locH, 2, row);

        Label startH = new Label("Start");
        startH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(startH, 3, row);

        Label endH = new Label("End");
        endH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(endH, 4, row);

        Label hoursH = new Label("Hours");
        hoursH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(hoursH, 5, row);

        row++;

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

        ZoneId utc = ZoneId.of("UTC");
        ZoneId est = ZoneId.of("America/New_York");

        if (pastEventSignups == null || pastEventSignups.isEmpty()) {
            Label none = new Label("No past events recorded.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 6, 1);
        } else {
            pastEventSignups.sort((a, b) -> {
                LocalDateTime as = a.getEventSignupStartTime();
                LocalDateTime bs = b.getEventSignupStartTime();
                if (as == null && bs == null) return 0;
                if (as == null) return 1;
                if (bs == null) return -1;
                return bs.compareTo(as);
            });

            for (EventSignup signup : pastEventSignups) {
                LocalDateTime s = signup.getEventSignupStartTime();
                LocalDateTime ed = signup.getEventSignupEndTime();

                String dateStr = "-";
                String startStr = "-";
                String endStr = "-";

                if (s != null) {
                    ZonedDateTime utcStart = s.atZone(utc);
                    ZonedDateTime estStart = utcStart.withZoneSameInstant(est);

                    dateStr = estStart.toLocalDate().format(dateFmt);
                    startStr = estStart.format(timeFmt);
                }

                if (ed != null) {
                    ZonedDateTime utcEnd = ed.atZone(utc);
                    ZonedDateTime estEnd = utcEnd.withZoneSameInstant(est);

                    endStr = estEnd.format(timeFmt);
                }

                double hrs = Session.computeHours(s, ed);

                Label dateL = new Label(dateStr);
                dateL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(dateL, 0, row);

                Label eventL = new Label(safe(signup.getEventName()));
                eventL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(eventL, 1, row);

                Label locL = new Label(safe(signup.getEventLocation()));
                locL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(locL, 2, row);

                Label startL = new Label(startStr);
                startL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(startL, 3, row);

                Label endL = new Label(endStr);
                endL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(endL, 4, row);

                Label hoursL = new Label(String.format("%.2f", hrs));
                hoursL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(hoursL, 5, row);

                row++;
            }
        }

        Button btBack = new Button("Back");
        btBack.setOnAction(e -> VolunteerPage.showVolunteerPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30, 0, 0, 0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btBack);

        root.getChildren().addAll(pageTitle, header, gp, hbox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Volunteer Hours");
        stage.show();
    }

    private static String safe(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

}