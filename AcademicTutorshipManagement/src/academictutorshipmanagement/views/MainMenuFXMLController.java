/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.MessagesAlerts;
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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuFXMLController implements Initializable {

    @FXML
    private Label academicPersonnelLabel;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schoolPeriod = SchoolPeriodDAO.getCurrentSchoolPeriod();
        int responseCode = schoolPeriod.getResponseCode();
        if (responseCode == Constants.NO_DATABASE_CONNECTION_CODE) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        SessionInformation.getSessionInformation().setSchoolPeriod(schoolPeriod);
    }

    public void configureView(User user) {
        String username = user.getUsername();
        academicPersonnel = AcademicPersonnelDAO.getAcademicPersonnelByUser(username);
        academicPersonnel.setUser(user);
        academicPersonnelLabel.setText(academicPersonnelLabel.getText() + academicPersonnel + ".");
        SessionInformation.getSessionInformation().setAcademicPersonnel(academicPersonnel);
        SessionInformation.getSessionInformation().getAcademicPersonnel().setUser(user);
    }

    @FXML
    private void tutorialReportManagementButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialReportManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            TutorialReportManagementMenuFXMLController tutorialReportManagementMenuFXMLController = loader.getController();
            tutorialReportManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de reportes tutoriales.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The TutorialReportManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void tutorialSessionAdministrationButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialSessionAdministrationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            TutorialSessionAdministrationMenuFXMLController tutorialSessionAdministrationMenuFXMLController = loader.getController();
            tutorialSessionAdministrationMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Administración de sesiones de tutoría.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The TutorialSessionAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void followUpOnAcademicProblemsButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryFollowUpOnAcademicProblemsListFXML.fxml"));
        try{
            Parent root = loader.load();
            QueryFollowUpOnAcademicProblemsListFXMLController controller = loader.getController();
            Scene queryFollowUpOnAcademicProblemsList = new Scene(root);
            Stage stage = (Stage) this.academicPersonnelLabel.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsList);
            controller.configureView();
            stage.setTitle("Lista de Problemáticas Académicas");
            stage.show();
        }catch(IOException ioException){
            MessagesAlerts.showFailureLoadWindow();
        }
    }

    @FXML
    private void studentManagementButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            StudentManagementMenuFXMLController studentManagementMenuFXMLController = loader.getController();
            studentManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de estudiantes.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The StudentManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void educationalProgramAdministrationButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EducationalProgramAdministrationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            EducationalProgramAdministrationMenuFXMLController educationalProgramAdministrationMenuFXMLController  = loader.getController();
            educationalProgramAdministrationMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Administración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void specificFormatGenerationButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SpecificFormatGenerationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            SpecificFormatGenerationMenuFXMLController specificFormatGenerationMenuFXMLController = loader.getController();
            specificFormatGenerationMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Generación de formatos específicos.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The SpecificFormatGenerationMenu.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void logOutButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
        try {
            Parent root = loader.load();
            Scene logInView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(logInView);
            stage.setTitle("Iniciar sesión.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'LogInFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void modifyUserButton(ActionEvent event) {
        try {
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/academictutorshipmanagement/views/ModifyUserFXML.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuCoordinador.setScene(scene);
            stageMenuCoordinador.setTitle("Editar usuario");
            stageMenuCoordinador.alwaysOnTopProperty();
            stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
            stageMenuCoordinador.show();
        } catch (IOException exception) {
            System.err.println("The 'ModifyUserFXML.fxml' file could not be open. Please try again later.");
        }
    }

}