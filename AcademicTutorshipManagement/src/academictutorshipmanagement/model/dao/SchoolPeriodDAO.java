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
import java.util.ArrayList;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchoolPeriodDAO {

    public static SchoolPeriod getCurrentSchoolPeriod() {
        SchoolPeriod schoolPeriod = new SchoolPeriod();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
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
    
    public static ArrayList<SchoolPeriod> getSchoolPeriods() {
        ArrayList<SchoolPeriod> schoolPeriods = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM schoolPeriod";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SchoolPeriod schoolPeriod = new SchoolPeriod();
                schoolPeriod.setIdSchoolPeriod(resultSet.getInt("idSchoolPeriod"));
                schoolPeriod.setStartDate(resultSet.getDate("startDate"));
                schoolPeriod.setEndDate(resultSet.getDate("endDate"));
                schoolPeriods.add(schoolPeriod);
            }
        } catch (SQLException exception) {
            schoolPeriods = null;
        } finally {
            databaseConnection.close();
        }
        return schoolPeriods;
    }
    
     public ObservableList <SchoolPeriod> getAllPeriods() throws SQLException{
        ObservableList<SchoolPeriod> schoolPeriod  = FXCollections.observableArrayList(); 
        DatabaseConnection databaseConnection = new DatabaseConnection();        
        try(Connection connection = databaseConnection.open()){
            String query = "SELECT  idSchoolPeriod, startDate, endDate FROM schoolPeriod";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();     
            
                while(resultSet.next()){
                    int idSchoolPeriod = resultSet.getInt("idSchoolPeriod");
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");                    
                    
                    SchoolPeriod schoolPeriods = new SchoolPeriod(idSchoolPeriod, startDate, endDate);
                    schoolPeriod.add(schoolPeriods);
                }
           
        }catch(SQLException exception){
            schoolPeriod = null;
        } finally{
            databaseConnection.close();
        }
        return schoolPeriod;
    }
    
}