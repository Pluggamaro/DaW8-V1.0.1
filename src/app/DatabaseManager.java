package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {


	// Users Database

	private static final String DATABASE_URL = "jdbc:sqlite:app_database.db";
	private static final String BINCARD_DATABASE_URL = "jdbc:sqlite:bincard_database.db";

	public static Connection getConnection() throws SQLException {
		createDatabaseIfNotExists(); // Ensure the database file exists
		return DriverManager.getConnection(DATABASE_URL);
	}

	private static void createDatabaseIfNotExists() {
		Path databasePath = Paths.get("app_database.db");

		// Check if the database file exists, create it if 
		if (!Files.exists(databasePath)) {
			try {
				Files.createFile(databasePath);
				System.out.println("Database file created: " + databasePath.toAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void createBinCardDataBaseIfNotExists() {
		Path binCardPath = Paths.get("bincard_database.db");

		if(!Files.exists(binCardPath)) {
			try {
				Files.createFile(binCardPath);
				System.out.println("BinCard Database file created: " + binCardPath.toAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Connection getBinCardConnection() throws SQLException {
		createBinCardDataBaseIfNotExists();
		return DriverManager.getConnection(BINCARD_DATABASE_URL);
	}


}
