/**
 * Name(s) of the programmer(s): Armando Omar Obando Muñóz and María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: May 25, 2023.
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
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

public class ModifyAcademicTutorshipReportFXMLController implements Initializable, IAcademicProblem {

    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField educationalProgramTextField;
    @FXML
    private TextField schoolPeriodTextField;
    @FXML
    private TextField academicTutorshipSessionTextField;
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

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel, AcademicTutorshipReport academicTutorshipReport) {
        this.schoolPeriod = schoolPeriod;
        academicTutorship = schoolPeriod.getAcademicTutorships().get(Constants.FIRST_ACADEMIC_TUTORSHIP_SESSION_INDEX);
        this.academicPersonnel = academicPersonnel;
        educationalProgram = academicPersonnel.getUser().getEducationalProgram();
        this.academicTutorshipReport = academicTutorshipReport;
        configureAcademicTutorshipReportInformation();
        loadStudentsByAcademicTutorshipReport();
        loadAcademicProblemsByAcademicTutorshipReport();
    }

    private void configureAcademicTutorshipReportInformation() {
        numberOfStudentsAttendingTextField.setText(String.valueOf(academicTutorshipReport.getNumberOfStudentsAttending()));
        numberOfStudentsAtRiskTextField.setText(String.valueOf(academicTutorshipReport.getNumberOfStudentsAtRisk()));
        educationalProgramTextField.setText(educationalProgram.toString());
        schoolPeriodTextField.setText(schoolPeriod.toString());
        AcademicTutorshipSession academicTutorshipSession = academicTutorship.getAcademicTutorshipSession();
        academicTutorshipSessionTextField.setText(academicTutorshipSession.toString());
        sessionNumberTextField.setText(String.valueOf(academicTutorshipSession.getSessionNumber()));
        generalCommentTextArea.setText(academicTutorshipReport.getGeneralComment());
    }

    private void loadAcademicProblemsByAcademicTutorshipReport() {
        int idAcademicTutorshipReport = academicTutorshipReport.getIdAcademicTutorshipReport();
        academicProblems = AcademicProblemDAO.loadAcademicProblemsByAcademicTutorshipReport(idAcademicTutorshipReport);
    }

    private void loadStudentsByAcademicTutorshipReport() {
        idAcademicTutorshipReport = academicTutorshipReport.getIdAcademicTutorshipReport();
        ArrayList<Student> studentsResultSet = StudentDAO.getStudentsByAcademicTutorshipReport(idAcademicTutorshipReport);
        if (!studentsResultSet.isEmpty()) {
            students.addAll(studentsResultSet);
            students.forEach(student -> {
                boolean isDisabled = false;
                student.getAttendedBy().setDisable(isDisabled);
                student.getAtRisk().setDisable(isDisabled);
            });
            studentsTableView.setItems(students);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void acceptButtonClick(ActionEvent actionEvent) {
        String generalComment = generalCommentTextArea.getText();
        int numberOfStudentsAttending = calculateNumberOfStudentsAttending();
        int numberOfStudentsAtRisk = calculateNumberOfStudentsAtRisk();
        numberOfStudentsAttendingTextField.setText(String.valueOf(numberOfStudentsAttending));
        numberOfStudentsAtRiskTextField.setText(String.valueOf(numberOfStudentsAtRisk));
        academicTutorshipReport.setGeneralComment(generalComment);
        academicTutorshipReport.setNumberOfStudentsAttending(numberOfStudentsAttending);
        academicTutorshipReport.setNumberOfStudentsAtRisk(numberOfStudentsAtRisk);
        updateAcademicTutorshipReport();
    }

    private int calculateNumberOfStudentsAttending() {
        int numberOfStudentsAttending = 0;
        for (Student student : students) {
            boolean isAttendedBy = student.getAttendedBy().isSelected();
            if (isAttendedBy) {
                numberOfStudentsAttending++;
            }
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
        }
        return numberOfStudentsAtRisk;
    }

    private void updateAcademicTutorshipReport() {
        int responseCode = AcademicTutorshipReportDAO.updateAcademicTutorshipReport(academicTutorshipReport);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            logAcademicProblemsByAcademicTutorshipReport();
            updateStudentsByAcademicTutorshipReport();
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
            if (academicProblem.getIdAcademicOffering() == Constants.PRIMARY_KEY_OF_NON_EXISTENT_RECORD_IN_DATABASE) {
                AcademicProblemDAO.logAcademicProblemByAcademicTutorshipReport(academicProblem, idAcademicTutorshipReport);
            } else {
                AcademicProblemDAO.updateAcademicProblemByAcademicTutorshipReport(academicProblem);
            }
        });
    }

    private void updateStudentsByAcademicTutorshipReport() {
        students.forEach(student -> {
            StudentDAO.updateStudentByAcademicTutorshipReport(student, idAcademicTutorshipReport);
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
        if (academicProblem.getIdAcademicProblem() == Constants.PRIMARY_KEY_OF_NON_EXISTENT_RECORD_IN_DATABASE) {
            academicProblems.add(academicProblem);
        } else {
            int index = 0;
            while (index < academicProblems.size()) {
                if (academicProblem.getIdAcademicProblem() == academicProblem.getIdAcademicProblem()) {
                    academicProblems.set(index, academicProblem);
                }
                index++;
            }
        }
    }

}