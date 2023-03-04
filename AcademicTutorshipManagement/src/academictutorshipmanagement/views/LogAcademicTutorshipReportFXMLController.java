/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 02, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.model.pojo.User;
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
import javafx.stage.Stage;

public class LogAcademicTutorshipReportFXMLController implements Initializable {

    @FXML
    private TextField numberOfStudentsAttendingTextBox;
    @FXML
    private TextField numberOfStudentsAtRiskTextBox;
    @FXML
    private TextField schoolPeriodTextBox;
    @FXML
    private TextField academicTutorshipSessionTextBox;
    @FXML
    private TextField educationalProgramTextBox;
    @FXML
    private TextField sessionNumberTextBox;
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

    private AcademicPersonnel academicPersonnel;
    private EducationalProgram educationalProgram;
    private SchoolPeriod schoolPeriod;
    private AcademicTutorshipSession academicTutorshipSession;
    private ObservableList<Student> students;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void configureView(AcademicPersonnel academicPersonnel, SchoolPeriod schoolPeriod) {
        this.academicPersonnel = academicPersonnel;
        educationalProgram = academicPersonnel.getUser().getEducationalProgram();
        this.schoolPeriod = schoolPeriod;
        academicTutorshipSession = schoolPeriod.getAcademicTutorshipSessions().get(0);
        configureAcademicTutorshipReportInformation();
        loadStudentsByAcademicPersonnel();
    }

    private void configureAcademicTutorshipReportInformation() {
        educationalProgramTextBox.setText(educationalProgram.toString());
        schoolPeriodTextBox.setText(schoolPeriod.toString());
        academicTutorshipSessionTextBox.setText(academicTutorshipSession.toString());
        sessionNumberTextBox.setText(String.valueOf(academicTutorshipSession.getSessionNumber()));
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
            Stage stage = (Stage) numberOfStudentsAttendingTextBox.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'MainMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void logAcademicProblemButtonClick(ActionEvent event) {
    }

    @FXML
    private void viewAcademicProblemsButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        goToMainMenu();
    }

}
