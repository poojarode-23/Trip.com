package com.example.Controller;

import com.example.MainWindow.LoginController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GetStarted extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;


        // Creating a VBox layout
        VBox vbox = new VBox(20); // 20 is the spacing between elements
        vbox.setAlignment(Pos.CENTER); // Center the VBox
        vbox.setPadding(new Insets(20));

        // Creating a button
        Button button = new Button("Get Started");
        button.setPrefSize(200, 50);
        button.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        // Add any event handler to the button if needed
        button.setOnAction(event -> {
            LoginController loginController = new LoginController(this.primaryStage);
            this.primaryStage.setScene(loginController.getLoginScene());
        });


        // Load background image
        Image backgroundImage = new Image("Planning-travel.jpeg"); // Replace with your image path
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1900);
        backgroundImageView.setFitHeight(1000);

        // Create a StackPane to hold the background image and the VBox
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, vbox);

        // Add the button to the VBox
        vbox.getChildren().add(button);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(root, 1900, 1000);
        primaryStage.setScene(scene);

        // Setting the title of the stage
        primaryStage.setTitle("Welcome");

        // Displaying the stage
        primaryStage.show();
    }


}
