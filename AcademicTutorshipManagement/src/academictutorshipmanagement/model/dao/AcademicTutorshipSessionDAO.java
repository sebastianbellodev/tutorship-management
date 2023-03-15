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

public class AcademicTutorshipSessionDAO {

    public static AcademicTutorshipSession getCurrentAcademicTutorshipSession() {
        AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
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

    public ObservableList <AcademicTutorshipSession> verifyTutorships(int schoolPeriod){
        ObservableList<AcademicTutorshipSession> academicTutorshipSessions  = FXCollections.observableArrayList(); 
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try(Connection connection = databaseConnection.open()){
            String query = "SELECT startDate, endDate, closingDateReportSubmission FROM academicTutorshipSession WHERE idSchoolPeriod = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, schoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();           
            if(resultSet.wasNull()){
                    
            }else{
                while(resultSet.next()){
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");
                    Date reportDate = resultSet.getDate("closingDateReportSubmission");   
                    int session = resultSet.getInt("sessionNumber");
                    
                    AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession(startDate, endDate, reportDate, session);
                    academicTutorshipSessions.add(academicTutorshipSession);
                }
            }
        }catch(SQLException exception){
             
        } finally{
            databaseConnection.close();
        }
        return academicTutorshipSessions;
    }
    
    public int saveDates(Date starDate, Date endDate, Date reportDate, int session, int schoolPeriod){
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "INSERT INTO academicTutorshipSession\n"
                + "(startDate, endDate, closingDateReportSubmission, sessionNumber, idSchoolPeriod)\n"
                + "VALUES(?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {           
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, starDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setDate(3, reportDate);
            preparedStatement.setInt(4, session);
            preparedStatement.setInt(5, schoolPeriod);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public void updateDates(Date starDate, Date endDate, Date reportDate, int session, int schoolPeriod){
        
    }
}