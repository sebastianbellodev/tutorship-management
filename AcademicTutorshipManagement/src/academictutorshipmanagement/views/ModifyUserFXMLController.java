/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.UserDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class ModifyUserFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationPasswordField;

    public String username;
    public String password;
    public Integer idAcademicPersonnel;

    AcademicPersonnel academicPersonnel = SessionInformation.getSessionInformation().getAcademicPersonnel();
    User user = SessionInformation.getSessionInformation().getUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameTextField.setText(academicPersonnel.getName());
        paternalSurnameTextField.setText(academicPersonnel.getPaternalSurname());
        maternalSurnameTextField.setText(academicPersonnel.getMaternalSurname());
        emailAddressTextField.setText(academicPersonnel.getEmailAddress());
        username = user.getUsername();
        usernameTextField.setText(username);
        password = user.getPassword();
        passwordField.setText(user.getPassword());
        idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
    }

    private boolean validateEmptyField() {
        if(this.password.contentEquals(passwordField.getText())) {
            return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty()
                || usernameTextField.getText().isEmpty()
                || passwordField.getText().isEmpty();
        }
        return nameTextField.getText().isEmpty()
                || paternalSurnameTextField.getText().isEmpty()
                || maternalSurnameTextField.getText().isEmpty()
                || emailAddressTextField.getText().isEmpty()
                || usernameTextField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || confirmationPasswordField.getText().isEmpty();
    }

    private boolean validateInvalidData() {
        Integer emailAddress = emailAddressTextField.getText().length();
        Integer name = nameTextField.getText().length();
        Integer paternalSurname = paternalSurnameTextField.getText().length();
        Integer maternalSurname = maternalSurnameTextField.getText().length();
        Integer username = usernameTextField.getText().length();
        Integer password = passwordField.getText().length();
        Integer passwordConfirmation = confirmationPasswordField.getText().length();
        boolean passwordValidation = passwordField.getText().contentEquals(confirmationPasswordField.getText());
        
        if(this.password.contentEquals(passwordField.getText())) {
            return emailAddressTextField.getText().startsWith(usernameTextField.getText())
                && emailAddressTextField.getText().endsWith("@uv.mx")
                && Utilities.compareGeneralFieldLength(emailAddress)
                && Utilities.compareGeneralFieldLength(name)
                && Utilities.compareGeneralFieldLength(paternalSurname)
                && Utilities.compareGeneralFieldLength(maternalSurname)
                && Utilities.compareGeneralFieldLength(username)
                && Utilities.comparePasswordFieldLength(password);
        }
        return emailAddressTextField.getText().startsWith(usernameTextField.getText())
                && emailAddressTextField.getText().endsWith("@uv.mx")
                && Utilities.compareGeneralFieldLength(emailAddress)
                && Utilities.compareGeneralFieldLength(name)
                && Utilities.compareGeneralFieldLength(paternalSurname)
                && Utilities.compareGeneralFieldLength(maternalSurname)
                && Utilities.compareGeneralFieldLength(username)
                && Utilities.comparePasswordFieldLength(password)
                && Utilities.comparePasswordFieldLength(passwordConfirmation)
                && passwordValidation;
    }

    private boolean checkUser() {
        if (username.equalsIgnoreCase(usernameTextField.getText())) {
            return false;
        } else {
            Integer responseCode = UserDAO.checkUser(usernameTextField.getText());
            return responseCode.equals(Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT);
        }
    }

    private void updateUserAcademicPersonnel() throws NoSuchAlgorithmException {
        if (validateEmptyField()) {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else if (!validateInvalidData()) {
            Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else if (checkUser()) {
            Utilities.showAlert("La información ingresada corresponde a un estudiante que ya se encuentra registrado en el sistema.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else {
            String name = nameTextField.getText();
            String paternalSurname = paternalSurnameTextField.getText();
            String maternalSurname = maternalSurnameTextField.getText();
            String emailAddress = emailAddressTextField.getText();
            AcademicPersonnel academicPersonnel = new AcademicPersonnel(name, paternalSurname, maternalSurname, emailAddress);
            academicPersonnel.setIdAcademicPersonnel(idAcademicPersonnel);
            String username = usernameTextField.getText();
            String password = Utilities.computeSHA256Hash(passwordField.getText());
            User user = new User(username, password);
            updateUserAcademicPersonnel(user, academicPersonnel);
        }
    }

    private void updateUserAcademicPersonnel(User user, AcademicPersonnel academicPersonnel) {
        Integer firstResponseCode = UserDAO.updateUser(user, this.username);
        Integer secondResponseCode = AcademicPersonnelDAO.updateAcademicPersonnelInformation(academicPersonnel);
        int responseCode = (firstResponseCode.equals(Constants.CORRECT_OPERATION_CODE )
                && secondResponseCode.equals(Constants.CORRECT_OPERATION_CODE )) 
                ? Constants.CORRECT_OPERATION_CODE 
                : Constants.NO_DATABASE_CONNECTION_CODE;
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                Utilities.showAlert("La información se actualizó correctamente en el sistema.",
                        Alert.AlertType.WARNING);
                closeWindow();
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                closeWindow();
                break;
        }
    }

    private void clearTextField() {
        nameTextField.clear();
        emailAddressTextField.clear();
        paternalSurnameTextField.clear();
        maternalSurnameTextField.clear();
        usernameTextField.clear();
        passwordField.clear();
        confirmationPasswordField.clear();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeWindow();
    }
    
    private void closeWindow() {
        Stage stage = (Stage) this.passwordField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) throws NoSuchAlgorithmException {
        updateUserAcademicPersonnel();
    }

}
