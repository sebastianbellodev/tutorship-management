/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 *
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoleDAO {

    public static ArrayList<Role> getRolesByEducationalProgram(int idEducationalProgram, String username)  {
        ArrayList<Role> roles = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT role.*\n"
                + "FROM role\n"
                + "INNER JOIN educationalProgramRole\n"
                + "ON role.idRole = educationalProgramRole.idRole\n"
                + "WHERE educationalProgramRole.idEducationalProgram = ? AND educationalProgramRole.username = ?"
                + "ORDER BY name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setIdRole(resultSet.getInt("idRole"));
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }
        } catch (SQLException exception) {
            roles = null;
        } finally {
            databaseConnection.close();
        }
        return roles;
    }
    
    public ObservableList<Role> getAllRoles(){
        ObservableList<Role> roles = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.open()) {
            String query = "SELECT  idRole, name FROM role WHERE idRole IN (1,2,3)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();     
            while(resultSet.next()){
                Role role = new Role();
                role.setIdRole(resultSet.getInt("idRole"));
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }
            
        }catch(SQLException exception){
            System.out.print(exception.getMessage());
        }finally{
            databaseConnection.close();
        }
        return roles;
    }
    
    public ObservableList<Role> getAllRolesByAcademicPersonnel(String username){
        ObservableList<Role> roles = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.open()) {
            String query = "SELECT  role.name\n" +
                            "FROM role \n" +
                            "INNER JOIN educationalprogramrole ON  role.idRole = educationalprogramrole.idRole\n" +
                            "INNER JOIN academicpersonnel ON educationalprogramrole.username = academicpersonnel.username\n" +
                            "WHERE academicpersonnel.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();     
            while(resultSet.next()){
                Role role = new Role();
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }            
        }catch(SQLException exception){
            System.out.print(exception.getMessage());
        }finally{
            databaseConnection.close();
        }
        return roles;
    }

}