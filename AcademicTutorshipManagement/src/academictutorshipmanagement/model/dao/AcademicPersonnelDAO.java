/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: April 20, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.ContractType;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcademicPersonnelDAO {

    public static ArrayList<AcademicPersonnel> getAcademicPersonnel() {
        ArrayList<AcademicPersonnel> academicPersonnels = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM academicPersonnel\n"
                + "ORDER By name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
                academicPersonnels.add(academicPersonnel);
            }
        } catch (SQLException exception) {
            academicPersonnels = null;
        } finally {
            databaseConnection.close();
        }
        return academicPersonnels;
    }
    
    public static ArrayList<AcademicPersonnel> getAcademicPersonnelByTutorship(int idSchoolPeriod, int idEducationalProgram) throws SQLException {
        ArrayList<AcademicPersonnel> academicPersonnels = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "Select distinct academicpersonnel.* from academicpersonnel "
                + "JOIN academictutorshipreport ON academictutorshipreport.idAcademicPersonnel = academicpersonnel.idAcademicPersonnel "
                + "JOIN academictutorship ON academictutorship.idAcademicTutorship = academictutorshipreport.idAcademicTutorship "
                + "JOIN academictutorshipsession ON academictutorship.idAcademicTutorshipSession = academictutorshipsession.idAcademicTutorshipSession "
                + "where idSchoolPeriod = ? and idEducationalProgram = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSchoolPeriod);
            preparedStatement.setInt(2, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
                academicPersonnels.add(academicPersonnel);
            }
        } finally {
            databaseConnection.close();
        }
        return academicPersonnels;    
    }
    
    
    public static ArrayList<AcademicPersonnel> getAcademicPersonnelByEducationalExperience(int idEducationalExperience, int idSchoolPeriod) {
        ArrayList<AcademicPersonnel> academicPersonnels = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT DISTINCT academicPersonnel.*\n"
                + "FROM academicPersonnel\n"
                + "INNER JOIN academicOffering\n"
                + "ON academicPersonnel.idAcademicPersonnel = academicOffering.idAcademicPersonnel\n"
                + "WHERE academicOffering.idEducationalExperience = ? AND academicOffering.idSchoolPeriod = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalExperience);
            preparedStatement.setInt(2, idSchoolPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
                academicPersonnels.add(academicPersonnel);
            }
        } catch (SQLException exception) {
            academicPersonnels = null;
        } finally {
            databaseConnection.close();
        }
        return academicPersonnels;
    }

    public static ArrayList<AcademicPersonnel> getAcademicPersonnelByRole(int idEducationalProgram, int idRole) {
        ArrayList<AcademicPersonnel> academicPersonnels = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicPersonnel.*\n"
                + "FROM educationalProgramRole\n"
                + "INNER JOIN academicPersonnel\n"
                + "ON educationalProgramRole.username = academicPersonnel.username\n"
                + "WHERE idEducationalProgram = ? AND idRole = ?\n"
                + "ORDER BY academicPersonnel.name ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idRole);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                ContractType contractType = new ContractType();
                User user = new User();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
                contractType.setIdContractType(resultSet.getInt("idContractType"));
                academicPersonnel.setContractType(contractType);
                user.setUsername(resultSet.getString("username"));
                academicPersonnel.setUser(user);
                academicPersonnels.add(academicPersonnel);
            }
        } catch (SQLException exception) {
            academicPersonnels = null;
        } finally {
            databaseConnection.close();
        }
        return academicPersonnels;
    }

    
    
        public static ArrayList<AcademicPersonnel> getAllAcademicPersonnel() throws SQLException {
        ArrayList<AcademicPersonnel> academicPersonnels = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT academicpersonnel.idAcademicPersonnel, academicpersonnel.name, "
                + "academicpersonnel.paternalSurname, academicpersonnel.maternalSurname, "
                + "academicpersonnel.emailAddress FROM academictutorshipmanagement.academicpersonnel";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicPersonnel academicPersonnel = new AcademicPersonnel();
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnels.add(academicPersonnel);
            }
        }finally {
            databaseConnection.close();
        }
        return academicPersonnels;
    }

    public static AcademicPersonnel getAcademicPersonnelByUser(String username) {
        AcademicPersonnel academicPersonnel = new AcademicPersonnel();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM academicPersonnel\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicPersonnel.setIdAcademicPersonnel(resultSet.getInt("idAcademicPersonnel"));
                academicPersonnel.setName(resultSet.getString("name"));
                academicPersonnel.setPaternalSurname(resultSet.getString("paternalSurname"));
                academicPersonnel.setMaternalSurname(resultSet.getString("maternalSurname"));
                academicPersonnel.setEmailAddress(resultSet.getString("emailAddress"));
                academicPersonnel.setResponseCode(Constants.CORRECT_OPERATION_CODE);
            }
        } catch (SQLException exception) {
            academicPersonnel.setResponseCode(Constants.NO_DATABASE_CONNECTION_CODE);
        } finally {
            databaseConnection.close();
        }
        return academicPersonnel;
    }  
    
    public static int checkAcademicPersonnel(String username) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "SELECT * FROM academicPersonnel\n"
                + "WHERE username = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            responseCode = (resultSet.next()) ? Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static int logAcademicPersonnel(AcademicPersonnel academicPersonnel, User user) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicpersonnel\n"
                + "(name, paternalSurname, maternalSurname, emailAddress, idContractType, username)\n"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String name = academicPersonnel.getName();
            String paternalSurname = academicPersonnel.getPaternalSurname();
            String maternalSurname = academicPersonnel.getMaternalSurname();
            String emailAddress = academicPersonnel.getEmailAddress();
            int idContractType = academicPersonnel.getContractType().getIdContractType();
            String username = user.getUsername();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, paternalSurname);
            preparedStatement.setString(3, maternalSurname);
            preparedStatement.setString(4, emailAddress);
            preparedStatement.setInt(5, idContractType);
            preparedStatement.setString(6, username);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public ObservableList <AcademicPersonnel> getAllAcademicPersonnelsByRole(AcademicPersonnel usableAcademicPersonnel) {
        ObservableList<AcademicPersonnel> academicPersonnels  = FXCollections.observableArrayList(); 
        DatabaseConnection databaseConnection = new DatabaseConnection();        
        try(Connection connection = databaseConnection.open()){
            String query = "SELECT academicpersonnel.idAcademicPersonnel, academicpersonnel.name, paternalSurname, maternalSurname, emailAddress, contracttype.name, academicpersonnel.username\n" +
                            "FROM academicpersonnel \n" +
                            "INNER JOIN contracttype ON academicpersonnel.idContractType = contracttype.idContractType\n" +
                            "INNER JOIN educationalprogramrole ON academicpersonnel.username = educationalprogramrole.username\n" +
                            "WHERE educationalprogramrole.idRole = 3 AND educationalprogramrole.idEducationalProgram = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usableAcademicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram());
            ResultSet resultSet = preparedStatement.executeQuery();     
            
                while(resultSet.next()){
                    int idAcademicPersonnel = resultSet.getInt("academicpersonnel.idAcademicPersonnel");
                    String name = resultSet.getString("academicpersonnel.name");
                    String paternalSurname = resultSet.getString("paternalSurname");
                    String maternalSurname = resultSet.getString("maternalSurname");
                    String emailAddress = resultSet.getString("emailAddress");
                    String username = resultSet.getString("academicpersonnel.username");
                    String contractType = resultSet.getString("contracttype.name");
                    
                    ContractType newContractType = new ContractType(contractType);
                    User user = new User(username);
                    
                    AcademicPersonnel academicPersonnel = new AcademicPersonnel(idAcademicPersonnel, name, paternalSurname, maternalSurname, emailAddress);
                    academicPersonnel.setContractType(newContractType);
                    academicPersonnel.setUser(user);
                    academicPersonnels.add(academicPersonnel);                   
                }
           
        }catch(SQLException exception){
            academicPersonnels = null;
        } finally{
            databaseConnection.close();
        }
        return academicPersonnels;
    }
    
    public static int updateAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicpersonnel \n" +
                          "SET name = ?, paternalSurname = ?, maternalSurname = ?, emailAddress = ?, idContractType = ?\n" +
                          "WHERE idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            String name = academicPersonnel.getName();
            String paternalSurname = academicPersonnel.getPaternalSurname();
            String maternalSurname = academicPersonnel.getMaternalSurname();
            String emailAddress = academicPersonnel.getEmailAddress();
            int idContractType = academicPersonnel.getContractType().getIdContractType();   
            int idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, paternalSurname);
            preparedStatement.setString(3, maternalSurname);
            preparedStatement.setString(4, emailAddress);
            preparedStatement.setInt(5, idContractType);
            preparedStatement.setInt(6, idAcademicPersonnel);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static int updateAcademicPersonnelInformation(AcademicPersonnel academicPersonnel) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicpersonnel \n" +
                          "SET name = ?, paternalSurname = ?, maternalSurname = ?, emailAddress = ?\n" +
                          "WHERE idAcademicPersonnel = ?";
        try (Connection connection = databaseConnection.open()) {
            String name = academicPersonnel.getName();
            String paternalSurname = academicPersonnel.getPaternalSurname();
            String maternalSurname = academicPersonnel.getMaternalSurname();
            String emailAddress = academicPersonnel.getEmailAddress();
            int idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, paternalSurname);
            preparedStatement.setString(3, maternalSurname);
            preparedStatement.setString(4, emailAddress);
            preparedStatement.setInt(5, idAcademicPersonnel);
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