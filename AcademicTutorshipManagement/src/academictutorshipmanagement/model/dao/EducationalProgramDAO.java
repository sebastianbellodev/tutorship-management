/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: April 20, 2023.
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

    public static int assignEducationalExperieceToEducationalProgram(int idEducationalProgram, int idEducationalExperience) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO syllabus\n"
                + "(idEducationalProgram, idEducationalExperience)\n"
                + "VALUES(?, ?)";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idEducationalExperience);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static ArrayList<EducationalProgram> getEducationalPrograms() {
        ArrayList<EducationalProgram> educationalPrograms = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT educationalProgram.*\n"
                + "FROM educationalprogram";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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

    public static ArrayList<EducationalProgram> getEducationalProgramsByEducationalExperience(int idEducationalExperience) {
        ArrayList<EducationalProgram> educationalPrograms = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT educationalProgram.*\n"
                + "FROM educationalProgram\n"
                + "INNER JOIN syllabus\n"
                + "ON educationalProgram.idEducationalProgram = syllabus.idEducationalProgram\n"
                + "WHERE syllabus.idEducationalExperience = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalExperience);
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

    public static ArrayList<EducationalProgram> getEducationalProgramsByUser(String username) {
        ArrayList<EducationalProgram> educationalPrograms = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT educationalProgram.*\n"
                + "FROM educationalProgram\n"
                + "INNER JOIN educationalProgramRole\n"
                + "ON educationalProgram.idEducationalProgram = educationalProgramRole.idEducationalProgram\n"
                + "WHERE educationalProgramRole.username = ?\n"
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

    public static int logEducationalProgramByRole(User user, EducationalProgram educationalProgram, Role role, int available){
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO educationalprogramrole\n"
                + "(idEducationalProgram, idRole, username, available)\n"
                + "VALUES(?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            int idEducationalProgram = educationalProgram.getIdEducationalProgram();
            int idRole = role.getIdRole();
            String username = user.getUsername();            
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idRole);
            preparedStatement.setString(3, username); 
            preparedStatement.setInt(4, available);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static int updateEducationalProgramByRole(User user, EducationalProgram educationalProgram, Role role, int available){
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE educationalprogramrole \n" +
                          "SET available = ?\n" +
                          "WHERE username = ? AND idEducationalProgram = ? AND idRole = ?;";
        try (Connection connection = databaseConnection.open()) {
            int idEducationalProgram = educationalProgram.getIdEducationalProgram();
            int idRole = role.getIdRole();           
            String username = user.getUsername();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, available);
            preparedStatement.setString(2, username);
            preparedStatement.setInt(3, idEducationalProgram);
            preparedStatement.setInt(4, idRole);                                   
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