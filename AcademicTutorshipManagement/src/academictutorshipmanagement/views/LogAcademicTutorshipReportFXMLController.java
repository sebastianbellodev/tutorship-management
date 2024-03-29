/**
 * Name(s) of the programmer(s): Armando Omar Obando Muñóz and María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: April 20, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IAcademicProblem;
import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipReportDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.AcademicTutorship;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogAcademicTutorshipReportFXMLController implements Initializable, IAcademicProblem {

    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField schoolPeriodTextField;
    @FXML
    private TextField academicTutorshipSessionTextField;
    @FXML
    private TextField educationalProgramTextField;
    @FXML
    private TextField sessionNumberTextField;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn registrationNumberTableColumn;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn paternalSurnameTableColumn;
    @FXML
    private TableColumn maternalSurnameTableColumn;
    @FXML
    private TableColumn attendedByTableColumn;
    @FXML
    private TableColumn atRiskTableColumn;
    @FXML
    private TextArea generalCommentTextArea;

    private ArrayList<AcademicProblem> academicProblems;

    private ObservableList<Student> students;

    private SchoolPeriod schoolPeriod;
    private AcademicTutorship academicTutorship;
    private AcademicPersonnel academicPersonnel;
    private EducationalProgram educationalProgram;
    private AcademicTutorshipReport academicTutorshipReport;

    private int idAcademicPersonnel;
    private int idAcademicTutorshipReport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        academicProblems = new ArrayList<>();
        students = FXCollections.observableArrayList();
        configureTableViewColumns();
    }

    private void configureTableViewColumns() {
        registrationNumberTableColumn.setCellValueFactory(new PropertyValueFactory("registrationNumber"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        paternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("paternalSurname"));
        maternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("maternalSurname"));
        attendedByTableColumn.setCellValueFactory(new PropertyValueFactory("attendedBy"));
        atRiskTableColumn.setCellValueFactory(new PropertyValueFactory("atRisk"));
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        academicTutorship = schoolPeriod.getAcademicTutorships().get(Constants.FIRST_ACADEMIC_TUTORSHIP_SESSION_INDEX);
        this.academicPersonnel = academicPersonnel;
        educationalProgram = academicPersonnel.getUser().getEducationalProgram();
        configureAcademicTutorshipReportInformation();
        loadStudentsByAcademicPersonnel();
    }

    private void configureAcademicTutorshipReportInformation() {
        educationalProgramTextField.setText(educationalProgram.toString());
        schoolPeriodTextField.setText(schoolPeriod.toString());
        AcademicTutorshipSession academicTutorshipSession = academicTutorship.getAcademicTutorshipSession();
        academicTutorshipSessionTextField.setText(academicTutorshipSession.toString());
        sessionNumberTextField.setText(String.valueOf(academicTutorshipSession.getSessionNumber()));
    }

    private void loadStudentsByAcademicPersonnel() {
        try {
            int idEducationalProgram = educationalProgram.getIdEducationalProgram();
            idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
            ArrayList<Student> studentsResultSet = StudentDAO.getStudentsByAcademicPersonnel(idEducationalProgram, idAcademicPersonnel);
            if (!studentsResultSet.isEmpty()) {
                students.addAll(studentsResultSet);
                configureTableViewCheckBoxes();
                studentsTableView.setItems(students);
            } else {
                Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                        + "Por favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } catch (SQLException sqlException) {
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }

    private void configureTableViewCheckBoxes() {
        students.forEach(student -> {
            boolean isDisabled = false;
            student.getAttendedBy().setDisable(isDisabled);
            student.getAtRisk().setDisable(isDisabled);
        });
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        String generalComment = generalCommentTextArea.getText();
        int numberOfStudentsAttending = calculateNumberOfStudentsAttending();
        int numberOfStudentsAtRisk = calculateNumberOfStudentsAtRisk();
        numberOfStudentsAttendingTextField.setText(String.valueOf(numberOfStudentsAttending));
        numberOfStudentsAtRiskTextField.setText(String.valueOf(numberOfStudentsAtRisk));
        academicTutorshipReport = new AcademicTutorshipReport();
        academicTutorshipReport.setGeneralComment(generalComment);
        academicTutorshipReport.setNumberOfStudentsAttending(numberOfStudentsAttending);
        academicTutorshipReport.setNumberOfStudentsAttending(numberOfStudentsAttending);
        academicTutorshipReport.setAcademicPersonnel(academicPersonnel);
        academicTutorshipReport.setAcademicTutorship(academicTutorship);
        logAcademicTutorshipReport();
    }

    private int calculateNumberOfStudentsAttending() {
        int numberOfStudentsAttending = 0;
        for (Student student : students) {
            boolean isAttendedBy = student.getAttendedBy().isSelected();
            if (isAttendedBy) {
                numberOfStudentsAttending++;
            }
            student.setAttendedBy(isAttendedBy);
        }
        return numberOfStudentsAttending;
    }

    private int calculateNumberOfStudentsAtRisk() {
        int numberOfStudentsAtRisk = 0;
        for (Student student : students) {
            boolean isAtRisk = student.getAtRisk().isSelected();
            if (isAtRisk) {
                numberOfStudentsAtRisk++;
            }
            student.setAtRisk(isAtRisk);
        }
        return numberOfStudentsAtRisk;
    }

    private void logAcademicTutorshipReport() {
        int responseCode = AcademicTutorshipReportDAO.logAcademicTutorshipReport(academicTutorshipReport);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            int idAcademicTutorship = academicTutorship.getIdAcademicTutorship();
            idAcademicTutorshipReport = AcademicTutorshipReportDAO.getAcademicTutorshipReport(idAcademicTutorship, idAcademicPersonnel).getIdAcademicTutorshipReport();
            logAcademicProblemsByAcademicTutorshipReport();
            logStudentsByAcademicTutorshipReport();
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        goToTutorialReportManagementMenu();
    }

    private void logAcademicProblemsByAcademicTutorshipReport() {
        academicProblems.forEach(academicProblem -> {
            AcademicProblemDAO.logAcademicProblemByAcademicTutorshipReport(academicProblem, idAcademicTutorshipReport);
        });
    }

    private void logStudentsByAcademicTutorshipReport() {
        students.forEach(student -> {
            StudentDAO.logStudentByAcademicTutorshipReport(student, idAcademicTutorshipReport);
        });
    }

    private void goToTutorialReportManagementMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialReportManagementMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            TutorialReportManagementMenuFXMLController tutorialReportManagementMenuFXMLController = loader.getController();
            tutorialReportManagementMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene tutorialReportManagementMenuView = new Scene(root);
            Stage stage = (Stage) numberOfStudentsAttendingTextField.getScene().getWindow();
            stage.setScene(tutorialReportManagementMenuView);
            stage.setTitle("Gestión de reportes tutoriales.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'TutorialReportManagementMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void logAcademicProblemButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogAcademicProblemFXML.fxml"));
        try {
            Parent root = loader.load();
            LogAcademicProblemFXMLController logAcademicProblemFXMLController = loader.getController();
            int numberOfStudentsByAcademicPersonnel = students.size();
            logAcademicProblemFXMLController.configureView(this, schoolPeriod, educationalProgram, numberOfStudentsByAcademicPersonnel);
            Stage stage = new Stage();
            Scene logAcademicProblemView = new Scene(root);
            stage.setScene(logAcademicProblemView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar problemática académica.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("The LogAcademicProblemFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void viewAcademicProblemsButtonClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAcademicProblemListFXML.fxml"));
        try {
            Parent root = loader.load();
            ModifyAcademicProblemListFXMLController modifyAcademicProblemListFXMLController = loader.getController();
            int numberOfStudentsByAcademicPersonnel = students.size();
            modifyAcademicProblemListFXMLController.configureView(this, schoolPeriod, educationalProgram, numberOfStudentsByAcademicPersonnel, academicProblems);
            Stage stage = new Stage();
            Scene logAcademicProblemView = new Scene(root);
            stage.setScene(logAcademicProblemView);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar problemática académica.");
            stage.showAndWait();
        } catch (IOException exception) {
            System.err.println("The ModifyAcademicProblemListFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent actionEvent) {
        goToTutorialReportManagementMenu();
    }

    @Override
    public void configureAcademicProblem(AcademicProblem academicProblem) {
        if(academicProblem.getIdAcademicProblem() == Constants.PRIMARY_KEY_OF_NON_EXISTENT_RECORD_IN_DATABASE){
            academicProblems.add(academicProblem);
        }else{
            int index = 0;
            for(AcademicProblem academic: academicProblems){
                if(academic.getIdAcademicProblem() == academicProblem.getIdAcademicProblem()){
                    academicProblems.set(index, academicProblem);
                    return;
                }
            }
        }
    }

}