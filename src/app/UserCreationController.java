package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class UserCreationController {

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

    @FXML
    private RadioButton adminRadioButton;

    @FXML
    private RadioButton userRadioButton;

    @FXML
    private Button setupButton;

    @FXML
    private Button cancelButton;

    
    
    public UserCreationController() {
    	
    }
    
    @FXML
    private void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        adminRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setToggleGroup(toggleGroup);
    }
    
    
    @FXML
    private void setupButtonClicked() {
        String name = nameTextField.getText().trim();
        String surname = surnameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = ""; 
        String status = "Not Approved";

        
        if (adminRadioButton.isSelected()) {
            role = "Admin";
        } else if (userRadioButton.isSelected()) {
            role = "User";
        }

        
        if (password.equals(confirmPassword)) {
            UserManager userManager = new UserManager();
            userManager.insertUser(name, surname, email, password, role, status);

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Creation Request Sent");
            alert.setHeaderText("Account creation request sent to admin.");
            alert.setContentText("You will be notified when your account is approved.");
            alert.showAndWait();

            
            closeSetupWindow();
        } else {
            
            showAlert("Password Mismatch", "Passwords do not match. Please make sure the passwords match.");
        }
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
    
    
    private void closeSetupWindow() {
        Stage stage = (Stage) setupButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void close() {
        
        System.exit(0);
    }
    
 
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

