/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 04, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationalExperienceDAO {

    public static ArrayList<EducationalExperience> getEducationalExperiencesByEducationalProgram(int idSchoolPeriod, int idEducationalProgram) {
        ArrayList<EducationalExperience> educationalExperiences = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT educationalExperience.*\n"
                + "FROM educationalExperience\n"
                + "INNER JOIN academicOffering\n"
                + "ON educationalExperience.idEducationalExperience = academicOffering.idEducationalExperience\n"
                + "INNER JOIN syllabus\n"
                + "ON academicOffering.idEducationalExperience = syllabus.idEducationalExperience\n"
                + "WHERE academicOffering.idSchoolPeriod = ? AND syllabus.idEducationalProgram = ?\n"
                + "ORDER BY educationalExperience.name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EducationalExperience educationalExperience = new EducationalExperience();
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                educationalExperience.setName(resultSet.getString("name"));
                educationalExperiences.add(educationalExperience);
            }
        } catch (SQLException exception) {
            educationalExperiences = null;
        } finally {
            databaseConnection.close();
        }
        return educationalExperiences;
    }

}