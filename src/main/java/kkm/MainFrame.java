package kkm;

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
import kkm.pages.LoginPage;
import kkm.pages.SignUpPage;

public class MainFrame extends Application {
    public static final Font PAGE_HEADING_FONT = new Font("Comic Sans MS", 35);
    public static final Font TABLE_HEADING_FONT = new Font("Comic Sans MS", 24);
    public static final Font TABLE_BODY_FONT = new Font("Comic Sans MS", 22);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)  {
        loadMenu(primaryStage);
}
    public static void loadMenu (Stage stage) {        
        Button btLogin = new Button("  User Login  ");
        Button btSignUp = new Button("User Signup");

        btLogin.setOnAction(e-> {
           LoginPage.showLogin(stage);
        }
        );

        btSignUp.setOnAction(e->{
            SignUpPage.showSignUp(stage);
        });

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.add(btLogin, 0, 1);
        gp.add(btSignUp, 0, 2);
        gp.setHgap(0);
        gp.setVgap(20);

        Label pageTitle = new Label("KKM Volunteer Tracker");
        pageTitle.setFont(MainFrame.PAGE_HEADING_FONT);
        pageTitle.setPadding(new Insets(20,0,30,0));
        
        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(pageTitle, gp);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.setTitle("KKM Volunteer Tracker");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }


}