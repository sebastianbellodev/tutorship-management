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
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configuredQuery = database.prepareStatement(query);
            configuredQuery.setString(1, username);
            ResultSet queryResult = configuredQuery.executeQuery();
            if (queryResult.next()) {
                academicPersonnel.setIdAcademicPersonnel(queryResult.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(queryResult.getString("name"));
                academicPersonnel.setPaternalSurname(queryResult.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(queryResult.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(queryResult.getString("emailAddress"));
            }
        } catch (SQLException exception) {
            System.err.println("There is no connection to the database. Please try again later.");
        } finally {
            databaseConnection.close();
        }
        return academicPersonnel;
    }

}