package app;

import java.io.File;

import java.io.IOException;

import app.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class DirectorySetterController {

    @FXML
    private TextField defaultLocationTextField;

    @FXML
    private Button confirmButton;

    public DirectorySetterController() {
    }

    @FXML
    private void initialize() {

        defaultLocationTextField.setText(AppConfig.getDefaultLocation());
    }

    @FXML
    private void chooseLocationButtonClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            
            AppConfig.setDefaultLocation(selectedDirectory.getAbsolutePath());
            defaultLocationTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void confirmButtonClicked() {

        AppConfig.setDirectorySettingDone(true);
        

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
       
    }
}
