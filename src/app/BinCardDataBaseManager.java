package app;

import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

@SuppressWarnings("unused")
public class BinCardDataBaseManager {


	private static final BinCardDataBaseManager instance = new BinCardDataBaseManager();
	
	private static boolean saveOrNot;
	private static boolean deleteResult;
	
	private Connection connection;
	
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	public BinCardDataBaseManager() {
		try {
			// Database Connection
			connection = DatabaseManager.getBinCardConnection();

			createBinCardTables();

		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static BinCardDataBaseManager getInstance() {
		return instance;
	}
	
	public boolean getSaveOrNot(){
		return BinCardDataBaseManager.saveOrNot;
	}
	
	private void setSaveOrNot(boolean saveOrNot ) {
		BinCardDataBaseManager.saveOrNot = saveOrNot;
	}
	
	public boolean getDeleteResult() {
		return BinCardDataBaseManager.deleteResult;
	}
	
	private void setDeleteResult(boolean deleteResult) {
		BinCardDataBaseManager.deleteResult = deleteResult;
	}
	
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	private void createBinCardTables() {
		try {
			final Statement statement = connection.createStatement();
			// Create dynamic_data table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS dynamic_data (" +
				    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
				    "Date DATE," +
				    "PatientNo INTEGER," +
				    "QueNo INTEGER," + 
				    "TimeIN TIME," +
				    "TimeOUT TIME," +
				    "WaitingTime INTEGER," +
				    "Status TEXT " +
				    ")");


			
		}catch (SQLException e) {
	        // Rollback the transaction in case of an exception
	        e.printStackTrace();
	    } 
	}

	public void insertDynamicData( final LocalDate date, final Integer patientNo, final Integer queNo, final LocalTime timeIN, final LocalTime timeOUT, final Integer waitingTime, final String status ) {
		try {
			
			final String sql = "INSERT INTO dynamic_data (Date, PatientNo, QueNo, TimeIN, TimeOUT, WaitingTime, Status) VALUES (?, ?, ?, ?, ?, ?, ? )";
			final PreparedStatement statement = connection.prepareStatement(sql);
			
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        java.sql.Time sqlTimeIN = java.sql.Time.valueOf(timeIN);
	        java.sql.Time sqlTimeOUT = java.sql.Time.valueOf(timeOUT);
			
			statement.setDate(1, sqlDate);
			statement.setInt(2, patientNo);
			statement.setInt(3, queNo);
			statement.setTime(4, sqlTimeIN);
			statement.setTime(5, sqlTimeOUT);
			statement.setInt(6, waitingTime);
			statement.setString(7, status);

			
			final int rowsAffected = statement.executeUpdate();

			
			
			if (rowsAffected > 0) {
				System.out.println("Dynamic data INSERTED successfully. "+ rowsAffected + " row(s) created successfully");
				setSaveOrNot(true);
				
			} else {
				System.err.println("Failed to insert dynamic data.");
				setSaveOrNot(false);
			}
		} catch (SQLException e) {
	        // Rollback the transaction in case of an exception
	        e.printStackTrace();
	    }
		
	}

	public List<DynamicData> retrieveDynamicData(final LocalDate date) {
		final List<DynamicData> dynamicDataList = new ArrayList<>();
		try {
			
			
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			
			final String sql = "SELECT * FROM dynamic_data WHERE Date = ?";
			final PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, sqlDate);
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				final DynamicData dynamicData = new DynamicData();
				
				 // Retrieve dates as java.sql.Date objects
			    java.sql.Date convertDate = resultSet.getDate("Date");
			    java.sql.Time timeIN = resultSet.getTime("TimeIN");
			    java.sql.Time timeOUT = resultSet.getTime("TimeOUT");
			    
			    // Convert java.sql.Date objects to LocalDate
			    LocalDate localDate = convertDate.toLocalDate();
			    LocalTime sqlTimeIN = timeIN.toLocalTime();
			    LocalTime sqlTimeOUT = timeOUT.toLocalTime();
				
			    String status = resultSet.getString("Status");
			    
				dynamicData.setDate(localDate);
				dynamicData.setPatientNo(resultSet.getInt("PatientNo"));
				dynamicData.setQueNo(resultSet.getInt("QueNo"));
				dynamicData.setTimeIN(sqlTimeIN);
				dynamicData.setTimeOUT(sqlTimeOUT);
				dynamicData.setCurrentWT(resultSet.getInt("WaitingTime"));
				dynamicData.setStatus(status);
				// Add the DynamicData object to the list
				dynamicDataList.add(dynamicData);
			}
		} catch (SQLException e) {
	        // Rollback the transaction in case of an exception
	        e.printStackTrace();
	    } 
		return dynamicDataList;
	}

	public List<DynamicData> retrieveDynamicDataPeriod(LocalDate startDate, LocalDate endDate) {
	    final List<DynamicData> dynamicDataList = new ArrayList<>();
	    try {
	        final String sql = "SELECT * FROM dynamic_data WHERE Date BETWEEN ? AND ?";
	        final PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setDate(1, java.sql.Date.valueOf(startDate));
	        statement.setDate(2, java.sql.Date.valueOf(endDate));
	        final ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            final DynamicData dynamicData = new DynamicData();

	            // Retrieve dates as java.sql.Date objects
			    java.sql.Date convertDate = resultSet.getDate("Date");
			    java.sql.Time timeIN = resultSet.getTime("TimeIN");
			    java.sql.Time timeOUT = resultSet.getTime("TimeOUT");
			    
			    // Convert java.sql.Date objects to LocalDate
			    LocalDate localDate = convertDate.toLocalDate();
			    LocalTime sqlTimeIN = timeIN.toLocalTime();
			    LocalTime sqlTimeOUT = timeOUT.toLocalTime();
				
			    String status = resultSet.getString("Status");
			    
				dynamicData.setDate(localDate);
				dynamicData.setPatientNo(resultSet.getInt("PatientNo"));
				dynamicData.setQueNo(resultSet.getInt("QueNo"));
				dynamicData.setTimeIN(sqlTimeIN);
				dynamicData.setTimeOUT(sqlTimeOUT);
				dynamicData.setCurrentWT(resultSet.getInt("WaitingTime"));
				dynamicData.setStatus(status);
	            // Add the DynamicData object to the list
	            dynamicDataList.add(dynamicData);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dynamicDataList;
	}

	
	
	public void updateDynamicData(final LocalDate date, final Integer patientNo , final Integer queNo,
	        final LocalTime timeIN, final LocalTime timeOUT, final Integer waitingTime, String status) {
	    try {
	    	
	    	if(timeOUT != LocalTime.parse("00:00", timeFormatter) && (!status.equals("IN"))) {
	    	
	        // Update the dynamic data entry in the database
	        final String sql = "UPDATE dynamic_data SET QueNo = ?, TimeIN = ?, TimeOUT = ? , WaitingTime = ?, Status = ? WHERE Date = ? AND PatientNo = ? ";
	        
	        final PreparedStatement statement = connection.prepareStatement(sql);
	        
	        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        java.sql.Time sqlTimeIN = java.sql.Time.valueOf(timeIN);
	        java.sql.Time sqlTimeOUT = java.sql.Time.valueOf(timeOUT);
	        
	        
	        statement.setInt(1, queNo);
	        statement.setTime(2, sqlTimeIN);
	        statement.setTime(3, sqlTimeOUT);
	        statement.setInt(4, waitingTime);
	        statement.setString(5, status);
	        statement.setDate(6, sqlDate);
	        statement.setInt(7, patientNo);


	        
	        final int rowsAffected = statement.executeUpdate();
	        
	        
	        if(rowsAffected > 0 ) {
	            System.out.println("Dynamic data UPDATED successfully. " + rowsAffected + " row(s) updated successfully.");
	            setSaveOrNot(true);
	            
	        }
	        }else {
	            System.out.println("UPDATE NOT NECESSARY.");
	        }
	        
	    } catch (SQLException e) {
	        // Rollback the transaction in case of 
	        e.printStackTrace();
	    } 
	}

	
	public boolean staticInfoExists(final LocalDate date) {
		try {
			final PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM dynamic_data WHERE date = ?");
			
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			
			checkStatement.setDate(1, sqlDate);
			final ResultSet resultSet = checkStatement.executeQuery();

			return resultSet.next(); 
		} catch (final SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}
	
	
	public LocalDate staticInfoIdCheck(final LocalDate date) {
		
		LocalDate obj =null;
		
		try {
			final PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM dynamic_data WHERE Date = ?");
			
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			
			checkStatement.setDate(1, sqlDate);
			final ResultSet resultSet = checkStatement.executeQuery();
			
			if(resultSet.next()) {
				
			return	obj = sqlDate.toLocalDate();
				
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public void insertOrUpdateDynamicData(final LocalDate date, final Integer patientNo, final Integer queNo, final LocalTime timeIN, final LocalTime timeOUT, final Integer waitingTime, final String status) {
        try {
             // Start a transaction
            if (dynamicDataExists(date, patientNo)) {
                updateDynamicData(date, patientNo, queNo, timeIN, timeOUT, waitingTime, status);
            } else {
                insertDynamicData(date, patientNo, queNo, timeIN, timeOUT, waitingTime, status);
            }
             // Commit the transaction
        } catch (final Exception e) {
            e.printStackTrace();
        } 
    }
	
	public boolean dynamicDataExists(final LocalDate date, final Integer patientNo) {
	    try {
	    	
	    	
	        final String sql = "SELECT * FROM dynamic_data WHERE Date = ? AND PatientNo = ? ";
	        final PreparedStatement statement = connection.prepareStatement(sql);
	        
	        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        
	        statement.setDate(1, sqlDate);
	        statement.setInt(2, patientNo);
	        final ResultSet resultSet = statement.executeQuery();
	        resultSet.next(); // Move to the first row
	        return resultSet.getInt(1) > 0; // Check if any rows exist
	    } catch (SQLException e) {
	        // Rollback the transaction in case of an exception
	        e.printStackTrace();
	    } 
	    
		return false;
	}
	
	 
	public boolean staticDataExists(final int staticId, final String cardNo) {
		try {
			final String sql = "SELECT COUNT(*) FROM static_info WHERE id = ? AND cardNo = ?";
	        final PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setInt(1, staticId);
	        statement.setString(2, cardNo);
	        final ResultSet resultSet = statement.executeQuery();
	        resultSet.next(); // Move to the first row
	        return resultSet.getInt(1) > 0; // Check if any rows exist
		}
		catch (final SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void deleteTableEntryByRegistrationNo(Integer patientNo, LocalDate date) {
	    try {
	        /* First, retrieve the static_info_id associated with the registrationNo
	        int staticInfoId = -1; // Initialize with an invalid value
	        String sql = "SELECT static_info_id FROM dynamic_data WHERE registrationNo = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, registrationNo);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            staticInfoId = resultSet.getInt("static_info_id");
	        }*/

	        // Now, delete the dynamic data
	        String sql = "DELETE FROM dynamic_data WHERE PatientNo = ? AND Date = ? ";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        
	        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        
	        statement.setInt(1, patientNo);
	        statement.setDate(2,sqlDate);
	        int dynamicRowsAffected = statement.executeUpdate();

	        // Count the total number of rows affected
	        int totalRowsAffected = dynamicRowsAffected;

	        // Print the total number of rows deleted
	        System.out.println(totalRowsAffected + " row(s) deleted from dynamic_data.");
	        
	        if(totalRowsAffected > 0) {
	        	setDeleteResult(true);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteBinCardData(LocalDate date) {
	    try {
	        // Retrieve the static_info_id associated with the registrationNo
	        java.sql.Date internalDate = null; // Initialize with an invalid value
	        String sql = "SELECT id FROM dynamic_data WHERE Date = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        
	        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        
	        statement.setDate(1, sqlDate);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            //internalDate = resultSet.getDate("Date");

	            // Now, delete the dynamic data entries based on static_info_id
	            sql = "DELETE FROM dynamic_data WHERE Date = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setDate(1, sqlDate);
	            int dynamicRowsAffected = statement.executeUpdate();
	            System.out.println(dynamicRowsAffected + " row(s) deleted from dynamic_data.");
	            if( dynamicRowsAffected > 0) {
	            	setDeleteResult(true);
	            	
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	 
	
}
