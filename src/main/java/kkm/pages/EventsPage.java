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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;
import kkm.model.Event;

public class EventsPage {

    // Show all events that start today 
    public static void showDailyEvents(Stage stage) {
        ArrayList<Event> allEvents = DB.loadEvents();

        LocalDate today = LocalDate.now();
        List<Event> todaysEvents = new ArrayList<>();
        for (Event e : allEvents) {
            if (e.getEventStart() != null && e.getEventStart().toLocalDate().isEqual(today)) {
                todaysEvents.add(e);
            }
        }

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        //Create Labels for All the Parameters
        int row = 0;
        Label nameLabel = new Label("Name");
        nameLabel.setTextFill(Color.DARKGREEN);
        nameLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(nameLabel, 0, row);
        Label locLabel = new Label("Location");
        locLabel.setTextFill(Color.DARKGREEN);
        locLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(locLabel, 1, row);
        Label startLabel = new Label("Start");
        startLabel.setTextFill(Color.DARKGREEN);
        startLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(startLabel, 2, row);
        Label endLabel = new Label("End");
        endLabel.setTextFill(Color.DARKGREEN);
        endLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(endLabel, 3, row);
        Label volsLabel = new Label("# Volunteers");
        volsLabel.setTextFill(Color.DARKGREEN);
        volsLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(volsLabel, 4, row);
        Label descLabel = new Label("Description");
        descLabel.setTextFill(Color.DARKGREEN);
        descLabel.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(descLabel, 5, row);
        row++;

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mm a");

        if (todaysEvents.isEmpty()) {
            Label none = new Label("No events scheduled for today.");
            none.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(none, 0, row, 6, 1);
        } else {
            for (Event ev : todaysEvents) {
                Label name = new Label(ev.getEventName());
                name.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(name, 0, row);
                Label loc = new Label(ev.getEventLocation());
                loc.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(loc, 1, row);

                String startStr = "";
                if(ev.getEventStart() == null) {
                    startStr = "-";
                } else {
                    startStr = ev.getEventEnd().format(timeFmt);
                }

                String endStr = "";
                if(ev.getEventEnd() == null) {
                    endStr = "-";
                } else {
                    endStr = ev.getEventEnd().format(timeFmt);
                }

                Label start = new Label(startStr);
                start.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(start, 2, row);
                Label end = new Label(endStr);
                end.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(end, 3, row);
                Label vols = new Label(String.valueOf(ev.getEventVolunteers()));
                vols.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(vols, 4, row);
                
                String descString = "";
                if(ev.getEventDescription() != null){
                    descString = ev.getEventDescription();
                }
                Label desc = new Label(descString);
                desc.setFont(MainFrame.TABLE_BODY_FONT);
                gp.add(desc, 5, row);

                row++;
            }
        }

        Button btBack = new Button("Back");
        btBack.setOnAction(e -> VolunteerPage.showVolunteerPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30, 0, 0, 0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btBack);

        Label pageTitle = new Label("Today's Events");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0, 0, 30, 0));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Events Page");
        stage.show();
    }
}