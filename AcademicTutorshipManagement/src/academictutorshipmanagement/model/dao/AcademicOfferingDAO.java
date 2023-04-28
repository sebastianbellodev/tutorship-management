/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicOfferingDAO {

    public static ArrayList<AcademicOffering> getAcademicOfferings(int idEducationalExperience, int idSchoolPeriod) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT idAcademicOffering, nrc\n"
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
                academicOfferings.add(academicOffering);
            }
        } catch (SQLException exception) {
            academicOfferings = null;
        } finally {
            databaseConnection.close();
        }
        return academicOfferings;
    }

    public static ArrayList<AcademicOffering> getAcademicOfferings(int idEducationalExperience, int idSchoolPeriod, int idAcademicPersonnel) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT idAcademicOffering, nrc\n"
                + "FROM academicOffering\n"
                + "WHERE idEducationalExperience = ? AND idAcademicPersonnel = ? AND idSchoolPeriod = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalExperience);
            preparedStatement.setInt(2, idAcademicPersonnel);
            preparedStatement.setInt(3, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicOffering academicOffering = new AcademicOffering();
                academicOffering.setIdAcademicOffering(resultSet.getInt("idAcademicOffering"));
                academicOffering.setNrc(resultSet.getInt("nrc"));
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
        }finally {
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
    
    public static boolean logAcademicOffering(AcademicOffering academicOffering) throws SQLException{
        Boolean response = false;        
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "INSERT INTO academicoffering (`nrc`, `idEducationalExperience`, `idAcademicPersonnel`, `idSchoolPeriod`) VALUES (?,?,?,?);";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(4, academicOffering.getSchoolPeriod().getIdSchoolPeriod());
            preparedStatement.setInt(1, academicOffering.getNrc());
            preparedStatement.setInt(2, academicOffering.getEducationalExperience().getIdEducationalExperience());
            preparedStatement.setInt(3, academicOffering.getAcademicPersonnel().getIdAcademicPersonnel());
            preparedStatement.executeUpdate();
            response = true;
        }finally {
            databaseConnection.close();
        }
        return response;
    }

}