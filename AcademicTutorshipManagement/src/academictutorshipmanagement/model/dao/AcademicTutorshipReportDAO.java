/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicTutorshipReportDAO {

    public static AcademicTutorshipReport getAcademicTutorshipReport(int idAcademicPersonnel, int idAcademicTutorship) {
        AcademicTutorshipReport academicTutorshipReport = new AcademicTutorshipReport();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT idAcademicTutorshipReport, generalComment, numberOfStudentsAttending, numberOfStudentsAtRisk\n"
                + "FROM academicTutorshipReport\n"
                + "WHERE idAcademicPersonnel = ? AND idAcademicTutorship = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicPersonnel);
            preparedStatement.setInt(2, idAcademicTutorship);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicTutorshipReport.setIdAcademicTutorshipReport(resultSet.getInt("idAcademicTutorshipReport"));
                academicTutorshipReport.setGeneralComment(resultSet.getString("generalComment"));
                academicTutorshipReport.setNumberOfStudentsAttending(resultSet.getInt("numberOfStudentsAttending"));
                academicTutorshipReport.setNumberOfStudentsAtRisk(resultSet.getInt("numberOfStudentsAtRisk"));
                academicTutorshipReport.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            }
        } catch (SQLException exception) {
            academicTutorshipReport.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return academicTutorshipReport;
    }

    public static int logAcademicTutorshipReport(AcademicTutorshipReport academicTutorshipReport) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicTutorshipReport\n"
                + "(generalComment, numberOfStudentsAttending, numberOfStudentsAtRisk, idAcademicPersonnel, idAcademicTutorship)\n"
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String generalComment = (academicTutorshipReport.getGeneralComment().isEmpty()) ? null : academicTutorshipReport.getGeneralComment();
            int numberOfStudentsAttending = academicTutorshipReport.getNumberOfStudentsAttending();
            int numberOfStudentsAtRisk = academicTutorshipReport.getNumberOfStudentsAtRisk();
            int idAcademicPersonnel = academicTutorshipReport.getAcademicPersonnel().getIdAcademicPersonnel();
            int idAcademicTutorship = academicTutorshipReport.getAcademicTutorship().getIdAcademicTutorship();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, generalComment);
            preparedStatement.setInt(2, numberOfStudentsAttending);
            preparedStatement.setInt(3, numberOfStudentsAtRisk);
            preparedStatement.setInt(4, idAcademicPersonnel);
            preparedStatement.setInt(5, idAcademicTutorship);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected == Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

}