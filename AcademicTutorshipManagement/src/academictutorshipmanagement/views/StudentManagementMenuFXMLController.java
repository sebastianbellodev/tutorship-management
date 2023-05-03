/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 04, 2023.
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentManagementMenuFXMLController implements Initializable {

    @FXML
    private Button backButton;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    private int idRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        idRole = academicPersonnel.getUser().getRole().getIdRole();
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
    private void logStudentButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToLogStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void goToLogStudent() {
        try {
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/academictutorshipmanagement/views/LogStudentFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuCoordinador.setScene(scene);
            stageMenuCoordinador.setTitle("Registrar estudiante");
            stageMenuCoordinador.alwaysOnTopProperty();
            stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
            stageMenuCoordinador.show();
        } catch (IOException exception) {
            System.err.println("The 'LogStudentFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void queryStudentButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.CAREER_HEAD_ID_ROLE) {
            goToQueryStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void goToQueryStudent() {
        try {
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/academictutorshipmanagement/views/QueryStudentFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuCoordinador.setScene(scene);
            stageMenuCoordinador.setTitle("Consultar estudiante");
            stageMenuCoordinador.alwaysOnTopProperty();
            stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
            stageMenuCoordinador.show();
        } catch (IOException exception) {
            System.err.println("The 'QueryStudentFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void modifyStudentButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToModifyStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    public void goToModifyStudent() {
        try {
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/academictutorshipmanagement/views/ModifyStudentFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuCoordinador.setScene(scene);
            stageMenuCoordinador.setTitle("Editar estudiante");
            stageMenuCoordinador.alwaysOnTopProperty();
            stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
            stageMenuCoordinador.show();
        } catch (IOException exception) {
            System.err.println("The 'ModifyStudentFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void assignStudentButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToAssignStudentToAcademicTutor();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void goToAssignStudentToAcademicTutor() {
        try {
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/academictutorshipmanagement/views/AssignStudentToAcademicTutorFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuCoordinador.setScene(scene);
            stageMenuCoordinador.setTitle("Asignar tutor académico a estudiante");
            stageMenuCoordinador.alwaysOnTopProperty();
            stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
            stageMenuCoordinador.show();
        } catch (IOException exception) {
            System.err.println("The 'AssignStudentToAcademicTutorFXML.fxml' file could not be open. Please try again later.");
        }
    }

}
