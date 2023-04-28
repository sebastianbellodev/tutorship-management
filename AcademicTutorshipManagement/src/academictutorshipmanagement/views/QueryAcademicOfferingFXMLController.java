/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicOfferingDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.utilities.MessagesAlerts;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class QueryAcademicOfferingFXMLController implements Initializable {

    @FXML
    private TextField filterTextField;
    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<InnerAcademicOffering, ?> NRCTableColumn;
    @FXML
    private TableColumn<InnerAcademicOffering, String> educationalExperienceTableColumn;
    @FXML
    private TableColumn<InnerAcademicOffering, String> personalTableColumn;
    @FXML
    private TableView<InnerAcademicOffering> academicOfferingTableView;
    
    private ObservableList<InnerAcademicOffering> academicOfferingList = FXCollections.observableArrayList();

            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.configureView();
        try {
            this.loadSchoolPeriods();
        } catch (SQLException ex) {
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }    
    
    private void configureView(){
        this.initializeColumns();
    }
    
    private void initializeFilter(){
        FilteredList<InnerAcademicOffering> academicOfferingFilteredList =
                new FilteredList(this.academicOfferingList, academicOffering -> true);
        this.filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            academicOfferingFilteredList.setPredicate(innerAcademicOffering -> {
                if(newValue.isEmpty() || newValue == null){
                    return true;                
                }
                String searchWord = newValue.toLowerCase();
                String dataSearch = 
                        innerAcademicOffering.getInnerAcademicPersonnel().toLowerCase() + " " +
                        innerAcademicOffering.getInnerEducationalExperience().toLowerCase() + " " +
                        String.valueOf(innerAcademicOffering.getNrc()).toLowerCase();
                if(dataSearch.indexOf(searchWord)>-1){
                    return true;                
                }else{
                    return false;
                }
            });
        });
        SortedList<InnerAcademicOffering> academicOfferingOrderList = new SortedList<>(academicOfferingFilteredList);
        academicOfferingOrderList.comparatorProperty().bind(this.academicOfferingTableView.comparatorProperty());
        this.academicOfferingTableView.setItems(academicOfferingOrderList);  
    }
    
    private ObservableList<SchoolPeriod> getSchoolPeriod() throws SQLException{
        ObservableList<SchoolPeriod> schoolPeriodList = FXCollections.observableArrayList();
        ArrayList<SchoolPeriod> schoolPeriodQuery = SchoolPeriodDAO.getAllSchoolPeriods();
        for(SchoolPeriod schoolPeriod: schoolPeriodQuery){
            schoolPeriodList.add(schoolPeriod);
        }
        return schoolPeriodList;
    }
    
    
    private void loadSchoolPeriods() throws SQLException{
        this.schoolPeriodComboBox.setItems(this.getSchoolPeriod());
    }   
    
    private ObservableList<InnerAcademicOffering> getAcademicOffering(int idSchoolPeriod) throws SQLException{
        ObservableList<InnerAcademicOffering> academicOfferingList = FXCollections.observableArrayList();
        ArrayList<AcademicOffering> academicOfferingQuery = AcademicOfferingDAO.getAcademicOffering(idSchoolPeriod, 
                SessionInformation.getSessionInformation().getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram()); 
        for(AcademicOffering academicOffering : academicOfferingQuery){
            InnerAcademicOffering innerAcademicOffering = new InnerAcademicOffering(academicOffering);
            academicOfferingList.add(innerAcademicOffering);
        }
        return academicOfferingList;
    }

    private void initializeColumns(){
        this.NRCTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nrc"));
        this.educationalExperienceTableColumn.setCellValueFactory(new PropertyValueFactory<>("innerEducationalExperience"));
        this.personalTableColumn.setCellValueFactory(new PropertyValueFactory("innerAcademicPersonnel"));   
    }
    
     
    private void loadAcademicOffering(int idSchoolPeriod)throws SQLException{
        this.academicOfferingList = this.getAcademicOffering(idSchoolPeriod);
        this.initializeFilter();
    }
    
    
    @FXML
    private void backButtonClick(ActionEvent event) {
    
    }

    @FXML
    private void schoolPeriodSelected(ActionEvent event) {
        SchoolPeriod newValue = this.schoolPeriodComboBox.getValue();
        if(newValue != null){
            try{
            this.loadAcademicOffering(newValue.getIdSchoolPeriod());
            }catch(SQLException sqlException){
                MessagesAlerts.showDataBaseLostConnectionAlert();
            }
        }    
    }
    
    public class InnerAcademicOffering extends AcademicOffering{
        public InnerAcademicOffering(AcademicOffering academicOffering){
            this.setAcademicPersonnel(academicOffering.getAcademicPersonnel());
            this.setEducationalExperience(academicOffering.getEducationalExperience());
            this.setIdAcademicOffering(academicOffering.getIdAcademicOffering());
            this.setNrc(academicOffering.getNrc());
            this.setSchoolPeriod(academicOffering.getSchoolPeriod());
        }
        
        public String getInnerEducationalExperience(){
            return this.getEducationalExperience().getName();
        }
        public String getInnerAcademicPersonnel(){
            String fullname = this.getAcademicPersonnel().getName() + " " 
                    + this.getAcademicPersonnel().getPaternalSurname() + " " 
                    + this.getAcademicPersonnel().getMaternalSurname();
            return fullname;
        }
    }
    
}
