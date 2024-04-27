package app;

import javafx.beans.property.ObjectProperty;

import javafx.beans.property.SimpleBooleanProperty;
import app.StaticInfo;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

//import javafx.util.converter.LocalDateStringConverter;

import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.scene.paint.Color;
import javafx.beans.value.ObservableBooleanValue;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@SuppressWarnings("unused")
public class MainWindowController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button addEntryButton;

	@FXML
	private TableColumn<BinCard, Integer> patientNoColumn;

	@FXML
	private TextField patientNoTextField;

	@FXML
	private TableView<BinCard> daW8BackendTableView;

	@FXML
	private Button checkButton;

	@FXML
	private TableColumn<BinCard, LocalDate> dateColumn;

	@FXML
	private TextField dateTextField;

	@FXML
	private Button deleteTableEntryButton;

	@FXML
	private Button deleteBinCardButton;

	@FXML
	private Button editStaticInfoButton;

	@FXML
	private TableColumn<BinCard, LocalTime> timeINColumn;
	
	@FXML
	private TableColumn<BinCard, LocalTime> timeOUTColumn;

	@FXML
	private TextField queNoTextField;
	
	@FXML
	private TextField timeINTextField;

	@FXML
	private TextField timeOUTTextField;
	
	@FXML
	private TextField statusTextField;
	
	@FXML
	private Button exportAsButton;

	@FXML
	private Button logOutButton;

	@FXML
	private Button newBinCardButton;

	@FXML
	private Button openReviewButton;
	
	@FXML
	private Button showFrontScreen;
	
	@FXML
	private Button hideFrontScreen;

	@FXML
	private TableColumn<BinCard, Integer> queNoColumn;

	@FXML
	private TextField waitingTimeTextField;

	@FXML
	private TableColumn<BinCard, Integer> waitingTimeColumn;
	
	@FXML
	private TableColumn<BinCard, Integer> statusColumn;

	@FXML
	private Button refreshButton;
	
	@FXML
	private Button exportToExcel;
	
	@FXML
	private Button saveToDatabase;
	
	@FXML
	private Button clearCard;

	@FXML
	private Button saveStaticInfoButton;
	
	@FXML
    private StackPane stackPane;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Rectangle speechBubble;
	    
	@FXML
	private Polyline speechBubbleTail;
	
	@FXML
	private Circle greenCircle;
	
	@FXML
	private Polyline tick;
	
	@FXML
	private Circle redCircle;
	
	@FXML
	private Line cross1;
	
	@FXML
	private Line cross2;

    @FXML
    private DatePicker datePicker;
	
	private Connection connection;
	private static boolean checkIfExistingButtonPressed = false;
	private static boolean firstTimeInputChecked = false;
	private static boolean inputFitForEntry = false;
	private static boolean addEntryButtonPressed = false;
	private static boolean firstEntry = true;
	@FXML
	private ListView<String> binCardListView;

	AuthorizationController data = AuthorizationController.getInstance();
	
	ScreenController frontData = ScreenController.getInstance();
	
	ScreenController frontScreen;
	
	private static final String EXCEL_FILE_PATH = "binCardData.xlsx";

	private StaticInfo staticInfo = new StaticInfo();
	private DynamicData dynamicData = new DynamicData();
	private BinCardDataBaseManager binCarddbManager;
	private BinCard binCard = new BinCard();
	private boolean eventHandlerActive = true;
	static boolean authorizationWindowShown = false;
	private static boolean loadPressed = false;

	private static int quantityOrderedDefault = 0;
	private static int quantityReceivedDefault = 0;
	private static String expiryDateDefault = "";
	private static int quantityIssuedDefault = 0;
	private static String toFromDefault = "";
	private static String referenceNoDefault = "";
	private static String signatureDefault = "";
	// MainWindowController
	private static String dateIssueSolved = "NO";
	private boolean alertShown = false;
	public  final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	public  final ScheduledExecutorService schedulerFront = Executors.newScheduledThreadPool(1);
	
	Login adminCheck = new Login();
	
	BinCardDataBaseManager data2 = BinCardDataBaseManager.getInstance();
	
	@FXML
    private ChoiceBox<String> choiceBox;
	
	 private static final int MIN_VALUE = 100;
	    private static final int MAX_VALUE = 999;

	    private Set<Integer> assignedNumbers = new HashSet<>();
	    private static int assignedNumbersCount = 0;
	    
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	// Beginning of authorization stageLauncher

//Beginning of authWindowShow

	    public Stage showAuthorizationWindow() {
	        try {
	            Stage primaryStage = new Stage();
	            FXMLLoader authorizationWindowLoader = new FXMLLoader(getClass().getResource("Authorization.fxml"));
	            Pane authorizationWindowRoot = authorizationWindowLoader.load();
	            AuthorizationController authorizationController = authorizationWindowLoader.getController();

	            Screen screen = Screen.getPrimary();
	            double screenWidth = screen.getBounds().getWidth();
	            double screenHeight = screen.getBounds().getHeight();

	            Scene authorizationWindowScene = new Scene(authorizationWindowRoot, (screenWidth * 0.25),
	                    (screenHeight * 0.3));
	            authorizationWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            primaryStage.setTitle("Setup Wizard");
	            primaryStage.setScene(authorizationWindowScene);
	            primaryStage.show();

	            primaryStage.setOnHidden(event -> {
	            	showAlert("Authorization","Authorized: "+data.isAdmin()+"");
	            });
	            
	            return primaryStage; // Return the stage of the authorization window
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null; // Return null in case of an exception
	        }
	    }
	    
	    public void setSecondaryController(ScreenController secondaryController) {
	        this.frontScreen = secondaryController;
	    }


