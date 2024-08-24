package com.example.Controller;
/* *
import com.example.DataAccess.PlacesUIPage;
import com.example.DataAccess.TravelersUIPage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage Scene) {
        // Creating an HBox layout for right-aligned buttons
        HBox rightHBox = new HBox(20); // 20 is the spacing between elements
        rightHBox.setAlignment(Pos.BASELINE_RIGHT); // Align to the baseline right
        rightHBox.setPadding(new Insets(20));
        //rightHBox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Creating buttons for the right side
        Button right1 = new Button("PLACES");
        right1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right1.setOnAction(event -> System.out.println(right1.getText() + " clicked!"));
        rightHBox.getChildren().add(right1);

        Button right2 = new Button("TRAVELS");
        right2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right2.setOnAction(event -> System.out.println(right2.getText() + " clicked!"));
        rightHBox.getChildren().add(right2);

        Button right = new Button("HOTELS");
        right.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right.setOnAction(event -> System.out.println(right.getText() + " clicked!"));
        rightHBox.getChildren().add(right);


        Button right3 = new Button("BOOKING");
        right3.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right3.setOnAction(event -> System.out.println(right3.getText() + " clicked!"));
        rightHBox.getChildren().add(right3);

        Button right4 = new Button("ABOUT US");
        right4.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right4.setOnAction(event -> System.out.println(right4.getText() + " clicked!"));
        rightHBox.getChildren().add(right4);

        Button right5 = new Button("PROFILE");
        right5.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        right5.setOnAction(event -> System.out.println(right5.getText() + " clicked!"));
        rightHBox.getChildren().add(right5);

        // Creating an HBox layout for the left-aligned button
        HBox leftHBox = new HBox(20); // 20 is the spacing between elements
        leftHBox.setAlignment(Pos.BASELINE_LEFT); // Align to the baseline left
        leftHBox.setPadding(new Insets(20));

        // Creating the left-aligned button
        Button leftButton = new Button("Logout");
        leftButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        leftButton.setOnAction(event -> System.out.println(leftButton.getText() + " clicked!"));
        leftHBox.getChildren().add(leftButton);

        // Creating an HBox layout for the center search bar
        HBox centerHBox = new HBox(10); // 10 is the spacing between elements
        centerHBox.setAlignment(Pos.BASELINE_CENTER); // Align to the baseline center
        centerHBox.setPadding(new Insets(20));

        // Creating the search bar (TextField) and search button
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> System.out.println("Search for: " + searchBar.getText()));
        centerHBox.getChildren().addAll(searchBar, searchButton);

        // Load background image
         Image backgroundImage = new Image("figma.png"); // Replace with your image path
         ImageView backgroundImageView = new ImageView(backgroundImage);
         backgroundImageView.setFitWidth(1900);
         backgroundImageView.setFitHeight(1000);

        // Create a StackPane to hold the background image and the HBoxes
        StackPane root = new StackPane();
        root.getChildren().addAll(rightHBox, leftHBox, centerHBox);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(root, 1900, 1000);
        Scene.setScene(scene);

        // Setting the title of the stage
        Scene.setTitle("Welcome");

        // Displaying the stage
        Scene.show();
    }

    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}
*/

import com.example.APIService.APIService;
import com.example.DataAccess.AboutUsUIPage;
import com.example.DataAccess.BookingUIPage;
import com.example.DataAccess.HotelsUIPage;
import com.example.DataAccess.PlacesUIPage;
import com.example.DataAccess.TrainsUIPage;
import com.example.DataAccess.TravelersUIPage;
import com.example.MainWindow.LoginController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomePage {

    private Stage stage;
    private LoginController loginController;
    private Scene scene;

    public HomePage(LoginController loginController, Stage stage) {
        this.stage = stage;
        this.loginController = loginController;
        this.scene = initScene();
    }

    public Scene getScene() {
        return scene;
    }

    private Scene initScene() {
        // Creating an HBox layout for top navigation bar
        HBox navBar = new HBox(20); // 20 is the spacing between elements
        navBar.setAlignment(Pos.CENTER_RIGHT); // Align to the center right
        navBar.setPadding(new Insets(20));

        Button placesButton = createButton("PLACES", event -> {
            //stage.close(); // Close current window
            PlacesUIPage placesUIPage = new PlacesUIPage(this, stage);
            stage.setScene(placesUIPage.getScene()); // Open PlacesUIPage
        });
        Button travelsButton = createButton("TRAVELS", event -> {
            //stage.close(); // Close current window
            TravelersUIPage travelersUIPage = new TravelersUIPage(this, stage); // Open TravelersUIPage
            stage.setScene(travelersUIPage.getScene());
        });
        Button hotelsButton = createButton("HOTELS", event -> {
            //stage.close(); // Close current window
            HotelsUIPage hotelsUIPage = new HotelsUIPage(this, stage);
            stage.setScene(hotelsUIPage.getScene());
        });
        Button trainsButton = createButton("TRAINS", event -> {
           // stage.close(); // Close current window
            TrainsUIPage trainsUIPage = new TrainsUIPage(this, stage);  
            stage.setScene(trainsUIPage.getScene());
        });
        Button bookingButton = createButton("BOOKING", event -> {
            //stage.close(); // Close current window
           BookingUIPage bookingUIPage = new BookingUIPage(this, stage);
           stage.setScene(bookingUIPage.getScene());
        });
        Button aboutUsButton = createButton("ABOUTUS", event -> {
           // stage.close(); // Close current window
          AboutUsUIPage aboutUsUIPage = new AboutUsUIPage(this, stage);
          stage.setScene(aboutUsUIPage.getScene());

        });
        Button location = createButton("LOCATION", event -> {
           // stage.close(); // Close current window
           APIService apiService = new APIService(this,stage);
           stage.setScene(apiService.getScene());
            
        });

        navBar.getChildren().addAll(placesButton, travelsButton, hotelsButton, bookingButton, aboutUsButton, location);

        // Creating an HBox layout for the search bar
        HBox searchHBox = new HBox(10); // 10 is the spacing between elements
        searchHBox.setAlignment(Pos.CENTER); // Align to the center
        searchHBox.setPadding(new Insets(20));

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> System.out.println("Search for: " + searchBar.getText()));
        searchHBox.getChildren().addAll(searchBar, searchButton);

        // Load background image
        Image backgroundImage = new Image("figma.png"); // Replace with your image path
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1900);
        backgroundImageView.setFitHeight(1000);

        // Create a BorderPane to hold the background image and other components
        BorderPane root = new BorderPane();

        // Create a pane for the background image
        BorderPane backgroundPane = new BorderPane();
        backgroundPane.setCenter(backgroundImageView);
        root.setCenter(backgroundPane);

        // Set top navigation bar
        root.setTop(navBar);

        // Set bottom search bar
        root.setBottom(searchHBox);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(root, 1900, 1000);

        return scene;
    }

    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnAction(handler);
        return button;
    }
}
