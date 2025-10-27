package kkm.pages;

import java.time.Duration;
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
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;
import kkm.model.Event;

public class HoursPage {

    /**
     * Shows a page with the user's name, total hours, and list of past events
     * attended.
     */
    public static void showUserHours(Stage stage) {
        int userId = Session.getUserId();
        String userName = Session.getUserName();
        String displayName = (userName == null || userName.isEmpty()) ? ("User #" + userId) : userName;

        ArrayList<Event> pastEvents = DB.loadPastEventsForUser(userId);

        double totalHours = 0.0;
        for (Event e : pastEvents) {
            totalHours += computeHours(e.getEventStart(), e.getEventEnd());
        }

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);

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

        if (pastEvents == null || pastEvents.isEmpty()) {
            Label none = new Label("No past events recorded.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 6, 1);
        } else {
            pastEvents.sort((a, b) -> {
                LocalDateTime as = a.getEventStart();
                LocalDateTime bs = b.getEventStart();
                if (as == null && bs == null)
                    return 0;
                if (as == null)
                    return 1;
                if (bs == null)
                    return -1;
                return bs.compareTo(as);
            });

            for (Event e : pastEvents) {
                LocalDateTime s = e.getEventStart();
                LocalDateTime ed = e.getEventEnd();
                String dateStr = (s == null) ? "-" : s.toLocalDate().format(dateFmt);
                String startStr = (s == null) ? "-" : s.format(timeFmt);
                String endStr = (ed == null) ? "-" : ed.format(timeFmt);
                double hrs = computeHours(s, ed);

                Label dateL = new Label(dateStr);
                dateL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(dateL, 0, row);
                Label eventL = new Label(safe(e.getEventName()));
                eventL.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(eventL, 1, row);
                Label locL = new Label(safe(e.getEventLocation()));
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
        return (s == null) ? "" : s;
    }

    private static double computeHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null)
            return 0.0;
        if (end.isBefore(start))
            return 0.0;
        long minutes = Duration.between(start, end).toMinutes();
        return minutes / 60.0;
    }
}