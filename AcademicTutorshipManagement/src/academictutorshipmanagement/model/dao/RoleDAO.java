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

public class RoleDAO {

    public static ArrayList<Role> getRolesByEducationalProgram(int idEducationalProgram, String username) {
        ArrayList<Role> roles = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT role.*\n"
                + "FROM role\n"
                + "INNER JOIN educationalProgramRole\n"
                + "ON role.idRole = educationalProgramRole.idRole\n"
                + "WHERE educationalProgramRole.idEducationalProgram = ? AND educationalProgramRole.username = ?"
                + "ORDER BY name ASC";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configuredQuery = database.prepareStatement(query);
            configuredQuery.setInt(1, idEducationalProgram);
            configuredQuery.setString(2, username);
            ResultSet queryResult = configuredQuery.executeQuery();
            while (queryResult.next()) {
                Role role = new Role();
                role.setIdRole(queryResult.getInt("idRole"));
                role.setName(queryResult.getString("name"));
                roles.add(role);
            }
        } catch (SQLException exception) {
            roles = null;
        } finally {
            databaseConnection.close();
        }
        return roles;
    }

}