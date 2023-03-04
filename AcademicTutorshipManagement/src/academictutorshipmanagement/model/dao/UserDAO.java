/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
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
        String query = "SELECT username\n"
                + "FROM user\n"
                + "WHERE username = ? AND password = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
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