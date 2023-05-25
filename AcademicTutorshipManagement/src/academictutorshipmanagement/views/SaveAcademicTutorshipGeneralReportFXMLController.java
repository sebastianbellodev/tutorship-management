/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.documentformat.AcademicTutorshipGeneralReport;
import academictutorshipmanagement.utilities.documentformat.SignatureList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class SaveAcademicTutorshipGeneralReportFXMLController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField fileNameTextField;
    
    private static ArrayList<AcademicTutorshipReport> academicTutorshipReports;
    private static ArrayList<QueryAcademicTutorshipGeneralReportFXMLController.InnerAcademicProblem> academicProblems;
    private static AcademicTutorshipSession academicTutorshipSession;
    private static String numberOfStudentsAttending;
    private static String numberOfStudentsAtRisk;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {
        FileChooser save = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files", "* pdf");
        save.getExtensionFilters().add(extFilter);
        try {
            this.checkEmptyFields();
            save.setInitialFileName(this.fileNameTextField.getText() + ".pdf");
            File file = save.showSaveDialog(null);
            if (file != null) {
                this.saveFile(file);
            }
        } catch (DataFormatException dfException) {
            MessagesAlerts.showBlankFieldsAlert();
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
        stage.close();
    }

    private void saveFile(File file) {
        AcademicTutorshipGeneralReport academicTutorshipGeneralReport = new AcademicTutorshipGeneralReport();
        academicTutorshipGeneralReport.setAcademicProblems(academicProblems);
        academicTutorshipGeneralReport.setAcademicTutorshipReports(academicTutorshipReports);
        academicTutorshipGeneralReport.setAcademicTutorshipSession(academicTutorshipSession);
        academicTutorshipGeneralReport.setNumberOfStudentsAtRisk(numberOfStudentsAtRisk);
        academicTutorshipGeneralReport.setNumberOfStudentsAttending(numberOfStudentsAttending);
        try {
            academicTutorshipGeneralReport.generateDocument(file);
            MessagesAlerts.showAlert("Se ha guardado con exito el formato de Reporte General de Tutorías Académicas", Alert.AlertType.INFORMATION);
        } catch (IOException ex) {
            MessagesAlerts.showAlert("Ha ocurrido un error al momento de almacenar el archivo", Alert.AlertType.INFORMATION);
        }
        this.closeWindow();
    }

    private void checkEmptyFields() throws DataFormatException {
        if (this.fileNameTextField.getText().isEmpty()) {
            throw new DataFormatException();
        }
    }

    public static ArrayList<AcademicTutorshipReport> getAcademicTutorshipReports() {
        return academicTutorshipReports;
    }

    public static void setAcademicTutorshipReports(ArrayList<AcademicTutorshipReport> academicTutorshipReports) {
        SaveAcademicTutorshipGeneralReportFXMLController.academicTutorshipReports = academicTutorshipReports;
    }

    public static ArrayList<QueryAcademicTutorshipGeneralReportFXMLController.InnerAcademicProblem> getAcademicProblems() {
        return academicProblems;
    }

    public static void setAcademicProblems(ArrayList<QueryAcademicTutorshipGeneralReportFXMLController.InnerAcademicProblem> academicProblems) {
        SaveAcademicTutorshipGeneralReportFXMLController.academicProblems = academicProblems;
    }

    public static AcademicTutorshipSession getAcademicTutorshipSessionDate() {
        return academicTutorshipSession;
    }

    public static void setAcademicTutorshipSessionDate(AcademicTutorshipSession academicTutorshipSession) {
        SaveAcademicTutorshipGeneralReportFXMLController.academicTutorshipSession = academicTutorshipSession;
    }

    public static String getNumberOfStudentsAttending() {
        return numberOfStudentsAttending;
    }

    public static void setNumberOfStudentsAttending(String numberOfStudentsAttending) {
        SaveAcademicTutorshipGeneralReportFXMLController.numberOfStudentsAttending = numberOfStudentsAttending;
    }

    public static String getNumberOfStudentsAtRisk() {
        return numberOfStudentsAtRisk;
    }

    public static void setNumberOfStudentsAtRisk(String numberOfStudentsAtRisk) {
        SaveAcademicTutorshipGeneralReportFXMLController.numberOfStudentsAtRisk = numberOfStudentsAtRisk;
    }
    
    
}
