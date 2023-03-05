/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class QueryAcademicTutorshipReportByAcademicTutorFXMLController implements Initializable {

    @FXML
    private TableView<?> academicTutorshipSessionTableView;
    @FXML
    private TableColumn<?, ?> registrationNumberTableColumn;
    @FXML
    private TableColumn<?, ?> studentTableColumn;
    @FXML
    private TableColumn<?, ?> studentAttendingTableColumn;
    @FXML
    private TableColumn<?, ?> studentAtRiskTableColumn;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField academicTutorshipSessionDateTextField;
    @FXML
    private TextField educativeProgramTextField;
    @FXML
    private ComboBox<?> sessionNumberComboBox;
    @FXML
    private ComboBox<?> schoolPeriodComboBox;
    @FXML
    private ComboBox<?> academicPersonnelComboBox;
    @FXML
    private TextArea generalCommentTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelButtonClick(ActionEvent event) {
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
