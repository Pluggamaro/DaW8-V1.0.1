package app;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class User {

    private final StringProperty username;
    private final StringProperty role;
    private final StringProperty status;
    private final ObjectProperty<LocalDate> dateOfAction;

    // 
    public User(String username, String role, LocalDate dateOfAction) {
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty();
        this.dateOfAction = new SimpleObjectProperty<>(dateOfAction);
     }
    
    public User(String username, String role) {
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty("");
        this.dateOfAction = new SimpleObjectProperty<>();
    }
    

    
    public String getUsername() {
        return username.get();
    }

    public String getRole() {
        return role.get();
    }

    /*
    public String getStatus() {
        return status.get();
    }*/

    public LocalDate getDateOfAction() {
        return dateOfAction.get();
    }

    
    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public void setStatus(String status) {
        
        try (Connection connection = DatabaseManager.getConnection()) {
            String updateQuery = "UPDATE users SET status = ? WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, status);
                statement.setString(2, getUsername());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    
                    this.status.set(status);
                } else {
                    System.out.println("Failed to update status for user: " + getUsername());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDateOfAction(LocalDate dateOfAction) {
        this.dateOfAction.set(dateOfAction);
    }

    
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty roleProperty() {
        return role;
    }

    public StringProperty statusProperty() {
        return status;
    }
}
