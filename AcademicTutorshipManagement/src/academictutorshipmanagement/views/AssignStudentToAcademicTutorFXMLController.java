/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class AssignStudentToAcademicTutorFXMLController implements Initializable {

    @FXML
    private ComboBox<AcademicPersonnel> academicPersonnelComboBox;
    @FXML
    private TableView<InnerStudent> studentsUnassignedTableView;
    @FXML
    private TableColumn registrationNumberUnassignedTableColumn;
    @FXML
    private TableColumn nameUnassignedTableColumn;
    @FXML
    private TableColumn assingUnassignedTableColumn;
    @FXML
    private ComboBox<AcademicPersonnel> academicTutorComboBox;
    @FXML
    private TableView<InnerStudent> studentsAssignedTableView;
    @FXML
    private TableColumn registrationNumberAssignedTableColumn;
    @FXML
    private TableColumn nameAssignedTableColumn;
    @FXML
    private TableColumn assignAssignedTableColumn;
    
    private ObservableList<InnerStudent> studentsUnassigned;
    private ObservableList<InnerStudent> studentsAssigned;
    private ObservableList<AcademicPersonnel> academicPersonnels;
    private ObservableList<AcademicPersonnel> academicTutors;
    
    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentsUnassigned = FXCollections.observableArrayList();
        studentsAssigned = FXCollections.observableArrayList();
        academicPersonnels = FXCollections.observableArrayList();
        academicTutors = FXCollections.observableArrayList();
        academicTutorComboBox.disableProperty().bind(academicPersonnelComboBox.valueProperty().isNull());
        loadStudentsAssigned();
    }    
    
    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        configureStudentsUnassignedTableViewColumns();
        configureStudentsAssignedTableViewColumns();
        loadStudentsUnassigned();
        loadAcademicPersonnel();
    }
    
    private void configureStudentsUnassignedTableViewColumns() {
        registrationNumberUnassignedTableColumn.setCellValueFactory(new PropertyValueFactory("registrationNumber"));
        nameUnassignedTableColumn.setCellValueFactory(new PropertyValueFactory("innerStudent"));
        assingUnassignedTableColumn.setCellValueFactory(new PropertyValueFactory("assignAcademicPersonnelCheckBox"));
    }
    
    private void configureStudentsAssignedTableViewColumns() {
        registrationNumberAssignedTableColumn.setCellValueFactory(new PropertyValueFactory("registrationNumber"));
        nameAssignedTableColumn.setCellValueFactory(new PropertyValueFactory("innerStudent"));
        assignAssignedTableColumn.setCellValueFactory(new PropertyValueFactory("assignAcademicPersonnelCheckBox"));
    }
    
    private void loadStudentsUnassigned() {
        ArrayList<Student> studentsResultSet = StudentDAO.getStudentsUnassigned(academicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram());
        studentsUnassigned.clear();
        for (Student student : studentsResultSet) {
            studentsUnassigned.add(new InnerStudent(student));
        }
        configureStudentsUnassignedTableViewCheckBoxes();
        studentsUnassignedTableView.setItems(studentsUnassigned);
    }
    
    private void configureStudentsUnassignedTableViewCheckBoxes() {
        studentsUnassigned.forEach(student -> {
            student.getAssignAcademicPersonnelCheckBox().setSelected(student.isAttendedBy());
        });
    }
    
    private void loadAcademicPersonnel() {
        ArrayList<AcademicPersonnel> academicPersonnelResulSet
                = AcademicPersonnelDAO.getAcademicPersonnelByRole(Constants.ACADEMIC_TUTOR_ID_ROLE,
                        academicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram());
        academicPersonnels.clear();
        if (academicPersonnelResulSet.isEmpty()) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            academicPersonnels.addAll(academicPersonnelResulSet);
            academicPersonnelComboBox.setItems(academicPersonnels);
            academicPersonnelComboBox.valueProperty().addListener((ObservableValue<? extends AcademicPersonnel> observable, AcademicPersonnel oldValue, AcademicPersonnel newValue) -> {
                if (newValue != null) {
                    academicTutors.clear();
                    studentsAssigned.clear();
                    academicTutors.addAll(academicPersonnelResulSet);
                    academicTutors.remove(newValue);
                    academicTutorComboBox.setItems(academicTutors);
                }
            });
        }
    }
    
    private void loadStudentsAssigned() {
        academicTutorComboBox.valueProperty().addListener((ObservableValue<? extends AcademicPersonnel> observable, AcademicPersonnel oldValue, AcademicPersonnel newValue) -> {
            if (newValue != null) {
                ArrayList<Student> studentsResultSet = StudentDAO.getStudentsByAcademicPersonnel(newValue.getIdAcademicPersonnel());
               studentsAssigned.clear();
                for (Student student : studentsResultSet) {
                    studentsAssigned.add(new InnerStudent(student));
                }
                configureStudentsAssignedTableViewCheckBoxes();
                studentsAssignedTableView.setItems(studentsAssigned);
            }
        });
    }
    
    private void configureStudentsAssignedTableViewCheckBoxes() {
        studentsAssigned.forEach(student -> {
            student.getAssignAcademicPersonnelCheckBox().setSelected(student.isAttendedBy());
        });
    }
    
    @FXML
    private void cancelButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            StudentManagementMenuFXMLController studentManagementMenuFXMLController = loader.getController();
            studentManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) studentsAssignedTableView.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de estudiantes.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The StudentManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if(academicPersonnelComboBox.getValue() != null) {
            assignStudent();
        }
    }
    
    private void assignStudent() {
        if(updateStudentsAcademicTutor()) {
            Utilities.showAlert("La información se registró correctamente en el sistema.",
                    Alert.AlertType.WARNING);
            loadStudentsUnassigned();
            loadAcademicPersonnel();
            studentsAssigned.clear();
            academicTutors.clear();
        } else{
            Utilities.showAlert("Seleccione al o los estudiantes que serán asignados al tutor académico.",
                    Alert.AlertType.ERROR);
        }
    }
    
    private boolean updateStudentsAcademicTutor() {
        boolean selected = false;

        for (InnerStudent student : studentsUnassigned) {
            if (student.assignAcademicPersonnelCheckBox.isSelected()) {
                StudentDAO.updateStudentAcademicPersonnel(student, academicPersonnelComboBox.getValue());
                selected = true;
            }
        }

        if (!studentsAssigned.isEmpty()) {
            for (InnerStudent student : studentsAssigned) {
                if (student.assignAcademicPersonnelCheckBox.isSelected()) {
                    StudentDAO.updateStudentAcademicPersonnel(student, academicPersonnelComboBox.getValue());
                    selected = true;
                }
            }
        }

        return selected;
    }
    
    public class InnerStudent extends Student {
       private boolean assignAcademicPersonnel;
       private CheckBox assignAcademicPersonnelCheckBox;
        
        private InnerStudent(Student student) {
            this.setName(student.getName());
            this.setPaternalSurname(student.getPaternalSurname());
            this.setMaternalSurname(student.getMaternalSurname());
            this.setRegistrationNumber(student.getRegistrationNumber());
            this.setAtRisk(student.isAtRisk());
            this.setAtRiskCheckBox(student.getAtRiskCheckBox());
            this.setAttendedBy(student.isAttendedBy());
            this.setAttendedByCheckBox(student.getAttendedByCheckBox());
            assignAcademicPersonnel = false;
            assignAcademicPersonnelCheckBox = new CheckBox();
        }
        
        public String getInnerStudent() {
            return this.getName() + " " + this.getPaternalSurname() + " " + this.getMaternalSurname();
        }
        
        public CheckBox getAssignAcademicPersonnelCheckBox() {
            return assignAcademicPersonnelCheckBox;
        }
    }
}