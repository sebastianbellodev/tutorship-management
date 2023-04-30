package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.ContractTypeDAO;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;
import academictutorshipmanagement.model.dao.RoleDAO;
import academictutorshipmanagement.model.dao.UserDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.ContractType;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jonat
 */
public class ModifyAcademicPersonnelFXMLController implements Initializable {

    @FXML
    private ComboBox cbb_academicPersonnel;
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
    @FXML
    private TableColumn roleTableColumn;
    @FXML
    private TableColumn valueTableColumn;
    private ObservableList<Role> roles;
    private AcademicPersonnel actualAacademicPersonnel;
    private AcademicPersonnel usableAcademicPersonnelCombo;
    @FXML
    private TableView rolesTableView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void configureView(AcademicPersonnel academicPersonnel){
        this.actualAacademicPersonnel = academicPersonnel; 
        roles = FXCollections.observableArrayList();       
        loadAcademicPersonnelCombo();
        configureRolesTableView();
    }
    
    private void configureRolesTableView(){
        roleTableColumn.setCellValueFactory(new PropertyValueFactory("name"));  
        valueTableColumn.setCellValueFactory(new PropertyValueFactory("roleByCheckBox"));
    }
    
    private void loadAcademicPersonnelCombo(){
        AcademicPersonnelDAO academicPersonnelDAO = new AcademicPersonnelDAO();
        ObservableList<AcademicPersonnel> academicPersonnels = academicPersonnelDAO.getAllAcademicPersonnelsByRole(actualAacademicPersonnel);
        this.cbb_academicPersonnel.setItems(academicPersonnels);
        cbb_academicPersonnel.valueProperty().addListener((ov, oldValue, newValue) -> {
                    usableAcademicPersonnelCombo = (AcademicPersonnel) newValue;     
                    loadAcademicPersonnelInformation(usableAcademicPersonnelCombo);
                    loadCombos();
                    loadRolesTableView();   
                    enableFields();
            });       
    }
    
    private void loadAcademicPersonnelInformation(AcademicPersonnel academicPersonnel){
        nameTextField.setText(academicPersonnel.getName());
        cbb_contract.setValue(academicPersonnel.getContractType().getName());
        emailAddressTextField.setText(academicPersonnel.getEmailAddress());
        paternalSurnameTextField.setText(academicPersonnel.getPaternalSurname());
        maternalSurnameTextField.setText(academicPersonnel.getMaternalSurname());
        cbb_educationalProgram.setValue(actualAacademicPersonnel.getUser().getEducationalProgram().getName());
        usernameTextField.setText(academicPersonnel.getUser().getUsername());
    }

    
    private void loadRolesTableView(){
        RoleDAO roleDAO = new RoleDAO();
        int available = Constants.ACADEMIC_PERSONNEL_AVAILABLE;
        String username = usableAcademicPersonnelCombo.getUser().getUsername();
        ObservableList<Role> roleByAcademicPersonnel = roleDAO.getAllRolesByAcademicPersonnel(username, available);
        roles = roleDAO.getAllRoles();

        roles.forEach(role -> {
            boolean isEnabled = false;
            role.setAssociatedTo(isEnabled);
            roleByAcademicPersonnel.forEach(rolesByAcademicPersonnel -> {
                int idRole = rolesByAcademicPersonnel.getIdRole();
                boolean isAssociatedTo = role.getIdRole()== idRole;
                if (isAssociatedTo) {
                    role.setAssociatedTo(isAssociatedTo);
                }
            });
        });

       rolesTableView.setItems(roles);       
    }
     
    private void loadCombos(){
        ContractTypeDAO contractTypeDAO = new ContractTypeDAO();
        EducationalProgramDAO educationalProgramDAO = new EducationalProgramDAO();
        
        ObservableList<EducationalProgram> educationalPrograms = educationalProgramDAO.getAllEducationalPrograms();
        ObservableList<ContractType> contractTypes = contractTypeDAO.getAllContractTypes();
        
        this.cbb_educationalProgram.setItems(educationalPrograms);
        this.cbb_contract.setItems(contractTypes);        
    }
    
