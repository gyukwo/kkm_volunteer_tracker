package kkm.pages;

import java.time.LocalDateTime;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;

public class LoginPage {

    public static final Font PAGE_HEADING_FONT   = new Font("Arial Rounded MT Bold", 34);
    public static final Font TABLE_HEADING_FONT = new Font("Arial", 40);
    public static final Font TABLE_BODY_FONT = new Font("Arial", 25);

    private static final String BACKGROUND_COLOR = "#E5F3FD";
    private static final String GREEN = "#008000"; 
    private static final String RED =  "D30000";


    public static void showLogin(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER); 
        root.setPadding(new Insets(30));

        Label title = new Label("User Login");
        title.setFont(MainFrame.PAGE_HEADING_FONT);
        title.setStyle("-fx-text-fill: #333333;"); 

        GridPane form = new GridPane();
        form.setAlignment(Pos.TOP_CENTER);
        form.setHgap(12);
        form.setVgap(12);

        Label userL = new Label("Username:");
        userL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField userF = new TextField();
        userF.setPrefWidth(280);
        userF.setPrefHeight(40);
        userF.setStyle(
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #A3BFD9;" +   
            "-fx-border-width: 2;" +
            "-fx-background-color: white;"
        );

        Label passL = new Label("Password:");
        passL.setFont(MainFrame.TABLE_HEADING_FONT);
        PasswordField passF = new PasswordField();
        passF.setPrefWidth(280);
        passF.setPrefHeight(40);
        passF.setStyle(
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-border-color: #A3BFD9;" +
            "-fx-border-width: 2;" +
            "-fx-background-color: white;"
        );

        form.add(userL, 0, 0);
        form.add(userF, 1, 0);
        form.add(passL, 0, 1);
        form.add(passF, 1, 1);

        Label error = new Label();
        error.setTextFill(Color.RED);
        error.setFont(MainFrame.TABLE_BODY_FONT);

        Button btLogin = new Button("Login");
        Button btBack = new Button("Back");
        styleButton(btLogin, GREEN);
        styleButton(btBack, RED);
        

        btLogin.setOnAction(e -> {
            String username = (userF.getText() == null) ? "" : userF.getText().trim();
            String password = (passF.getText() == null) ? "" : passF.getText();

            if (username.isEmpty() || password.isEmpty()) {
                error.setText("Enter username and password.");
                return;
            }

            int userId = DB.verifyUser(username, password);
            if (userId <= 0) {
                error.setText("Invalid username or password.");
                return;
            }

            String userType = DB.getUserTypeByUserId(userId);
            Session.signIn(userId, username, LocalDateTime.now());
            if (userType.equals("volun")){
                VolunteerPage.showVolunteerPage(stage);
            }
            else if (userType.equals("admin")) {
                AdminPage.showAdminPage(stage);
            }

        });

        btBack.setOnAction(e -> MainFrame.loadMenu(stage));


        VBox actions = new VBox(20, btLogin, btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(20, 0, 0, 0));

        root.getChildren().addAll(title, form, error, actions);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();

        scene.setOnKeyPressed(k -> {
            KeyCode code = k.getCode();
            if (code == KeyCode.ENTER) {
                btLogin.fire();
            } else if (code == KeyCode.ESCAPE) {
                btBack.fire();
            }
        });
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
