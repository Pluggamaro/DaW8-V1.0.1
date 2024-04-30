package app;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login {

	@SuppressWarnings("unused")
	private UserManager userManager;
	@FXML private TextField usernameTextField; 
	@FXML private TextField passwordTextField; 
	@FXML private Button closeButton;
	@FXML private Button createAccountButton;
	@FXML private Button forgotPasswordButton;
	@FXML private Button loginButton;


	
	AuthorizationController authorizationController;
	
	private static boolean isAdmin;
	
	private static final Login instance = new Login();
    Login data = Login.getInstance();
	
	public Login() {

	}

	 public static Login getInstance() {
	    	return instance;
	    }

	 public boolean isAdmin() {
			return isAdmin;
		}
		
		private void setIsAdmin(boolean isAdmin) {
			Login.isAdmin = isAdmin;
		}
	 
	void openUserCreationWindow() {
		try {
			FXMLLoader userCreationLoader = new FXMLLoader(getClass().getResource("UserCreation.fxml"));
			AnchorPane userCreationRoot = userCreationLoader.load();
			@SuppressWarnings("unused")
			UserCreationController userCreationController = userCreationLoader.getController();

			Stage userCreationStage = new Stage();

			Scene userCreationScene = new Scene(userCreationRoot);
			userCreationStage.setScene(userCreationScene);
			userCreationStage.setTitle("User Creation");
			userCreationStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void createAccountButtonClicked() {
		openUserCreationWindow();
	}



	public Login(UserManager userManager, TextField usernameTextField, TextField passwordTextField) {
		this.userManager = userManager;
		this.usernameTextField = usernameTextField;
		this.passwordTextField = passwordTextField;
		
	}


	public void loginButtonClicked() {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		
		if (authenticateUser(username, password)) {
			
			if (isUserApproved(username)) {
				// Successful login
				UserRole userRole = getUserRoleAfterLogin(username);
				Stage stage = (Stage) loginButton.getScene().getWindow();
				stage.close();
				handleRoleSpecificActions(userRole);
			} else {
				
				displayErrorMessage("Account not approved. Seek approval.");
			}
		} else {
			
			displayErrorMessage("Invalid username or password. Please try again.");
		}
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



	private void handleRoleSpecificActions(UserRole userRole) {
		switch (userRole) {
		case ADMIN:
			setIsAdmin(true);
			openAdminWindow();
			break;
		case USER:
			setIsAdmin(false);
			openBackend();
			break;
			
		}
	}
	
	static Stage secondaryStage = new Stage();

	void frontScreenOpen() {
		  
        Login.secondaryStage.show();
        
	}
	
	void frontScreenHide() {
		Login.secondaryStage.hide();
	}
	
	void openBackend() {
		try {
			
			Stage adminWindow = new Stage();
			
			
			
			 FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("DaW8Backend.fxml"));
		        AnchorPane adminRoot = adminLoader.load();
		        MainWindowController adminController = adminLoader.getController();

		        // Load the SecondaryWindow
		        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("Screen.fxml"));
		        AnchorPane secondaryRoot = secondaryLoader.load();
		        ScreenController secondaryController = secondaryLoader.getController();

		        // Set the SecondaryWindowController instance in the AdminWindowController
		        adminController.setSecondaryController(secondaryController);

		        // Additional setup...
		        
		        Screen screen = Screen.getPrimary();
	            double screenWidth = screen.getBounds().getWidth();
	            double screenHeight = screen.getBounds().getHeight();

	            Scene loginScene = new Scene(adminRoot, (screenWidth * 0.6), (screenHeight * 0.8));
	            loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            
	            adminWindow.initStyle(StageStyle.UTILITY);
	            
	            
	            
	            adminWindow.setFullScreen(true);
		        adminWindow.setTitle("Backend Window");
		        adminWindow.setScene(loginScene);
		        adminWindow.show();
		        
		        
		        adminWindow.setOnShowing(event -> {
		        	adminController.patientNoTextField.requestFocus();
		        });
		        
		        adminWindow.focusedProperty().addListener((observable, oldValue, newValue) -> {
		            if (!newValue && !adminWindow.isIconified()) { // If window gets focus and is not minimized
		                adminController.patientNoTextField.requestFocus(); // Reapply focus to text field
		            }
		        });

		        // Listen for stage iconified (minimized) property change
		        adminWindow.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
		            if (newValue) { // If stage is minimized
		                adminController.patientNoTextField.requestFocus(); // Reapply focus to text field
		            }
		        });

		        adminWindow.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
	                if (event.getCode() == KeyCode.ESCAPE) {
	                    event.consume(); // Consume the event to prevent exiting fullscreen
	                }
	            });
		        
		        adminWindow.setOnHidden(event -> {
		        	Login.secondaryStage.close();
		        	adminController.scheduler.shutdown();
		        	adminController.schedulerFront.shutdown();
		        	System.out.print(adminController.scheduler.isShutdown());
		        });
		        
		        // Show the secondary stage
		        
		        Login.secondaryStage.setTitle("Secondary Window");
		        Login.secondaryStage.setScene(new Scene(secondaryRoot, screenWidth * 0.99, screenHeight * 0.93));
		        //secondaryStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unused")
	private void showDirectorySetterWindow() {
		try {
			FXMLLoader directorySetterLoader = new FXMLLoader(getClass().getResource("DirectorySetter.fxml"));
			AnchorPane directorySetterRoot = directorySetterLoader.load();
			DirectorySetterController directorySetterController = directorySetterLoader.getController();

			
			Stage directorySetterStage = new Stage();


			Screen screen = Screen.getPrimary();
			double screenWidth = screen.getBounds().getWidth();
			double screenHeight = screen.getBounds().getHeight();

			
			Scene directorySetterScene = new Scene(directorySetterRoot, (screenWidth * 0.45), (screenHeight * 0.6));
			directorySetterScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			directorySetterStage.setTitle("Directory Setter");
			directorySetterStage.setScene(directorySetterScene);
			directorySetterStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unused")
	private void openAdminWindow() {
		try {

			FXMLLoader mainWindow = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
			AnchorPane root = mainWindow.load();
			AdminPanelWindowController controller = mainWindow.getController();

			
			Stage userMainWindowStage = new Stage();
			
			
			Screen screen = Screen.getPrimary();
			double screenWidth = screen.getBounds().getWidth();
			double screenHeight = screen.getBounds().getHeight();

			
			Scene mainWindowScene = new Scene(root, (screenWidth * 0.8), (screenHeight * 0.9));
			mainWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			userMainWindowStage.setTitle("ADMIN PANEL");
			userMainWindowStage.setScene(mainWindowScene);
			userMainWindowStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayErrorMessage(String message) {

		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}

	@FXML
	public void exitButtonClicked() {
		
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
		confirmation.showAndWait();

		if (confirmation.getResult() == ButtonType.YES) {
			
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		}
	}
}
