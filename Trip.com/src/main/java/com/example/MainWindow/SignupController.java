package com.example.MainWindow;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SignupController {
    
    private LoginController loginController;

    public SignupController(LoginController loginController){
        this.loginController = loginController;
    }

    public Scene createSignupScene(Stage primaryStage){

        //Load the logo image
        Image logo=new Image("Location.jpeg");
        ImageView logoView=new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        Label userLabel = new Label("Username:");
        userLabel.setFont(new Font("Arial", 20));
        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-min-width:270; -fx-min-height:30;-fx-background-radius:15;");


        Label passLabel = new Label("password:");
        passLabel.setFont(new Font("Arial", 20));
        PasswordField passField = new PasswordField();
        passField.setStyle("-fx-min-width:270; -fx-min-height:30;-fx-background-radius:15;");


        Button signupButton = new Button("Signup");
        signupButton.setFont(new Font("Arial",15));


        Text text1=new Text(10,40,"Already have an account?");
        text1.setFont(new Font(20));

        Button loginButton =new Button("Login");
        


        VBox fieldBox1 = new VBox(10,userLabel,userTextField);
        fieldBox1.setMaxSize(300,30);

        VBox fieldBox2 = new VBox(10,passLabel,passField);
        fieldBox2.setMaxSize(300,30);

        HBox buttonBox = new HBox(20,text1,loginButton);
        buttonBox.setMaxSize(350,30);
        buttonBox.setAlignment(Pos.CENTER);

        signupButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event){
                handleSignup(primaryStage,userTextField.getText(),passField.getText());
            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                loginController.showLoginScene();
            }
            });

        //userLabel.setStyle("-fx-text-fill: BLACK;-fx-font-size: 20px;");
        //passLabel.setStyle("-fx-text-fill: BLACK;-fx-font-size: 20px;");
        loginButton.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;-fx-background-color:null");
        //signupButton.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;-fx-background-color;");

    

        VBox vbox = new VBox(30,logoView,fieldBox1,fieldBox2,signupButton,buttonBox);
        vbox.setStyle("-fx-background-image: url('Signup.jpg');-fx-background-size:cover;");
        
        //loginScene = new Scene(vbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new javafx.geometry.Insets(20));

        return new Scene(vbox,1900,1000);
    }

    private void handleSignup(Stage primaryStage, String username, String password){
        DataService dataService;

        try{
            dataService = new DataService();

            Map<String,Object> data = new HashMap<>();
            
            data.put("password", password);
            data.put("username",username);

            dataService.addData("users",username,data);

            System.out.println("Users registered successfully");

            loginController.showLoginScene();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}