package kkm.pages;

import java.time.LocalDateTime;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kkm.DB;
import kkm.MainFrame;
import kkm.Session;

public class SignUpPage {

    public static void showSignUp(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("User Signup");
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

        Label pass2L = new Label("Confirm Password:");
        pass2L.setFont(MainFrame.TABLE_HEADING_FONT);
        PasswordField pass2F = new PasswordField();

        Label typeL = new Label("User Type:");
        typeL.setFont(MainFrame.TABLE_HEADING_FONT);
        TextField typeF = new TextField();
        typeF.setPromptText("User Type (volun/admin)");

        form.add(userL, 0, 0);
        form.add(userF, 1, 0);
        form.add(passL, 0, 1);
        form.add(passF, 1, 1);
        form.add(pass2L, 0, 2);
        form.add(pass2F, 1, 2);
        form.add(typeL, 0, 3);
        form.add(typeF, 1, 3);

        Label error = new Label();
        error.setTextFill(Color.RED);
        error.setFont(MainFrame.TABLE_BODY_FONT);

        Button btCreate = new Button("Create Account");
        Button btBack = new Button("Back");

        btCreate.setOnAction(e -> {
            String username = userF.getText().trim();
            String password = passF.getText().trim();
            String confirm = pass2F.getText().trim();
            String userType = typeF.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || userType.isEmpty()) {
                error.setText("Username and password are required.");
                return;
            }
            if (!password.equals(confirm)) {
                error.setText("Passwords do not match.");
                return;
            }
            if (DB.usernameExists(username)) {
                error.setText("Username already exists.");
                return;
            }

            int id = DB.insertUser(username, password, userType);
            if (id <= 0) {
                error.setText("Failed to create user. Try again.");
                return;
            }
            else {
                error.setText("Account created successfully!");
                Session.signIn(id, username, LocalDateTime.now());
                VolunteerPage.showVolunteerPage(stage);
            }
        });

        btBack.setOnAction(e -> MainFrame.loadMenu(stage));

        HBox actions = new HBox(15, btCreate, btBack);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(title, form, error, actions);

        Scene scene = new Scene(root, 520, 320);
        stage.setScene(scene);
        stage.setTitle("User Signup");
        stage.show();
    }
}