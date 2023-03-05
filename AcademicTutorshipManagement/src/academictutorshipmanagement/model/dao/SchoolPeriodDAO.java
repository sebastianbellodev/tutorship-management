/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolPeriodDAO {

    public static SchoolPeriod getCurrentSchoolPeriod() {
        SchoolPeriod schoolPeriod = new SchoolPeriod();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT schoolPeriod.*\n"
                + "FROM schoolPeriod\n"
                + "WHERE NOW() BETWEEN startDate AND endDate";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                schoolPeriod.setIdSchoolPeriod(resultSet.getInt("idSchoolPeriod"));
                schoolPeriod.setStartDate(resultSet.getDate("startDate"));
                schoolPeriod.setEndDate(resultSet.getDate("endDate"));
                schoolPeriod.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            }
        } catch (SQLException exception) {
            schoolPeriod.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return schoolPeriod;
    }
    
}