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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;

public class LoginPage {

    public static void showLogin(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("User Login");
        title.setFont(MainFrame.PAGE_HEADING_FONT);

        GridPane form = new GridPane();
        form.setAlignment(Pos.TOP_CENTER);
        form.setHgap(12);
        form.setVgap(12);

        Label userL = new Label("Username:");
        userL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField userF = new TextField();

        Label passL = new Label("Password:");
        passL.setFont(MainFrame.TABLE_HEADING_FONT);
        PasswordField passF = new PasswordField();

        form.add(userL, 0, 0);
        form.add(userF, 1, 0);
        form.add(passL, 0, 1);
        form.add(passF, 1, 1);

        Label error = new Label();
        error.setTextFill(Color.RED);
        error.setFont(MainFrame.TABLE_BODY_FONT);

        Button btLogin = new Button("Login");
        Button btBack = new Button("Back");

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

            Session.signIn(userId, username, LocalDateTime.now());
            VolunteerPage.showVolunteerPage(stage);
        });

        btBack.setOnAction(e -> MainFrame.loadMenu(stage));

        HBox actions = new HBox(15, btLogin, btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(title, form, error, actions);

        Scene scene = new Scene(root, 480, 260);
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
}
