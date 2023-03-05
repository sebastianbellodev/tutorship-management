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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class QueryAcademicTutorshipGeneralReportFXMLController implements Initializable {

    @FXML
    private ComboBox<?> schoolPeriodComboBox;
    @FXML
    private ComboBox<?> sessionNumberComboBox;
    @FXML
    private TextField educativeProgramTextField;
    @FXML
    private TextField academicTutorshipSessionDateTextField;
    @FXML
    private TextField numberOfStudentsAttendingTextField;
    @FXML
    private TextField numberOfStudentsAtRiskTextField;
    @FXML
    private TableView<?> academicProblemTableView;
    @FXML
    private TableColumn<?, ?> educativeExperienceTableColumn;
    @FXML
    private TableColumn<?, ?> academicPersonnelTableColumn;
    @FXML
    private TableColumn<?, ?> titleTableColumn;
    @FXML
    private TableColumn<?, ?> numberOfStudentsTableColumn;
    @FXML
    private TableView<?> generalCommentTableView;
    @FXML
    private TableColumn<?, ?> generalCommentTableColumn;
    @FXML
    private TableColumn<?, ?> academicTutorTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void downloadButtonClick(ActionEvent event) {
    }

    @FXML
    private void printButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }
    
}
