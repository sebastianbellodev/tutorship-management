/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: April 21, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicOfferingDAO {

    public static int logAcademicOffering(AcademicOffering academicOffering) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicOffering\n"
                + "(nrc, idEducationalExperience, idAcademicPersonnel, idSchoolPeriod)\n"
                + "VALUES(?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            int nrc = academicOffering.getNrc();
            int idEducationalExperience = academicOffering.getEducationalExperience().getIdEducationalExperience();
            int idAcademicPersonnel = academicOffering.getAcademicPersonnel().getIdAcademicPersonnel();
            int idSchoolPeriod = academicOffering.getSchoolPeriod().getIdSchoolPeriod();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, nrc);
            preparedStatement.setInt(2, idEducationalExperience);
            preparedStatement.setInt(3, idAcademicPersonnel);
            preparedStatement.setInt(4, idSchoolPeriod);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static ArrayList<AcademicOffering> getAcademicOfferingsByEducationalExperience(int idEducationalExperience, int idSchoolPeriod) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicOffering.*\n"
                + "FROM academicOffering\n"
                + "WHERE idEducationalExperience = ? AND idSchoolPeriod = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalExperience);
            preparedStatement.setInt(2, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicOffering academicOffering = new AcademicOffering();
                academicOffering.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                academicOffering.setNrc(resultSet.getInt("nrc"));
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicOffering.setAcademicPersonnel(academicPersonnel);
                EducationalExperience educationalExperience = new EducationalExperience();
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                academicOffering.setEducationalExperience(educationalExperience);
                academicOfferings.add(academicOffering);
            }
        } catch (SQLException exception) {
            academicOfferings = null;
        } finally {
            databaseConnection.close();
        }
        return academicOfferings;
    }

    public static ArrayList<AcademicOffering> getAcademicOfferingsByAcademicPersonnel(int idEducationalExperience, int idSchoolPeriod, int idAcademicPersonnel) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicOffering.*\n"
                + "FROM academicOffering\n"
                + "WHERE idEducationalExperience = ? AND idSchoolPeriod = ? AND idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalExperience);
            preparedStatement.setInt(2, idSchoolPeriod);
            preparedStatement.setInt(3, idAcademicPersonnel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicOffering academicOffering = new AcademicOffering();
                academicOffering.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                academicOffering.setNrc(resultSet.getInt("nrc"));
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicOffering.setAcademicPersonnel(academicPersonnel);
                EducationalExperience educationalExperience = new EducationalExperience();
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                academicOffering.setEducationalExperience(educationalExperience);
                academicOfferings.add(academicOffering);
            }
        } catch (SQLException exception) {
            academicOfferings = null;
        } finally {
            databaseConnection.close();
        }
        return academicOfferings;
    }
    
    
    public static ArrayList<AcademicOffering> getAcademicOffering(int idSchoolPeriod, int idEducationalProgram) throws SQLException{
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "Select * from academicoffering join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "join syllabus on educationalexperience.idEducationalExperience = syllabus.idEducationalExperience "
                + "where academicoffering.idSchoolPeriod = ? and syllabus.idEducationalProgram = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){           
            }else{
                do{
                    AcademicOffering academicOffering = new AcademicOffering();
                    academicOffering.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                    academicOffering.setNrc(resultSet.getInt("nrc")); 
                    academicOffering.getAcademicPersonnel().setName(resultSet.getString("academicpersonnel.name"));
                    academicOffering.getAcademicPersonnel().setPaternalSurname(resultSet.getString("academicpersonnel.paternalSurname"));
                    academicOffering.getAcademicPersonnel().setMaternalSurname(resultSet.getString("academicpersonnel.maternalSurname"));
                    academicOffering.getEducationalExperience().setName(resultSet.getString("educationalexperience.name"));              
                    academicOfferings.add(academicOffering);
                
                }while(resultSet.next());
            }
        } finally {
            databaseConnection.close();
        }
        return academicOfferings;
    }
    
    public static AcademicOffering getAcademicOffering(int NRC, int idSchoolPeriod, int idEducationalProgram) throws SQLException{
        AcademicOffering academicOffering = null;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "Select * from academicoffering join educationalexperience on academicoffering.idEducationalExperience = educationalexperience.idEducationalExperience "
                + "join academicpersonnel on academicoffering.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "join syllabus on educationalexperience.idEducationalExperience = syllabus.idEducationalExperience "
                + "where academicoffering.idSchoolPeriod = ? and syllabus.idEducationalProgram = ? and academicoffering.nrc = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idEducationalProgram);
            preparedStatement.setInt(3, NRC);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){           
            }else{
                    academicOffering = new AcademicOffering();
                    academicOffering.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                    academicOffering.setNrc(resultSet.getInt("nrc")); 
                    academicOffering.getAcademicPersonnel().setName(resultSet.getString("academicpersonnel.name"));
                    academicOffering.getAcademicPersonnel().setPaternalSurname(resultSet.getString("academicpersonnel.paternalSurname"));
                    academicOffering.getAcademicPersonnel().setMaternalSurname(resultSet.getString("academicpersonnel.maternalSurname"));
                    academicOffering.getEducationalExperience().setName(resultSet.getString("educationalexperience.name"));            }
        }finally {
            databaseConnection.close();
        }
        return academicOffering;
    }

    public static int updateAcademicOffering(AcademicOffering academicOffering) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicOffering\n"
                + "SET nrc = ?\n"
                + "WHERE idAcademicOffering = ?";
        try (Connection connection = databaseConnection.open()) {
            int idAcademicOffering = academicOffering.getIdAcademicOffering();
            int nrc = academicOffering.getNrc();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, nrc);
            preparedStatement.setInt(2, idAcademicOffering);
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