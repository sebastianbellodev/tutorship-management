/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: April 18, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicTutorshipDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipReportDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipSessionDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicTutorship;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

    private ArrayList<AcademicTutorship> academicTutorships;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    private AcademicTutorshipSession academicTutorshipSession;
    private AcademicTutorship academicTutorship;

    private int idRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        academicTutorships = new ArrayList<>();
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        idRole = academicPersonnel.getUser().getRole().getIdRole();
    }

    @FXML
    private void logAcademicTutorshipReportButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTOR_ID_ROLE) {
            academicTutorshipSession = AcademicTutorshipSessionDAO.getCurrentAcademicTutorshipSession();
            int responseCode = academicTutorshipSession.getResponseCode();
            switch (responseCode) {
                case Constants.CORRECT_OPERATION_CODE:
                    validateClosingDateReportSubmission();
                    break;
                case Constants.INVALID_CURRENT_DATE_CODE:
                    Utilities.showAlert("La sesión de tutoría académica ha finalizado.\n\n"
                            + "Por favor, inténtelo más tarde.\n",
                            Alert.AlertType.INFORMATION);
                    break;
                default:
                    Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                            + "Por favor, inténtelo más tarde.\n",
                            Alert.AlertType.ERROR);
                    break;
            }
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    private void validateClosingDateReportSubmission() {
        Date currentDate = new Date();
        Date closingDateReportSubmission = academicTutorshipSession.getClosingDateReportSubmission();
        boolean isValid = currentDate.compareTo(closingDateReportSubmission) <= Constants.MINIUM_NUMBER_OF_DAYS_FOR_ACADEMIC_TUTORSHIP_REPORT_SUBMISSION;
        if (isValid) {
            loadAcademicTutorship();
        } else {
            Utilities.showAlert("La fecha de entrega para el Reporte de Tutorías Académicas ha finalizado.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    private void loadAcademicTutorship() {
        int idEducationalProgram = academicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram();
        int idAcademicTutorshipSession = academicTutorshipSession.getIdAcademicTutorshipSession();
        academicTutorship = AcademicTutorshipDAO.getAcademicTutorship(idEducationalProgram, idAcademicTutorshipSession);
        academicTutorship.setAcademicTutorshipSession(academicTutorshipSession);
        academicTutorships.add(academicTutorship);
        schoolPeriod.setAcademicTutorships(academicTutorships);
        validateAcademicTutorshipReportExistence();
    }

    private void validateAcademicTutorshipReportExistence() {
        int idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
        int idAcademicTutorship = academicTutorship.getIdAcademicTutorship();
        AcademicTutorshipReport academicTutorshipReport = AcademicTutorshipReportDAO.getAcademicTutorshipReport(idAcademicTutorship, idAcademicPersonnel);
        int responseCode = academicTutorshipReport.getResponseCode();
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            goToModifyAcademicTutorshipReport(academicTutorshipReport);
        } else {
            goToLogAcademicTutorshipReport();
        }
    }

    private void goToLogAcademicTutorshipReport() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogAcademicTutorshipReportFXML.fxml"));
        try {
            Parent root = loader.load();
            LogAcademicTutorshipReportFXMLController logAcademicTutorshipReportFXMLController = loader.getController();
            logAcademicTutorshipReportFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene logAcademicTutorshipReportView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(logAcademicTutorshipReportView);
            stage.setTitle("Registrar Reporte de Tutorías Académicas.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'LogAcademicTutorshipReportFXML.fxml' file could not be open. Please try again later.");
        }
    }

    private void goToModifyAcademicTutorshipReport(AcademicTutorshipReport academicTutorshipReport) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAcademicTutorshipReportFXML.fxml"));
        try {
            Parent root = loader.load();
            ModifyAcademicTutorshipReportFXMLController modifyAcademicTutorshipReportFXMLController = loader.getController();
            modifyAcademicTutorshipReportFXMLController.configureView(schoolPeriod, academicPersonnel, academicTutorshipReport);
            Scene modifyAcademicTutorshipReportView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(modifyAcademicTutorshipReportView);
            stage.setTitle("Editar Reporte de Tutorías Académicas.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'ModifyAcademicTutorshipReportFXML.fxml' file could not be open. Please try again later.");
        }
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
    private void queryAcademicTutorshipReportByAcademicTutorButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE || idRole == Constants.CAREER_HEAD_ID_ROLE) {
            goToQueryAcademicTutorshipReportByAcademicTutor();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    private void goToQueryAcademicTutorshipReportByAcademicTutor() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryAcademicTutorshipReportByAcademicTutorFXML.fxml"));
        try {
            Parent root = loader.load();
            QueryAcademicTutorshipReportByAcademicTutorFXMLController queryAcademicTutorshipReportByAcademicTutorFXMLController = loader.getController();
            queryAcademicTutorshipReportByAcademicTutorFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene logAcademicTutorshipReportView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(logAcademicTutorshipReportView);
            stage.setTitle("Consultar Reporte de Tutorías Académicas por tutor académico.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'QueryAcademicTutorshipReportByAcademicTutorFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void queryAcademicTutorshipGeneralReportButtonClick(ActionEvent actionEvent) {
        if (idRole == Constants.ACADEMIC_TUTORSHIP_COORDINATOR_ID_ROLE || idRole == Constants.CAREER_HEAD_ID_ROLE) {
            goToQueryAcademicTutorshipGeneralReport();
        } else {
            Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
        }
    }
    
    private void goToQueryAcademicTutorshipGeneralReport() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryAcademicTutorshipGeneralReportFXML.fxml"));
        try {
            Parent root = loader.load();
            QueryAcademicTutorshipGeneralReportFXMLController queryAcademicTutorshipGeneralReportFXMLController = loader.getController();
            queryAcademicTutorshipGeneralReportFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene logAcademicTutorshipReportView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(logAcademicTutorshipReportView);
            stage.setTitle("Consultar Reporte General de Tutorías Académicas.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'QueryAcademicTutorshipGeneralReportFXML.fxml' file could not be open. Please try again later.");
        }
    }
    
}