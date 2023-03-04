/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 02, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicPersonnelDAO {

    public static AcademicPersonnel getAcademicPersonnelByUser(String username) {
        AcademicPersonnel academicPersonnel = new AcademicPersonnel();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicPersonnel.*\n"
                + "FROM academicPersonnel\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
            }
        } catch (SQLException exception) {
            System.err.println("There is no connection to the connection. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return academicPersonnel;
    }

}