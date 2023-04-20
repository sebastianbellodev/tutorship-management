/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: April 20, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcademicOfferingDAO {

    public static ArrayList<AcademicOffering> getAcademicOfferingsByEducationalExperience(int idSchoolPeriod, int idEducationalExperience) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicOffering.*\n"
                + "FROM academicOffering\n"
                + "WHERE idSchoolPeriod = ? AND idEducationalExperience = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idEducationalExperience);
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

    public static ArrayList<AcademicOffering> getAcademicOfferingsByAcademicPersonnel(int idSchoolPeriod, int idEducationalExperience, int idAcademicPersonnel) {
        ArrayList<AcademicOffering> academicOfferings = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicOffering.*\n"
                + "FROM academicOffering\n"
                + "WHERE idSchoolPeriod = ? AND idEducationalExperience = ? AND idAcademicPersonnel = ?";
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

}