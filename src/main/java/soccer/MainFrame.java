package soccer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import soccer.pages.LeaguePages;

public class MainFrame extends Application {
    public static final Font PAGE_HEADING_FONT = new Font("Arial Bold", 28);
    public static final Font TABLE_HEADING_FONT = new Font("Arial Bold", 24);
    public static final Font TABLE_BODY_FONT = new Font("Arial", 22);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)  {
        loadMenu(primaryStage);
}
    public static void loadMenu (Stage stage) {        
        Button btLeagues = new Button("Manage Leagues");
        Button btCoaches = new Button("Manage Coaches");

        btLeagues.setOnAction(e-> LeaguePages.showLeagueListPage(stage));

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.add(btLeagues, 0, 1);
        gp.add(btCoaches, 1, 1);
        gp.setHgap(10);
        gp.setVgap(10);

        Label pageTitle = new Label("Main menu");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(0,0,30,0));
        
        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.setTitle("Recreation Soccer League");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }




}