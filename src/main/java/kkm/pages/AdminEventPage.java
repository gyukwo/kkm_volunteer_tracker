package kkm.pages;

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
import kkm.model.Event;
import kkm.model.EventSignup;
import kkm.Session;

public class AdminEventPage {
    // time zone stuff from
    // https://docs.oracle.com/javase/8/docs/api/java/util/TimeZone.html
    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId NEW_YORK = ZoneId.of("America/New_York");
    
    private static final String BACKGROUND_COLOR = "#E5F3FD";

    public static void showEvent(Stage stage, Event event) {
        int eventId = event.getEventId();

        ArrayList<EventSignup> signups = DB.loadEventSignupsForEvent(eventId);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        Label title = new Label("Event: " + event.getEventName());
        title.setFont(MainFrame.PAGE_HEADING_FONT);

        GridPane header = new GridPane();
        header.setAlignment(Pos.TOP_CENTER);
        header.setHgap(20);
        header.setVgap(10);

        // date time formatter from
        // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

        String dateStr;
        String startStr;
        String endStr;

        LocalDateTime rawStart = event.getEventStart();
        LocalDateTime rawEnd = event.getEventEnd();

        if (rawStart == null) {
            dateStr = "-";
            startStr = "-";
        } else {
            dateStr = rawStart.toLocalDate().format(dateFmt);
            startStr = rawStart.format(timeFmt);
        }

        if (rawEnd == null) {
            endStr = "-";
        } else {
            endStr = rawEnd.format(timeFmt);
        }

        Label dateH = new Label("Date:");
        dateH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(dateH, 0, 0);
        Label dateV = new Label(dateStr);
        dateV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(dateV, 1, 0);

        Label locH = new Label("Location:");
        locH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(locH, 0, 1);
        Label locV = new Label(safe(event.getEventLocation()));
        locV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(locV, 1, 1);

        Label startH = new Label("Start:");
        startH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(startH, 0, 2);
        Label startV = new Label(startStr);
        startV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(startV, 1, 2);

        Label endH = new Label("End:");
        endH.setFont(MainFrame.TABLE_HEADING_FONT);
        header.add(endH, 0, 3);
        Label endV = new Label(endStr);
        endV.setFont(MainFrame.TABLE_BODY_FONT);
        header.add(endV, 1, 3);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 0, 0, 0));

        int row = 0;
        Label volH = new Label("Volunteer");
        volH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(volH, 0, row);

        Label signupStartH = new Label("Start Time");
        signupStartH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(signupStartH, 1, row);

        Label signupEndH = new Label("End Time");
        signupEndH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(signupEndH, 2, row);

        Label hoursH = new Label("Hours");
        hoursH.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(hoursH, 3, row);

        row++;

        if (signups == null || signups.isEmpty()) {
            Label none = new Label("No volunteers signed up for this event yet.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 4, 1);
        } else {
            signups.sort((a, b) -> {
                LocalDateTime as = a.getEventSignupStartTime();
                LocalDateTime bs = b.getEventSignupStartTime();
                if (as == null && bs == null)
                    return 0;
                if (as == null)
                    return 1;
                if (bs == null)
                    return -1;
                return as.compareTo(bs);
            });

            for (EventSignup s : signups) {
                LocalDateTime st = s.getEventSignupStartTime();
                LocalDateTime ed = s.getEventSignupEndTime();

                String startCell;
                if (st == null) {
                    startCell = "-";
                } else {
                    ZonedDateTime nyStart = st.atZone(UTC).withZoneSameInstant(NEW_YORK);
                    startCell = nyStart.format(timeFmt);
                }

                String endCell;
                if (ed == null) {
                    endCell = "-";
                } else {
                    ZonedDateTime nyEnd = ed.atZone(UTC).withZoneSameInstant(NEW_YORK);
                    endCell = nyEnd.format(timeFmt);
                }

                double hrs = Session.computeHours(st, ed);

                String volunteerLabel = DB.getUserNameByUserId(s.getVolunteerId());
                if (volunteerLabel == null || volunteerLabel.trim().isEmpty()) {
                    volunteerLabel = "User #" + s.getVolunteerId();
                }

                Label volL = new Label(volunteerLabel);
                volL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(volL, 0, row);

                Label startL = new Label(startCell);
                startL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(startL, 1, row);

                Label endL = new Label(endCell);
                endL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(endL, 2, row);

                Label hoursL = new Label(String.format("%.2f", hrs));
                hoursL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(hoursL, 3, row);

                row++;
            }
        }

        Button btBack = new Button("Back");
        btBack.setOnAction(e -> SeeEventsPage.showDailyEvents(stage));

        HBox actions = new HBox(btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(15, 0, 0, 0));

        root.getChildren().addAll(title, header, gp, actions);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Event Details");
        stage.show();
    }

    private static String safe(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

}