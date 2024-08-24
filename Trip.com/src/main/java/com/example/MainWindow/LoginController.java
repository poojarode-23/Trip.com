package com.example.MainWindow;
import com.example.Controller.HomePage;
import com.example.MainWindow.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.ExecutionException;

public class LoginController {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene userScene;
    private DataService dataService;

    public static String key;

    public LoginController(Stage primaryStage2){
        this.primaryStage = primaryStage2;
        dataService = new DataService();

        initScenes();
    }

    private void initScenes(){
        initLoginScene();
    }

    private void initLoginScene(){

      //Load the logo image

        Text text1=new Text(10,40,"Welcome,We are glad you are here!!");
        text1.setFont(new Font("Arial Italic",25));
       // text1.setFont("Arial Italic");
        text1.setFill(Color.BROWN);


        Image logo=new Image("file:BackLogin.jpg");
        ImageView logoView=new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        
        Label userLabel = new Label("Username");
        userLabel.setFont(new Font("Arial", 20));
        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-min-width:270; -fx-min-height:30;-fx-background-radius:15;");


        Label passLabel = new Label("Password");
        passLabel.setFont(new Font("Arial",20));
        PasswordField passField = new PasswordField();
        passField.setStyle("-fx-min-width:270; -fx-min-height:30;-fx-background-radius:15;");

        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial",15));

        Text text2=new Text(10,40,"Don't have an account?");
        text2.setFont(new Font(20));
     
        Button signupButton = new Button("Sign up");
        

        loginButton.setOnAction(event->{

            HomePage homepPage = new HomePage(this,primaryStage);
            System.out.println(primaryStage);
            primaryStage.setScene(homepPage.getScene());
                userTextField.clear();
                passField.clear();
            
        });
             //   handleLogin(userTextField.getText(),passField.getText());
                
        

        signupButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                showSignupScene();
                userTextField.clear();
                passField.clear();
            }
        });

        // userLabel.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;");
        //passLabel.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;");
        //loginButton.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;");
         signupButton.setStyle("-fx-text-fill: BLACK;-fx-font-size: 15px;-fx-background-color:null;");

        VBox fieldBox1 = new VBox(5, userLabel,userTextField);
        fieldBox1.setMaxSize(300,30);

        VBox fieldBox2 = new VBox(5,passLabel,passField);
        fieldBox2.setMaxSize(300,30);
        
        HBox buttonBox = new HBox(10,text2,signupButton);
        buttonBox.setMaxSize(350,30);
        buttonBox.setAlignment(Pos.CENTER);
         
        

        //userTextField.setStyle("-fx-set-pref-width:250");
        //passField.setStyle("-fx-set-pref-width:250");

        VBox vbox = new VBox(20,text1,logoView,fieldBox1,fieldBox2,loginButton,buttonBox);
        vbox.setStyle("-fx-background-image: url('loginpage.jpg');-fx-background-size:cover;");
        vbox.setAlignment(Pos.CENTER);
        loginScene = new Scene(vbox,1900,1000);

    }

    private void initUserScene() {
        UserPage userPage = new UserPage(dataService);

        userScene = new Scene(userPage.createUserScene(this::handleLogout),1900,1000);
    }

    public Scene getLoginScene(){
        return loginScene;
    }

    public void showLoginScene(){
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void handleLogin(String username,String password){
        try{
            if
    (dataService.authenticateUser(username, password)){
                key = username;
                initUserScene();
                primaryStage.setScene(userScene);
                primaryStage.setTitle("User Dashboard");
            }else{
                System.out.println("Invalid Credentials");
                key = null;
            }
        }catch (ExecutionException | InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void showSignupScene(){
        SignupController signupController = new SignupController(this);
        Scene signupScene = signupController.createSignupScene(primaryStage);

        primaryStage.setScene(signupScene);
        primaryStage.setTitle("Signup");
        primaryStage.setHeight(1025);
        primaryStage.setWidth(1950);
        // primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void handleLogout(){
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
    }
    
}