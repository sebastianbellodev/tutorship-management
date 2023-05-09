/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicOfferingDAO;
import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipReportDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.documentformat.AssistanceFormat;
import academictutorshipmanagement.utilities.documentformat.SignatureList;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class SaveAsistanceFormatFXMLController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField fileNameTextField;
    @FXML
    private ComboBox<SchoolPeriod> schoolPeriodComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadSchoolPeriod();
        } catch (SQLException sqlException) {
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }    
    
    private void loadSchoolPeriod()throws SQLException{
        this.schoolPeriodComboBox.setItems(this.getSchoolPeriod());
    }
    
    private ObservableList<SchoolPeriod> getSchoolPeriod()throws SQLException{
        ObservableList<SchoolPeriod> schoolPeriodList = FXCollections.observableArrayList();
        ArrayList<SchoolPeriod> schoolPeriodQuery = SchoolPeriodDAO.getAllSchoolPeriods();
        for(SchoolPeriod schoolPeriod : schoolPeriodQuery){
            schoolPeriodList.add(schoolPeriod);
        }
        return schoolPeriodList;
    }
    
    private void checkEmptyFields() throws DataFormatException{
        if(this.fileNameTextField.getText().isEmpty() || this.schoolPeriodComboBox.getValue() == null){
            throw new DataFormatException();        
        } 
    }
    
    private void saveFile(File file){
        SessionInformation sessionInformation = getSessionInformation();
        int idSchoolPeriod = this.schoolPeriodComboBox.getSelectionModel().getSelectedItem().getIdSchoolPeriod();
        try{    
            AssistanceFormat assistanceFormat = new AssistanceFormat();
            ArrayList<AcademicPersonnel> academicPersonnelList = this.getAcademicPersonnel();
            for(AcademicPersonnel academicPersonnel: academicPersonnelList){
                assistanceFormat.addAsistance(academicPersonnel, 
                        this.getAsistance(idSchoolPeriod, academicPersonnel.getIdAcademicPersonnel()));
            }
            assistanceFormat.generateDocument(file);
            MessagesAlerts.showAlert("Se ha guardado con exito la lista de asistencia", Alert.AlertType.INFORMATION);
            this.closeWindow();
        }catch(IOException ioException){
            MessagesAlerts.showAlert("Ha ocurrido un error al momento de almacenar el archivo", Alert.AlertType.INFORMATION);
        }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }
    
    @FXML
    private void saveButtonClick(ActionEvent event) {
        FileChooser save = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files","* pdf");
        save.getExtensionFilters().add(extFilter);
        try{
            this.checkEmptyFields();
            save.setInitialFileName(this.fileNameTextField.getText()+".pdf");
            File file = save.showSaveDialog(null);
            if(file != null){
                this.saveFile(file);
            }
        }catch(DataFormatException dfException){
            MessagesAlerts.showBlankFieldsAlert();
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        this.closeWindow();
    }
    
    private void closeWindow(){
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
        stage.close(); 
    }
    
    private ArrayList<AcademicPersonnel> getAcademicPersonnel() throws SQLException{
        SessionInformation sessionInformation = getSessionInformation();        
        ArrayList<AcademicPersonnel> academicPersonnel = 
                AcademicPersonnelDAO.getAcademicPersonnelByTutorship(
                    this.schoolPeriodComboBox.getSelectionModel().getSelectedItem().getIdSchoolPeriod(), 
                    sessionInformation.getUser().getEducationalProgram().getIdEducationalProgram());    
        return academicPersonnel;
    }
    private ArrayList<Integer> getAsistance(int idSchoolPeriod, int idAcademicPersonnel) throws SQLException{
        return AcademicTutorshipReportDAO.getAsistancesOfAcademicTutorShipReport(idSchoolPeriod, idAcademicPersonnel);
    }
    
}
