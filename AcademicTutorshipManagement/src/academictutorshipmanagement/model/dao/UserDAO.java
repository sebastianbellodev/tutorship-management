/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 *
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User logIn(String username, String password) {
        User user = new User();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT user.username\n"
                + "FROM user\n"
                + "WHERE username = ? AND password = ?";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configuredQuery = database.prepareStatement(query);
            configuredQuery.setString(1, username);
            configuredQuery.setString(2, password);
            ResultSet queryResult = configuredQuery.executeQuery();
            if (queryResult.next()) {
                user.setUsername(queryResult.getString("username"));
                user.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            } else {
                user.setResponseCode(Constants.INVALID_DATA_ENTERED_CODE);
            }
        } catch(SQLException exception) {
            user.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return user;
    }
    
}