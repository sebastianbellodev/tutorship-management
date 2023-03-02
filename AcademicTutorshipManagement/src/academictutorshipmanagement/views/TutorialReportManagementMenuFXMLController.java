/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 02, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TutorialReportManagementMenuFXMLController implements Initializable {

    @FXML
    private Button backButton;

    private AcademicPersonnel academicPersonnel;
    private SchoolPeriod schoolPeriod;
    private int idRol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(AcademicPersonnel academicPersonnel, SchoolPeriod schoolPeriod) {
        this.academicPersonnel = academicPersonnel;
        this.schoolPeriod = schoolPeriod;
        idRol = academicPersonnel.getUser().getRole().getIdRole();
    }

    @FXML
    private void logAcademicTutorshipReportButtonClick(ActionEvent event) {
        if (idRol == Constants.ACADEMIC_TUTOR_ID_ROLE) {
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            MainMenuFXMLController mainMenuFXMLController = loader.getController();
            User user = academicPersonnel.getUser();
            mainMenuFXMLController.configureView(user);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

}