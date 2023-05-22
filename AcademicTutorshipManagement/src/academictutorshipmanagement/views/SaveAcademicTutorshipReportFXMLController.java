/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.documentformat.AcademicTutorshipReport;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class SaveAcademicTutorshipReportFXMLController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField fileNameTextField;
    
    private static ArrayList<QueryAcademicTutorshipReportByAcademicTutorFXMLController.InnerStudent> students;
    private static AcademicTutorshipSession academicTutorshipSession;
    private static String numberOfStudentsAttending;
    private static String numberOfStudentsAtRisk;
    private static String generalComment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        AcademicTutorshipReport academicTutorshipReport = new AcademicTutorshipReport();
        academicTutorshipReport.setAcademicTutorshipSession(academicTutorshipSession);
        academicTutorshipReport.setGeneralComment(generalComment);
        academicTutorshipReport.setNumberOfStudentsAtRisk(numberOfStudentsAtRisk);
        academicTutorshipReport.setNumberOfStudentsAttending(numberOfStudentsAttending);
        academicTutorshipReport.setStudents(students);
        try {
            academicTutorshipReport.generateDocument(file);
            MessagesAlerts.showAlert("Se ha guardado con exito el formato de Reporte de Tutorías Académicas", Alert.AlertType.INFORMATION);
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

    public static ArrayList<QueryAcademicTutorshipReportByAcademicTutorFXMLController.InnerStudent> getStudents() {
        return students;
    }

    public static AcademicTutorshipSession getAcademicTutorshipSession() {
        return academicTutorshipSession;
    }

    public static void setAcademicTutorshipSession(AcademicTutorshipSession academicTutorshipSession) {
        SaveAcademicTutorshipReportFXMLController.academicTutorshipSession = academicTutorshipSession;
    }
    
    public static void setStudents(ArrayList<QueryAcademicTutorshipReportByAcademicTutorFXMLController.InnerStudent> students) {
        SaveAcademicTutorshipReportFXMLController.students = students;
    }

    public static String getNumberOfStudentsAttending() {
        return numberOfStudentsAttending;
    }

    public static void setNumberOfStudentsAttending(String numberOfStudentsAttending) {
        SaveAcademicTutorshipReportFXMLController.numberOfStudentsAttending = numberOfStudentsAttending;
    }

    public static String getNumberOfStudentsAtRisk() {
        return numberOfStudentsAtRisk;
    }

    public static void setNumberOfStudentsAtRisk(String numberOfStudentsAtRisk) {
        SaveAcademicTutorshipReportFXMLController.numberOfStudentsAtRisk = numberOfStudentsAtRisk;
    }

    public static String getGeneralComment() {
        return generalComment;
    }

    public static void setGeneralComment(String generalComment) {
        SaveAcademicTutorshipReportFXMLController.generalComment = generalComment;
    }
    
}
