package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.RoleDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jonatan
 */
public class QueryAcademicPersonnelFXMLController implements Initializable {

    @FXML
    private ComboBox cbb_academicPersonnel;
    @FXML
    private TableView rolesTableView;
    @FXML
    private TableColumn roleTableColumn;
    @FXML
    private TableView studentTableView;
    @FXML
    private TableColumn registrationNumberTableColumn;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn paternalSurnameTableColumn;
    @FXML
    private TableColumn maternalSurnameTableColumn;
    @FXML
    private TableColumn emailAddressTableColumn;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField contractTypeTextField;
    @FXML
    private TextField educationalProgramTextField;
    private ObservableList<Role> roles;
    private ObservableList<Student> students;
    private AcademicPersonnel usableAcademicPersonnelCombo;
    private AcademicPersonnel actualAacademicPersonnel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void configureView(AcademicPersonnel academicPersonnel){
        this.actualAacademicPersonnel = academicPersonnel; 
        roles = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        loadAcademicPersonnelCombo();
        configureRolesTableView();
        configureStudentsTableView();
    }
    
    private void loadAcademicPersonnelCombo(){
        AcademicPersonnelDAO academicPersonnelDAO = new AcademicPersonnelDAO();
        ObservableList<AcademicPersonnel> academicPersonnels = academicPersonnelDAO.getAllAcademicPersonnelsByRole(actualAacademicPersonnel);
        this.cbb_academicPersonnel.setItems(academicPersonnels);
        cbb_academicPersonnel.valueProperty().addListener((ov, oldValue, newValue) -> {
                    usableAcademicPersonnelCombo = (AcademicPersonnel) newValue;     
                    loadAcademicPersonnelInformation(usableAcademicPersonnelCombo);
                    loadStudentsTableView();
                    loadRolsTableView(usableAcademicPersonnelCombo.getUser().getUsername());                    
            });       
    }
    
    private void loadAcademicPersonnelInformation(AcademicPersonnel academicPersonnel){
        nameTextField.setText(academicPersonnel.getName());
        contractTypeTextField.setText(academicPersonnel.getContractType().getName());
        emailAddressTextField.setText(academicPersonnel.getEmailAddress());
        paternalSurnameTextField.setText(academicPersonnel.getPaternalSurname());
        maternalSurnameTextField.setText(academicPersonnel.getMaternalSurname());
        educationalProgramTextField.setText(actualAacademicPersonnel.getUser().getEducationalProgram().getName());
    }
    
    private void loadRolsTableView(String username){
       RoleDAO roleDAO = new RoleDAO();
       int available = Constants.ACADEMIC_PERSONNEL_AVAILABLE;
       roles = roleDAO.getAllRolesByAcademicPersonnel(username, available );     
       rolesTableView.setItems(roles);
    }
    
    private void loadStudentsTableView(){  
        int idEducationalProgram = actualAacademicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram();
        int idAcademicPersonnel = usableAcademicPersonnelCombo.getIdAcademicPersonnel();       
        students = StudentDAO.getAllStudentStundesByAcademicPersonnel(idEducationalProgram, idAcademicPersonnel);        
        studentTableView.setItems(students);
    }
    
    private void configureRolesTableView(){
        roleTableColumn.setCellValueFactory(new PropertyValueFactory("name"));         
    }
     
    private void configureStudentsTableView(){ 
        registrationNumberTableColumn.setCellValueFactory(new PropertyValueFactory("registrationNumber"));    
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));    
        paternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("paternalSurname"));     
        maternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("maternalSurname"));    
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory("emailAddress"));    
    }
    
    @FXML
    private void backButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    
}
