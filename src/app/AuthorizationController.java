package app;

import java.io.IOException;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class AuthorizationController {
	
	@FXML private TextField usernameTextField; 
	@FXML private PasswordField passwordTextField; 
	@FXML private Button confirmButton;
	@FXML private Button cancelButton;
	


	    
	 @FXML
	 void initialize() {
	     
	 }
	public AuthorizationController() {

	}

private static boolean isAdmin;
	
    private static final AuthorizationController instance = new AuthorizationController();
    AuthorizationController data = AuthorizationController.getInstance();
    MainWindowController controller = new MainWindowController();
    
    public static AuthorizationController getInstance() {
    	return instance;
    }
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(boolean isAdmin) {
		AuthorizationController.isAdmin = isAdmin;
	}

	@FXML
	public void confirmButtonClicked() throws IOException {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		System.out.println("CONFIRM Button Clicked");
		
		if (authenticateUser(username, password)) {
			
			if (isUserApproved(username)) {
				// Successful login
				UserRole userRole = getUserRoleAfterLogin(username);
				
				 handleRoleSpecificActions(userRole);
				
			} else {
				
				displayErrorMessage("This user is not approved.");
			}
		} else {
			
			displayErrorMessage("Invalid username or password!");
		}
	}
	
	
	private boolean authenticateUser(String username, String password) {
		try (Connection connection = DatabaseManager.getConnection()) {
			String authenticateUserSQL = "SELECT * FROM users WHERE name = ? AND password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(authenticateUserSQL)) {
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next(); 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	private boolean isUserApproved(String username) {
		try (Connection connection = DatabaseManager.getConnection()) {
			String queryStatusSQL = "SELECT status FROM users WHERE name = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(queryStatusSQL)) {
				preparedStatement.setString(1, username);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String status = resultSet.getString("status");
						return "Approved".equals(status);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	UserRole getUserRoleAfterLogin(String username) {
		try (Connection connection = DatabaseManager.getConnection()) {
			String queryRoleSQL = "SELECT role FROM users WHERE name = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(queryRoleSQL)) {
				preparedStatement.setString(1, username);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String role = resultSet.getString("role");
						if ("admin".equalsIgnoreCase(role)) {
							return UserRole.ADMIN;
						} else {
							return UserRole.USER;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return UserRole.USER;
	}
	
	AdminPanelWindowController obj = new AdminPanelWindowController();
	
	private void handleRoleSpecificActions(UserRole userRole) throws IOException {
    	
    	// Get controller of MainWindow
    	
    	
		
		switch (userRole) {
	        case ADMIN:
	            // Set isAdmin to true
	           
	        	data.setIsAdmin(true);
	        	//showAlert("Authorization Successful", "Success! You may edit.");
	        	confirm();
	        	break;
	        case USER:
	            // Set isAdmin to false
	        	showAlert("Authorization Failed", "You are not an authorized.");
	            data.setIsAdmin(false);
	            cancelButtonCLicked();
	            break;
	            
	    }
	    
	    
	}

	
	@FXML
	private void cancelButtonCLicked() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void confirm() {
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		stage.close();
	}
	
	private void displayErrorMessage(String message) {

		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	
}
