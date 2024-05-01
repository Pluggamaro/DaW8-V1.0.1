package app;

import app.AppConfig;

import app.UserRole;

import java.io.File;
import java.io.IOException;
import app.Login;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Main extends Application {
	
	private static boolean isRunning;
	
    @Override
    public void start(Stage primaryStage) {
        try {
            
        	Image image = new Image(getClass().getResourceAsStream("da-w8ng-tym-high-resolution-logo-transparent.png"));

        	
            if (!AppConfig.isInitialized()) {
                
                showSetupWizard(primaryStage);
            } else {
                	
                	showLoginWindow(primaryStage);
                	
                }
            
            }
         catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
   

    // Method to process the selected Excel file
    private void processExcelFile(File file) {
        // Implement your logic to read and process the Excel file here
        // Example:
        System.out.println("Selected file: " + file.getAbsolutePath());
    }
    
    
    /*
    private void showDirectorySetterWindow(Stage primaryStage) {
    	if (!AppConfig.isDirectorySettingDone()) {
    	try {
            FXMLLoader directorySetterLoader = new FXMLLoader(getClass().getResource("DirectorySetter.fxml"));
            AnchorPane directorySetterRoot = directorySetterLoader.load();
            DirectorySetterController directorySetterController = directorySetterLoader.getController();

            
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            
            Scene directorySetterScene = new Scene(directorySetterRoot, (screenWidth * 0.45), (screenHeight * 0.6));
            directorySetterScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Directory Setter");
            primaryStage.setScene(directorySetterScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	}
    }
    
    void openReviewWindow() {
        try {
        	
        	Stage primaryStage = new Stage();
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewWindow.fxml"));
            AnchorPane reviewRoot = loader.load();
            ReviewController reviewController = loader.getController();

            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            Scene directorySetterScene = new Scene(reviewRoot, (screenWidth * 0.45), (screenHeight * 0.6));
            directorySetterScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Review Window");
            primaryStage.setScene(directorySetterScene);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
void showAuthorizationWindow() {
    	
    	
    	try {
    		
    		Stage primaryStage = new Stage();
    		FXMLLoader authorizationWindowLoader = new FXMLLoader(getClass().getResource("Authorization.fxml"));
			Pane authorizationWindowRoot = authorizationWindowLoader.load();
			AuthorizationController authorizationController = authorizationWindowLoader.getController();
			authorizationController.setMainWindowController(mainWindowController);
			
			
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();
            
            Scene authorizationWindowScene = new Scene(authorizationWindowRoot, (screenWidth * 0.25), (screenHeight * 0.3));
            authorizationWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Setup Wizard");
            primaryStage.setScene(authorizationWindowScene);
            primaryStage.show();
            
            primaryStage.setOnHidden(event -> {
                UserRole user = authorizationWindowController.getUserRoleAfterLogin(authorizationWindowController.usernameTextField.getText());
                // Check if the current user is an admin
                if (user == UserRole.ADMIN) {
                    // Perform actions specific to admin
                    System.out.println("User is an admin.");
                } else {
                    // Perform actions specific to non-admin user
                    System.out.println("User is not an admin.");
                }
                
            });
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }*/
    
    
    private void showSetupWizard(Stage primaryStage) {
        try {
        	
        	if (WindowManager.getSetupWizard() != null) {
                // Bring the setup wizard window to front if already open
                WindowManager.getSetupWizard().toFront();
            }else {
        	
            FXMLLoader setupWizardLoader = new FXMLLoader(getClass().getResource("SetupWizard.fxml"));
            AnchorPane setupWizardRoot = setupWizardLoader.load();
            SetupWizardController setupWizardController = setupWizardLoader.getController();

            
            // Set the setup wizard instance in the WindowManager
            WindowManager.setSetupWizard(primaryStage);
            
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            
            Scene setupWizardScene = new Scene(setupWizardRoot, (screenWidth * 0.45), (screenHeight * 0.6));
            setupWizardScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Setup Wizard");
            primaryStage.setScene(setupWizardScene);
            primaryStage.show();
            
            
            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

     Login showLoginWindow(Stage primaryStage) throws IOException {
       
    	 
    	 FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
         AnchorPane root = loginLoader.load();
    	 Login controller = loginLoader.getController();
    	/* 
    	 if (WindowManager.getLoginWindow() != null) {
             // Bring the login window to front if already open
             WindowManager.getLoginWindow().toFront();
         } else {
    	 
        	 WindowManager.setLoginWindow(primaryStage);*/
        	 
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        
        
        Scene loginScene = new Scene(root, (screenWidth * 0.99), (screenHeight * 0.93 ));
        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        
        
        
        // Set the login window instance in the WindowManager
        
        
         
        return controller;
    }
    
    void showMainWindow(Stage primaryStage) {	
    	
    	try {
    		
    		FXMLLoader mainWindow = new FXMLLoader(getClass().getResource("DaW8Backend.fxml"));
    		AnchorPane root = mainWindow.load();
    		MainWindowController controller = mainWindow.getController();
    		
    		
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            
            Scene mainWindowScene = new Scene(root, (screenWidth * 0.8), (screenHeight * 0.8));
            mainWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Main");
            primaryStage.setScene(mainWindowScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    		
    	
    }
    
    
     void showAdminWindow(Stage primaryStage) throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
        AnchorPane adminRoot = adminLoader.load();
        MainWindowController adminController = adminLoader.getController();

        
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        
        Scene adminScene = new Scene(adminRoot, (screenWidth * 0.8), (screenHeight * 0.8));
        adminScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Admin Window");
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
