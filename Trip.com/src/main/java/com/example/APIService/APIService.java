package com.example.APIService;
import com.example.APIService.APIService;
import com.example.Controller.HomePage;
import com.example.MainWindow.LoginController;
import com.google.cloud.firestore.Firestore;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class APIService  {

    static String locationData = "668ea0b0d51ad599145126ufjee9607";

    public static void fetchLocationData(String place) throws Exception {
        double[] coordinates = DataUrl.getCoordinates(place);

        if (coordinates != null) {
            String locationName = DataUrl.getLocationName(coordinates[0], coordinates[1]);

            if (locationName != null) {
                locationData = "Place: " + place + "\n" +
                               "Location Name: " + locationName + "\n" +
                               "Coordinates: (" + coordinates[0] + ", " + coordinates[1] + ")";
            } else {
                locationData = "Location name not found.";
            }
        } else {
            locationData = "Invalid place or coordinates not found.";
        }
    }

  //  @Override
    private Firestore firestore;
    private HomePage homePage;
    private Stage stage;
    private Scene scene;

    public Scene getScene(){
        return scene;
    }
    public APIService(HomePage homePage, Stage stage) {
        this.homePage=homePage;
        this.stage=stage;
        this.scene=initScene(stage);
    }


    public Scene initScene(Stage stage){
      //  primaryStage.setTitle("Location Fetcher");
        System.out.println("in start!!");

        // Create UI elements
        Label placeLabel = new Label("Place:");
        TextField placeInput = new TextField();
        Button fetchButton = new Button("Location Data");

        // Load background image from local file system
        Image backgroundImage = new Image("R.jpeg"); 
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1950);
        backgroundImageView.setFitHeight(1100);

        // Setup GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(placeLabel, 0, 0);
        grid.add(placeInput, 1, 0);
        grid.add(fetchButton, 1, 1);

        // // Create a StackPane to hold the background image and the grid
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, grid);

        // SetOnAction for location fetch button
        fetchButton.setOnAction(e -> {
            String place = placeInput.getText();
            try {
                System.out.println(place);
                System.out.println("fetch clicked!!!!!");
                fetchLocationData(place);
                showLocationDataWindow(locationData);
            } catch (Exception ex) {
                ex.printStackTrace();
                showLocationDataWindow("Failed to fetch location data.");
            }
        });
        // return scene;
        BorderPane rootPane = new BorderPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            //stage.close(); // Close current window
            stage.setScene(homePage.getScene()); // Open HomePage
            stage.show();
        });
            rootPane.setTop(backButton);

        // Set the scene
       Scene scene = new Scene(root, 1900, 1000);
    //     primaryStage.setScene(scene);
    //     primaryStage.show();
    return scene;
     }

    private void showLocationDataWindow(String data) {
       Stage newWindow = new Stage();
       // newWindow.setTitle("Location Data");

        // Load background image for the new window
        Image backgroundImage = new Image("background_image.jpg"); 
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(400);
        backgroundImageView.setFitHeight(300);

        TextArea locationArea = new TextArea(data);
        locationArea.setWrapText(true);

        // Create a StackPane to hold the background image and the text area
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(backgroundImageView, locationArea);

        Scene secondScene = new Scene(secondaryLayout, 400, 300);
        
       // primaryStage.setScene(secondScene);
       
    
    }


}
/* 

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class APIService {

    static String locationData = "668ea0b0d51ad599145126ufjee9607";

    public static void fetchLocationData(String place) throws Exception {
        double[] coordinates = DataUrl.getCoordinates(place);

        if (coordinates != null) {
            String locationName = DataUrl.getLocationName(coordinates[0], coordinates[1]);

            if (locationName != null) {
                locationData = "Place: " + place + "\n" +
                        "Location Name: " + locationName + "\n" +
                        "Coordinates: (" + coordinates[0] + ", " + coordinates[1] + ")";
            } else {
                locationData = "Location name not found.";
            }
        } else {
            locationData = "Invalid place or coordinates not found.";
        }
    }

    private HomePage homePage;
    private Stage stage;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public APIService(HomePage homePage, Stage stage) {
        this.homePage = homePage;
        this.stage = stage;
        this.scene = initScene(stage);
    }

    public Scene initScene(Stage stage) {
        System.out.println("in start!!");

        // Create UI elements
        Label placeLabel = new Label("Place:");
        TextField placeInput = new TextField();
        Button fetchButton = new Button("Location Data");

        // Load background image from local file system
        Image backgroundImage = new Image("Location.jpeg"); 
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1900);
        backgroundImageView.setFitHeight(1000);

        // Setup GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(placeLabel, 0, 0);
        grid.add(placeInput, 1, 0);
        grid.add(fetchButton, 1, 1);

        // Create a StackPane to hold the background image and the grid
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, grid);

        // SetOnAction for location fetch button
        fetchButton.setOnAction(e -> {
            String place = placeInput.getText();
            try {
                System.out.println(place);
                System.out.println("fetch clicked!!!!!");
                fetchLocationData(place);
                showLocationDataWindow(locationData);
            } catch (Exception ex) {
                ex.printStackTrace();
                showLocationDataWindow("Failed to fetch location data.");
            }
        });

        BorderPane rootPane = new BorderPane();
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            stage.setScene(homePage.getScene());
        });
        rootPane.setTop(backButton);

        rootPane.setCenter(root);

        // Set the scene
        Scene scene = new Scene(rootPane, 1900, 1000);
        return scene;
    }

    private void showLocationDataWindow(String data) {
        Stage newWindow = new Stage();
        newWindow.setTitle("Location Data");

        // Load background image for the new window
        Image backgroundImage = new Image("background_image.jpg"); 
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(400);
        backgroundImageView.setFitHeight(300);

        TextArea locationArea = new TextArea(data);
        locationArea.setWrapText(true);

        // Create a StackPane to hold the background image and the text area
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(backgroundImageView, locationArea);

        Scene secondScene = new Scene(secondaryLayout, 400, 300);
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    // @Override
    public void start(Stage Stage) {
        HomePage homePage = new HomePage(new LoginController(), Stage);
        APIService apiService = new APIService(homePage, Stage);
        Stage.setScene(apiService.getScene());
        Stage.show();
    }
}
    */

