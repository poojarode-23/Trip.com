package com.example.MainWindow;

import com.example.MainWindow.LoginController;
import com.example.MainWindow.SignupController;
import com.example.MainWindow.InitializeApp;
import com.example.MainWindow.DataService;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserPage {
    static String userName;
    private DataService dataService;
    VBox vb;

    public UserPage(DataService dataService) {
        this.dataService = dataService;
        
    }

    public VBox createUserScene(Runnable logoutHandler){
        Button logoutButton = new Button("Logout");
        Label dataLabel = new Label();

        try{
            String key  = LoginController.key;
            System.out.println("Value of keys:" + key);
            DocumentSnapshot dataObject = dataService.getData("users", key);

            userName = dataObject.getString("username");

            System.out.println("username fetched:" + userName);
            dataLabel.setText(userName);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logoutHandler.run();
            }
        
        });

        Text message = new Text("Welcome" + dataLabel.getText());
        message.setStyle("-fx-text-fill: white;-fx-font-weight:bold;-fx-font-size:60");
        vb = new VBox(350, message,logoutButton);
        vb.setStyle("-fx-background-color:DARKGREY");
        return vb;
    }
    
}