/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicTutorship;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicTutorshipDAO {

    public static AcademicTutorship getAcademicTutorship(int idEducationalProgram, int idAcademicTutorshipSession) {
        AcademicTutorship academicTutorship = new AcademicTutorship();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT idAcademicTutorship\n"
                + "FROM academicTutorship\n"
                + "WHERE idEducationalProgram = ? AND idAcademicTutorshipSession = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idAcademicTutorshipSession);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicTutorship.setIdAcademicTutorship(resultSet.getInt("idAcademicTutorship"));
                academicTutorship.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            } else {
                academicTutorship.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
            }
        } catch (SQLException exception) {
            academicTutorship.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return academicTutorship;
    }

}