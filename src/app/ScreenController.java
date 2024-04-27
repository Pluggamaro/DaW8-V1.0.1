package app;

import java.net.URL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScreenController implements Initializable{

	@FXML
	public TableView<BinCard> screenTableView; 
	
	@FXML public TableColumn<BinCard, LocalDate> screenDateColumn;
	@FXML public TableColumn<BinCard, Integer> screenPatientNoColumn;
	@FXML public TableColumn<BinCard, Integer> screenQueNoColumn;
	@FXML public TableColumn<BinCard, LocalTime> screenTimeINColumn;
	@FXML public TableColumn<BinCard, Integer> screenWaitingTimeColumn;
	@FXML private Button refresh;
	
	BinCardDataBaseManager data = BinCardDataBaseManager.getInstance();
	
	private static final ScreenController instance = new ScreenController();
    ScreenController numberData = ScreenController.getInstance();
	
	 private static Set<Integer> assignedNumbers = new HashSet<>();
	
	 public static ScreenController getInstance() {
	    	return instance;
	    }
	 
	 public boolean isOnTable(Integer value) {
		 if(assignedNumbers.contains(value)) {
			 return true;
		 }
		 
			return false;
		}
		
		public void setIsAdmin(Integer isAdmin) {
			assignedNumbers.add(isAdmin); 
		}
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		initializeTableColumns();
		 
	}
	
	private void initializeTableColumns() {

		screenDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
		screenPatientNoColumn.setCellValueFactory(new PropertyValueFactory<>("PatientNo"));
		screenQueNoColumn.setCellValueFactory(new PropertyValueFactory<>("QueNo"));
		screenTimeINColumn.setCellValueFactory(new PropertyValueFactory<>("TimeIN"));
		
		
		//screenWaitingTimeColumn.setCellFactory(column -> new WaitingTImeCell());
		
		
		screenWaitingTimeColumn.setCellValueFactory( new PropertyValueFactory<>("WaitingTime"));
		
	}
	/*
	@FXML
	private void handleRefresh() {
		//daW8BackendTableView.setItems(dynamicData);
		
		 //dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>());
	     patientNoColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(dynamicData.getPatientNo()));
	     queNoColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getQueNo()));
	     timeINColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTimeIN()));
	     waitingTimeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getWaitingTime()));
	     
		System.out.println(dynamicData.getPatientNo());
		//BinCard newEntry = adminController.createBinCardFromInput();
		
		screenTableView.getItems().add(dynamicData);
	     
	}
	*/
}
