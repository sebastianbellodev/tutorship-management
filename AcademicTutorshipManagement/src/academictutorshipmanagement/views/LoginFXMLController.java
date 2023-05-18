/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: May 18, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IEducationalProgram;
import academictutorshipmanagement.interfaces.IRole;
import academictutorshipmanagement.model.dao.UserDAO;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginFXMLController implements Initializable, IEducationalProgram, IRole {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void logInButtonClick(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        if (!validateEmptyFields()) {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            password = Utilities.computeSHA256Hash(password);
            logIn(username, password);
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty();
    }

    private void logIn(String username, String password) {
        user = UserDAO.logIn(username, password);
        user.setPassword(passwordField.getText());
        int responseCode = user.getResponseCode();
        switch (responseCode) {
            case Constants.CORRECT_OPERATION_CODE:
                goToSelectEducationalProgramRole();
                break;
            case Constants.INVALID_DATA_ENTERED_CODE:
                Utilities.showAlert("Los datos ingresados son inválidos.\n\n"
                        + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                passwordField.clear();
                break;
            default:
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    private void goToSelectEducationalProgramRole() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectEducationalProgramRoleFXML.fxml"));
        try {
            Parent root = loader.load();
            SelectEducationalProgramRoleFXMLController selectEducationalProgramRoleFXMLController = loader.getController();
            selectEducationalProgramRoleFXMLController.configureView(this, this, user);
            Scene selectEducationalProgramRoleView = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(selectEducationalProgramRoleView);
            stage.setResizable(false);
            stage.setTitle("Seleccionar programa educativo y rol.");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("The 'SelectEducationalProgramRoleFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void forgottenPasswordOnMouseClicked(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RecoverPasswordFXML.fxml"));
        try {
            Parent root = loader.load();
            Scene recoverPasswordView = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(recoverPasswordView);
            stage.setResizable(false);
            stage.setTitle("Recuperar contraseña.");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("The 'RecoverPasswordFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @Override
    public void configureEducationalProgram(EducationalProgram educationalProgram) {
        user.setEducationalProgram(educationalProgram);
    }

    @Override
    public void configureRole(Role role) {
        user.setRole(role);
        goToMainMenu();
    }

    private void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            MainMenuFXMLController mainMenuFXMLController = loader.getController();
            mainMenuFXMLController.configureView(user);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

}