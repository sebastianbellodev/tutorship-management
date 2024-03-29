/**
 * Name(s) of the programmer(s): Armando Omar Obando Muñóz and María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: April 20, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicProblemDAO {

    public static int logAcademicProblemByAcademicTutorshipReport(AcademicProblem academicProblem, int idAcademicTutorshipReport) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicProblem\n"
                + "(title, description, numberOfStudents, idAcademicOffering, idAcademicTutorshipReport)\n"
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String title = academicProblem.getTitle();
            String description = academicProblem.getDescription();
            int numberOfStudents = academicProblem.getNumberOfStudents();
            int idAcademicOffering = academicProblem.getAcademicOffering().getIdAcademicOffering();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, numberOfStudents);
            preparedStatement.setInt(4, idAcademicOffering);
            preparedStatement.setInt(5, idAcademicTutorshipReport);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    
    public static ArrayList<AcademicProblem> queryAcademicProblemForEducationalProgram(int idEducationalProgram) throws SQLException{
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<AcademicProblem> academicProblemQuery = new ArrayList<>();
        String sentence = 
                "select * from academicproblem join academictutorshipreport on academictutorshipreport.idAcademicTutorshipReport = academicproblem.idAcademicTutorshipReport "
                + "join academicoffering on academicproblem.idAcademicOffering = academicoffering.idAcademicOffering "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academictutorship on academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship "
                + "join schoolperiod on academicoffering.idSchoolPeriod = schoolperiod.idSchoolPeriod where academictutorship.idEducationalProgram = ?";
        try(Connection connection = databaseConnection.open()){
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1,idEducationalProgram);
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next()){   
            }else{
                do{
                    AcademicProblem academicProblem = new AcademicProblem();
                    academicProblem.setIdAcademicProblem(result.getInt("idAcademicProblem"));
                    academicProblem.setTitle(result.getString("title"));
                    academicProblem.setDescription(result.getString("description"));
                    academicProblem.setNumberOfStudents(result.getInt("numberOfStudents"));
                    academicProblem.getAcademicOffering().setIdAcademicOffering(result.getInt("idAcademicOffering"));
                    academicProblem.getAcademicOffering().getEducationalExperience().setIdEducationalExperience(result.getInt("idEducationalExperience"));
                    academicProblem.getAcademicOffering().getEducationalExperience().setName(result.getString("educationalexperience.name"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setIdSchoolPeriod(result.getInt("idSchoolPeriod"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setStartDate(result.getDate("startDate"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setEndDate(result.getDate("endDate"));
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setName(result.getString("academicpersonnel.name"));
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setPaternalSurname(result.getString("academicpersonnel.paternalSurname"));
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setMaternalSurname(result.getString("academicpersonnel.maternalSurname"));
                    academicProblemQuery.add(academicProblem);
                }while(result.next());
            }
        }finally{
            databaseConnection.close();
        }
        return academicProblemQuery;
    }
    
    public static ArrayList<AcademicProblem> queryAcademicProblemForAcademicPersonnel(int idAcademicPersonel, int idEducationalProgram) throws SQLException{
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ArrayList<AcademicProblem> academicProblemsQuery = new ArrayList<>();
        String sentence = 
          "select * from academicproblem join academictutorshipreport on academictutorshipreport.idAcademicTutorshipReport = academicproblem.idAcademicTutorshipReport "
                + "join academicoffering on academicproblem.idAcademicOffering = academicoffering.idAcademicOffering "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academictutorship on academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship "
                + "join schoolperiod on academicoffering.idSchoolPeriod = schoolperiod.idSchoolPeriod where academictutorshipreport.idAcademicPersonnel = ? and academictutorship.idEducationalProgram = ?";
        try(Connection connection = databaseConnection.open()){
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1,idAcademicPersonel);
            preparedStatement.setInt(2,idEducationalProgram);
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next()){   
            }else{
                do{
                    AcademicProblem academicProblem = new AcademicProblem();
                    academicProblem.setIdAcademicProblem(result.getInt("idAcademicProblem"));
                    academicProblem.setTitle(result.getString("title"));
                    academicProblem.setDescription(result.getString("description"));
                    academicProblem.setNumberOfStudents(result.getInt("numberOfStudents"));
                    academicProblem.getAcademicOffering().setIdAcademicOffering(result.getInt("idAcademicOffering"));
                    academicProblem.getAcademicOffering().getEducationalExperience().setIdEducationalExperience(result.getInt("idEducationalExperience"));
                    academicProblem.getAcademicOffering().getEducationalExperience().setName(result.getString("educationalexperience.name"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setIdSchoolPeriod(result.getInt("idSchoolPeriod"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setStartDate(result.getDate("startDate"));
                    academicProblem.getAcademicOffering().getSchoolPeriod().setEndDate(result.getDate("endDate")); 
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setName(result.getString("academicpersonnel.name"));
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setPaternalSurname(result.getString("academicpersonnel.paternalSurname"));
                    academicProblem.getAcademicOffering().getAcademicPersonnel().setMaternalSurname(result.getString("academicpersonnel.maternalSurname"));
                    academicProblemsQuery.add(academicProblem);
                }while(result.next());
            }
        }finally{
            databaseConnection.close();
        }
        return academicProblemsQuery;
    }
    
    public static AcademicProblem queryAcademicProblemWithFollowUp(int idAcademicProblem) throws SQLException{
        DatabaseConnection databaseConnection = new DatabaseConnection();
        AcademicProblem queryAcademicProblem = new AcademicProblem();
        String sentence = "SELECT * FROM academicproblem join academicproblemfollowup on academicproblem.idAcademicProblem = academicproblemfollowup.idAcademicProblem "
                + "join academicoffering on academicproblem.idAcademicOffering = academicoffering.idAcademicOffering " 
                + "join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel where academicproblem.idAcademicProblem = ?";
        try(Connection connection = databaseConnection.open()){
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1,idAcademicProblem);           
            ResultSet result = preparedStatement.executeQuery();
            if(!result.next()){  
            }else{
                queryAcademicProblem.setIdAcademicProblem(result.getInt("idAcademicProblem"));
                queryAcademicProblem.setTitle(result.getString("academicproblem.title"));
                queryAcademicProblem.setDescription(result.getString("academicproblem.description"));
                queryAcademicProblem.setNumberOfStudents(result.getInt("academicproblem.numberOfStudents"));
                queryAcademicProblem.getAcademicOffering().setIdAcademicOffering(result.getInt("idAcademicOffering"));
                queryAcademicProblem.getAcademicOffering().getEducationalExperience().setIdEducationalExperience(result.getInt("idEducationalExperience"));
                queryAcademicProblem.getAcademicOffering().getEducationalExperience().setName(result.getString("educationalexperience.name"));
                queryAcademicProblem.getAcademicOffering().getAcademicPersonnel().setName(result.getString("academicpersonnel.name"));
                queryAcademicProblem.getAcademicOffering().getAcademicPersonnel().setPaternalSurname(result.getString("academicpersonnel.paternalSurname"));
                queryAcademicProblem.getAcademicOffering().getAcademicPersonnel().setMaternalSurname(result.getString("academicpersonnel.maternalSurname"));
                queryAcademicProblem.getAcademicProblemFollowUp().setIdAcademicProblemFollowUp(result.getInt("idAcademicProblemFollowUp"));       
                queryAcademicProblem.getAcademicProblemFollowUp().setDescription(result.getString("academicproblemfollowup.description"));
                queryAcademicProblem.getAcademicProblemFollowUp().setDate(result.getDate("academicproblemfollowup.date"));
            }
        }finally{
            databaseConnection.close();
        }    
        return queryAcademicProblem;
    }

    
    
    public static int registerAcademicProblemFollowUp(AcademicProblem academicProblem) throws SQLException {
        int response;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO `academictutorshipmanagement`.`academicproblemfollowup`"
                + " (`description`, `date`, `idAcademicProblem`) VALUES (?,?,?);";
        try(Connection connection = databaseConnection.open()){
            PreparedStatement preparedStatement = connection.prepareCall(sentence);
            preparedStatement.setString(1,academicProblem.getAcademicProblemFollowUp().getDescription());
            preparedStatement.setDate(2, academicProblem.getAcademicProblemFollowUp().getDate());
            preparedStatement.setInt(3,academicProblem.getIdAcademicProblem());
            response = preparedStatement.executeUpdate();  
        }finally{
            databaseConnection.close();
        }
        return response;
    }
    
    public static int ModifyAcademicProblemFollowUp(AcademicProblem academicProblem, int academicProblemFollowUp) throws SQLException{
        int response;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academictutorshipmanagement.academicproblemfollowup SET description = ?, date = ?, idAcademicProblem = ?  WHERE idAcademicProblemFollowUp = ?;";
        try(Connection connection = databaseConnection.open()){
            PreparedStatement preparedStatement = connection.prepareCall(sentence);
            preparedStatement.setString(1,academicProblem.getAcademicProblemFollowUp().getDescription());
            preparedStatement.setDate(2, academicProblem.getAcademicProblemFollowUp().getDate());
            preparedStatement.setInt(3,academicProblem.getIdAcademicProblem());
            preparedStatement.setInt(4, academicProblemFollowUp);
            response = preparedStatement.executeUpdate();  
        }finally{
            databaseConnection.close();
        }
        return response;
    }

    public static int updateAcademicProblemByAcademicTutorshipReport(AcademicProblem academicProblem){
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicProblem "
                + "SET title = ?, description = ?, numberOfStudents = ?, idAcademicOffering = ? "
                + "WHERE idAcademicProblem= ?";
        try (Connection connection = databaseConnection.open()) {
            String title = academicProblem.getTitle();
            String description = academicProblem.getDescription();
            int numberOfStudents = academicProblem.getNumberOfStudents();
            int idAcademicOffering = academicProblem.getAcademicOffering().getIdAcademicOffering();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, numberOfStudents);
            preparedStatement.setInt(4, idAcademicOffering);
            preparedStatement.setInt(5, academicProblem.getIdAcademicProblem());
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static ArrayList<AcademicProblem> loadAcademicProblemsByAcademicTutorshipReport(int idAcademicTutorshipReport) {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = 
                "select * from academicproblem join academictutorshipreport on academictutorshipreport.idAcademicTutorshipReport = academicproblem.idAcademicTutorshipReport "
                + "join academicoffering on academicproblem.idAcademicOffering = academicoffering.idAcademicOffering "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academictutorship on academictutorshipreport.idAcademicTutorship = academictutorship.idAcademicTutorship "
                + "join schoolperiod on academicoffering.idSchoolPeriod = schoolperiod.idSchoolPeriod where academicproblem.idAcademicTutorshipReport = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicTutorshipReport);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicProblem academicProblem = new AcademicProblem();
                academicProblem.setIdAcademicProblem(resultSet.getInt("idAcademicProblem"));
                academicProblem.setTitle(resultSet.getString("title"));
                academicProblem.setDescription(resultSet.getString("description"));
                academicProblem.setNumberOfStudents(resultSet.getInt("numberOfStudents"));
                academicProblem.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                academicProblem.getAcademicOffering().setNrc(resultSet.getInt("nrc"));
                academicProblem.getAcademicOffering().getEducationalExperience().setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                academicProblem.getAcademicOffering().getEducationalExperience().setName(resultSet.getString("educationalexperience.name"));
                academicProblem.getAcademicOffering().getSchoolPeriod().setIdSchoolPeriod(resultSet.getInt("idSchoolPeriod"));
                academicProblem.getAcademicOffering().getSchoolPeriod().setStartDate(resultSet.getDate("startDate"));
                academicProblem.getAcademicOffering().getSchoolPeriod().setEndDate(resultSet.getDate("endDate")); 
                academicProblem.getAcademicOffering().getAcademicPersonnel().setName(resultSet.getString("academicpersonnel.name"));
                academicProblem.getAcademicOffering().getAcademicPersonnel().setPaternalSurname(resultSet.getString("academicpersonnel.paternalSurname"));
                academicProblem.getAcademicOffering().getAcademicPersonnel().setMaternalSurname(resultSet.getString("academicpersonnel.maternalSurname"));
                academicProblems.add(academicProblem);
            }
        } catch (SQLException exception) {
            academicProblems = null;
        } finally {
            databaseConnection.close();
        }
         return academicProblems;
    }
    
    public static ArrayList<AcademicProblem> loadAcademicProblemsByAcademicTutorshipGeneralReport(int idAcademicTutorshipReport) {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicProblem.*, educationalexperience.name as educationalExperience, academicpersonnel.name,\n"
                + "academicpersonnel.paternalSurname, academicpersonnel.maternalSurname\n"
                + "FROM academicProblem\n"
                + "INNER JOIN academicOffering\n"
                + "ON academicoffering.idAcademicOffering = academicproblem.idAcademicOffering\n"
                + "INNER JOIN educationalExperience\n"
                + "ON educationalExperience.idEducationalExperience = academicOffering.idEducationalExperience\n"
                + "INNER JOIN academicPersonnel\n"
                + "ON academicPersonnel.idAcademicPersonnel = academicOffering.idAcademicPersonnel\n"
                + "WHERE idAcademicTutorshipReport = ?";
         try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicTutorshipReport);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicProblem academicProblem = new AcademicProblem();
                academicProblem.setIdAcademicProblem(resultSet.getInt("idAcademicProblem"));
                academicProblem.setTitle(resultSet.getString("title"));
                academicProblem.setDescription(resultSet.getString("description"));
                academicProblem.setNumberOfStudents(resultSet.getInt("numberOfStudents"));
                academicProblem.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                AcademicOffering academicOffering = new AcademicOffering();
                EducationalExperience educationalExperience = new EducationalExperience();
                educationalExperience.setName(resultSet.getString("educationalExperience"));
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicOffering.setEducationalExperience(educationalExperience);
                academicOffering.setAcademicPersonnel(academicPersonnel);
                academicProblem.setAcademicOffering(academicOffering);
                academicProblems.add(academicProblem);
            }
        } catch (SQLException exception) {
            academicProblems = null;
        } finally {
            databaseConnection.close();
        }
         return academicProblems;
    }

}