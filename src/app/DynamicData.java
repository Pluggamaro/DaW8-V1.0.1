package app;

import javafx.beans.property.SimpleIntegerProperty;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

@SuppressWarnings("unused")
public class DynamicData {
	
	private final SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty patientNo = new SimpleIntegerProperty();
    private final SimpleIntegerProperty queNo = new SimpleIntegerProperty();
    private final SimpleObjectProperty<LocalTime> timeIN = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalTime> timeOUT = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty currentWT = new SimpleIntegerProperty();
    private String status = new String();
    
    private DynamicData dynamicData;
	
	 private ObservableList<BinCard> binCardData = FXCollections.observableArrayList();

	    public ObservableList<BinCard> getBinCardData() {
	        return binCardData;
	    }

	    public DynamicData getDynamicData() {
	    	return dynamicData;
	    }
	    
	    public void setBinCardData(ObservableList<BinCard> binCardData) {
	        this.binCardData = binCardData;
	    }

	    public void addBinCard(BinCard binCard) {
	        binCardData.add(binCard);
	    }

	    public void removeBinCard(BinCard binCard) {
	        binCardData.remove(binCard);
	    }
	    
	    
	    public LocalDate getDate() {
			return date.get();
		}

		public void setDate(final LocalDate value) {
			this.date.set(value);
		}

		public void setPatientNo(final Integer patientNo) {
			this.patientNo.set(patientNo);
		}
		
		public Integer getPatientNo() {
			return patientNo.get();
		}

		public void setQueNo(final Integer queNo) {
			this.queNo.set(queNo);
		}

		public Integer getQueNo() {
			return queNo.get();
		}

		public LocalTime getTimeIN() {
			return timeIN.get();
		}

		public void setTimeIN(final LocalTime timeIN) {
			this.timeIN.set(timeIN);
		}
		
		public LocalTime getTimeOUT() {
			return timeOUT.get();
		}

		public void setTimeOUT(final LocalTime timeOUT) {
			this.timeOUT.set(timeOUT);
		}

		public Integer getCurrentWT() {
			return currentWT.get();
		}

		public void setCurrentWT(final Integer currentWT) {
			this.currentWT.set(currentWT);
		}

		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
}

