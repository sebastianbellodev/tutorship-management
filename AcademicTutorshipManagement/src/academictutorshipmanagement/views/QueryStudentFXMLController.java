/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class QueryStudentFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField registrationNumberTextField;
    @FXML
    private TextField educationalProgramTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField queryTextField;

    private ArrayList<Student> students;

    private AcademicPersonnel academicPersonnel;
    private SchoolPeriod schoolPeriod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        students = StudentDAO.getStudentsByEducationalProgram(academicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram());
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            StudentManagementMenuFXMLController studentManagementMenuFXMLController = loader.getController();
            studentManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de estudiantes.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The StudentManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }

    @FXML
    private void queryButtonClick(ActionEvent event) {
        clearTextField();
        for (Student student : students) {
            if (student.getRegistrationNumber().equalsIgnoreCase(queryTextField.getText())) {
                getStudent(student);
            }
        }
    }

    private void getStudent(Student student) {
        nameTextField.setText(student.getName());
        registrationNumberTextField.setText(student.getRegistrationNumber());
        emailAddressTextField.setText(student.getEmailAddress());
        paternalSurnameTextField.setText(student.getPaternalSurname());
        maternalSurnameTextField.setText(student.getMaternalSurname());
        educationalProgramTextField.setText(academicPersonnel.getUser().getEducationalProgram().getName());
    }

    private void clearTextField() {
        nameTextField.clear();
        registrationNumberTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
        educationalProgramTextField.clear();
    }
}
