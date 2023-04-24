package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.ContractTypeDAO;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;import academictutorshipmanagement.model.dao.RoleDAO;
import academictutorshipmanagement.model.dao.UserDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.ContractType;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jonatan
 */
public class LogAcademicPersonnelFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private ComboBox cbb_contract;
    @FXML
    private ComboBox cbb_educationalProgram;    
    private ObservableList<Role> roles;
    @FXML
    private TableView rolesTableView;
    @FXML
    private TableColumn roleTableColumn;
    @FXML
    private TableColumn valueTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roles = FXCollections.observableArrayList();
        loadCombos();
        configureRolesTableView();
        loadRolesTableView();        
    }    
    
    private void loadCombos(){
        ContractTypeDAO contractTypeDAO = new ContractTypeDAO();
        EducationalProgramDAO educationalProgramDAO = new EducationalProgramDAO();
        
        ObservableList<EducationalProgram> educationalPrograms = educationalProgramDAO.getAllEducationalPrograms();
        ObservableList<ContractType> contractTypes = contractTypeDAO.getAllContractTypes();
        
        this.cbb_educationalProgram.setItems(educationalPrograms);
        this.cbb_contract.setItems(contractTypes);
        
    }
    
    private void loadRolesTableView(){
       RoleDAO roleDAO = new RoleDAO();
       roles = roleDAO.getAllRoles();     
       rolesTableView.setItems(roles);       
    }
    
    private void configureRolesTableView(){
        roleTableColumn.setCellValueFactory(new PropertyValueFactory("name"));  
        valueTableColumn.setCellValueFactory(new PropertyValueFactory("roleByCheckBox"));
    }
    private boolean validateEmptyField() {
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || usernameTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty()
                || cbb_contract.getSelectionModel().isEmpty()
                || cbb_educationalProgram.getSelectionModel().isEmpty();

    }
    
    private boolean validateInvalidData() {
        Integer username = usernameTextField.getText().length();
        Integer emailAddress = emailAddressTextField.getText().length();
        Integer name = nameTextField.getText().length();
        Integer paternalSurname = paternalSurnameTextField.getText().length();
        Integer maternalSurname = maternalSurnameTextField.getText().length();

        return emailAddressTextField.getText().endsWith("@uv.mx")
                && Utilities.compareGeneralFieldLength(username)
                && Utilities.compareGeneralFieldLength(name)
                && Utilities.compareGeneralFieldLength(paternalSurname)
                && Utilities.compareGeneralFieldLength(maternalSurname);  
    }
    
    private boolean checkUsername() {
        Integer responseCode = AcademicPersonnelDAO.checkAcademicPersonnel(usernameTextField.getText());
        return responseCode.equals(Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT);
    }
       
    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if(validateEmptyField()){
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }else if(!validateInvalidData()){
            Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        }else if(checkUsername()){
            Utilities.showAlert("La información ingresada corresponde a un personal academico que ya se encuentra registrado en el sistema.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        }else{
            String username = usernameTextField.getText();
            String name = nameTextField.getText();
            String paternalSurname = paternalSurnameTextField.getText();
            String maternalSurname = maternalSurnameTextField.getText();
            String emailAddress = emailAddressTextField.getText();
            ContractType contractType = (ContractType) cbb_contract.getValue();
            String password = Constants.GLOBAL_PASSWORD;
            
            EducationalProgram educationalProgram = (EducationalProgram) cbb_educationalProgram.getValue();
            User user = new User(username, password);
            
            AcademicPersonnel academicPersonnel = new AcademicPersonnel(name, paternalSurname, maternalSurname, emailAddress);
            academicPersonnel.setContractType(contractType);       
            
            logAcademicPersonnel(academicPersonnel, user, educationalProgram);
        }
    }
    
    private void clearTextField() {
        nameTextField.clear();
        usernameTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
        cbb_contract.valueProperty().set(null);
        cbb_educationalProgram.setValue(null);
    }
        
    private int logSelectedRoles(User user, EducationalProgram educationalProgram){   
        int numberRolesSelected = 0;
        for (Role role : roles) {            
            if (role.getRoleByCheckBox().isSelected()) {
                
                EducationalProgramDAO.logEducationalProgramByRole(user, educationalProgram, role); 
                numberRolesSelected++;
            }            
        }
        return numberRolesSelected;
    }
    
    //User -> EducationalProgram -> AcademicPersonnel
    private void logAcademicPersonnel(AcademicPersonnel academicPersonnel, User user, EducationalProgram educationalProgram) {
        int responseUserQuery = UserDAO.logUser(user);        
        switch (responseUserQuery) {
            case Constants.CORRECT_OPERATION_CODE:                
                int responseLogRole = logSelectedRoles(user, educationalProgram);
                int responseCode = AcademicPersonnelDAO.logAcademicPersonnel(academicPersonnel, user);
                if (responseLogRole != 0 && responseCode == Constants.CORRECT_OPERATION_CODE ) {
                    Utilities.showAlert("El personal academico se registró correctamente en el sistema.",
                            Alert.AlertType.WARNING);
                    clearTextField();
                } else {
                    Utilities.showAlert("Error al registrar el Personal Academico.\n\n"
                            + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                            Alert.AlertType.WARNING);
                }
                break;
            case Constants.INVALID_DATA_ENTERED_CODE:
                Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
