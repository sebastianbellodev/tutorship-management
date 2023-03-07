/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class LogStudentFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField registrationNumberTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField educationalProgramTextField;
    
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
        educationalProgramTextField.setText(academicPersonnel.getUser().getEducationalProgram().getName());
        educationalProgramTextField.setDisable(true);
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
    
    private boolean validateEmptyFields() {
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || registrationNumberTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty();
    }
    
    private boolean validateRegistrationNumber() {
        Integer registrationNumber = registrationNumberTextField.getText().length();
        Integer emailAddress = emailAddressTextField.getText().length();
        return registrationNumberTextField.getText().startsWith("S")
                && emailAddressTextField.getText().startsWith("zs")
                && emailAddressTextField.getText().endsWith(registrationNumberTextField.getText().replace("S", "") + "@estudiantes.uv.mx")
                && Utilities.compareStudentEmailAddressLength(emailAddress)
                && Utilities.compareRegistrationNumberLength(registrationNumber);
    }
    
    private boolean checkStudent() {
        Integer responseCode = StudentDAO.checkStudent(registrationNumberTextField.getText());
        return responseCode.equals(Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT);
    }
    
    private void clearTextField() {
        nameTextField.clear();
        registrationNumberTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (validateEmptyFields()) {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else if (!validateRegistrationNumber()) {
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
            EducationalProgram educationalProgram = academicPersonnel.getUser().getEducationalProgram();
            Student student = new Student(name, paternalSurname, maternalSurname, emailAddress);
            student.setRegistrationNumber(registrationNumber);
            student.setEducationalProgram(educationalProgram);
            logStudent(student);
        }
    }
    
    private void logStudent(Student student) {
        int responseCode = StudentDAO.logStudent(student);
        switch(responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("El estudiante se registró correctamente en el sistema.",
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
                break;
        }
    }
    
}
