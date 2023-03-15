/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class QueryAcademicTutorshipGeneralReportFXMLController implements Initializable {

    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;
    @FXML
    private ComboBox<?> academicTutorshipSessionComboBox;
    @FXML
    private TextField academicTutorshipSessionDateTextField;
    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField educationalProgramTextField;
    @FXML
    private TableView<AcademicProblem> academicProblemTableView;
    @FXML
    private TableColumn educativeExperienceTableColumn;
    @FXML
    private TableColumn academicPersonnelTableColumn;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn numberOfStudentsTableColumn;
    @FXML
    private TableView<AcademicTutorshipReport> generalCommentTableView;
    @FXML
    private TableColumn generalCommentTableColumn;
    @FXML
    private TableColumn academicTutorTableColumn;

    private ObservableList<SchoolPeriod> schoolPeriods;
    private ObservableList<Integer> academicTutorshipSessions;
    private ObservableList<AcademicTutorshipReport> academicTutorshipReports;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    private int idSchoolPeriod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        schoolPeriods = FXCollections.observableArrayList();
        academicTutorshipSessions = FXCollections.observableArrayList();
        academicTutorshipSessionComboBox.disableProperty().bind(schoolPeriodComboBox.valueProperty().isNull());
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        loadSchoolPeriods();
    }

    private void loadSchoolPeriods() {
        ArrayList<SchoolPeriod> schoolPeriodsResultSet = SchoolPeriodDAO.getSchoolPeriods();
        if (schoolPeriodsResultSet.isEmpty()) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            schoolPeriods.addAll(schoolPeriodsResultSet);
            schoolPeriodComboBox.setItems(schoolPeriods);
            schoolPeriodComboBox.valueProperty().addListener((ObservableValue<? extends SchoolPeriod> observable, SchoolPeriod oldValue, SchoolPeriod newValue) -> {
                if (newValue != null) {
                    academicTutorshipSessions.clear();
                    academicTutorshipReports.clear();
                    clearTextField();
                    idSchoolPeriod = newValue.getIdSchoolPeriod();
                }
            });
        }
    }

    @FXML
    private void downloadButtonClick(ActionEvent event) {
    }

    @FXML
    private void printButtonClick(ActionEvent event) {
    }

    private void clearTextField() {
        numberOfStudentsAttendingTextField.clear();
        numberOfStudentsAtRiskTextField.clear();
        educationalProgramTextField.clear();
        academicTutorshipSessionDateTextField.clear();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }

}
