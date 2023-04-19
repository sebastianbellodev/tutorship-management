package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.ContractType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContractTypeDAO {
    
    public ObservableList<ContractType> getAllContractTypes(){
        ObservableList<ContractType> contractTypes = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (Connection connection = databaseConnection.open()) {
            String query = "SELECT  idContractType, name FROM contracttype";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();     
            while(resultSet.next()){
                int idContractType = resultSet.getInt("idContractType");
                String name = resultSet.getString("name");
                
                ContractType contractType = new ContractType(idContractType, name);
                contractTypes.add(contractType);
            }
            
        }catch(SQLException exception){
            System.out.print(exception.getMessage());
        }finally{
            databaseConnection.close();
        }
        return contractTypes;
    }
    
}
