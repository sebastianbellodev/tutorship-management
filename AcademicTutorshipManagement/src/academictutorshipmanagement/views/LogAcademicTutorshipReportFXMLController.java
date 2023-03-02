package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.Student;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LogAcademicTutorshipReportFXMLController implements Initializable {

    @FXML
    private TextField numberOfStudentsAttendingTextBox;
    @FXML
    private TextField numberOfStudentsAtRiskTextBox;
    @FXML
    private TextField educationalProgramTextBox;
    @FXML
    private TextField SchoolPeriodTextBox;
    @FXML
    private TextField tutorialAcademicSessionTextBox;
    @FXML
    private TextField numberOfSessionTextBox;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn registrationNumberTableColumn;
    @FXML
    private TableColumn fullNameTableColumn;
    @FXML
    private TableColumn attendingTableColumn;
    @FXML
    private TextArea generalCommentTextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }

    @FXML
    private void logAcademicProblemButtonClick(ActionEvent event) {
    }

    @FXML
    private void viewAcademicProblemsButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }
    
}