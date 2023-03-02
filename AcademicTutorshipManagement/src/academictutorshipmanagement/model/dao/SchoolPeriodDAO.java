/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 02, 2023.
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
                + "WHERE NOW() BETWEEN schoolPeriod.startDate AND schoolPeriod.endDate;";
        try (Connection database = databaseConnection.open()) {
            PreparedStatement configuredQuery = database.prepareStatement(query);
            ResultSet queryResult = configuredQuery.executeQuery();
            if (queryResult.next()) {
                schoolPeriod.setIdSchoolPeriod(queryResult.getInt("idSchoolPeriod"));
                schoolPeriod.setStartDate(queryResult.getDate("startDate"));
                schoolPeriod.setEndDate(queryResult.getDate("endDate"));
            }
        } catch (SQLException exception) {
            schoolPeriod.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return schoolPeriod;
    }
}