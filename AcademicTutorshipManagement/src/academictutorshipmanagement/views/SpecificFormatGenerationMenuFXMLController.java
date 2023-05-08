/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.Roles;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpecificFormatGenerationMenuFXMLController implements Initializable {

    @FXML
    private Button backButton;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    @FXML
    private Button buttonSignatureList;
    @FXML
    private Button assistanceButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
    }

    @FXML
    private void backButtonClick(ActionEvent actionEvent) {
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

    @FXML
    private void buttonSignatureListClick(ActionEvent event) {
        SessionInformation sessionInformation = getSessionInformation();
        if(sessionInformation.getUser().getRole().getIdRole() == Roles.ACADEMIC_TUTOR_ID_ROLE){
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            try {
                root = loader.load(getClass().getResource("/academictutorshipmanagement/views/SaveSignatureFormatListFXML.fxml").openStream());
                Scene scene = new Scene(root);
                stageMenuCoordinador.setScene(scene);
                stageMenuCoordinador.setTitle("Generar Formato de Lista de Firmas");
                stageMenuCoordinador.alwaysOnTopProperty();
                stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
                stageMenuCoordinador.show();
            } catch (IOException ex) {
                MessagesAlerts.showFailureLoadWindow();
            }
        }else{
            MessagesAlerts.showAccessDenied();
        }

    }

    @FXML
    private void assistanceButtonClick(ActionEvent event) {
        SessionInformation sessionInformation = getSessionInformation();
        if(sessionInformation.getUser().getRole().getIdRole() == Roles.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE ||  sessionInformation.getUser().getRole().getIdRole() == Roles.CAREER_HEAD_ID_ROLE){
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            try {
                root = loader.load(getClass().getResource("/academictutorshipmanagement/views/SaveAsistanceFormatFXML.fxml").openStream());
                Scene scene = new Scene(root);
                stageMenuCoordinador.setScene(scene);
                stageMenuCoordinador.setTitle("Generar Formato de Asistencia");
                stageMenuCoordinador.alwaysOnTopProperty();
                stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
                stageMenuCoordinador.show();
            } catch (IOException ex) {
                MessagesAlerts.showFailureLoadWindow();
            }
        }else{
            MessagesAlerts.showAccessDenied();
        }
    
    
    }
    
}