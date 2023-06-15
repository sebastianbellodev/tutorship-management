/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: April 21, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicTutorship;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicTutorshipReportDAO {

    public static AcademicTutorshipReport getAcademicTutorshipReport(int idAcademicTutorship, int idAcademicPersonnel) {
        AcademicTutorshipReport academicTutorshipReport = new AcademicTutorshipReport();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT idAcademicTutorshipReport, generalComment, numberOfStudentsAttending, numberOfStudentsAtRisk\n"
                + "FROM academicTutorshipReport\n"
                + "WHERE idAcademicTutorship = ? AND idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicTutorship);
            preparedStatement.setInt(2, idAcademicPersonnel);
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
    
    public static ArrayList<AcademicTutorshipReport> getAcademicTutorshipReports(int idSchoolPeriod, int idAcademicPersonnel) {
        ArrayList<AcademicTutorshipReport> academicTutorshipReports = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academictutorshipreport.*, academictutorshipsession.*\n"
                + "FROM academictutorshipreport\n"
                + "INNER JOIN academictutorship\n"
                + "ON academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship\n"
                + "INNER JOIN academictutorshipsession\n"
                + "ON academictutorshipsession.idAcademicTutorshipSession = academictutorship.idAcademicTutorshipSession\n"
                + "WHERE idSchoolPeriod = ? AND idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idAcademicPersonnel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicTutorshipReport academicTutorshipReport = new AcademicTutorshipReport();
                academicTutorshipReport.setIdAcademicTutorshipReport(resultSet.getInt("idAcademicTutorshipReport"));
                academicTutorshipReport.setGeneralComment(resultSet.getString("generalComment"));
                academicTutorshipReport.setNumberOfStudentsAttending(resultSet.getInt("numberOfStudentsAttending"));
                academicTutorshipReport.setNumberOfStudentsAtRisk(resultSet.getInt("numberOfStudentsAtRisk"));             
                AcademicTutorship academicTutorship = new AcademicTutorship();
                AcademicTutorshipSession academicTutorshipSession = new AcademicTutorshipSession();
                academicTutorshipSession.setIdAcademicTutorshipSession(resultSet.getInt("IdAcademicTutorshipSession"));
                academicTutorshipSession.setStartDate(resultSet.getDate("startDate"));
                academicTutorshipSession.setEndDate(resultSet.getDate("endDate"));
                academicTutorshipSession.setSessionNumber(resultSet.getInt("sessionNumber"));
                academicTutorshipSession.setClosingDateReportSubmission(resultSet.getDate("closingDateReportSubmission"));
                academicTutorship.setAcademicTutorshipSession(academicTutorshipSession);
                academicTutorshipReport.setAcademicTutorship(academicTutorship);
                academicTutorshipReports.add(academicTutorshipReport);
            }
        } catch (SQLException exception) {
            academicTutorshipReports = null;
        } finally {
            databaseConnection.close();
        }
        return academicTutorshipReports;
    }
    
    public static ArrayList<AcademicTutorshipReport> getAcademicTutorshipReportsForGeneral(int sessionNumber, int idSchoolPeriod) {
        ArrayList<AcademicTutorshipReport> academicTutorshipReports = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academictutorshipreport.*, academicpersonnel.*\n"
                + "FROM academictutorshipreport\n"
                + "INNER JOIN academictutorship\n"
                + "ON academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship\n"
                + "INNER JOIN academictutorshipsession\n"
                + "ON academictutorship.idAcademicTutorshipSession = academictutorshipsession.idAcademicTutorshipSession\n"
                + "INNER JOIN academicpersonnel\n"
                + "ON academictutorshipreport.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel\n"
                + "WHERE sessionNumber = ? AND idSchoolPeriod = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sessionNumber);
            preparedStatement.setInt(2, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicTutorshipReport academicTutorshipReport = new AcademicTutorshipReport();
                academicTutorshipReport.setIdAcademicTutorshipReport(resultSet.getInt("idAcademicTutorshipReport"));
                academicTutorshipReport.setGeneralComment(resultSet.getString("generalComment"));
                academicTutorshipReport.setNumberOfStudentsAttending(resultSet.getInt("numberOfStudentsAttending"));
                academicTutorshipReport.setNumberOfStudentsAtRisk(resultSet.getInt("numberOfStudentsAtRisk"));             
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicTutorshipReport.setAcademicPersonnel(academicPersonnel);
                academicTutorshipReports.add(academicTutorshipReport);
            }
        } catch (SQLException exception) {
            academicTutorshipReports = null;
        } finally {
            databaseConnection.close();
        }
        return academicTutorshipReports;
    }

    public static int logAcademicTutorshipReport(AcademicTutorshipReport academicTutorshipReport) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicTutorshipReport\n"
                + "(generalComment, numberOfStudentsAttending, numberOfStudentsAtRisk, idAcademicPersonnel, idAcademicTutorship)\n"
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String generalComment = academicTutorshipReport.getGeneralComment();
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

    public static int updateAcademicTutorshipReport(AcademicTutorshipReport academicTutorshipReport) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicTutorshipReport\n"
                + "SET generalComment = ?, numberOfStudentsAttending = ?, numberOfStudentsAtRisk = ?\n"
                + "WHERE idAcademicTutorshipReport = ?";
        try (Connection connection = databaseConnection.open()) {
            String generalComment = academicTutorshipReport.getGeneralComment();
            int numberOfStudentsAttending = academicTutorshipReport.getNumberOfStudentsAttending();
            int numberOfStudentsAtRisk = academicTutorshipReport.getNumberOfStudentsAtRisk();
            int idAcademicTutorshipReport = academicTutorshipReport.getIdAcademicTutorshipReport();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, generalComment);
            preparedStatement.setInt(2, numberOfStudentsAttending);
            preparedStatement.setInt(3, numberOfStudentsAtRisk);
            preparedStatement.setInt(4, idAcademicTutorshipReport);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected == Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static ArrayList<Integer> getAsistancesOfAcademicTutorShipReport(int idSchoolPeriod, int idAcademicPersonnel) throws SQLException{
        ArrayList<Integer> asistance = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "Select * from academictutorshipreport "
                + "JOIN academictutorship ON academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship "
                + "JOIN academictutorshipsession ON academictutorship.idAcademicTutorshipSession = academictutorshipsession.idAcademicTutorshipSession "
                + "where idAcademicPersonnel = ? and idSchoolPeriod = ?;";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicPersonnel);
            preparedStatement.setInt(2, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            for(int i=0;i<3;i++){
                if(resultSet.next()){
                    asistance.add(resultSet.getInt("numberOfStudentsAttending"));                
                }else{
                    asistance.add(0);
                }
            }
        } finally {
            databaseConnection.close();
        }
        return asistance;
    }

}