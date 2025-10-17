package soccer.pages;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soccer.DB;
import soccer.MainFrame;
import soccer.model.League;

public class LeaguePages {


    public static void showLeagueListPage (Stage stage) {       
        ArrayList<League> leagues = DB.loadLeagues();
        
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label idHeading = new Label("Id");
        idHeading.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(idHeading, 0, 0);

        Label nameHeader = new Label("Name");
        nameHeader.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(nameHeader, 1, 0);

        for (int i=0; i<leagues.size(); i++) {
            final int leagueId = leagues.get(i).getLeagueId();

            Button btModify = new Button("Modify");
            btModify.setOnAction(e -> {
                showModifyLeague(stage, leagueId);
            });

            Button btDelete = new Button("Delete");
            btDelete.setOnAction(e-> {
                DB.deleteLeague(leagueId);
                showLeagueListPage(stage);
            });

            League league = leagues.get(i);

            Label idBody = new Label("" + league.getLeagueId());
            idBody.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(idBody, 0, i+1);

            Label nameBody = new Label("" + league.getLeagueName());
            nameBody.setFont(MainFrame.TABLE_BODY_FONT);
            gp.add(nameBody, 1, i+1);

            gp.add(btModify, 2, i+1);

            if (league.hasTeams() == false) {
                gp.add(btDelete, 3, i+1);
            }
        }

        Button btAddLeague = new Button("Add New League");
        btAddLeague.setOnAction(e -> showAddLeague(stage));

        Button btMainMenu = new Button("Main Menu");
        btMainMenu.setOnAction(e -> MainFrame.loadMenu(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btAddLeague, btMainMenu);

        Label pageTitle = new Label("Leagues");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);


        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }


    public static void showAddLeague (Stage stage) {       
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label labelName = new Label("Name");
        labelName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelName, 0, 0);

        TextField txtName = new TextField();
        gp.add(txtName, 1, 0);

        Button btAddLeague = new Button("Add");
        btAddLeague.setOnAction(e -> {
            DB.insertLeague(txtName.getText());
            showLeagueListPage(stage);
        });
        
        Button btMainMenu = new Button("Cancel");
        btMainMenu.setOnAction(e -> showLeagueListPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btAddLeague, btMainMenu);

        Label pageTitle = new Label("Add League");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }


    public static void showModifyLeague (Stage stage, int leagueId) {       
        League league = DB.loadLeague(leagueId);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(10);

        Label labelLeagueIdHeading = new Label("Id");
        labelLeagueIdHeading.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelLeagueIdHeading, 0, 0);

        Label labelLeagueId = new Label("" + leagueId);
        labelLeagueId.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(labelLeagueId, 1, 0);

        Label labelName = new Label("Name");
        labelName.setFont(MainFrame.TABLE_HEADING_FONT);
        gp.add(labelName, 0, 1);

        TextField txtName = new TextField(league.getLeagueName());
        txtName.setFont(MainFrame.TABLE_BODY_FONT);
        gp.add(txtName, 1, 1);

        Button btModifyLeague = new Button("Modify");
        btModifyLeague.setOnAction(e -> {
            league.setLeagueName(txtName.getText());
            DB.updateLeague(league);
            showLeagueListPage(stage);
        });
        
        Button btMainMenu = new Button("Cancel");
        btMainMenu.setOnAction(e -> showLeagueListPage(stage));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(30,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(btModifyLeague, btMainMenu);

        Label pageTitle = new Label("Modify League");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));

        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp, hbox);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }




}