package com.example.DataAccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.Controller.HomePage;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TrainsUIPage {
    private GridPane gridPane;
    private int columnCount = 0;
    private int rowCount = 0;
    private Firestore firestore;
    private Stage stage;
    private HomePage homePage;
    private Scene scene;

    public TrainsUIPage(HomePage homePage, Stage stage){
        this.homePage=homePage;
        this.stage=stage;
        this.scene=initScene(stage);

    }
    public Scene getScene(){
        return scene;
    }
   public Scene initScene(Stage stage) {
        this.stage = stage;

        // Initialize Firestore
        try {
            initializeFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HBox rightHBox = new HBox(20); // 20 is the spacing between elements
        rightHBox.setAlignment(Pos.BASELINE_RIGHT); // Align to the baseline right
        rightHBox.setPadding(new Insets(20));

        // Create main BorderPane
        BorderPane borderPane = new BorderPane();

        // Add button at the top-left corner to navigate back to HomePage
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
           // stage.close(); // Close current window
            stage.setScene(homePage.getScene());
        });
        borderPane.setTop(backButton);

        // Center area for place boxes (GridPane)
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5));
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
        // stage.setTitle("Trains Page");
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
            List<QueryDocumentSnapshot> documents = firestore.collection("Trains").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String imgpath = document.getString("Image");
                String trainName = document.getString("TrainName");
                String location = document.getString("Location");
                String priceStr = document.getString("Price");

                // Parse price from String to double (assuming price is in decimal format)
                double price = Double.parseDouble(priceStr);

                addPlaceBox(createPlaceBox(imgpath, trainName, location, price));
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

    private VBox createPlaceBox(String imgpath, String trainName, String location, double price) {
        VBox placeBox = new VBox(10);
        placeBox.setPadding(new Insets(10));
        placeBox.setPrefSize(370, 450); // Increased height to accommodate booking button

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
        imageView.setFitHeight(250); // Adjusted height for image

        Label trainNameLabel = new Label(trainName);
        trainNameLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        Label locationLabel = new Label("Location: " + location);
        locationLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 15));

        Label priceLabel = new Label("Price: Rs" + price);
        priceLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 15));

        // Button for booking
        Button bookButton = new Button("Add To Booking");
        bookButton.setOnAction(event -> {
            addBookingToFirestore(trainName, location, price);
        });

        VBox detailsBox = new VBox(10);
        detailsBox.getChildren().addAll(trainNameLabel, locationLabel, priceLabel, bookButton);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(10));

        placeBox.getChildren().addAll(imageView, detailsBox);
        return placeBox;
    }

    private void addBookingToFirestore(String trainName, String location, double price) {
        try {
            Map<String, Object> booking = new HashMap<>();
            booking.put("TrainName", trainName);
            booking.put("Location", location);
            booking.put("Price", price);
            ApiFuture<WriteResult> future = firestore.collection("Booking").document().set(booking, SetOptions.merge());

            showAlert(AlertType.INFORMATION, "Booking Added", "Booking for " + trainName + " added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to add booking.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class Booking {
        private String trainName;
        private String location;
        private double price;

        public Booking(String trainName, String location, double price) {
            this.trainName = trainName;
            this.location = location;
            this.price = price;
        }
    }
}
