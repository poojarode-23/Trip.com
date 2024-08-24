package com.example.DataAccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.Controller.HomePage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.DocumentSnapshot;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PlacesUIPage {
    private GridPane gridPane;
    private int columnCount = 0;
    private int rowCount = 0;
    private Firestore firestore;
    private HomePage homePage;
    private Stage stage;
    private Scene scene;

    public Scene getScene(){
        return scene;
    }
    public PlacesUIPage(HomePage homePage, Stage stage) {
        this.homePage=homePage;
        this.stage=stage;
        this.scene=initScene(stage);
    }


    public Scene initScene(Stage stage){
        // Initialize Firestore
        try {
            initializeFirestore();
        } catch (IOException e) {
            e.printStackTrace();
            
        }

        // Create main BorderPane
        BorderPane borderPane = new BorderPane();

         

        // Add button at the top-left corner to navigate back to HomePage
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            //stage.close(); // Close current window
            stage.setScene(homePage.getScene());
        });
        borderPane.setTop(backButton);

        // Add image at the top
        // Image sceneImage = new Image("Adminbutton.jpeg");
        // ImageView imageView = new ImageView(sceneImage);
        // imageView.setFitWidth(1900); // Adjust width as needed
        // imageView.setPreserveRatio(true); // Maintain aspect ratio
        // borderPane.setTop(imageView); // Set image at the top of the BorderPane

        // Center area for place boxes (GridPane)
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        // Fetch and display places from Firestore
        try {
            fetchAndDisplayPlaces();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ScrollPane to contain the main content
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set GridPane in the center of the BorderPane
        borderPane.setCenter(scrollPane);

        // Create Scene and set in Stage
        Scene scene = new Scene(borderPane, 1900, 1000);
        // stage.setTitle("Place Page");
        // stage.setScene(scene);
        // stage.show();
        return scene;
    }

    private void initializeFirestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("tripplanner\\src\\main\\resources\\ServiceAccountKey.json");
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId("fx-auth-fb-2876e")
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        firestore = firestoreOptions.getService();
    }

    private void fetchAndDisplayPlaces() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("Places").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String imgpath = document.getString("Image");
                String placeName = document.getString("PlaceName");
                String location = document.getString("Location");

                addPlaceBox(createPlaceBox(imgpath, placeName, location));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addPlaceBox(VBox placeBox) {
        if (columnCount == 4) {
            columnCount = 0;
            rowCount++;
        }

        gridPane.add(placeBox, columnCount, rowCount);
        columnCount++;
    }

    private VBox createPlaceBox(String imgpath, String placeName, String location) {
        VBox placeBox = new VBox(10);
        placeBox.setPadding(new Insets(10));
        placeBox.setPrefSize(370, 50);

        // Add border to placeBox
        placeBox.setBorder(new Border(new BorderStroke(
                Color.LIGHTPINK, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(15), // Rounded corners
                new BorderWidths(2) // Border widths
        )));

        // Add shadow to the VBox
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(15);
        shadow.setOffsetY(15);
        shadow.setColor(Color.GRAY);

        StackPane shadowWrapper = new StackPane(placeBox);
        shadowWrapper.setEffect(shadow);
        shadowWrapper.setPadding(new Insets(5));

        // Image setup
        Image image = new Image(imgpath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(false);

        Label placeNameLabel = new Label(placeName);
        placeNameLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 25));

        Label locationLabel = new Label(location);
        locationLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        VBox vb1 = new VBox(placeNameLabel, locationLabel);
        placeBox.getChildren().addAll(imageView, vb1);
        return placeBox;
    }
}


