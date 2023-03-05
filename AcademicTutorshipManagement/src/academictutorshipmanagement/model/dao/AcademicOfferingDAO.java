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

    public static ArrayList<AcademicOffering> getAcademicOfferings(int idEducationalExperience, int idAcademicPersonnel, int idSchoolPeriod) {
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

}