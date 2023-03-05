/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicTutorshipSessionDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
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

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    private AcademicTutorshipSession academicTutorshipSession;

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
    private void logAcademicTutorshipReportButtonClick(ActionEvent event) {
        if (idRol == Constants.ACADEMIC_TUTOR_ID_ROLE) {
            academicTutorshipSession = AcademicTutorshipSessionDAO.getCurrentAcademicTutorshipSession();
            int responseCode = academicTutorshipSession.getResponseCode();
            switch (responseCode) {
                case Constants.CORRECT_OPERATION_CODE:
                    if (validateClosingDateReportSubmission()) {
                        ArrayList<AcademicTutorshipSession> academicTutorshipSessions = new ArrayList<>();
                        academicTutorshipSessions.add(academicTutorshipSession);
                        schoolPeriod.setAcademicTutorshipSessions(academicTutorshipSessions);
                        goToLogAcademicTutorshipReport();
                    } else {
                        Utilities.showAlert("La fecha de entrega para el Reporte de Tutorías Académicas ha finalizado.\n\n"
                                + "Por favor, inténtelo más tarde.\n",
                                Alert.AlertType.INFORMATION);
                    }
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

    private boolean validateClosingDateReportSubmission() {
        Date currentDate = new Date();
        Date closingDateReportSubmission = academicTutorshipSession.getClosingDateReportSubmission();
        return currentDate.compareTo(closingDateReportSubmission) <= Constants.MINIUM_NUMBER_OF_DAYS_FOR_ACADEMIC_TUTORSHIP_REPORT_SUBMISSION;
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
            System.err.println("The 'LogAcademicTutorshipReport.fxml' file could not be open. Please try again later.");
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