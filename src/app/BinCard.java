package app;

import java.text.ParseException;




import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.beans.property.*;

@SuppressWarnings("unused")
public class BinCard {

	
	
	// Table INFO
	private ObjectProperty<LocalDate> date;
	private Integer patientNo;
	private IntegerProperty queNo;
	private ObjectProperty<LocalTime> timeIN;
	private ObjectProperty<LocalTime> timeOUT;
	private int waitingTime;
	private String status;

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private DynamicData dynamicData;

	public BinCard() {
		dynamicData = new DynamicData();
	}

	public BinCard(final DynamicData dynamicData) {
		this.date = new SimpleObjectProperty<>(dynamicData.getDate());
		this.patientNo = (dynamicData.getPatientNo());
		this.queNo = new SimpleIntegerProperty(dynamicData.getQueNo());
		this.timeIN = new SimpleObjectProperty<>(dynamicData.getTimeIN());
		this.timeOUT = new SimpleObjectProperty<>(dynamicData.getTimeOUT());
		this.waitingTime = dynamicData.getCurrentWT();
		this.status = new String(dynamicData.getStatus());
	}

	public BinCard(final LocalDate date, final Integer patientNo,
			final Integer queNo, final LocalTime timeIN, LocalTime timeOUT, int currentWT, String status) {

		this.date = new SimpleObjectProperty<>(date);
		this.patientNo = (patientNo);
		this.queNo = new SimpleIntegerProperty(queNo);
		this.timeIN = new SimpleObjectProperty<>(timeIN);
		this.timeOUT = new SimpleObjectProperty<>(timeOUT);
		this.waitingTime = (currentWT);
		this.status = (status);
		
	}

	public BinCard(final LocalDate date, final Integer patientNo,
			final Integer queNo, final LocalTime timeIN, final int currentWT) {

		this.date = new SimpleObjectProperty<>(date);
		this.patientNo = (patientNo);
		this.queNo = new SimpleIntegerProperty(queNo);
		this.timeIN = new SimpleObjectProperty<>(timeIN);
		this.waitingTime = (currentWT);
		
	}
	

	public DynamicData getDynamicData() {
		return dynamicData;
	}

	public void setDynamicData(final DynamicData dynamicData) {
		this.dynamicData = dynamicData;
	}

	

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}


	public Integer patientNoProperty() {
		return patientNo;
	}

	public IntegerProperty queNoProperty() {
		return queNo;
	}

	public ObjectProperty<LocalTime> timeINProperty() {
	
		return timeIN;
	}
	
	public ObjectProperty<LocalTime> timeOUTProperty() {
		return timeOUT;
	}

	public Integer currentWTProperty() {
		return waitingTime;
	}
	
	public String statusProperty() {
		return status;
	}

	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(final LocalDate value) {
		this.date.set(value);
	}

	public void setPatientNo(final Integer patientNo) {
		this.patientNo = patientNo;
	}
	
	public Integer getPatientNo() {
		return patientNo;
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

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime( int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus( String status) {
		this.status = status;
	}
	
}
