/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipReportDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipSessionDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class QueryAcademicTutorshipGeneralReportFXMLController implements Initializable {

    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;
    @FXML
    private ComboBox<Integer> academicTutorshipSessionComboBox;
    @FXML
    private TextField academicTutorshipSessionDateTextField;
    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField educationalProgramTextField;
    @FXML
    private TableView<InnerAcademicProblem> academicProblemTableView;
    @FXML
    private TableColumn educationalExperienceTableColumn;
    @FXML
    private TableColumn academicPersonnelTableColumn;
    @FXML
    private TableColumn titleTableColumn;
    @FXML
    private TableColumn numberOfStudentsTableColumn;
    @FXML
    private TableView<AcademicTutorshipReport> academicTutorshipReportTableView;
    @FXML
    private TableColumn generalCommentTableColumn;
    @FXML
    private TableColumn academicTutorTableColumn;

    private ObservableList<SchoolPeriod> schoolPeriods;
    private ObservableList<Integer> academicTutorshipSessions;
    private ObservableList<AcademicTutorshipReport> academicTutorshipReports;
    private ObservableList<InnerAcademicProblem> academicProblems;
    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    private ArrayList<AcademicTutorshipSession> academicTutorshipSessionsList;
    private ArrayList<AcademicTutorshipReport> academicTutorshipReportsList;
    private ArrayList<InnerAcademicProblem> academicProblemsList;
    private AcademicTutorshipSession academicTutorshipSession;
    private String numberOfStudentsAttending;
    private String numberOfStudentsAtRisk;
    private int idSchoolPeriod;
    private int sessionNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        schoolPeriods = FXCollections.observableArrayList();
        academicTutorshipSessions = FXCollections.observableArrayList();
        academicTutorshipReports = FXCollections.observableArrayList();
        academicProblems = FXCollections.observableArrayList();
        academicProblemsList = new ArrayList<>();
        academicTutorshipSessionComboBox.disableProperty().bind(schoolPeriodComboBox.valueProperty().isNull());
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        configureAcademicTutorshipReportTableView();
        configureAcademicProblemTableView();
        loadSchoolPeriods();
    }

    public void configureAcademicTutorshipReportTableView() {
        academicTutorTableColumn.setCellValueFactory(new PropertyValueFactory("academicPersonnel"));
        generalCommentTableColumn.setCellValueFactory(new PropertyValueFactory("generalComment"));
    }

    public void configureAcademicProblemTableView() {
        educationalExperienceTableColumn.setCellValueFactory(new PropertyValueFactory("innerEducationalExperience"));
        academicPersonnelTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerAcademicPersonnel"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        numberOfStudentsTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerNumberOfReports"));
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
                    academicProblemsList.clear();
                    academicTutorshipReports.clear();
                    academicProblems.clear();
                    clearTextField();
                    idSchoolPeriod = newValue.getIdSchoolPeriod();
                    loadacademicTutorshipSessions(idSchoolPeriod);
                }
            });
        }
    }

    private void loadacademicTutorshipSessions(int idSchoolPeriod) {
        ArrayList<AcademicTutorshipSession> academicTutorshipSessionResultSet = AcademicTutorshipSessionDAO.getAcademicTutorshipSessions(idSchoolPeriod);
        academicTutorshipSessionsList = academicTutorshipSessionResultSet;
        if (academicTutorshipSessionResultSet.isEmpty()) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            LocalDate today = LocalDate.now();
            for (AcademicTutorshipSession academicTutorshipSession : academicTutorshipSessionResultSet) {
                if (today.isAfter(today.parse(academicTutorshipSession.getStartDate().toString()))) {
                    academicTutorshipSessions.add(academicTutorshipSession.getSessionNumber());
                }
            }
            academicTutorshipSessionComboBox.setItems(academicTutorshipSessions);
            academicTutorshipSessionComboBox.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                if (newValue != null) {
                    academicTutorshipReports.clear();
                    academicProblemsList.clear();
                    academicProblems.clear();
                    clearTextField();
                    sessionNumber = newValue;
                    loadAcademicTutorshipReports(newValue, idSchoolPeriod);
                }
            });
        }
    }

    private void loadAcademicTutorshipReports(int sessionNumber, int idSchoolPeriod) {
        ArrayList<AcademicTutorshipReport> academicTutorshipReportsResultSet = AcademicTutorshipReportDAO.getAcademicTutorshipReportsForGeneral(sessionNumber, idSchoolPeriod);
        academicTutorshipReportsList = academicTutorshipReportsResultSet;
        if (academicTutorshipReportsResultSet.isEmpty()) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            academicTutorshipReports.clear();
            academicProblemsList.clear();
            clearTextField();
            academicTutorshipReports.addAll(academicTutorshipReportsResultSet);
            academicTutorshipReportTableView.setItems(academicTutorshipReports);
            configureAcademicTutorshipReportInformation(sessionNumber, academicTutorshipReportsResultSet);
            for (AcademicTutorshipReport academicTutorshipReport : academicTutorshipReports) {
                loadAcademicProblems(academicTutorshipReport.getIdAcademicTutorshipReport());
            }
        }
    }

    private void loadAcademicProblems(int idAcademicTutorshipReport) {
        ArrayList<AcademicProblem> academicProblemResulSet = AcademicProblemDAO.loadAcademicProblemsByAcademicTutorshipGeneralReport(idAcademicTutorshipReport);
        if (academicProblemResulSet == null) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            for (AcademicProblem academicProblem : academicProblemResulSet) {
                academicProblems.add(new InnerAcademicProblem(academicProblem));
                academicProblemsList.add(new InnerAcademicProblem(academicProblem));
            }
            academicProblemTableView.setItems(academicProblems);
        }
    }

    private void configureAcademicTutorshipReportInformation(int sessionNumber, ArrayList<AcademicTutorshipReport> academicTutorshipReports) {
        numberOfStudentsAttendingTextField.setText(String.valueOf(calculateNumberOfStudentsAttending(academicTutorshipReports)));
        numberOfStudentsAttending = numberOfStudentsAttendingTextField.getText();
        numberOfStudentsAtRiskTextField.setText(String.valueOf(calculateNumberOfStudentsAtRisk(academicTutorshipReports)));
        numberOfStudentsAtRisk = numberOfStudentsAtRiskTextField.getText();
        educationalProgramTextField.setText(academicPersonnel.getUser().getEducationalProgram().toString());
        for (AcademicTutorshipSession academicTutorshipSession : academicTutorshipSessionsList) {
            if (academicTutorshipSession.getSessionNumber() == sessionNumber) {
                academicTutorshipSessionDateTextField.setText(academicTutorshipSession.toString());
                this.academicTutorshipSession = academicTutorshipSession;
            }
        }
    }

    private int calculateNumberOfStudentsAttending(ArrayList<AcademicTutorshipReport> academicTutorshipReports) {
        int students = 0;
        for (AcademicTutorshipReport academicTutorshipReport : academicTutorshipReports) {
            students += academicTutorshipReport.getNumberOfStudentsAttending();
        }
        return students;
    }

    private int calculateNumberOfStudentsAtRisk(ArrayList<AcademicTutorshipReport> academicTutorshipReports) {
        int students = 0;
        for (AcademicTutorshipReport academicTutorshipReport : academicTutorshipReports) {
            students += academicTutorshipReport.getNumberOfStudentsAtRisk();
        }
        return students;
    }

    @FXML
    private void downloadButtonClick(ActionEvent event) {
        if (!academicTutorshipSessionDateTextField.getText().isEmpty()) {
            SaveAcademicTutorshipGeneralReportFXMLController.setAcademicProblems(academicProblemsList);
            SaveAcademicTutorshipGeneralReportFXMLController.setAcademicTutorshipReports(academicTutorshipReportsList);
            SaveAcademicTutorshipGeneralReportFXMLController.setAcademicTutorshipSessionDate(academicTutorshipSession);
            SaveAcademicTutorshipGeneralReportFXMLController.setNumberOfStudentsAtRisk(numberOfStudentsAtRisk);
            SaveAcademicTutorshipGeneralReportFXMLController.setNumberOfStudentsAttending(numberOfStudentsAttending);
            Stage stageMenuCoordinador = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root;
            try {
                root = loader.load(getClass().getResource("/academictutorshipmanagement/views/SaveAcademicTutorshipGeneralReportFXML.fxml").openStream());
                Scene scene = new Scene(root);
                stageMenuCoordinador.setScene(scene);
                stageMenuCoordinador.setTitle("Generar Formato de Reporte General de Tutorías Académicas");
                stageMenuCoordinador.alwaysOnTopProperty();
                stageMenuCoordinador.initModality(Modality.APPLICATION_MODAL);
                stageMenuCoordinador.show();
            } catch (IOException ex) {
                MessagesAlerts.showFailureLoadWindow();
            }
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialReportManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            TutorialReportManagementMenuFXMLController tutorialReportManagementMenuFXMLController = loader.getController();
            tutorialReportManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) academicTutorshipReportTableView.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Gestión de reportes tutoriales.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The TutorialReportManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    public class InnerAcademicProblem extends AcademicProblem {

        private InnerAcademicProblem(AcademicProblem academicProblem) {
            this.setTitle(academicProblem.getTitle());
            this.setDescription(academicProblem.getDescription());
            this.setNumberOfStudents(academicProblem.getNumberOfStudents());
            this.setIdAcademicProblem(academicProblem.getIdAcademicProblem());
            this.setAcademicOffering(academicProblem.getAcademicOffering());
        }

        public String getInnerAcademicPersonnel() {
            return this.getAcademicOffering().getAcademicPersonnel().toString();
        }

        public String getInnerEducationalExperience() {
            return this.getAcademicOffering().getEducationalExperience().toString();
        }

        public String getInnerNumberOfReports() {
            return Integer.toString(this.getNumberOfStudents());
        }
    }

}
