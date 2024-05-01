package app;

import app.AppConfig;


import app.UserRole;

import java.io.File;
import java.io.IOException;
import app.Login;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

         Image image = new Image(getClass().getResourceAsStream("da-w8ng-tym-high-resolution-logo-transparent.png"));
         Image backgroundImage = new Image(getClass().getResourceAsStream("Charis & CLock.png"));

         Screen screen = Screen.getPrimary();
         double screenWidth = screen.getBounds().getWidth();
         double screenHeight = screen.getBounds().getHeight();

         // Create an ImageView to display the image
         ImageView imageView = new ImageView(image);
         ImageView backgroundImageView = new ImageView(backgroundImage);
         

         // Set fitHeight and fitWidth
         imageView.setFitHeight(346.0);
         imageView.setFitWidth(617.0);
         
         backgroundImageView.setFitHeight(screenHeight);
         backgroundImageView.setFitWidth(screenWidth);

         // Set pickOnBounds and preserveRatio
         imageView.setPickOnBounds(true);
         imageView.setPreserveRatio(true);
         
         backgroundImageView.setPickOnBounds(true);
         backgroundImageView.setPreserveRatio(true);

         // Create InnerShadow effect
         InnerShadow innerShadow = new InnerShadow();
         innerShadow.setBlurType(BlurType.GAUSSIAN); // Use BlurType.GAUSSIAN directly
         innerShadow.setChoke(0.22);
         innerShadow.setHeight(144.99);
         innerShadow.setRadius(80.1825);
         innerShadow.setWidth(177.74);

         // Apply effect to the ImageView
         imageView.setEffect(innerShadow);

         // Create a StackPane to center the ImageView
         StackPane imageRoot = new StackPane(imageView);
         imageRoot.setLayoutX((screenWidth)/3.07);
         imageRoot.setLayoutY(screenHeight / 55);
         
      // Create a StackPane to center the ImageView
         StackPane backgroundRoot = new StackPane(backgroundImageView);
         backgroundRoot.setLayoutX(0);
         backgroundRoot.setLayoutY(0);
         
         root.getChildren().add(0,backgroundRoot);
         
      // Add the logo image to the root AnchorPane
      root.getChildren().add(imageRoot);
        
         
         
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
