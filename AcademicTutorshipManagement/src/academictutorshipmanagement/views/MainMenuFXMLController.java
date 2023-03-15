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
import javafx.stage.Stage;

public class MainMenuFXMLController implements Initializable {

    @FXML
    private Label academicPersonnelLabel;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        schoolPeriod = SchoolPeriodDAO.getCurrentSchoolPeriod();
        int responseCode = schoolPeriod.getResponseCode();
        if (responseCode == Constants.NO_DATABASE_CONNECTION_CODE) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    public void configureView(User user) {
        String username = user.getUsername();
        academicPersonnel = AcademicPersonnelDAO.getAcademicPersonnelByUser(username);
        academicPersonnel.setUser(user);
        academicPersonnelLabel.setText(academicPersonnelLabel.getText() + academicPersonnel + ".");
        SessionInformation sessionInformation = getSessionInformation();
        sessionInformation.setAcademicPersonnel(AcademicPersonnelDAO.getAcademicPersonnelByUser(username));
        sessionInformation.getAcademicPersonnel().setUser(user);
    }

    @FXML
    private void tutorialReportManagementButtonClick(ActionEvent event) {
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
    private void tutorialSessionAdministrationButtonClick(ActionEvent event) {
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
    private void followUpOnAcademicProblemsButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryFollowUpOnAcademicProblemsListFXML.fxml"));
        try{
            Parent root = loader.load();
            Scene queryFollowUpOnAcademicProblemsListView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsListView);
            stage.show();
        }catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    @FXML
    private void studentManagementButtonClick(ActionEvent event) {
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
    private void educationalProgramAdministrationButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EducationalProgramAdministrationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            EducationalProgramAdministrationMenuFXMLController educationalProgramAdministrationMenuFXMLController  = loader.getController();
            educationalProgramAdministrationMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicPersonnelLabel.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Admnistración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void specificFormatGenerationButtonClick(ActionEvent event) {
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
    private void logOutButtonClick(ActionEvent event) {
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

}