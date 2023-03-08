/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class QueryAcademicTutorshipReportByAcademicTutorFXMLController implements Initializable {

    @FXML
    private TableView<Student> academicTutorshipSessionTableView;
    @FXML
    private TableColumn registrationNumberTableColumn;
    @FXML
    private TableColumn studentTableColumn;
    @FXML
    private TableColumn studentAttendingTableColumn;
    @FXML
    private TableColumn studentAtRiskTableColumn;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField academicTutorshipSessionDateTextField;
    @FXML
    private TextField educativeProgramTextField;
    @FXML
    private ComboBox<AcademicTutorshipSession> sessionNumberComboBox;
    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;
    @FXML
    private ComboBox<AcademicPersonnel> academicPersonnelComboBox;
    @FXML
    private TextArea generalCommentTextArea;
    
    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        educativeProgramTextField.setText(academicPersonnel.getUser().getEducationalProgram().getName());
        academicTutorshipSessionTableView.setDisable(true);
        numberOfStudentsAtRiskTextField.setDisable(true);
        numberOfStudentsAttendingTextField.setDisable(true);
        academicTutorshipSessionDateTextField.setDisable(true);
        educativeProgramTextField.setDisable(true);
        sessionNumberComboBox.setDisable(true);
        academicPersonnelComboBox.setDisable(true);
        generalCommentTextArea.setDisable(true);
    }
    
    @FXML
    private void cancelButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialReportManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            TutorialReportManagementMenuFXMLController tutorialReportManagementMenuFXMLController = loader.getController();
            tutorialReportManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicTutorshipSessionTableView.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de reportes tutoriales.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The TutorialReportManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void printButtonClick(ActionEvent event) {
    }

    @FXML
    private void downloadButtonClick(ActionEvent event) {
    }

    @FXML
    private void queryAcademicProblemButtonClick(ActionEvent event) {
    }
    
}
