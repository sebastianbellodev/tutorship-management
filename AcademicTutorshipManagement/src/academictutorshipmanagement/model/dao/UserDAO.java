/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: May 18, 2023.
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

    public static User getUser(int idAcademicPersonnel) {
        User user = new User();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT user.username\n"
                + "FROM user\n"
                + "INNER JOIN academicPersonnel\n"
                + "ON user.username = academicPersonnel.username\n"
                + "WHERE academicPersonnel.idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicPersonnel);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                user.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            } else {
                user.setResponseCode(Constants.INVALID_DATA_ENTERED_CODE);
            }
        } catch (SQLException exception) {
            user.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return user;
    }

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
        } catch (SQLException exception) {
            user.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return user;
    }

    public static int logUser(User user) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO user\n"
                + "(username, password)\n"
                + "VALUES(?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String username = user.getUsername();
            String password = user.getPassword();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int checkUser(String username) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "SELECT *\n"
                + "FROM user\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            responseCode = (resultSet.next()) ? Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int updateUser(User user) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE user\n"
                + "SET password = ?\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            String username = user.getUsername();
            String password = user.getPassword();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int updateUsername(String newUsername, String oldUsername) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE user\n"
                + "SET username = ?\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

}