// END of authWindowShow

	// End of seperate authorization Class
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		binCarddbManager = new BinCardDataBaseManager();
		try {
			connection = DatabaseManager.getBinCardConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initialize();
                
	}
	
	@FXML
	public void handleHideFrontScreen() {
		
			adminCheck.frontScreenHide();
		
	}
	
	@FXML
	public void handleShowFrontScreen() {
		
			adminCheck.frontScreenOpen();
		
	}
	
	private void initialize() {
		initializeTableColumns();

		emptyBinCardButtonSetup();

		daW8BackendTableView.setItems(dynamicData.getBinCardData());
		
		populateEditChoiceBox();
		
		textFieldEditHandler();
	        
		
	/*	BARCODE SCANNER part
		barcodeTextField.setOnKeyTyped(event -> {
            // Check if the input is a carriage return (barcode scanner delimiter)
            if (event.getCharacter().charAt(0) == '\n') {
                // Barcode was scanned, process it
                String scannedBarcode = barcodeTextField.getText();
                processScannedBarcode(scannedBarcode);

                // Clear the text field for the next scan
                barcodeTextField.clear();
            }
        });
    }*/
		
		
		daW8BackendTableView.focusedProperty().addListener((observable1, old1, newValue1) -> {
			if(!daW8BackendTableView.getItems().isEmpty()) {
				patientNoTextField.setDisable(false);
				showSpeechBubble(false);
			}
		});
		
		dateTextField.textProperty().addListener((observable2,oldValue2,newValue2) -> {
			 if(!(binCarddbManager.staticInfoExists(LocalDate.parse(dateTextField.getText())) && daW8BackendTableView.getItems().isEmpty()) && dateTextField.getText().length() == 10) {
	            	
	            	patientNoTextField.setDisable(false);
	        		showSpeechBubble(false);
	        	}
			 else if(binCarddbManager.staticInfoExists(LocalDate.parse(dateTextField.getText()))) {
				 showSpeechBubble(true);
				 patientNoTextField.setDisable(true);
				 
			 }
		}
		);
		
		//patientNoTextField.focusedProperty().addListener((observable, oldValue, newValue) -> handleCheckIfExisting());
			
		 // Set the current date in the dateTextField
         dateTextField.setText(LocalDate.now().toString());
       
       refreshHandler();
      
       pushEntryInOut();
      
        setupDatePickerListener();
        LocalDate initialDate = LocalDate.now();
        populateAvailableDates(initialDate);
        
        updateSpeechBubbleVisibility(handleCheckIfExisting());
        
        greenCircle.setVisible(false);
        tick.setVisible(false);
        redCircle.setVisible(false);
        cross1.setVisible(false);
        cross2.setVisible(false);
        
	}
	
	private void populateEditChoiceBox() {
		List<String> itemList = Arrays.asList("Date", "Queue No", "Time IN", "Time OUT");

        // Add items to the choiceBox
        choiceBox.getItems().addAll(itemList);
	}
	
	private void textFieldEditHandler() {
		choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        	if(adminCheck.isAdmin()) {
            // Handle the selection change here
        	if(newValue.equalsIgnoreCase("Date")) {
        		dateTextField.setEditable(true);
        		queNoTextField.setEditable(false);
        		timeINTextField.setEditable(false);
        		timeOUTTextField.setEditable(false);
        	}
        	
        	if(newValue.equalsIgnoreCase("Queue No")) {
        		queNoTextField.setEditable(true);
        		dateTextField.setEditable(false);
        		timeINTextField.setEditable(false);
        		timeOUTTextField.setEditable(false);
        	}
        	
        	if(newValue.equalsIgnoreCase("Time IN")) {
        		timeINTextField.setEditable(true);
        		dateTextField.setEditable(false);
        		queNoTextField.setEditable(false);
        		timeOUTTextField.setEditable(false);
        	}
        	
        	if(newValue.equalsIgnoreCase("Time OUT")) {
        		timeOUTTextField.setEditable(true);
        		dateTextField.setEditable(false);
        		queNoTextField.setEditable(false);
        		timeINTextField.setEditable(false);
        	}
        	
            System.out.println("Selected item: " + newValue);
            // You can perform any action based on the selected item
        	}
        	else {
	        	Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText("NOT AUTHORIZED");
				alert.setContentText("You need AUTHORIZATION TO BE ABLE TO CHANGE FIELDS!");
				ButtonType authorizeButton = new ButtonType("Authorize", ButtonBar.ButtonData.OK_DONE);
				ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(authorizeButton, cancelButton);
				Optional<ButtonType> result1 = alert.showAndWait();

				result1.ifPresent(buttonType -> {
					if (buttonType == authorizeButton) {
						
		                    // Show the authorization window
		                    showAuthorizationWindow().setOnHidden(action -> {
		                    	if(data.isAdmin()) {
		                    		actionSuccess();
		                    		if(newValue.equalsIgnoreCase("Date")) {
		                    			queNoTextField.setEditable(false);
		            	        		dateTextField.setEditable(true);
		            	        		timeINTextField.setEditable(false);
		            	        		timeOUTTextField.setEditable(false);
		            	        	}
		            	        	
		            	        	if(newValue.equalsIgnoreCase("Queue No")) {
		            	        		queNoTextField.setEditable(true);
		            	        		dateTextField.setEditable(false);
		            	        		timeINTextField.setEditable(false);
		            	        		timeOUTTextField.setEditable(false);
		            	        	}
		            	        	
		            	        	if(newValue.equalsIgnoreCase("Time IN")) {
		            	        		queNoTextField.setEditable(false);
		            	        		dateTextField.setEditable(false);
		            	        		timeINTextField.setEditable(true);
		            	        		timeOUTTextField.setEditable(false);
		            	        	}
		            	        	
		            	        	if(newValue.equalsIgnoreCase("Time OUT")) {
		            	        		queNoTextField.setEditable(false);
		            	        		dateTextField.setEditable(false);
		            	        		timeINTextField.setEditable(false);
		            	        		timeOUTTextField.setEditable(true);
		            	        	}
		                    	}
		                    	else {
		                    		actionFail();
		                    	}
		                    });

		                    // Set the flag to indicate that the authorization window has been shown
		                    //authorizationWindowShown = true;
					
					}
						
				});
	        }
        	
        });
	}
	
	private void pushEntryInOut() {
		// Set the handler for Enter key press in patientNoTextField
        patientNoTextField.setOnAction(event -> {
			try {
				
				int patientNo = Integer.parseInt(patientNoTextField.getText());

			    // Find the BinCard object corresponding to the patient number
			    BinCard initialQueueCard = null;
			    BinCard renewedQueueCard = null;
			    BinCard screenRemove = null;
			    for (BinCard card : daW8BackendTableView.getItems()) {
			        if (card.getPatientNo() == patientNo) {
			        	
			        	if(card.getTimeOUT() != LocalTime.parse("00:00", timeFormatter)) {
			            renewedQueueCard = card;
			            Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText("Confirm Entry");
						alert.setContentText("This Patient was SIGNED OUT!");
						
						ButtonType cancelButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
						alert.getButtonTypes().setAll(cancelButton);
						alert.showAndWait();
						break;
			        	}
			        	if(card.getStatus().equalsIgnoreCase("IN")) {
			        		initialQueueCard = card;
			        		break;
			        	}
			        }
			    }
			    

			    if (initialQueueCard != null && (initialQueueCard.getTimeOUT() == LocalTime.parse("00:00", timeFormatter))) {
			        // Update timeOut with the current time
			        initialQueueCard.setTimeOUT(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));

			        // Calculate waiting time
			        Duration duration = Duration.between(initialQueueCard.getTimeIN(), initialQueueCard.getTimeOUT());
			        long minutes = duration.toMinutes(); // Convert duration to minutes
			        int waitingTime = (int) minutes;
			        
			        // Update waitingTime property of the BinCard object
			        initialQueueCard.setWaitingTime(waitingTime);
			        initialQueueCard.setStatus("OUT");
			        
			        BinCard screenEntryToRemove = null;
	                for (BinCard screenEntry : frontScreen.screenTableView.getItems()) {
	                    if ((int)screenEntry.getPatientNo() == (int)initialQueueCard.getPatientNo()) {
	                        screenEntryToRemove = screenEntry;
	                   
	                        //removeScreenEntry(frontScreen.screenTableView,screenEntryToRemove);
	                        
	                        frontScreen.screenTableView.getItems().remove(screenEntryToRemove);
		                    frontScreen.screenTableView.refresh();
	                        break;
	                    }
	                    
	                }
	               
			        // Update TableView
			        daW8BackendTableView.refresh();
			        
			    }if((initialQueueCard == null) && (renewedQueueCard == null)) {
				timeINTextField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
				statusTextField.setText("IN");
				handleAddEntry();
				refreshHandler();
			    }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	 public static void removeScreenEntry(TableView<BinCard> tableView, BinCard entryToRemove) {
	        javafx.util.Duration SLIDE_DURATION = javafx.util.Duration.millis(500);
	        double TABLE_WIDTH = 840;
	        
	        // Create and play slide-out animation
	        TranslateTransition slideOutTransition = new TranslateTransition(SLIDE_DURATION , tableView);
	        slideOutTransition.setFromX(0);
	        
			slideOutTransition.setToX(TABLE_WIDTH );
	        slideOutTransition.setOnFinished(event -> {
	            // Remove entry from TableView after animation completes
	            tableView.getItems().remove(entryToRemove);
	            tableView.refresh();
	        });
	        slideOutTransition.play();
	    }
	
	 private void updateSpeechBubbleVisibility(boolean isVisible) {
		 	stackPane.setVisible(isVisible);
	        speechBubble.setVisible(isVisible);
	        speechBubbleTail.setVisible(isVisible);
	        patientNoTextField.setDisable(isVisible);
	    }
	 
	 private void showSpeechBubble(boolean show) {
	        updateSpeechBubbleVisibility(show);
	    }
	 
	 private void populateAvailableDates(LocalDate selectedDate) {
		    // Query your database to retrieve available dates and populate the DatePicker
		    List<LocalDate> availableDates = yourDatabaseQueryMethod(selectedDate);
		    datePicker.setDayCellFactory(picker -> new DateCell() {
		        @Override
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            if (!empty && !availableDates.contains(date)) {
		                setDisable(true);
		                setStyle("-fx-background-color: #ffc0cb;"); // Disabled date style
		            }
		        }
		    });
		}


	 private void setupDatePickerListener() {
		    datePicker.setOnAction(event -> {
		    	
		        LocalDate selectedDate = datePicker.getValue();
		        if (selectedDate != null) {
		        if(!daW8BackendTableView.getItems().isEmpty()) {
		            yourDatabaseQueryMethod(selectedDate);
		            System.out.print(selectedDate);
		            Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText("Confirm LOAD");
					alert.setContentText("You are about to load a different QUEUE CARD.\nAny unsaved changes WILL BE  LOST.");
					ButtonType proceedButton = new ButtonType("Proceed", ButtonBar.ButtonData.OK_DONE);
					ButtonType cancelButton2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
					alert.getButtonTypes().setAll(proceedButton, cancelButton2);
					Optional<ButtonType> result2 = alert.showAndWait();

					result2.ifPresent(buttonType2 -> {
						if (buttonType2 == proceedButton)
					                handleLoadFromDatabase(selectedDate);
					});
		        }else
		        handleLoadFromDatabase(selectedDate);
		    	}
		        else {
					Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
					alert2.setHeaderText("LOAD FAIL");
					alert2.setContentText("CARD could NOT be RETRIEVED.");
					alert2.showAndWait();
				}
		    });
		    
		   
		}
	    
	 private List<LocalDate> yourDatabaseQueryMethod(LocalDate selectedDate) {
		    List<LocalDate> availableDates = new ArrayList<>();
		    
		    LocalDate date = null;
		    
		    if (selectedDate != null) {
		             String query = "SELECT DISTINCT Date FROM dynamic_data"; // Modify this query according to your database schema
		             try (PreparedStatement preparedStatement = connection.prepareStatement(query);
		                  ResultSet resultSet = preparedStatement.executeQuery()) {
		                 while (resultSet.next()) {
		                     date = resultSet.getDate("Date").toLocalDate();
		                     availableDates.add(date);
		                 }
		             }
		          catch (SQLException e) {
		             e.printStackTrace();
		             // Handle any SQL exceptions
		         }
		    }
		    return availableDates;
		}

	@FXML
	private void handleEndSession() {
	    int patientNo = Integer.parseInt(patientNoTextField.getText());

	    // Find the BinCard object corresponding to the patient number
	    BinCard binCard = null;
	    for (BinCard card : daW8BackendTableView.getItems()) {
	        if (card.getPatientNo() == patientNo) {
	            binCard = card;
	            break;
	        }
	    }

	    if (binCard != null) {
	        // Update timeOut with the current time
	        binCard.setTimeOUT(LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));

	        // Calculate waiting time
	        Duration duration = Duration.between(binCard.getTimeIN(), binCard.getTimeOUT());
	        long minutes = duration.toMinutes(); // Convert duration to minutes
	        int waitingTime = (int) minutes;

	        // Update waitingTime property of the BinCard object
	        binCard.setWaitingTime(waitingTime);
	        binCard.setStatus("OUT");

	        // Update TableView
	        daW8BackendTableView.refresh();
	    } else {
	        // Patient not found, handle error
	        handleInvalidInput();
	    }
	}
	
	public int generateNextQueNumber() {
        int currentNumber = 1;
        int generatedNumber;

        do {
            // Generate a random number between MIN_VALUE and MAX_VALUE
            generatedNumber = currentNumber++;
        } while (assignedNumbers.contains(generatedNumber)); // Repeat if the number is already assigned

        assignedNumbers.add(generatedNumber); // Add the generated number to the set of assigned numbers
        return generatedNumber;
    }
	
	
	// Method to validate date format
	private boolean isValidDateFormat(String date) {
		try {
	        // Attempt to parse the date string
	        LocalDate.parse(date);
	        // If parsing succeeds, return true
	        return true;
	    } catch (DateTimeParseException e) {
	        // If parsing fails, return false
	        return false;
	    }
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void enableDynamicInfoFields(boolean bool) {

		dateTextField.setEditable(bool);

		queNoTextField.setEditable(bool);
		timeINTextField.setEditable(bool);
		timeOUTTextField.setEditable(bool);
		
	}

	private boolean allDynamicTextFieldsFilled() {
		return !dateTextField.getText().isEmpty() && !patientNoTextField.getText().isEmpty()
				&& !queNoTextField.getText().isEmpty() && !timeINTextField.getText().isEmpty();
	}
/*
	private void calculateBalanceFromTable() {
		int balance = 0;
		int activeBalance = 0;
		ObservableList<BinCard> data = daW8BackendTableView.getItems();
		for (BinCard entry : data) {
			balance += entry.getQuantityReceived() - entry.getQuantityIssued();
			activeBalance = balance + Integer.parseInt(quantityReceivedTextField.getText())
					- Integer.parseInt(quantityIssuedTextField.getText());
		}
		balanceTextField.setText(Integer.toString(activeBalance));

	}
*/
	
	
	
	public void startAutoSave() {
        //scheduler.scheduleAtFixedRate(this::handleSaveStaticInfo, 3, 1, TimeUnit.MINUTES);
        
        scheduler.scheduleWithFixedDelay(this::handleSaveStaticInfo, 1, 1, TimeUnit.MINUTES);  
            
    }
	
	@FXML
	private void handleSaveStaticInfo() {
	    try {
	        // Begin a transaction
	        // Iterate through table rows to collect dynamic information
	        ObservableList<BinCard> data = daW8BackendTableView.getItems();
	        
	        for (BinCard entry : data) {
	            LocalDate date = entry.getDate();
	            LocalTime timeIN = entry.getTimeIN();
	            LocalTime timeOUT = entry.getTimeOUT();
	            Integer patientNo = entry.getPatientNo();
	            Integer queNo = entry.getQueNo();
	            Integer waitingTime = entry.getWaitingTime();
	            String status = entry.getStatus();

	           binCarddbManager.insertOrUpdateDynamicData(date, patientNo, queNo, timeIN, timeOUT, waitingTime, status);
	           
	            
	        }
	        
	        int saveIconCount = 0;
	        if(saveIconCount == 0) {
	        if(data2.getSaveOrNot()) {
	        	   
	        	 actionSuccess();
	        	   
	        	 saveIconCount ++;
	        	 
	           }else {
	        	   
	        	   actionFail();
	        	   saveIconCount ++;
	        	   
	           }
	        }
	    } catch (Exception e) {

	        e.printStackTrace();
	    } 
	}
	
	public void actionSuccess() {
		
		PauseTransition pause1 = new PauseTransition(javafx.util.Duration.seconds(5));
		PauseTransition pause2 = new PauseTransition(javafx.util.Duration.seconds(5));
		  pause1.setOnFinished(event -> greenCircle.setVisible(false));
 	   pause2.setOnFinished(event2 -> tick.setVisible(false));
 	   greenCircle.setVisible(true);
 	   tick.setVisible(true);
 	   pause1.play();
 	   pause2.play();
 	   
	}
	
	public void actionFail() {
	
		PauseTransition pause1 = new PauseTransition(javafx.util.Duration.seconds(5));
		PauseTransition pause2 = new PauseTransition(javafx.util.Duration.seconds(5));
		PauseTransition pause3 = new PauseTransition(javafx.util.Duration.seconds(5));
		  pause1.setOnFinished(event -> redCircle.setVisible(false));
 	   pause2.setOnFinished(event -> cross1.setVisible(false));
 	  pause3.setOnFinished(event -> cross2.setVisible(false));
 	   redCircle.setVisible(true);
 	   cross1.setVisible(true);
 	   cross2.setVisible(true);
 	   pause1.play();
 	  pause2.play();
 	 pause3.play();
		
	}
	
	@FXML
	private boolean handleCheckIfExisting() {
/*
		if (!alertShown && binCarddbManager.staticInfoExists(LocalDate.parse(dateTextField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Confirm LOAD");
            alert.setContentText("A Queue CARD of THIS DATE EXISTS. \nFor data protection, you CANNOT produce another!");
            ButtonType proceedButton = new ButtonType("LOAD", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(proceedButton, cancelButton);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == proceedButton) {
                    handleLoadFromDatabase(LocalDate.parse(dateTextField.getText()));
                    patientNoTextField.setDisable(false); // Enable patientNoTextField after LOAD
                } else {
                    // If CANCEL is clicked, reset focus to dateTextField
                    dateTextField.requestFocus();
                }
            });
            //alertShown = true; // Set flag to true after showing the alert
            patientNoTextField.setDisable(true); // Disable patientNoTextField while the alert is shown
        }
*/
		return binCarddbManager.staticInfoExists(LocalDate.parse(dateTextField.getText()));

	}
/*
	@FXML
	private void handleLoadFromDatabase() {

	 {
			//Date selection object should be here which will listen for the selected date by the administrator

				// loading dynamic data associated with this static info ID
				List<DynamicData> dynamicDataList = binCarddbManager.retrieveDynamicData(/*Date Object will then be used here for retrieval of such dated info );

				dynamicData.getBinCardData().clear();
				
				BinCard binCard = new BinCard(dynamicData);

				for (DynamicData dynamicData : dynamicDataList) {
					
					daW8BackendTableView.getItems().add(binCard);;
				}

				// Update the UI
				updateUIWithDynamicData(dynamicDataList);

				loadBinCardButtonSetup();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Bin Card loaded successfully from the database.");
				alert.showAndWait();
			} 
		 
	}
	*/
	
	private void handleLoadFromDatabase(LocalDate selectedDate) {
	    try {
	        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);

	        // loading dynamic data associated with this static info ID
	        List<DynamicData> dynamicDataList = binCarddbManager.retrieveDynamicData(selectedDate);

	        daW8BackendTableView.getItems().clear(); // Clear previous items
	        frontScreen.screenTableView.getItems().clear();

	        for (DynamicData dynamicData : dynamicDataList) {
	            BinCard binCard = new BinCard(dynamicData);
	            daW8BackendTableView.getItems().add(binCard);
	        }

	        // Update the UI
	        updateUIWithDynamicData(dynamicDataList);
	        
	        fillFrontScreen();
	        
	        loadBinCardButtonSetup();
	        
	        if(loadPressed) {
	        	patientNoTextField.setDisable(false);
	        	updateSpeechBubbleVisibility(false);
	        }
	        
	        BinCard binCard = null;
		    for (BinCard card : daW8BackendTableView.getItems()) {
		        if (card != null) {
		            binCard = card;
		            break;
		        }
		    }
		    
		    dateTextField.setText(binCard.getDate().toString());
		    
		    
		    
		    startWaitingTimeUpdateTimer();

	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setHeaderText("Success");
	        alert.setContentText("Bin Card loaded successfully from the database.");
	        alert.showAndWait();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void fillFrontScreen() {
		BinCard screenEntryToAdd = null;
        for (BinCard screenEntry : daW8BackendTableView.getItems()) {
            if (screenEntry.getStatus().toString().equals("IN") ) {
                screenEntryToAdd = screenEntry;
           
                //removeScreenEntry(frontScreen.screenTableView,screenEntryToRemove);
                
                frontScreen.screenTableView.getItems().add(screenEntryToAdd);
                frontScreen.screenTableView.refresh();
                
            }
            
        }
	}

	@FXML
	private void handleEditBinCard() {

		if (allStaticInfoFieldsFilled()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("Bin Card Edit");
			alert.setContentText("Do you want to EDIT the Bin Card?");
			ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(yesButton, noButton);
			Optional<ButtonType> result = alert.showAndWait();

			result.ifPresent(buttonType -> {
				if (buttonType == yesButton) {

					showAuthorizationWindow();
				}
			});
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Table Edit");
			alert.setContentText("The static data CANNOT be EMPTY?");
			alert.showAndWait();

			if (alert.isShowing()) {
				Alert alert2 = new Alert(Alert.AlertType.WARNING);
				alert2.setContentText("This happens while it shows");
				alert2.showAndWait();
			}
		}
	}

	@FXML
	private void handleGenerateNewBinCard() {

		if(!daW8BackendTableView.getItems().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Save OR Delete");
			alert.setContentText("Table is NOT EMPTY.\nAny UNSAVED changes will BE LOST!");
			ButtonType clearButton = new ButtonType("CLEAR", ButtonBar.ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(clearButton, cancelButton);
			Optional<ButtonType> result = alert.showAndWait();

			result.ifPresent(buttonType -> {
				if (buttonType == clearButton) {
				// Clear the current bin card data and perform any other necessary actions
				clearCurrentBinCard();
				
				//initialize();
				
				firstEntry = true;
			}
		});
		}
		
		initialize();
		
	}

	private void clearCurrentBinCard() {
		

		// Clear table data
		daW8BackendTableView.getItems().clear();
		clearFrontScreen();
	}

	public boolean isBinCardEditable() {
		if (data.isAdmin()) {

			setCellFactories(true);

			return true;
		}
		return false;
	}

	private void updateUIWithDynamicData(List<DynamicData> dynamicDataList) {

		daW8BackendTableView.getItems().clear();

		for (DynamicData dynamicData : dynamicDataList) {

			BinCard binCard = new BinCard(dynamicData);
			daW8BackendTableView.getItems().add(binCard);
			
			if((int)dynamicData.getQueNo() < (generateNextQueNumber() + 1)){
				queNoTextField.setText(Integer.toString((int)dynamicData.getQueNo() + 1));
				break;
			}
			
			
		}
		
	}

	private boolean allStaticInfoFieldsFilled() {

		return !patientNoTextField.getText().isEmpty()&& !queNoTextField.getText().isEmpty() 
				&& !timeINTextField.getText().isEmpty();
	}

	private void initializeTableColumns() {

		dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
		patientNoColumn.setCellValueFactory(new PropertyValueFactory<>("PatientNo"));
		queNoColumn.setCellValueFactory(new PropertyValueFactory<>("QueNo"));
		timeINColumn.setCellValueFactory(new PropertyValueFactory<>("TimeIN"));
		timeOUTColumn.setCellValueFactory(new PropertyValueFactory<>("TimeOUT"));
		waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("WaitingTime"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
	}

	private void setCellFactories(boolean bool) {

		if (bool) {

			LocalDateStringConverter converter = new LocalDateStringConverter();
			
			setEditableCellFactory(dateColumn, new LocalDateStringConverter());
			setEditableCellFactory(patientNoColumn, new IntegerStringConverter());
			setEditableCellFactory(queNoColumn, new IntegerStringConverter());
			setEditableCellFactory(timeINColumn, new LocalTimeStringConverter());
			setEditableCellFactory(timeOUTColumn, new LocalTimeStringConverter());
			setEditableCellFactory(waitingTimeColumn, new IntegerStringConverter());

		}
	}

	private <T> void setEditableCellFactory(TableColumn<BinCard, T> column, javafx.util.StringConverter<T> converter) {
		column.setCellFactory(TextFieldTableCell.forTableColumn(converter));
	}
	
	void makeTableEditable() {
		daW8BackendTableView.setEditable(true);
		daW8BackendTableView.getSelectionModel().setCellSelectionEnabled(true);
		daW8BackendTableView.setOnKeyPressed(event -> handleTableKeyPress(event, daW8BackendTableView));
	}

	@SuppressWarnings("null")
	private void handleTableKeyPress(javafx.scene.input.KeyEvent event, TableView<BinCard> tableView) {
		@SuppressWarnings("unchecked")
		TablePosition<BinCard, ?> position = tableView.getFocusModel().getFocusedCell();
		if (position == null) {
			int row = position.getRow();
			int col = position.getColumn();
			if (event.getCode().isLetterKey()) {
				tableView.edit(row, (TableColumn<BinCard, ?>) tableView.getColumns().get(col));
			}
		}
	}

	@FXML
	private void exportAsPDF() {

	}

	@FXML
	private void exportAsExcel() {

		try {
			exportTableToExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportTableToExcel() throws IOException {
	    Workbook workbook = new XSSFWorkbook(); // or use HSSFWorkbook for format
	    Sheet sheet = workbook.createSheet("BinCardData");

	    createTable(sheet, daW8BackendTableView);

	    // Create file chooser
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Save Excel File");
	    fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
	            new FileChooser.ExtensionFilter("All Files", "*.*"));

	    // Show save file dialog
	    Stage stage = (Stage) daW8BackendTableView.getScene().getWindow();
	    File file = fileChooser.showSaveDialog(stage);

	    if (file != null) {
	        try (FileOutputStream fileOut = new FileOutputStream(file)) {
	            workbook.write(fileOut);
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setHeaderText("Export Successful");
	            alert.setContentText("Data exported to Excel successfully.");
	            alert.showAndWait();
	        } finally {
	            workbook.close();
	        }
	    }
	}

	private void createTable(Sheet sheet, TableView<BinCard> binCardTableView) {
		// Create header row
		Row headerRow = sheet.createRow(1);
		Row dataRow = sheet.createRow(2);
		CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
		CellStyle cautionStyle = sheet.getWorkbook().createCellStyle();
		CellStyle dangerStyle = sheet.getWorkbook().createCellStyle();
		Font headerFont = sheet.getWorkbook().createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cautionStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		cautionStyle.setFillPattern(FillPatternType.LESS_DOTS);
		
		dangerStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
		dangerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		headerRow.createCell(0).setCellValue("Date");
		headerRow.createCell(1).setCellValue("PatientNo");
		headerRow.createCell(2).setCellValue("QueueNo");
		headerRow.createCell(3).setCellValue("TimeIN");
		headerRow.createCell(4).setCellValue("TimeOUT");
		headerRow.createCell(5).setCellValue("WaitingTime");

		for (int i = 0; i < 6; i++) {
			headerRow.getCell(i).setCellStyle(headerStyle);
		}

		// Fetch data from the Table and populate the Excel table
		ObservableList<BinCard> data = binCardTableView.getItems();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // Define time format
		for (int i = 0; i < data.size(); i++) {
		    BinCard entry = data.get(i);
		    dataRow = sheet.createRow(i + 2); // Start from row 3 for data

		    dataRow.createCell(0).setCellValue(entry.getDate().toString());
		    dataRow.createCell(1).setCellValue(entry.getPatientNo());
		    dataRow.createCell(2).setCellValue(entry.getQueNo());
		    dataRow.createCell(3).setCellValue(entry.getTimeIN().format(timeFormatter)); // Convert LocalTime to string
		    dataRow.createCell(4).setCellValue(entry.getTimeOUT().format(timeFormatter)); // Convert LocalTime to string
		    dataRow.createCell(5).setCellValue(entry.getWaitingTime());
		    
		    if((int)entry.getWaitingTime() > 40 && (int)entry.getWaitingTime() < 60) {
		    	dataRow.getCell(5).setCellStyle(cautionStyle);
		    }
		    
		    if((int)entry.getWaitingTime() >= 60) {
		    	dataRow.getCell(5).setCellStyle(dangerStyle);
		    }
		
		}

	}


	private void updateWaitingTimeForAllEntries() {
	    ObservableList<BinCard> data = frontScreen.screenTableView.getItems();
	    for (BinCard entry : data) {
	        Duration duration = Duration.between(entry.getTimeIN(), LocalTime.now());
	        long waitingTimeInSeconds = duration.getSeconds();
	        int waitingTime = (int) (waitingTimeInSeconds / 60);
	        entry.setWaitingTime(waitingTime);
	        
	    }
	    frontScreen.screenTableView.refresh(); // Refresh the table to reflect changes
	}
	
	private void startWaitingTimeUpdateTimer() {
	    //
	    schedulerFront.scheduleAtFixedRate(this::updateWaitingTimeForAllEntries, 0, 1, TimeUnit.MINUTES);
	}
	
	@FXML
	private void handleAddEntry() throws SQLException {
		try {

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Confirm Entry");
			alert.setContentText(
					"Ensure that current entry is accurate.\nOnce inserted it can ONLY be rectified/changed by ADMIN");
			ButtonType insertButton = new ButtonType("Insert To Table", ButtonBar.ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(insertButton, cancelButton);
			Optional<ButtonType> result = alert.showAndWait();

			result.ifPresent(buttonType -> {
				if (buttonType == insertButton) {
					if (allDynamicTextFieldsFilled()) {
						BinCard newEntry = createBinCardFromInput();
						BinCard newEntry2 = createBinCardFromInput2();

						if (inputFitForEntry) {
							daW8BackendTableView.getItems().add(newEntry);
							frontScreen.screenTableView.getItems().add(newEntry2);
							
						}
						// Save the entry to Excel file
						// saveEntryToExcel(newEntry);

						//saveStaticInfoButton.setDisable(false);
						// Clear input fields

						if (firstEntry) {
							//balanceTextField.setEditable(false);
							firstEntry = false;
						}

						//calculateBalanceFromTable();

						clearDynamicInputFields();
						startWaitingTimeUpdateTimer();
						startAutoSave();
						
					} else {
						Alert alert2 = new Alert(Alert.AlertType.ERROR);
						alert2.setHeaderText("Incomplete Entry");
						alert2.setContentText("Please fill in all fields.");
						alert2.showAndWait();
					}

				}
			});

			// Display success message
		} catch (DateTimeParseException dtpe) {
	        System.err.println("Error parsing date/time: " + dtpe.getMessage());
	        handleInvalidInput();
	    } catch (NumberFormatException nfe) {
	        System.err.println("Error parsing number: " + nfe.getMessage());
	        handleInvalidInput();
	    } catch (Exception e) {
	        e.printStackTrace(); // This will print the stack trace and give you more details about other exceptions
	        handleInvalidInput();
	    }
	}

	private void handleGenerateNewQueueCard() {
		
		if(!daW8BackendTableView.getItems().isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Save OR Delete");
			alert.setContentText("Table is NOT EMPTY.\nAny UNSAVED changes will BE LOST!");
			ButtonType clearButton = new ButtonType("CLEAR", ButtonBar.ButtonData.OK_DONE);
			ButtonType cancelButton = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(clearButton, cancelButton);
			Optional<ButtonType> result = alert.showAndWait();

			result.ifPresent(buttonType -> {
				if (buttonType == clearButton) {
					daW8BackendTableView.getItems().removeAll();
				}
			});
			
		}
	
	}
	
	@FXML
	private void handleOpenReviewWindow() {

		Stage stage = (Stage) openReviewButton.getScene().getWindow();
		stage.close();

	}

	   private void bindStaticInfoLabels() {
		   
		   dateTextField.textProperty().bindBidirectional(binCard.dateProperty(), new LocalDateStringConverter("yyyy-MM-dd"));
		   //patientNoTextField.textProperty().bindBidirectional(binCard.patientNoProperty(), new NumberStringConverter());
		   queNoTextField.textProperty().bindBidirectional(binCard.queNoProperty(), new NumberStringConverter());
		   timeINTextField.textProperty().bindBidirectional(binCard.timeINProperty(), new LocalTimeStringConverter());
		   timeOUTTextField.textProperty().bindBidirectional(binCard.timeOUTProperty(), new LocalTimeStringConverter());
		   
	    }
	
	
	public BinCard createBinCardFromInput() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	    String dateText = dateTextField.getText();
	    String timeINText = timeINTextField.getText();
	    LocalDate date = LocalDate.parse(dateText, formatter);
	    LocalTime timeIN = LocalTime.parse(timeINText, DateTimeFormatter.ofPattern("HH:mm"));
	    int patientNo = Integer.parseInt(patientNoTextField.getText());
	    int queNo = Integer.parseInt(queNoTextField.getText());
		String status = "IN";

		inputFitForEntry = true;
		
		
		 // Set default timeOUT and waitingTime
	    LocalTime timeOUT = null; // Set to null initially
	    int waitingTime = 0; // Set default waiting time to 0

	    // Handle parsing of timeOUT
	    try {
	        timeOUT = LocalTime.parse("00:00", timeFormatter); // Default value
	    } catch (DateTimeParseException e) {
	        // Handle parsing error
	        System.err.println("Error parsing timeOUT: " + e.getMessage());
	    }
	    
	    System.out.println("Date Text: " + date);
		System.out.println("Que No: " + queNo);
		System.out.println("Patient No: " + patientNo);
		System.out.println("Time IN Text: " + timeIN);
		System.out.println("Time OUT Text: " + timeOUT);
		System.out.println("Waiting Time Text: " + waitingTime);
		System.out.println("Status :" + status);
	    // Create BinCard object with default values
	    return new BinCard(date, patientNo, queNo, timeIN, timeOUT, waitingTime, status);
	}
	
	public BinCard createBinCardFromInput2() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	    String dateText = dateTextField.getText();
	    String timeINText = timeINTextField.getText();
	    LocalDate date = LocalDate.parse(dateText, formatter);
	    LocalTime timeIN = LocalTime.parse(timeINText, DateTimeFormatter.ofPattern("HH:mm"));
	    int patientNo = Integer.parseInt(patientNoTextField.getText());
	    int queNo = Integer.parseInt(queNoTextField.getText());
		String status = "IN";

		inputFitForEntry = true;
		
		
		 // Set default timeOUT and waitingTime
	    LocalTime timeOUT = null; // Set to null initially
	    int waitingTime = 0; // Set default waiting time to 0

	    // Handle parsing of timeOUT
	    try {
	        timeOUT = LocalTime.parse("00:00", timeFormatter); // Default value
	    } catch (DateTimeParseException e) {
	        // Handle parsing error
	        System.err.println("Error parsing timeOUT: " + e.getMessage());
	    }
	    
	    System.out.println("Date Text: " + date);
		System.out.println("Que No: " + queNo);
		System.out.println("Patient No: " + patientNo);
		System.out.println("Time IN Text: " + timeIN);
		
		System.out.println("Waiting Time Text: " + waitingTime);
		
		frontData.setIsAdmin(patientNo);
		
	    // Create BinCard object with default values
	    return new BinCard(date, patientNo, queNo, timeIN, waitingTime);
	}

	private void handleInvalidInput() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Invalid Input");
		alert.setContentText("Please enter valid values.");
		alert.showAndWait();
	}

	@FXML
	private void handleDeleteBinCard() {
		
			BinCard selectedEntry = daW8BackendTableView.getSelectionModel().getSelectedItem();
			if (selectedEntry != null) {

				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.setHeaderText("Delete Entry");
				confirmation.setContentText(
						"Are you sure you want to delete this ENTIRE CARD?\n N.B THIS CANNOT BE UNDONE!");
				Optional<ButtonType> result = confirmation.showAndWait();

				LocalDate date = getSelectedEntryDate();
				
				if (result.isPresent() && result.get() == ButtonType.OK) {
					
					if(adminCheck.isAdmin()) {
						binCarddbManager.deleteBinCardData(date);
						if(data2.getDeleteResult()) {
							clearTableView();
							actionSuccess();
						}
						else {
							actionFail();
						}
						}
					else {
						showAuthorizationWindow().setOnHidden(action -> {
			            	if(data.isAdmin()) {
			            		binCarddbManager.deleteBinCardData(date);
			            		if(data2.getDeleteResult()) {
			    					clearTableView();
			    					actionSuccess();
			    				}
			    				else {
			    					actionFail();
			    				}
					}
			            	else {
	            				actionFail();
	            				
	            			}
				});
					
					
				}

			}
				} else {
				Alert warning = new Alert(Alert.AlertType.WARNING);
				warning.setHeaderText("No Selection");
				warning.setContentText("Please select atleast 1 entry for the BIN CARD to be DELETED.");
				warning.showAndWait();
			}
		
	}

	@FXML
	private void handleDeleteTableEntry() {
		BinCard selectedEntry = daW8BackendTableView.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {
			
			displayDeleteConfirmation(selectedEntry);
		} else {
			displayNoSelectionWarning();
		}

	}

	private void displayDeleteConfirmation(BinCard selectedEntry) {
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setHeaderText("Delete Entry");
		confirmation.setContentText("Are you sure you want to delete the selected entry?\n N.B THIS CANNOT BE UNDONE!");
		Optional<ButtonType> result = confirmation.showAndWait();

		LocalDate date = getSelectedEntryDate();
		Integer patientNo = getSelectedEntryPatientNo();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			if(adminCheck.isAdmin()) {
			
			binCarddbManager.deleteTableEntryByRegistrationNo(patientNo,date);
			if(data2.getDeleteResult()) {
				daW8BackendTableView.getItems().remove(selectedEntry);
				frontScreenDeleteEntry(patientNo);
				daW8BackendTableView.refresh();
				frontScreen.screenTableView.refresh();
				actionSuccess();
			}
			else {
				actionFail();
				
			}
			}
			else {
				showAuthorizationWindow().setOnHidden(action -> {
                	if(data.isAdmin()) {
            			binCarddbManager.deleteTableEntryByRegistrationNo(patientNo,date);
            			
            			if(data2.getDeleteResult()) {
            				daW8BackendTableView.getItems().remove(selectedEntry);
            				
         	                   frontScreenDeleteEntry(patientNo); 
         	                  daW8BackendTableView.refresh();
         	 				frontScreen.screenTableView.refresh();
         	                
            				actionSuccess();
            			}
            			else {
            				actionFail();
            				
            			}
			}
                	else {
        				actionFail();
        				
        			}
		});
			}
			
			
			}
		
				
	}

private void frontScreenDeleteEntry(int patientNo) {
	
	 BinCard screenEntryToRemove = null;
      for (BinCard screenEntry : frontScreen.screenTableView.getItems()) {
          if ((int)screenEntry.getPatientNo() == patientNo) {
              screenEntryToRemove = screenEntry;
         
              //removeScreenEntry(frontScreen.screenTableView,screenEntryToRemove);
              
              frontScreen.screenTableView.getItems().remove(screenEntryToRemove);
              frontScreen.screenTableView.refresh();
              break;
          }
      }
	
}

	// Method to get the registrationNo of the selected entry
	private LocalDate getSelectedEntryDate() {
		// Implement logic to retrieve the registrationNo of the selected entry
		// For example:
		BinCard selectedEntry = daW8BackendTableView.getSelectionModel().getSelectedItem();
		if (selectedEntry != null) {
			return selectedEntry.getDate();
		}
		return null;
	}

	private Integer getSelectedEntryPatientNo() {
		// Implement logic to retrieve the registrationNo of the selected entry
		// For example:
		BinCard selectedEntry = daW8BackendTableView.getSelectionModel().getSelectedItem();
		if (selectedEntry != null) {
			return selectedEntry.getPatientNo();
		}
		return null;
	}
	
	
	private void displayNoSelectionWarning() {
		Alert warning = new Alert(Alert.AlertType.WARNING);
		warning.setHeaderText("No Selection");
		warning.setContentText("Please select an entry to delete.");
		warning.showAndWait();
	}

	private void clearDynamicInputFields() {

		patientNoTextField.clear();
		queNoTextField.clear();
		timeINTextField.clear();
		timeOUTTextField.clear();
		waitingTimeTextField.clear();
		
	}

	private void clearTableView() {

		daW8BackendTableView.getItems().clear();
		clearFrontScreen();

	}
	
	private void clearFrontScreen() {
		frontScreen.screenTableView.getItems().clear();
	}

	@FXML
	private void logOutButtonClicked() {

		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("LOG OUT Confirmation");
		confirmationAlert.setHeaderText("Do you want to LOG OUT?\n Please note that any UNSAVED changes will be lost");

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
			
			scheduler.shutdownNow();
			schedulerFront.shutdownNow();
			
		}

	}

	private void emptyBinCardButtonSetup() {

		//checkButton.setDisable(false);
		//saveStaticInfoButton.setDisable(true);
		enableDynamicInfoFields(false);
	}

	private void loadBinCardButtonSetup() {

		//enableDynamicInfoFields(true);
		//checkButton.setDisable(true);
		firstEntry = false;
		loadPressed = true;
		
	}

	private void fetchAndDisplayBinCardData() {
		try {
			// Your database query to fetch bin card data (item, cardNo, schedule, strength,
			// unitOfIssue, code, icn, price, rol, size)
			String sql = "SELECT DISTINCT Date FROM dynamic_data";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			// Create an ObservableList to store the static info
			ObservableList<String> binCardData = FXCollections.observableArrayList();

			// Iterate through the ResultSet and add static info to the list
			while (resultSet.next()) {
				java.sql.Date sqlDate = resultSet.getDate("Date");
				binCardData.add(sqlDate  + "" ); // Add static info
																									// to the list
			}

			// Set the retrieved bin card data into the ListView
			binCardListView.setItems(binCardData);
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle SQLException
		}

	}

	@FXML
	private void locationChoose(ActionEvent event) {
		Stage primaryStage = new Stage();

		// Create a FileChooser object
		FileChooser fileChooser = new FileChooser();

		// Configure the FileChooser (optional)
		fileChooser.setTitle("Select Excel File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedFile != null) {
			// Handle the selected file (e.g., read its contents)
			// You can pass the selected file to a method for processing
			processExcelFile(selectedFile);
		}

	}

	private void processExcelFile(File selectedFile) {
		// TODO Auto-generated method stub

	}

	private void refreshHandler() {
		

			if(!daW8BackendTableView.getItems().isEmpty()) {
				int highest = daW8BackendTableView.getItems().getLast().getQueNo();
				queNoTextField.setText(Integer.toString(highest));
			}
			else {
				queNoTextField.setText(Integer.toString(generateNextQueNumber()));
			}
				showSpeechBubble(false);
     	patientNoTextField.setDisable(false);
     	dateTextField.setEditable(false);
	}
	
}
