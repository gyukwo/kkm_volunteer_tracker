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
    public static final Font PAGE_HEADING_FONT = new Font("Arial Rounded MT Bold", 34);
    public static final Font TABLE_HEADING_FONT = new Font("Arial", 20);
    public static final Font TABLE_BODY_FONT = new Font("Arial", 18);

    private static final String BACKGROUND_COLOR = "#E5F3FD";
    private static final String WHITE = "#FFFFFF";
    private static final String BLUE = "#7BB6FF";
    private static final String PINK = "#F9A8D4";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        loadMenu(primaryStage);
    }

    public static void loadMenu(Stage stage) {
        Label pageTitle = new Label("KKM Volunteer Tracker");
        pageTitle.setFont(PAGE_HEADING_FONT);
        pageTitle.setStyle("-fx-text-fill: #1F2937;");
        pageTitle.setPadding(new Insets(0, 0, 10, 0));

        Label subtitle = new Label("Empowering students to serve their community.");
        subtitle.setFont(new Font("Arial", 16));
        subtitle.setStyle("-fx-text-fill: #6B7280;");
        subtitle.setPadding(new Insets(0, 0, 20, 0));

        Button btLogin = new Button("User Login");
        Button btSignUp = new Button("User Signup");

        styleButton(btLogin, BLUE);
        styleButton(btSignUp, PINK);

        btLogin.setOnAction(e -> LoginPage.showLogin(stage));
        btSignUp.setOnAction(e -> SignUpPage.showSignUp(stage));

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(0);
        gp.setVgap(15);
        gp.add(btLogin, 0, 0);
        gp.add(btSignUp, 0, 1);

        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30, 40, 40, 40));
        card.getChildren().addAll(pageTitle, subtitle, gp);
        card.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(15,23,42,0.12), 18, 0, 0, 6);");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getChildren().add(card);
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("KKM Volunteer Tracker");
        stage.show();
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
