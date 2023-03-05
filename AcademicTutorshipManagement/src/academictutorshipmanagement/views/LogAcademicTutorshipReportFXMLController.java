/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IAcademicProblem;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Constants;
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
    private AcademicTutorshipSession academicTutorshipSession;
    private AcademicPersonnel academicPersonnel;
    private EducationalProgram educationalProgram;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        academicProblems = new ArrayList<>();
        students = FXCollections.observableArrayList();
        configureStudentsTableViewColumns();
    }

    private void configureStudentsTableViewColumns() {
        registrationNumberTableColumn.setCellValueFactory(new PropertyValueFactory("registrationNumber"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        paternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("paternalSurname"));
        maternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("maternalSurname"));
        attendedByTableColumn.setCellValueFactory(new PropertyValueFactory("attendedBy"));
        atRiskTableColumn.setCellValueFactory(new PropertyValueFactory("atRisk"));
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        academicTutorshipSession = schoolPeriod.getAcademicTutorshipSessions().get(Constants.FIRST_ACADEMIC_TUTORSHIP_SESSION_INDEX);
        this.academicPersonnel = academicPersonnel;
        educationalProgram = academicPersonnel.getUser().getEducationalProgram();
        configureAcademicTutorshipReportInformation();
        loadStudentsByAcademicPersonnel();
    }

    private void configureAcademicTutorshipReportInformation() {
        educationalProgramTextField.setText(educationalProgram.toString());
        schoolPeriodTextField.setText(schoolPeriod.toString());
        academicTutorshipSessionTextField.setText(academicTutorshipSession.toString());
        sessionNumberTextField.setText(String.valueOf(academicTutorshipSession.getSessionNumber()));
    }

    private void loadStudentsByAcademicPersonnel() {
        int idEducationalProgram = educationalProgram.getIdEducationalProgram();
        int idAcademicPersonnel = academicPersonnel.getIdAcademicPersonnel();
        ArrayList<Student> studentsResultSet = StudentDAO.getStudentsByAcademicPersonnel(idEducationalProgram, idAcademicPersonnel);
        students.addAll(studentsResultSet);
        studentsTableView.setItems(students);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }

    private void goToMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            MainMenuFXMLController mainMenuFXMLController = loader.getController();
            User user = academicPersonnel.getUser();
            mainMenuFXMLController.configureView(user);
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) numberOfStudentsAttendingTextField.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void logAcademicProblemButtonClick(ActionEvent event) {
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
    private void viewAcademicProblemsButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }

    @Override
    public void configureAcademicProblem(AcademicProblem academicProblem) {
        academicProblems.add(academicProblem);
    }

}