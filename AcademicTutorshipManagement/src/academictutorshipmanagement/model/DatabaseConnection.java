/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private Connection databaseConnection;

    public Connection open() throws SQLException {
        try {
            connectToDatabase();
        } catch (IOException ex) {
            System.err.println("The database configuration file could not be read correctly. Please try again later.");
        }
        return databaseConnection;
    }

    private void connectToDatabase() throws IOException {
        try {
            Properties attributes = new Properties();
            try (FileInputStream databaseConfigurationFile = new FileInputStream(new File("src\\academictutorshipmanagement\\model\\DatabaseConfiguration.txt"))) {
                attributes.load(databaseConfigurationFile);
            }
            String url = attributes.getProperty("URL");
            String username = attributes.getProperty("USERNAME");
            String password = attributes.getProperty("PASSWORD");
            try {
                databaseConnection = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.err.println("There is no connection to the database. Please try again later.");
            }
        } catch (FileNotFoundException exception) {
            System.err.println("The database configuration file could not be found. Please try again later.");
        }
    }

    public void close() {
        try {
            if (!databaseConnection.isClosed()) {
                databaseConnection.close();
            }
        } catch (SQLException exception) {
            System.err.println("There is no connection to the database. Please try again later.");
        }
    }

}