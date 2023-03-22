/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IAcademicProblem;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.MessagesAlerts;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class ModifyAcademicProblemListFXMLController implements Initializable {

    @FXML
    private TableView<AcademicProblem> academiProblemListTableView;
    @FXML
    private TableColumn<AcademicProblem, String> titleTableColumn;
    @FXML
    private TableColumn<AcademicProblem, ?> numberReportTableColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;

    
    private IAcademicProblem academicProblemInterface;

    private int idEducationalProgram;
    private int idSchoolPeriod;
    private int numberOfStudentsByAcademicPersonnel;
    
    private ObservableList<AcademicProblem> academicProblemList = FXCollections.observableArrayList();
    private ArrayList<AcademicProblem> academicProblems;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeColumns();
    }    
    
    private void initializeColumns(){
        this.numberReportTableColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfStudents"));
        this.titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }
    
    
    private void loadGUI(){
        this.academiProblemListTableView.setItems(academicProblemList);
    }
    
    public void configureView(IAcademicProblem academicProblemInterface, SchoolPeriod schoolPeriod, EducationalProgram educationalProgram, int numberOfStudentsByAcademicPersonnel,ArrayList<AcademicProblem> academicProblems) {
        this.academicProblemInterface = academicProblemInterface;
        this.idSchoolPeriod = schoolPeriod.getIdSchoolPeriod();
        this.idEducationalProgram = educationalProgram.getIdEducationalProgram();
        this.numberOfStudentsByAcademicPersonnel = numberOfStudentsByAcademicPersonnel;
        this.academicProblems = academicProblems;
        this.academicProblemList.addAll(academicProblems);
        this.loadGUI();
    }
    
    @FXML
    private void editButtonClick(ActionEvent event) {
        if(this.academiProblemListTableView.getSelectionModel().getSelectedItem()!=null){
            this.callWindowModifyAcademicProblem();
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        ((Stage)this.cancelButton.getScene().getWindow()).close();
    }
    
    private void callWindowModifyAcademicProblem(){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAcademicProblemFXML.fxml"));
        AcademicProblem academicProblemSelected = this.academiProblemListTableView.getSelectionModel().getSelectedItem();
        int academicProblemIndex = this.academiProblemListTableView.getSelectionModel().getSelectedIndex();
        try{
            Parent root = loader.load();
            ModifyAcademicProblemFXMLController modifyAcademicProblemFXMLController = loader.getController();
            Scene modifyAcademicProblemView = new Scene(root);
            Stage stage = (Stage) this.cancelButton.getScene().getWindow();
            stage.setScene(modifyAcademicProblemView);
            modifyAcademicProblemFXMLController.configureView(
                    academicProblemInterface, idSchoolPeriod, idEducationalProgram, 
                    numberOfStudentsByAcademicPersonnel, this.academicProblems,
                    academicProblemIndex);
            stage.show();
        }catch(IOException exception){
            MessagesAlerts.showFailureLoadWindow();
        }
    }
    
}
