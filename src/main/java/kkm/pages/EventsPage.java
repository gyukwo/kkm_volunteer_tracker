package kkm.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

public class EventsPage {

    /** Shows all events that start today (by local date of event_start). */
    public static void showDailyEvents(Stage stage) {
        ArrayList<Event> allEvents = DB.loadEvents();

        LocalDate today = LocalDate.now();
        List<Event> todays = new ArrayList<>();

        for (Event e : allEvents) {
            if (e.getEventStart() != null &&
                    e.getEventStart().toLocalDate().isEqual(today)) {
                todays.add(e);
            }
        }
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        // Headings
        int row = 0;
        Label nameH = new Label("Name");
        nameH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(nameH, 0, row);

        Label locH = new Label("Location");
        locH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(locH, 1, row);

        Label startH = new Label("Start");
        startH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(startH, 2, row);

        Label endH = new Label("End");
        endH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(endH, 3, row);

        Label volsH = new Label("# Volunteers");
        volsH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(volsH, 4, row);

        Label descH = new Label("Description");
        descH.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(descH, 5, row);

        row++;

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

        if (todays.isEmpty()) {
            Label none = new Label("No events scheduled for today.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 6, 1);
        } else {
            for (Event ev : todays) {
                Label name = new Label(ev.getEventName());
                name.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(name, 0, row);

                Label loc = new Label(ev.getEventLocation());
                loc.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(loc, 1, row);

                String startStr = ev.getEventStart() == null ? "-" : ev.getEventStart().format(timeFmt);
                String endStr = ev.getEventEnd() == null ? "-" : ev.getEventEnd().format(timeFmt);

                Label start = new Label(startStr);
                start.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(start, 2, row);

                Label end = new Label(endStr);
                end.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(end, 3, row);

                Label vols = new Label(String.valueOf(ev.getEventVolunteers()));
                vols.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(vols, 4, row);

                Label desc = new Label(ev.getEventDescription() == null ? "" : ev.getEventDescription());
                desc.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(desc, 5, row);

                row++;
            }
        }

        Button btMainMenu = new Button("Main Menu");
        btMainMenu.setOnAction(e -> MainFrame.loadMenu(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30, 0, 0, 0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btMainMenu);

        Label pageTitle = new Label("Today's Events");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0, 0, 30, 0));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Events Page");
        stage.show(); // Show the page
    }
}