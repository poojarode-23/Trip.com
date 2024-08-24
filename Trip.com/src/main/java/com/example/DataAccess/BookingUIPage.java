package com.example.DataAccess;
/* 
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.Controller.HomePage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;

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

public class BookingUIPage {
    private GridPane gridPane;
    private int columnCount = 0;
    private int rowCount = 0;
    private Firestore firestore;
    private Stage stage;

    public Scene getScene(){
        return scene;
    }
    public BookingUIPage(HomePage homePage, Stage stage) {
        this.homePage=homePage;
        this.stage=stage;
        this.scene=initScene(stage);
    }


  public Scene initScene(Stage stage) {
        this.stage = stage;
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

        // Center area for place boxes (GridPane)
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        // Fetch and display bookings from Firestore
        try {
            fetchAndDisplayBookings();
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
        // stage.setTitle("Booking Page");
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

    private void fetchAndDisplayBookings() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("Booking").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String bookingType = document.getString("BookingType");
                String name = document.getString("Name");
                String location = document.getString("Location");
                double price = document.getDouble("Price");

                addBookingBox(createBookingBox(bookingType, name, location, price));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addBookingBox(VBox bookingBox) {
        if (columnCount == 4) {
            columnCount = 0;
            rowCount++;
        }

        gridPane.add(bookingBox, columnCount, rowCount);
        columnCount++;
    }

    private VBox createBookingBox(String bookingType, String name, String location, double price) {
        VBox bookingBox = new VBox(10);
        bookingBox.setPadding(new Insets(10));
        bookingBox.setPrefSize(370, 450); // Increased height to accommodate payment button

        // Add border to bookingBox
        bookingBox.setBorder(new Border(new BorderStroke(
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

        StackPane shadowWrapper = new StackPane(bookingBox);
        shadowWrapper.setEffect(shadow);
        shadowWrapper.setPadding(new Insets(5));
        String travelName;
        if(bookingType == travelName){
            Label bookingTypeLabel = new Label("TravelName" + travelName );
            bookingTypeLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        }
        // String trainName;
        // ifelse(bookingType == trainName){
        //     Label bookingTypeLabel = new Label("Booking for: " + trainName );
        //     bookingTypeLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        // }
        else{
            String hotelName;
            Label bookingTypeLabel = new Label("HotelName" + hotelName );
            bookingTypeLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));
        }
        Label nameLabel = new Label("Name: " + name);
        nameLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        Label locationLabel = new Label("Location: " + location);
        locationLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        Label priceLabel = new Label("Price: Rs" + price);
        priceLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        // Button for payment
        Button payButton = new Button("Proceed to Pay");
        payButton.setOnAction(event -> {
            showPaymentOptions(price);
        });

        VBox detailsBox = new VBox(10);
        detailsBox.getChildren().addAll(bookingType, nameLabel, locationLabel, priceLabel, payButton);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(10));

        bookingBox.getChildren().addAll(detailsBox);
        return bookingBox;
    }

    private void ifelse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ifelse'");
    }

    private void showPaymentOptions(double amount) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment Options");
        alert.setHeaderText(null);
        alert.setContentText("Choose your payment method for the amount of Rs" + amount + "\n1. Phone Pay\n2. Card Pay\n3. Net Banking");
        alert.showAndWait();
    }
}
    */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.Controller.HomePage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BookingUIPage {
    private GridPane gridPane;
    private int columnCount = 0;
    private int rowCount = 0;
    private Firestore firestore;
    private Stage stage;
    private HomePage homePage;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public BookingUIPage(HomePage homePage, Stage stage) {
        this.homePage = homePage;
        this.stage = stage;
        this.scene = initScene(stage);
    }

    public Scene initScene(Stage stage) {
        this.stage = stage;
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
            stage.setScene(homePage.getScene());
        });
        borderPane.setTop(backButton);

        // Center area for place boxes (GridPane)
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        // Fetch and display bookings from Firestore
        try {
            fetchAndDisplayBookings();
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

    private void fetchAndDisplayBookings() {
        try {
            List<QueryDocumentSnapshot> documents = firestore.collection("Booking").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String TravelName = document.getString("TravelName");
                //String name = document.getString("Name");
                String location = document.getString("Location");
                double price = document.getDouble("Price");

                addBookingBox(createBookingBox(TravelName, location, price));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addBookingBox(VBox bookingBox) {
        if (columnCount == 4) {
            columnCount = 0;
            rowCount++;
        }

        gridPane.add(bookingBox, columnCount, rowCount);
        columnCount++;
    }

    private VBox createBookingBox(String TravelName, String location, double price) {
        VBox bookingBox = new VBox(10);
        bookingBox.setPadding(new Insets(10));
        bookingBox.setPrefSize(370, 450); // Increased height to accommodate payment button

        // Add border to bookingBox
        bookingBox.setBorder(new Border(new BorderStroke(
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

        bookingBox.setEffect(shadow);
        bookingBox.setPadding(new Insets(5));

        // Create labels
        Label bookingTypeLabel = new Label("Booking For: " + TravelName);
        bookingTypeLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        
        Label locationLabel = new Label("Location: " + location);
        locationLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        Label priceLabel = new Label("Price: Rs " + price);
        priceLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

        // Button for payment
        Button payButton = new Button("Proceed to Pay");
        payButton.setOnAction(event -> showPaymentOptions(price));

        VBox detailsBox = new VBox(10);
        detailsBox.getChildren().addAll(bookingTypeLabel,  locationLabel, priceLabel, payButton);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(10));

        bookingBox.getChildren().addAll(detailsBox);
        return bookingBox;
    }

    private void showPaymentOptions(double amount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Options");
        alert.setHeaderText(null);
        alert.setContentText("Choose your payment method for the amount of Rs " + amount + "\n1. Phone Pay\n2. Card Pay\n3. Net Banking");
        alert.showAndWait();
    }
}

