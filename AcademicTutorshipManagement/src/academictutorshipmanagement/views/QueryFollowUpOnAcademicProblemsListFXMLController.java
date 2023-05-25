/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.Roles;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author oband
 */
public class QueryFollowUpOnAcademicProblemsListFXMLController implements Initializable {

    @FXML
    private TableView<InnerAcademicProblem> academicProblemTableView;
    @FXML
    private TableColumn<InnerAcademicProblem, String> titleAcademicProblemTableColumn;
    @FXML
    private TableColumn<InnerAcademicProblem, String> nameEducationalExperienceTableColumn;
    @FXML
    private TableColumn<InnerAcademicProblem, String> schoolPeriodTableColumn;
    @FXML
    private TableColumn<InnerAcademicProblem, String> academicProblemNumberReportsTableColumn;
    @FXML
    private TextField filterTextField;
    @FXML
    private Button consultButton;
    @FXML
    private Button backButton;

    private ObservableList<InnerAcademicProblem> academicProblemList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeColums();
    }    
    
    private void initializeColums(){
        this.nameEducationalExperienceTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerEducationalExperience"));
        this.academicProblemNumberReportsTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerNumberOfReports"));
        this.schoolPeriodTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerSchoolPeriod"));
        this.titleAcademicProblemTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }   
    
    private void loadGUI(){
      try{
        this.academicProblemList = this.getAcademicProblemList();
        this.academicProblemTableView.setItems(this.academicProblemList);
        this.initializeFilter();
      }catch(SQLException sqlException){
          MessagesAlerts.showDataBaseLostConnectionAlert();
      }
    }
    
    public void configureView(ArrayList<AcademicProblem> academicProblems){
        this.academicProblemList = this.getAcademicProblemList(academicProblems);
        this.academicProblemTableView.setItems(this.academicProblemList);
        this.backButton.setOnAction(event -> this.closeWindow());
        this.initializeFilter();
    }
    
    public void configureView(){
        this.backButton.setOnAction(event -> this.backButtonClick());
        this.loadGUI();
    }
    
    private ObservableList<InnerAcademicProblem> getAcademicProblemList(ArrayList<AcademicProblem> academicProblems){
        ObservableList<InnerAcademicProblem> academicProblemList = FXCollections.observableArrayList();
        for(AcademicProblem academicProblem : academicProblems){
            academicProblemList.add(new InnerAcademicProblem(academicProblem));           
        }
        return academicProblemList;  
    }
    
    private ObservableList<InnerAcademicProblem> getAcademicProblemList() throws SQLException{
        SessionInformation sessionInformation = getSessionInformation();
        ObservableList<InnerAcademicProblem> academicProblemList = FXCollections.observableArrayList();
        ArrayList<AcademicProblem> academicProblemQuery = 
            sessionInformation.getAcademicPersonnel().getUser().getRole().getIdRole() == Roles.ACADEMIC_TUTOR_ID_ROLE? 
            AcademicProblemDAO.queryAcademicProblemForAcademicPersonnel(sessionInformation.getAcademicPersonnel().getIdAcademicPersonnel(),sessionInformation.getUser().getEducationalProgram().getIdEducationalProgram()):
            AcademicProblemDAO.queryAcademicProblemForEducationalProgram(sessionInformation.getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram());
        for(AcademicProblem academicProblem : academicProblemQuery){
            academicProblemList.add(new InnerAcademicProblem(academicProblem));           
        }
        return academicProblemList;    
    }
    
    private void initializeFilter(){
        FilteredList<InnerAcademicProblem> problematicFilteredList = 
            new FilteredList(this.academicProblemList, problematic -> true);
        this.filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            problematicFilteredList.setPredicate(innerProblematicTable -> {
            if(newValue.isEmpty() || newValue == null){
                return true;
            }
            String searchWord = newValue.toLowerCase();
            String dataSearch =
                    innerProblematicTable.getTitle().toLowerCase() + " " +
                    innerProblematicTable.getInnerEducationalExperience().toLowerCase() + " " +
                    innerProblematicTable.getInnerSchoolPeriod().toLowerCase();
            if(dataSearch.indexOf(searchWord)>-1){
                return true;
            }else{
                return false;
            }
        });
    });
        SortedList<InnerAcademicProblem> problematicOrderList =
                new SortedList<>(problematicFilteredList);
        problematicOrderList.comparatorProperty().bind(this.academicProblemTableView.comparatorProperty());
        this.academicProblemTableView.setItems(problematicOrderList);
    }

    @FXML
    private void consultButtonClick(ActionEvent event) {
        AcademicProblem academicProblem = (AcademicProblem)this.academicProblemTableView.getSelectionModel().getSelectedItem();
        if(academicProblem != null){
            this.callWindowQueryConsultFollowUpAcademicProblem(academicProblem);
        }
    }

    private void backButtonClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            MainMenuFXMLController mainMenuFXMLController = loader.getController();
            mainMenuFXMLController.configureView(getSessionInformation().getAcademicPersonnel().getUser());
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Menú principal.");
            stage.show();
        } catch (IOException exception) {
            MessagesAlerts.showFailureLoadWindow();
        }
    }
    
    private void closeWindow(){
        ((Stage) backButton.getScene().getWindow()).close();
    }
    
    private void callWindowQueryConsultFollowUpAcademicProblem(AcademicProblem academicProblemSelected){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryAcademicProblemFollowUpFXML.fxml"));
        try{
            Parent root = loader.load();
            QueryAcademicProblemFollowUpFXMLController controller= loader.getController();
            Scene queryAcademicProblemFollowUpView = new Scene(root);
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(queryAcademicProblemFollowUpView);
            controller.configureView(academicProblemSelected, true);
            stage.setTitle("Consulta Seguimiento a Problemática Académica");
            stage.show();
        }catch(IOException ioException){
           MessagesAlerts.showFailureLoadWindow();
        }
    }
    
    
    
    public class InnerAcademicProblem extends AcademicProblem{
        
        public InnerAcademicProblem(AcademicProblem academicProblem){
            this.setTitle(academicProblem.getTitle());
            this.setDescription(academicProblem.getDescription());
            this.setNumberOfStudents(academicProblem.getNumberOfStudents());
            this.setIdAcademicProblem(academicProblem.getIdAcademicProblem());
            this.setAcademicOffering(academicProblem.getAcademicOffering());
            this.setAcademicProblemFollowUp(academicProblem.getAcademicProblemFollowUp());
        }
        
        public String getInnerEducationalExperience(){
            return this.getAcademicOffering().getEducationalExperience().getName();
        }
        
        public String getInnerSchoolPeriod(){
            return this.getAcademicOffering().getSchoolPeriod().getStartDate().toString() + 
                    " - " + this.getAcademicOffering().getSchoolPeriod().getEndDate().toString();
        } 
        public String getInnerNumberOfReports(){
            return Integer.toString(this.getNumberOfStudents());
        }
    }
}

    
