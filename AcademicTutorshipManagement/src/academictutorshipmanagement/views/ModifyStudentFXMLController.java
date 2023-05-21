/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class ModifyStudentFXMLController implements Initializable {

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

    private String registrationNumber;
    
    @FXML
    private Button acceptButton;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrationNumberTextField.editableProperty().bind(registrationNumberTextField.textProperty().isNotEmpty());
        nameTextField.editableProperty().bind(registrationNumberTextField.textProperty().isNotEmpty());
        paternalSurnameTextField.editableProperty().bind(registrationNumberTextField.textProperty().isNotEmpty());
        maternalSurnameTextField.editableProperty().bind(registrationNumberTextField.textProperty().isNotEmpty());
        emailAddressTextField.editableProperty().bind(registrationNumberTextField.textProperty().isNotEmpty());
        students = StudentDAO.getStudentsByEducationalProgram(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean validateEmptyField() {
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || registrationNumberTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty();
    }
    
    private boolean validateInvalidData() {
        Integer registrationNumber = registrationNumberTextField.getText().length();
        Integer emailAddress = emailAddressTextField.getText().length();
        Integer name = nameTextField.getText().length();
        Integer paternalSurname = paternalSurnameTextField.getText().length();
        Integer maternalSurname = maternalSurnameTextField.getText().length();
        
        return registrationNumberTextField.getText().startsWith("S")
                && emailAddressTextField.getText().startsWith("zs")
                && emailAddressTextField.getText().endsWith(registrationNumberTextField.getText().replace("S", "") + "@estudiantes.uv.mx")
                && Utilities.compareStudentEmailAddressLength(emailAddress)
                && Utilities.compareRegistrationNumberLength(registrationNumber)
                && Utilities.compareGeneralFieldLength(name)
                && Utilities.compareGeneralFieldLength(paternalSurname)
                && Utilities.compareGeneralFieldLength(maternalSurname);
    }
    
    private boolean checkStudent() {
        if(registrationNumber.equalsIgnoreCase(registrationNumberTextField.getText())) {
            return false;
        } else {
            Integer responseCode = StudentDAO.checkStudent(registrationNumberTextField.getText());
            return responseCode.equals(Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT);
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if(!registrationNumberTextField.textProperty().isEmpty().get()) {
            updateStudent();
        }
    }

    private void updateStudent() {
        if (validateEmptyField()) {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else if (!validateInvalidData()) {
            Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        } else if (checkStudent()) {
            Utilities.showAlert("La información ingresada corresponde a un estudiante que ya se encuentra registrado en el sistema.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        } else {
            String registrationNumber = registrationNumberTextField.getText();
            String name = nameTextField.getText();
            String paternalSurname = paternalSurnameTextField.getText();
            String maternalSurname = maternalSurnameTextField.getText();
            String emailAddress = emailAddressTextField.getText();
            Student student = new Student(name, paternalSurname, maternalSurname, emailAddress);
            student.setRegistrationNumber(registrationNumber);
            updateStudent(student);
            queryTextField.clear();
            students.clear();
            students = StudentDAO.getStudentsByEducationalProgram(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
        }
    }
    
    private void updateStudent(Student student) {
        int responseCode = StudentDAO.updateStudent(student, registrationNumber);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se actualizó correctamente en el sistema.",
                        Alert.AlertType.WARNING);
                clearTextField();
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                clearTextField();
                break;
        }
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
        registrationNumber = student.getRegistrationNumber();
    }

    private void clearTextField() {
        nameTextField.clear();
        registrationNumberTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
        educationalProgramTextField.clear();
    }

    @FXML
    private void deleteButtonClick(ActionEvent event) {
        if(!registrationNumberTextField.textProperty().isEmpty().get()) {
            deleteStudent();
        }
    }
    
    private void deleteStudent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("¿Desea eliminar la información del sistema?\n\n Esta acción no podrá revertirse.\n");
        Optional<ButtonType> button = alert.showAndWait();
        if(button.get() == ButtonType.OK) {
            deleteStudent(registrationNumberTextField.getText());
            queryTextField.clear();
            students.clear();
            students = StudentDAO.getStudentsByEducationalProgram(SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
        }
    }
    
    private void deleteStudent(String registrationNumber) {
        int responseCode = StudentDAO.deleteStudent(registrationNumber);
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se eliminó correctamente en el sistema.",
                        Alert.AlertType.WARNING);
                clearTextField();
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
                clearTextField();
                break;
        }
    }
}
