/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: March 15, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicProblem;
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

    public static ArrayList<AcademicProblem> loadAcademicProblemsByAcademicTutorshipReport(int idAcademicTutorshipReport) {
        ArrayList<AcademicProblem> academicProblems = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM academicProblem\n"
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