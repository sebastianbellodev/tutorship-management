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
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class AcademicTutorshipSessionDAO {

    public static AcademicTutorshipSession getCurrentAcademicTutorshipSession() {
        AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM academicTutorshipSession\n"
                + "WHERE NOW() BETWEEN startDate AND closingDateReportSubmission";
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
    
    public static ArrayList<AcademicTutorshipSession> getAcademicTutorshipSessions(int idSchoolPeriod) {
        ArrayList<AcademicTutorshipSession> academicTutorshipSessions = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM academicTutorshipSession\n"
                + "WHERE idSchoolPeriod = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession();
                academicTutorshipSession.setIdAcademicTutorshipSession(resultSet.getInt("idAcademicTutorshipSession"));
                academicTutorshipSession.setStartDate(resultSet.getDate("startDate"));
                academicTutorshipSession.setEndDate(resultSet.getDate("endDate"));
                academicTutorshipSession.setClosingDateReportSubmission(resultSet.getDate("closingDateReportSubmission"));
                academicTutorshipSession.setSessionNumber(resultSet.getInt("sessionNumber"));
                academicTutorshipSession.setResponseCode(Constants.CORRECT_OPERATION_CODE);
                academicTutorshipSessions.add(academicTutorshipSession);
            }
        } catch (SQLException exception) {
            academicTutorshipSessions = null;
        } finally {
            databaseConnection.close();
        }
        return academicTutorshipSessions;
    }

    public ObservableList<AcademicTutorshipSession> verifyTutorships(int schoolPeriod) {
        ObservableList<AcademicTutorshipSession> tutorshipSessions = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.open()) {
            String query = "SELECT * FROM academictutorshipsession WHERE idSchoolPeriod = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, schoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            
           while (resultSet.next()){
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                Date reportDate = resultSet.getDate("closingDateReportSubmission");
                
                AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession(startDate, endDate, reportDate);
                tutorshipSessions.add(academicTutorshipSession);
            } 
           
        } catch (SQLException exception) {
            System.out.print(exception.getMessage());
        } finally {
            databaseConnection.close();
        }
        return tutorshipSessions;
    }

    public int saveNewDates(Date starDate, Date endDate, Date reportDate, int session, int schoolPeriod) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "INSERT INTO academicTutorshipSession\n"
                + "(startDate, endDate, closingDateReportSubmission, idSchoolPeriod, sessionNumber)\n"
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, starDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setDate(3, reportDate);
            preparedStatement.setInt(4, schoolPeriod);
            preparedStatement.setInt(5, session);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public int updateDates(Date starDate, Date endDate, Date reportDate, int session, int schoolPeriod) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "UPDATE academicTutorshipSession SET startDate = ?, endDate = ?, closingDateReportSubmission = ? WHERE idSchoolPeriod = ? AND sessionNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, starDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setDate(3, reportDate);
            preparedStatement.setInt(4, schoolPeriod);
            preparedStatement.setInt(5, session);
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
