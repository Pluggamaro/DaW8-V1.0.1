package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class UserManager {

	
	private Connection connection;

	
    
	
    private static final String CREATE_USERS_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS users (" +
            "id              INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" +
            "name            TEXT    NOT NULL,\r\n" +
            "surname         TEXT    NOT NULL,\r\n" +
            "email           TEXT    NOT NULL,\r\n" +
            "password        TEXT    NOT NULL,\r\n" +
            "role            TEXT    NOT NULL,\r\n" +
            "status          TEXT    DEFAULT 'Not Approved' NOT NULL,\r\n" +
            "date_of_creation DATE    DEFAULT CURRENT_DATE\r\n" + 
            ");";
    

    private static final String INSERT_USER_SQL =
            "INSERT INTO users (name, surname, email, password, role, status, date_of_creation) VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String AUTHENTICATE_USER_SQL =
            "SELECT * FROM users WHERE username = ? AND password = ?;";

    public UserManager() {
        
        try {
            connection = DatabaseManager.getConnection();
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USERS_TABLE_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_USER_SQL)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public UserRole getUserRole(String username) {
        String GET_USER_ROLE_SQL = "SELECT role FROM users WHERE username = ?;";

        try (PreparedStatement statement = connection.prepareStatement(GET_USER_ROLE_SQL)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return UserRole.valueOf(role); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }

    public void insertUser(String name, String surname, String email,String password, String role, String status) {
        //String status = "Not Approved"; 
        LocalDate currentDate = LocalDate.now(); 

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, status);
            preparedStatement.setObject(7, currentDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
