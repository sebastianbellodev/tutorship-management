/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: May 18, 2023.
 * Date of update: May 18, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.UserDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecoverPasswordFXMLController implements Initializable {

    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField randomSixDigitCodeTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationPasswordField;
    @FXML
    private Button acceptButton;

    private AcademicPersonnel academicPersonnel;
    private User user;
    private String randomSixDigitCode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void sendRandomSixDigitCodeButtonClick(ActionEvent event) throws IOException {
        String emailAddress = emailAddressTextField.getText();
        if (!emailAddress.isEmpty()) {
            academicPersonnel = AcademicPersonnelDAO.getAcademicPersonnel(emailAddress);
            if (academicPersonnel != null) {
                int idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
                user = UserDAO.getUser(idAcademicPersonnel);
                sendRandomSixDigitCode();
            } else {
                Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                emailAddressTextField.clear();
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private void sendRandomSixDigitCode() throws IOException {
        randomSixDigitCode = Utilities.generateRandomSixDigitCode();
        String subject = "Recuperar contraseña";
        String message = "Su código aleatorio de seis dígitos es " + randomSixDigitCode + ".";
        String emailAddress = academicPersonnel.getEmailAddress();
        boolean isSent = Utilities.sendEmail(subject, message, emailAddress);
        if (isSent) {
            Utilities.showAlert("El código aleatorio de seis dígitos se envío correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            acceptButton.setDisable(false);
        } else {
            Utilities.showAlert("No hay conexión con el servicio de envío de correo electrónico.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
            closePopUpWindow();
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) throws NoSuchAlgorithmException {
        if (!validateEmptyFields()) {
            String randomSixDigitCode = randomSixDigitCodeTextField.getText();
            String password = passwordField.getText();
            String passwordConfirmation = confirmationPasswordField.getText();
            if (this.randomSixDigitCode.equals(randomSixDigitCode) && password.equals(passwordConfirmation)) {
                password = Utilities.computeSHA256Hash(password);
                user.setPassword(password);
                updateUser();
            } else {
                Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return randomSixDigitCodeTextField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || confirmationPasswordField.getText().isEmpty();
    }

    private void updateUser() {
        int responseCode = UserDAO.updateUser(user);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se modificó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        closePopUpWindow();
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) emailAddressTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }

}