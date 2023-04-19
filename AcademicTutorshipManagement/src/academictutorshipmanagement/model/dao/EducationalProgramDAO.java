/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EducationalProgramDAO {

    public static ArrayList<EducationalProgram> getEducationalProgramsByUser(String username) {
        ArrayList<EducationalProgram> educationalPrograms = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT educationalProgram.*\n"
                + "FROM educationalprogram\n"
                + "INNER JOIN educationalProgramRole\n"
                + "ON educationalProgram.idEducationalProgram = educationalProgramRole.idEducationalProgram\n"
                + "WHERE educationalProgramRole.username = ?"
                + "ORDER BY name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EducationalProgram educationalProgram = new EducationalProgram();
                educationalProgram.setIdEducationalProgram(resultSet.getInt("idEducationalProgram"));
                educationalProgram.setName(resultSet.getString("name"));
                educationalPrograms.add(educationalProgram);
            }
        } catch (SQLException exception) {
            educationalPrograms = null;
        } finally {
            databaseConnection.close();
        }
        return educationalPrograms;
    }
    
    public ObservableList<EducationalProgram> getAllEducationalPrograms(){
        ObservableList<EducationalProgram> educationalPrograms = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.open()) {
            String query = "SELECT  idEducationalProgram, name FROM educationalprogram";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();     
            while(resultSet.next()){
                int idEducationalProgram = resultSet.getInt("idEducationalProgram");
                String name = resultSet.getString("name");
                
                EducationalProgram educationalProgram = new EducationalProgram(idEducationalProgram, name);
                educationalPrograms.add(educationalProgram);
            }
            
        }catch(SQLException exception){
            System.out.print(exception.getMessage());
        }finally{
            databaseConnection.close();
        }
        return educationalPrograms;
    }

    public static int logEducationalProgramByRole(User user, EducationalProgram educationalProgram, Role role){
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO educationalprogramrole\n"
                + "(idEducationalProgram, idRole, username)\n"
                + "VALUES(?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            int idEducationalProgram = educationalProgram.getIdEducationalProgram();
            int idRole = role.getIdRole();
            String username = user.getUsername();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idRole);
            preparedStatement.setString(3, username);           
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