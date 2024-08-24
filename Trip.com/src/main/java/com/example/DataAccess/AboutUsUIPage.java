package com.example.DataAccess;

import com.example.Controller.HomePage;

// import java.io.FileInputStream;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ExecutionException;

// import com.example.Controller.HomePage;
// import com.google.api.core.ApiFuture;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.cloud.firestore.Firestore;
// import com.google.cloud.firestore.FirestoreOptions;
// import com.google.cloud.firestore.QueryDocumentSnapshot;
// import com.google.cloud.firestore.SetOptions;
// import com.google.cloud.firestore.WriteResult;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.effect.DropShadow;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.Background;
// import javafx.scene.layout.BackgroundFill;
// import javafx.scene.layout.Border;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.BorderStroke;
// import javafx.scene.layout.BorderStrokeStyle;
// import javafx.scene.layout.BorderWidths;
// import javafx.scene.layout.CornerRadii;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;

// public class AboutUsUIPage {
//     private VBox mainVBox;
//     private Firestore firestore;
//     private Stage stage;
//     private HomePage homePage;
//     private Scene scene;

//     public AboutUsUIPage(HomePage homePage, Stage stage) {
//         this.homePage = homePage;
//         this.stage = stage;
//         this.scene = initScene(stage);
//     }

//     public Scene getScene() {
//         return scene;
//     }

//     public Scene initScene(Stage stage) {
//         this.stage = stage;

//         // Initialize Firestore
//         try {
//             initializeFirestore();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         // Create main BorderPane
//         BorderPane borderPane = new BorderPane();

//         // Add button at the top-left corner to navigate back to HomePage
//         Button backButton = new Button("Back");
//         backButton.setOnAction(event -> {
//             stage.setScene(homePage.getScene());
//         });
//         HBox topHBox = new HBox(backButton);
//         topHBox.setAlignment(Pos.TOP_LEFT);
//         topHBox.setPadding(new Insets(10));
//         borderPane.setTop(topHBox);

//         // Center area for place boxes (VBox)
//         mainVBox = new VBox(50); // 50 is the spacing between boxes
//         mainVBox.setAlignment(Pos.CENTER);
//         mainVBox.setPadding(new Insets(20));

//         // Fetch and display places from Firestore
//         try {
//             fetchAndDisplayPlaces();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         // ScrollPane to contain the main content
//         ScrollPane scrollPane = new ScrollPane(mainVBox);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setFitToHeight(true);

//         // Set VBox in the center of the BorderPane
//         borderPane.setCenter(scrollPane);

//         // Create Scene and set in Stage
//         Scene scene = new Scene(borderPane, 800, 600);
//         return scene;
//     }

//     private void initializeFirestore() throws IOException {
//         FileInputStream serviceAccount = new FileInputStream("tripplanner\\src\\main\\resources\\ServiceAccountKey.json");
//         FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
//                 .setProjectId("fx-auth-fb-2876e")
//                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                 .build();
//         firestore = firestoreOptions.getService();
//     }

//     private void fetchAndDisplayPlaces() {
//         try {
//             List<QueryDocumentSnapshot> documents = firestore.collection("AboutUs").get().get().getDocuments();
//             int displayedCount = 0;
//             for (QueryDocumentSnapshot document : documents) {
//                 if (displayedCount >= 2) break; // Only display 2 boxes
//                 String Name = document.getString("Name");
//                 String info = document.getString("Information");
               
//                 mainVBox.getChildren().add(createPlaceBox(Name, info));
//                 displayedCount++;
//             }
//         } catch (InterruptedException | ExecutionException e) {
//             e.printStackTrace();
//         }
//     }

//     private VBox createPlaceBox(String Name, String info) {
//         VBox placeBox = new VBox(10);
//         placeBox.setPadding(new Insets(10));
//         placeBox.setPrefSize(370, 450); // Size to accommodate booking button and image

//         // Add border to placeBox
//         placeBox.setBorder(new Border(new BorderStroke(
//                 Color.LIGHTPINK, // Border color
//                 BorderStrokeStyle.SOLID, // Border style
//                 new CornerRadii(15), // Rounded corners
//                 new BorderWidths(2) // Border widths
//         )));

//         // Add shadow to the VBox
//         DropShadow shadow = new DropShadow();
//         shadow.setOffsetX(15);
//         shadow.setOffsetY(15);
//         shadow.setColor(Color.GRAY);

//         StackPane shadowWrapper = new StackPane(placeBox);
//         shadowWrapper.setEffect(shadow);
//         shadowWrapper.setPadding(new Insets(5));

//         // Hardcoded image setup
//         Image image = new Image("file:");
//         ImageView imageView = new ImageView(image);
//         imageView.setFitWidth(350);
//         imageView.setFitHeight(250); // Adjusted height for image

//         Label hotelNameLabel = new Label(Name);
//         hotelNameLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 20));

//         Label locationLabel = new Label(": " + info);
//         locationLabel.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 15));

        
//         // // Button for booking
//         // Button bookButton = new Button("Add To Booking");
//         // bookButton.setOnAction(event -> {
//         //     addBookingToFirestore(hotelName, location, price);
//         // });

//         VBox detailsBox = new VBox(10);
//         detailsBox.getChildren().addAll(hotelNameLabel, locationLabel);
//         detailsBox.setAlignment(Pos.CENTER);
//         detailsBox.setPadding(new Insets(10));

//         placeBox.getChildren().addAll(imageView, detailsBox);
//         return placeBox;
//     }

// }

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AboutUsUIPage {

     private Stage stage;
    private HomePage homePage;
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public AboutUsUIPage(HomePage homePage, Stage stage) {
        this.homePage = homePage;
        this.stage = stage;
        this.scene = initScene(stage);
    }

    public Scene initScene(Stage stage) {
        this.stage = stage;
        // Load background image
        Image backgroundImage = new Image("Your paragraph text.png"); // Replace with your image path
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1900); // Adjust the width to your preference
        backgroundImageView.setFitHeight(1000); // Adjust the height to your preference
        backgroundImageView.setPreserveRatio(true);

        // Create a BorderPane to hold the background image and other components
        BorderPane root = new BorderPane();
         Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            //stage.close(); // Close current window
            stage.setScene(homePage.getScene()); // Open HomePage
            stage.show();
        });
            root.setTop(backButton);


        // Create a StackPane for the background image
        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().add(backgroundImageView);

        // Set the backgroundPane in the center of the BorderPane
        root.setCenter(backgroundPane);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 1900, 1000);
        //stage.setScene(scene);

        // Set the title of the stage
        //stage.setTitle("About Us");

        // Display the stage
       // stage.show();
        return scene;
    }

}

