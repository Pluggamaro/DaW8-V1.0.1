package app;

import javafx.stage.Stage;

public class WindowManager {
    private static Stage mainWindow;

    // Private constructor to prevent instantiation
    private WindowManager() {}

    // Method to get the main window instance
    public static Stage getMainWindow() {
        if (mainWindow == null) {
            mainWindow = new Stage();
            // Initialize the main window here
        }
        return mainWindow;
    }
    
    private static Stage setupWizard;
    private static Stage loginWindow;

    public static Stage getSetupWizard() {
        return setupWizard;
    }

    public static void setSetupWizard(Stage setupWizardStage) {
        setupWizard = setupWizardStage;
    }

    public static Stage getLoginWindow() {
        return loginWindow;
    }

    public static void setLoginWindow(Stage loginWindowStage) {
        loginWindow = loginWindowStage;
    }
    
    
}
