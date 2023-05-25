/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    Integer count = 0;

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
        configureStudentsUnassignedTableViewColumns();
        configureStudentsAssignedTableViewColumns();
        loadStudentsUnassigned();
        loadAcademicPersonnel();
        loadStudentsAssigned();
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
        ArrayList<Student> studentsResultSet = StudentDAO.getStudentsUnassigned(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
        studentsUnassigned.clear();
        for (Student student : studentsResultSet) {
            studentsUnassigned.add(new InnerStudent(student));
        }
        configureStudentsUnassignedTableViewCheckBoxes();
        studentsUnassignedTableView.setItems(studentsUnassigned);
    }

    private void configureStudentsUnassignedTableViewCheckBoxes() {
        studentsUnassigned.forEach(student -> {
            student.getAssignAcademicPersonnelCheckBox().setSelected(student.getAttendedBy().isSelected());
        });
    }

    private void loadAcademicPersonnel() {
        ArrayList<AcademicPersonnel> academicPersonnelResulSet
                = AcademicPersonnelDAO.getAcademicPersonnelByRole(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram(),
                        Constants.ACADEMIC_TUTOR_ID_ROLE);
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
            student.getAssignAcademicPersonnelCheckBox().setSelected(student.getAttendedBy().isSelected());
        });
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) this.academicPersonnelComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (academicPersonnelComboBox.getValue() != null) {
            assignStudent();
        }
    }

    private void assignStudent() {
        if (checkAssignStudentNumber() && count <= 30) {
            if (updateStudentsAcademicTutor()) {
                Utilities.showAlert("La información se registró correctamente en el sistema.",
                        Alert.AlertType.WARNING);
                loadStudentsUnassigned();
                loadAcademicPersonnel();
                studentsAssigned.clear();
                academicTutors.clear();
            } else {
                Utilities.showAlert("Seleccione al o los estudiantes que serán asignados al tutor académico.",
                        Alert.AlertType.ERROR);
            }
        } else {
            Utilities.showAlert("Número de estudiantes asignados excedido.",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean checkAssignStudentNumber() {
        boolean flag = false;
        AcademicPersonnel academicPersonnel = academicPersonnelComboBox.getValue();

        if (academicPersonnel != null) {
            flag = checkStudentNumber(academicPersonnel);
        }

        for (InnerStudent student : studentsUnassigned) {
            if (student.assignAcademicPersonnelCheckBox.isSelected()) {
                count++;
            }
        }

        if (!studentsAssigned.isEmpty()) {
            for (InnerStudent student : studentsAssigned) {
                if (student.assignAcademicPersonnelCheckBox.isSelected()) {
                    count++;
                }
            }
        }

        return flag;
    }

    private boolean checkStudentNumber(AcademicPersonnel academicPersonnel) {
        count = StudentDAO.checkAssignStudentNumber(academicPersonnel).get(0);
        if (count == null) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
            closeWindow();
        }
        return true;
    }

    private boolean updateStudentsAcademicTutor() {
        boolean selected = false;

        for (InnerStudent student : studentsUnassigned) {
            if (student.assignAcademicPersonnelCheckBox.isSelected() && updateStudentAcademicPersonnel(student)) {
                selected = true;
            }
        }

        if (!studentsAssigned.isEmpty()) {
            for (InnerStudent student : studentsAssigned) {
                if (student.assignAcademicPersonnelCheckBox.isSelected() && updateStudentAcademicPersonnel(student)) {
                    selected = true;
                }
            }
        }

        return selected;
    }

    private boolean updateStudentAcademicPersonnel(Student student) {
        boolean error = true;
        int responseCode = StudentDAO.updateStudentAcademicPersonnel(student, academicPersonnelComboBox.getValue());
        switch (responseCode) {
            case Constants.NO_DATABASE_CONNECTION_CODE:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                closeWindow();
                error = false;
                return error;
            default:
                error = true;
                return error;
        }
    }

    public class InnerStudent extends Student {

        boolean assignAcademicPersonnel;
        CheckBox assignAcademicPersonnelCheckBox;

        InnerStudent(Student student) {
            this.setName(student.getName());
            this.setPaternalSurname(student.getPaternalSurname());
            this.setMaternalSurname(student.getMaternalSurname());
            this.setRegistrationNumber(student.getRegistrationNumber());
            this.setAttendedBy(student.getAttendedBy().isSelected());
            this.setAtRisk(student.getAtRisk().isSelected());
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
