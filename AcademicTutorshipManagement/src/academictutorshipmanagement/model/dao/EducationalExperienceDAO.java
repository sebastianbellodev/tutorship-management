/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 04, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationalExperienceDAO {

    public static EducationalExperience checkEducationalExperienceExistence(String name) {
        EducationalExperience educationalExperience = new EducationalExperience();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT educationalExperience.*\n"
                + "FROM educationalExperience\n"
                + "WHERE REPLACE(LOWER(educationalExperience.name), ' ', '') = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            name = name.replace(" ", "").toLowerCase();
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                educationalExperience.setName(resultSet.getString("name"));
                educationalExperience.setAvailable(resultSet.getBoolean("available"));
            } else {
                educationalExperience = null;
            }
        } catch (SQLException exception) {
            educationalExperience = null;
        } finally {
            databaseConnection.close();
        }
        return educationalExperience;
    }

    public static EducationalExperience getEducationalExperience(String name) {
        EducationalExperience educationalExperience = new EducationalExperience();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT educationalExperience.*\n"
                + "FROM educationalExperience\n"
                + "WHERE name = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                educationalExperience.setName(resultSet.getString("name"));
                educationalExperience.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            }
        } catch (SQLException exception) {
            educationalExperience.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return educationalExperience;
    }

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

    public static int logEducationalExperience(EducationalExperience educationalExperience) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO educationalExperience\n"
                + "(name, available)\n"
                + "VALUES(?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String name = educationalExperience.getName();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, Constants.AVAILABLE);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int updateEducationalExperience(EducationalExperience educationalExperience) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE educationalExperience\n"
                + "SET name = ?, available = ?\n"
                + "WHERE idEducationalExperience = ?";
        try (Connection connection = databaseConnection.open()) {
            int idEducationalExperience = educationalExperience.getIdEducationalExperience();
            String name = educationalExperience.getName();
            boolean available = educationalExperience.isAvailable();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, available);
            preparedStatement.setInt(3, idEducationalExperience);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
        public static ArrayList<EducationalExperience> getEducationalExperiencesByEducationalProgram(int idEducationalProgram) throws SQLException{
        ArrayList<EducationalExperience> educationalExperiences = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT educationalExperience.*\n"
                + "FROM educationalExperience\n"
                + "INNER JOIN academicOffering\n"
                + "ON educationalExperience.idEducationalExperience = academicOffering.idEducationalExperience\n"
                + "INNER JOIN syllabus\n"
                + "ON academicOffering.idEducationalExperience = syllabus.idEducationalExperience\n"
                + "WHERE syllabus.idEducationalProgram = ?\n"
                + "ORDER BY educationalExperience.name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EducationalExperience educationalExperience = new EducationalExperience();
                educationalExperience.setIdEducationalExperience(resultSet.getInt("idEducationalExperience"));
                educationalExperience.setName(resultSet.getString("name"));
                educationalExperiences.add(educationalExperience);
            }
        }finally {
            databaseConnection.close();
        }
        return educationalExperiences;
    }

}