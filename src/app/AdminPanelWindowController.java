package app;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import app.StaticInfo;
import javafx.animation.Timeline;
import javafx.beans.binding.Binding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;

import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AdminPanelWindowController implements Initializable {
	@FXML
    private TableView<User> activeUsersTableView;
    @FXML private TableColumn<User, String> activeUsersColumn;
    @FXML private TableColumn<User, String> activeUsersRoleColumn;

    @FXML private TableView<User> blockedDeactivatedUsersTableView;
    @FXML private TableColumn<User, String> blockedDeactivatedUsersColumn;
    @FXML private TableColumn<User, String> inactiveUsersRoleColumn;


    @FXML private TableView<User> pendingUsersTableView;
    @FXML private TableColumn<User, String> pendingUsersColumn;
    @FXML private TableColumn<User, String> pendingAccessUsersColumn;
    @FXML private TableColumn<User, LocalDate> pendingDateColumn;



    @FXML
    private Button addUserButton;

    @FXML
    private Button blockDeactivateButton;

    @FXML
    private Button mainWindowButton;

    @FXML
    private Button reviewBincardsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button fileStorageButton;

    private ObservableList<User> activeUsersData = FXCollections.observableArrayList();
    private ObservableList<User> pendingUsersData = FXCollections.observableArrayList();
    private ObservableList<User> blockedUsersData = FXCollections.observableArrayList();


    public AdminPanelWindowController () {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        populateActiveUsersTable();
        populatePendingUsersTable();
        populateDeactivatedUsersTable();

        blockedDeactivatedUsersTableView.refresh();
        activeUsersTableView.refresh();
        pendingUsersTableView.refresh();

     // Context menu for pending users table
        AuthorizationHandler.setupContextMenu(pendingUsersTableView, activeUsersTableView);
        AuthorizationHandler.restoreUserContextMenu(blockedDeactivatedUsersTableView, activeUsersTableView);
    }

    private void setupTableColumns() {
        // Set up cell value factories for active users table columns
        activeUsersColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        activeUsersRoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        // Set up cell value factories for pending users table columns
        pendingUsersColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        pendingAccessUsersColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        pendingDateColumn.setCellValueFactory(cellData -> {
            SimpleObjectProperty<LocalDate> property = new SimpleObjectProperty<>();
            property.setValue(cellData.getValue().getDateOfAction());
            return property;
        });


        blockedDeactivatedUsersColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        inactiveUsersRoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());


    }

    private void populateActiveUsersTable() {
        try (Connection connection = DatabaseManager.getConnection()) {
            String activeUsersQuery = "SELECT name, role FROM users WHERE status = 'Approved'";
            try (PreparedStatement statement = connection.prepareStatement(activeUsersQuery)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    activeUsersData.add(new User(username, role));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        activeUsersTableView.setItems(activeUsersData);
    }

    private void populatePendingUsersTable() {
        try (Connection connection = DatabaseManager.getConnection()) {
            String pendingUsersQuery = "SELECT name, role, date_of_creation FROM users WHERE status = 'Not Approved'";
            try (PreparedStatement statement = connection.prepareStatement(pendingUsersQuery)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    // Convert the date object to LocalDate
                    LocalDate dateOfCreation = resultSet.getObject("date_of_creation", LocalDate.class);
                    pendingUsersData.add(new User(username, role, dateOfCreation));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pendingUsersTableView.setItems(pendingUsersData);
    }


    @FXML
    private void blockDeactivateButtonClicked() {
        // Retrieve the selected user from the active users table
        User selectedUser = activeUsersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {

        	Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Deactivation Confirmation");
            confirmationAlert.setHeaderText("Do you want to block " + selectedUser.getUsername() + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {


            populateDeactivatedUsersTable();

            updateUserStatus(selectedUser, "Blocked");

            activeUsersData.remove(selectedUser);
            }


            blockedDeactivatedUsersTableView.refresh();
            activeUsersTableView.refresh();
            pendingUsersTableView.refresh();
        }
        else {
        	Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("No User Selected");
            confirmationAlert.setHeaderText("No user has been selected. Please select an existing user.");
        }
    }

    private void populateDeactivatedUsersTable() {
    	 //blockedUsersData.clear();
        try (Connection connection = DatabaseManager.getConnection()) {
            String deactivatedUsersQuery = "SELECT name, role, date_of_creation FROM users WHERE status = 'Blocked'";
            try (PreparedStatement statement = connection.prepareStatement(deactivatedUsersQuery)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String username = resultSet.getString("name");
                    String role = resultSet.getString("role");
                    blockedUsersData.add(new User(username, role));
                }



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        blockedDeactivatedUsersTableView.setItems(blockedUsersData);
    }

    private void updateUserStatus(User user, String status) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String updateStatusQuery = "UPDATE users SET status = ? WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateStatusQuery)) {
                statement.setString(1, status);
                statement.setString(2, user.getUsername());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void fileStorageButtonClicked() {

        showDirectorySetterWindow();
    }

    private void showDirectorySetterWindow() {
        try {
            FXMLLoader directorySetterLoader = new FXMLLoader(getClass().getResource("DirectorySetter.fxml"));
            AnchorPane directorySetterRoot = directorySetterLoader.load();
            @SuppressWarnings("unused")
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

    @FXML
    private void addUserButtonClicked() {

    	Login userCreationWindow = new Login();
    	userCreationWindow.openUserCreationWindow();

    }

    @FXML
    private void mainWindowButtonClicked() {
    	Stage primaryStage = new Stage();

    	Login mainWindow = new Login();
    	mainWindow.openBackend();

    	Stage stage = (Stage) mainWindowButton.getScene().getWindow();
		stage.close();

    }



    @FXML
    private void logOutButtonClicked() {

    	Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("LOG OUT Confirmation");
        confirmationAlert.setHeaderText("Do you want to LOG OUT ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();

            Stage loginStage = new Stage();

            Main loginWindow = new Main();
            try {
				loginWindow.showLoginWindow(loginStage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

    }
}
