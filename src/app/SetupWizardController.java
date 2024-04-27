package app;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class SetupWizardController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML private Button setupButton;
    @FXML private Button closeButton;

    
    @FXML
    private void initialize() {
    	
    }
    /*
    @FXML
    private void handleCreateAdminButton() {
        String username = nameTextField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        

        // If admin is created successfully, update AppConfig
        AppConfig.setAdminUsername(username);
        AppConfig.setAdminPassword(password);
        AppConfig.setInitialized(true); // Indicate setup is complete

     
        closeSetupWindow();
        showLoginWindow();
    }*/
    
    private void closeSetupWindow() {
        Stage stage = (Stage) setupButton.getScene().getWindow();
        stage.close();
    }

    private void showLoginWindow() {
        try {
            Stage primaryStage = new Stage();
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            AnchorPane root = loginLoader.load();
            Login controller = loginLoader.getController();

            
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            
            Scene loginScene = new Scene(root, (screenWidth * 0.45), (screenHeight * 0.6));
            loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Login");
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void setupButtonClicked() {
        String name = nameTextField.getText().trim();
        String surname = surnameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        

        
        if (password.equals(confirmPassword)) {
            
            // Save the admin details using UserManager
            UserManager userManager = new UserManager();
            userManager.insertUser(name, surname, email, password, "Admin", "Approved");

            
            AppConfig.setAdminUsername(name);
            AppConfig.setAdminPassword(password);
            AppConfig.setInitialized(true); 

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Setup Successful");
            alert.setHeaderText("Admin account has been set up successfully.");
            alert.setContentText("You can now log in with the provided credentials.");
            alert.showAndWait();

            
            closeSetupWindow();
            showLoginWindow();
        } else {
            
            showAlert("Password Mismatch", "Passwords do not match. Please make sure the passwords match.");
        }
    }


    
 
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void close() {
        
        System.exit(0);
    }
}