    private void enableFields(){
        nameTextField.setEditable(true);        
        emailAddressTextField.setEditable(true);
        paternalSurnameTextField.setEditable(true);
        maternalSurnameTextField.setEditable(true);
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
        }else{
            String username = usableAcademicPersonnelCombo.getUser().getUsername();
            String name = nameTextField.getText();
            String paternalSurname = paternalSurnameTextField.getText();
            String maternalSurname = maternalSurnameTextField.getText();
            String emailAddress = emailAddressTextField.getText();
            int idAcademicPersonnel = usableAcademicPersonnelCombo.getIdAcademicPersonnel();
            
            ContractType contractType = (ContractType) cbb_contract.getValue();            
            
            EducationalProgram educationalProgram = (EducationalProgram) cbb_educationalProgram.getValue();
            User user = new User(username);
            
            AcademicPersonnel academicPersonnel = new AcademicPersonnel( idAcademicPersonnel, name, paternalSurname, maternalSurname, emailAddress);
            academicPersonnel.setContractType(contractType);       
            
            updateAcademicPersonnel(academicPersonnel, user, educationalProgram, event);
        }
    }
    
    private void updateAcademicPersonnel(AcademicPersonnel academicPersonnel, User user, EducationalProgram educationalProgram, ActionEvent event){        
        updateSelectedRoles(user, educationalProgram);
        int responseCode = AcademicPersonnelDAO.updateAcademicPersonnel(academicPersonnel);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("El personal academico se modificó correctamente en el sistema.",
                        Alert.AlertType.WARNING);
                clearTextField();
                closeWindow(event);
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

    
    private void updateSelectedRoles(User user, EducationalProgram educationalProgram) {
        RoleDAO roleDAO = new RoleDAO();

        int unavailable = Constants.ACADEMIC_PERSONNEL_UNAVAILABLE;
        int available = Constants.ACADEMIC_PERSONNEL_AVAILABLE;
        String username = usableAcademicPersonnelCombo.getUser().getUsername();
        ObservableList<Role> roleByAcademicPersonnel = roleDAO.getAllRolesByAcademicPersonnel(username, available);

        for (Role roleView : roles) {
            if (Check(roleByAcademicPersonnel, roleView)) {
                if (!roleView.getRoleByCheckBox().isSelected()) {
                    EducationalProgramDAO.updateEducationalProgramByRole(user, educationalProgram, roleView, unavailable);
                }
            } else {
                if (roleView.getRoleByCheckBox().isSelected()) {
                    EducationalProgramDAO.logEducationalProgramByRole(user, educationalProgram, roleView, available);
                }
            }
        }
    }

    private boolean Check(ObservableList<Role> roleByAcademicPersonnel, Role roles){        
        Boolean check = false;  
        
        for(Role academicPersonnelRole: roleByAcademicPersonnel )           
            if (roles.getIdRole() == academicPersonnelRole.getIdRole()) {
                check = true;
            }
        return check;
    }       
    
    private void closeWindow(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeWindow(event);
    }

    
    private void clearTextField() {
        cbb_academicPersonnel.valueProperty().set(null);
        nameTextField.clear();
        usernameTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
        cbb_contract.valueProperty().set(null);
        cbb_educationalProgram.valueProperty().set(null);
        rolesTableView.setItems(null);
    }
    
    private boolean validateEmptyField() {
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || usernameTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty()
                || cbb_contract.getSelectionModel().isEmpty()
                || cbb_educationalProgram.getSelectionModel().isEmpty()
                || validateEmptyCheckBoxes();

    }
    
    private boolean validateEmptyCheckBoxes(){
        Boolean check = false; 
        int unselectedBoxes = 0;
        
        for(Role roleView : roles){
            if(!roleView.getRoleByCheckBox().isSelected()){
                unselectedBoxes++;
            }
        }
        
        if(unselectedBoxes == 3){
            check = true;
        }
        
        return check;
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
}
