/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.model.pojo.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        students = StudentDAO.getStudentsByEducationalProgram(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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
        educationalProgramTextField.setText(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getName());
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
