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
import javafx.stage.Stage;

public class StudentManagementMenuFXMLController implements Initializable {

    @FXML
    private Button backButton;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    
    private int idRol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        idRol = academicPersonnel.getUser().getRole().getIdRole();
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

    @FXML
    private void logStudentButtonClick(ActionEvent event) {
        if (idRol == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToLogStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    public void goToLogStudent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogStudentFXML.fxml"));
        try {
            Parent root = loader.load();
            LogStudentFXMLController logStudentFXMLController = loader.getController();
            logStudentFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Registrar estudiante");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void queryStudentButtonClick(ActionEvent event) {
        if (idRol == Constants.CAREER_HEAD_ID_ROLE) {
            goToQueryStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    public void goToQueryStudent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryStudentFXML.fxml"));
        try {
            Parent root = loader.load();
            QueryStudentFXMLController queryStudentFXMLController = loader.getController();
            queryStudentFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Consultar estudiante");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void modifyStudentButtonClick(ActionEvent event) {
        if (idRol == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToModifyStudent();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    public void goToModifyStudent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyStudentFXML.fxml"));
        try {
            Parent root = loader.load();
            ModifyStudentFXMLController modifyStudentFXMLController = loader.getController();
            modifyStudentFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Editar estudiante");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void assignStudentButtonClick(ActionEvent event) {
        if (idRol == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE) {
            goToAssignStudentToAcademicTutor();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    public void goToAssignStudentToAcademicTutor() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignStudentToAcademicTutorFXML.fxml"));
        try {
            Parent root = loader.load();
            AssignStudentToAcademicTutorFXMLController assignStudentToAcademicTutorFXMLController = loader.getController();
            assignStudentToAcademicTutorFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Asignar tutor académico a estudiante");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

}