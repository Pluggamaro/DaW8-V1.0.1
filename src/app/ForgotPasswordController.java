package app;

import javafx.fxml.FXML;




import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ForgotPasswordController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button resetButton;

    @FXML
    private Button cancelButton;
    
    public ForgotPasswordController() {
    	
    }

    
    @SuppressWarnings("unused")
	private void displayConfirmationMessage(String message) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
    

    @FXML
    private void resetPassword() {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();

        if (isOnline()) {
            // Send password reset email 
            sendResetEmail(username, email);
        } else {
            
            offlineReset(username);
        }
    }

    @FXML
    private void cancelReset() {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // check online connectivity
    private boolean isOnline() {
        
        return true; 
    }

    // send password reset email
    private void sendResetEmail(String username, String email) {

        displayAlert("Password Reset", "Reset email sent successfully to: " + email, AlertType.INFORMATION);
    }

    // offline password reset
    private void offlineReset(String username) {

        displayAlert("Offline Password Reset", "Reset password offline for: " + username, AlertType.INFORMATION);
    }

    // Helper display an alert
    private void displayAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}

