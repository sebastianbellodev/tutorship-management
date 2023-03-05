/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicTutorshipSessionDAO {

    public static AcademicTutorshipSession getCurrentAcademicTutorshipSession() {
        AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicTutorshipSession.*\n"
                + "FROM academicTutorshipSession\n"
                + "WHERE NOW() BETWEEN startDate AND endDate";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicTutorshipSession.setIdAcademicTutorshipSession(resultSet.getInt("idAcademicTutorshipSession"));
                academicTutorshipSession.setStartDate(resultSet.getDate("startDate"));
                academicTutorshipSession.setEndDate(resultSet.getDate("endDate"));
                academicTutorshipSession.setClosingDateReportSubmission(resultSet.getDate("closingDateReportSubmission"));
                academicTutorshipSession.setSessionNumber(resultSet.getInt("sessionNumber"));
                academicTutorshipSession.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            } else {
                academicTutorshipSession.setResponseCode(Constants.INVALID_CURRENT_DATE_CODE);
            }
        } catch (SQLException exception) {
            academicTutorshipSession.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return academicTutorshipSession;
    }

}