/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicOfferingDAO;
import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.MessagesAlerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class RegisterAcademicOfferingFXMLController implements Initializable {

    @FXML
    private TextField educationalExperienceTextField;
    @FXML
    private ComboBox<EducationalProgram> educationalProgramComboBox;
    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;
    @FXML
    private TextField NRCTextField;
    @FXML
    private TextField academicPersonnelTextField;
    @FXML
    private TextField educationalFilterTextField;
    @FXML
    private TextField personelFilterTextField;
    @FXML
    private TableView<EducationalExperience> educationalExperienceTableView;
    @FXML
    private TableColumn<EducationalExperience, String> educationalExperienceTableColumn;
    @FXML
    private TableView<AcademicPersonnel> academicPersonelTableView;
    @FXML
    private TableColumn<AcademicPersonnel, String> academicPersonnelTableColumn;

    private ObservableList<EducationalExperience> educationalExperienceList = FXCollections.observableArrayList();
    private ObservableList<AcademicPersonnel> academicPersonelList = FXCollections.observableArrayList();
    @FXML
    private Button registrerButton;
    @FXML
    private Button backButton;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeColumns();
        try {
            this.schoolPeriodComboBox.setItems(this.getSchoolPeriod());
            this.academicPersonelTableView.setItems(this.getAcademicPersonnel());
            this.educationalProgramComboBox.setItems(this.getEducationalProgram());
            this.initializeFilterAcademicPersonnel();
            this.NRCTextField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    NRCTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            Pattern pattern = Pattern.compile(".{0,5}");
            TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
                return pattern.matcher(change.getControlNewText()).matches() ? change : null;
            });

            this.NRCTextField.setTextFormatter(formatter);
        } catch (SQLException sqlException) {
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }
        
    private ObservableList<EducationalProgram> getEducationalProgram() throws SQLException{
        ObservableList<EducationalProgram> educationalProgramList = FXCollections.observableArrayList();
        ArrayList<EducationalProgram> educationalProgramQuery = EducationalProgramDAO.getEducationalPrograms();
        for(EducationalProgram educationalProgram: educationalProgramQuery){
            educationalProgramList.add(educationalProgram);
        }
        return educationalProgramList; 
    }
    
    private ObservableList<AcademicPersonnel> getAcademicPersonnel() throws SQLException{
        ObservableList<AcademicPersonnel> academicPersonnelList = FXCollections.observableArrayList();
        ArrayList<AcademicPersonnel> academicPersonnelQuery = AcademicPersonnelDAO.getAllAcademicPersonnel();
        for(AcademicPersonnel academicPersonnel: academicPersonnelQuery){
            academicPersonelList.add(academicPersonnel);
        }    
        return academicPersonnelList; 
    }
    
    private ObservableList<SchoolPeriod> getSchoolPeriod() throws SQLException{
        ObservableList<SchoolPeriod> schoolPeriodList = FXCollections.observableArrayList();
        ArrayList<SchoolPeriod> schoolPeriodQuery = SchoolPeriodDAO.getAllSchoolPeriods();
        for(SchoolPeriod schoolPeriod: schoolPeriodQuery){
            schoolPeriodList.add(schoolPeriod);
        }
        return schoolPeriodList;    
    }
    
    private ObservableList<EducationalExperience> getEducationalExperiences(int idEducationalProgram)throws SQLException{
        ObservableList<EducationalExperience> educationalExperiencesList = FXCollections.observableArrayList();
        ArrayList<EducationalExperience> educationalExperiencesQuery = 
                EducationalExperienceDAO.getEducationalExperiencesByEducationalProgram(idEducationalProgram);
        for(EducationalExperience educationalExperience : educationalExperiencesQuery){
            educationalExperiencesList.add(educationalExperience);
        }
        return educationalExperiencesList;
    }
    
    private void loadEducationalExperience(int idEducationalProgram) throws SQLException{
        this.educationalExperienceList = this.getEducationalExperiences(idEducationalProgram);
        this.initializeFilterEducationalExperience();
    }
    
    
    private void initializeFilterEducationalExperience(){
        FilteredList<EducationalExperience> educationalExperiencesFilteredList = 
                new FilteredList(this.educationalExperienceList,educationalExperience -> true);
        this.educationalFilterTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            this.educationalExperienceTextField.clear();
            educationalExperiencesFilteredList.setPredicate(educationalExperience ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;               
                }
                String searchWord = newValue.toLowerCase();
                String dataSearch = 
                        educationalExperience.getName().toLowerCase();
                if(dataSearch.indexOf(searchWord)>-1){
                    return true;
                }else{
                    return false;                
                }            
            });
        });
        SortedList<EducationalExperience> educationalExperiencesOrderList = new SortedList<>(educationalExperiencesFilteredList);
        educationalExperiencesOrderList.comparatorProperty().bind(this.educationalExperienceTableView.comparatorProperty());
        this.educationalExperienceTableView.setItems(educationalExperiencesOrderList);   
    }
    
    private void initializeFilterAcademicPersonnel(){
            FilteredList<AcademicPersonnel> academicPersonnelFilteredList = 
            new FilteredList(this.academicPersonelList,academicPersonnel -> true);
        this.personelFilterTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            this.academicPersonnelTextField.clear();
            academicPersonnelFilteredList.setPredicate(academicPersonnel ->{
                if(newValue.isEmpty() || newValue == null){
                    return true;               
                }
                String searchWord = newValue.toLowerCase();
                String dataSearch = 
                        academicPersonnel.getPaternalSurname().toLowerCase() + " " 
                        + academicPersonnel.getMaternalSurname().toLowerCase() + " " 
                        + academicPersonnel.getName().toLowerCase();
                if(dataSearch.indexOf(searchWord)>-1){
                    return true;
                }else{
                    return false;                
                }            
            });
        });
        SortedList<AcademicPersonnel> academicPersonnelOrderList = new SortedList<>(academicPersonnelFilteredList);
        academicPersonnelOrderList.comparatorProperty().bind(this.academicPersonelTableView.comparatorProperty());
        this.academicPersonelTableView.setItems(academicPersonnelOrderList);
    }
    
    private void initializeColumns(){
        this.academicPersonnelTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.educationalExperienceTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    
    private void clearFields(){
        this.educationalExperienceTableView.getSelectionModel().clearSelection();
        this.educationalExperienceTextField.clear();
        this.NRCTextField.clear();
    }
    
    

    @FXML
    private void educationalProgramSelect(ActionEvent event) {
        this.clearFields();
        EducationalProgram newValue = this.educationalProgramComboBox.getValue();
        if(newValue != null){
            try{                
                this.loadEducationalExperience(newValue.getIdEducationalProgram());
            }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
            }
        }    
    }

    @FXML
    private void schoolPeriodSelect(ActionEvent event) {
    }

    @FXML
    private void academicPersonnelSelect(MouseEvent event) {
        AcademicPersonnel academicPersonnelSelected = this.academicPersonelTableView.getSelectionModel().getSelectedItem();
        this.academicPersonnelTextField.setText(null);
        if(academicPersonnelSelected != null){
            this.academicPersonnelTextField.setText(academicPersonnelSelected.getFullName());
        }    
    }

    @FXML
    private void registerButtonClick(ActionEvent event) {
        try{
            this.validateEmptyFields();
            AcademicOffering academicOfferingPreview = new AcademicOffering();
            academicOfferingPreview.setNrc(Integer.parseInt(this.NRCTextField.getText()));
            academicOfferingPreview.getEducationalExperience().setIdEducationalExperience(
                    this.educationalExperienceTableView.getSelectionModel().getSelectedItem().getIdEducationalExperience());
            academicOfferingPreview.getAcademicPersonnel().setIdAcademicPersonnel(
                    this.academicPersonelTableView.getSelectionModel().getSelectedItem().getIdAcademicPersonnel());
            academicOfferingPreview.getSchoolPeriod().setIdSchoolPeriod(
                    this.schoolPeriodComboBox.getSelectionModel().getSelectedItem().getIdSchoolPeriod());
            academicOfferingPreview.getEducationalExperience();
            AcademicOffering academicOfferingLog = AcademicOfferingDAO.getAcademicOffering(academicOfferingPreview.getNrc(), academicOfferingPreview.getSchoolPeriod().getIdSchoolPeriod(), this.educationalProgramComboBox.getSelectionModel().getSelectedItem().getIdEducationalProgram());
            if(academicOfferingLog == null){
                if(AcademicOfferingDAO.logAcademicOffering(academicOfferingPreview) > 0){
                    this.clearFields();
                    MessagesAlerts.showAlert("Se ha registrado el nuevo registro con exito en la base de datos ", Alert.AlertType.INFORMATION);
                }else{
                    MessagesAlerts.showAlert("No se ha registrado el registro ha ocurrido un error al momento de almacenar la información", Alert.AlertType.INFORMATION);
                }
                
            }else{
                MessagesAlerts.showAlert("Ese registro ya se encuentra registrado en el sistema", Alert.AlertType.INFORMATION);
            }
        }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
            sqlException.printStackTrace();
        }catch(DataFormatException dfException){
            MessagesAlerts.showAlert("No se pueden dejar campos vacios, porfavor verificar la información", Alert.AlertType.INFORMATION);
            
        
        }
    }
    
    private void validateEmptyFields() throws DataFormatException{
        if(this.NRCTextField.getText().isEmpty() || this.academicPersonelTableView.getSelectionModel().getSelectedItem()==null || 
                this.educationalExperienceTableView==null || this.educationalProgramComboBox.getSelectionModel().getSelectedItem()==null || 
                this.schoolPeriodComboBox.getSelectionModel().getSelectedItem()==null){
            throw new DataFormatException();
        
        }
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EducationalProgramAdministrationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            EducationalProgramAdministrationMenuFXMLController educationalProgramAdministrationMenuFXMLController  = loader.getController();
            Scene mainMenuView = new Scene(root);
            Stage stage = (Stage) this.NRCTextField.getScene().getWindow();
            stage.setScene(mainMenuView);
            stage.setTitle("Administración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    
    
    
    @FXML
    private void educationalExperienceSelect(MouseEvent event) {
        EducationalExperience educationalExperience = this.educationalExperienceTableView.getSelectionModel().getSelectedItem();
        if(educationalExperience != null){
            this.educationalExperienceTextField.setText(educationalExperience.getName());        
        }
    }
    
}
