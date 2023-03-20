package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.utilities.MessagesAlerts;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jonatan
 */
public class ModifyAcademicProblemFollowUpFXMLController implements Initializable {

    AcademicProblem academicProblem = new AcademicProblem();
    @FXML
    private Label titleAcademicProblemLabel;
    @FXML
    private Label nameEducationalExperienceLabel;
    @FXML
    private Label nameAcademicPersonnelLabel;
    @FXML
    private Label numberStudentsLabel;
    @FXML
    private Label descriptionAcademicProblemLabel;
    @FXML
    private TextArea descriptionFollowUpTextArea;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
    public void configureView(AcademicProblem academicProblem){
        this.academicProblem = academicProblem;
        this.loadGUI();
    }
        
    private void loadGUI(){
        this.titleAcademicProblemLabel.setText(this.academicProblem.getTitle());
        this.descriptionAcademicProblemLabel.setText(this.academicProblem.getDescription());
        this.nameAcademicPersonnelLabel.setText(this.academicProblem.getAcademicOffering().getAcademicPersonnel().getFullName());
        this.nameEducationalExperienceLabel.setText(this.academicProblem.getAcademicOffering().getEducationalExperience().getName());
        this.numberStudentsLabel.setText(Integer.toString(this.academicProblem.getNumberOfStudents()));
    }
    
    private void updateFollowUp(){
        Date todayDate = Date.valueOf(LocalDate.now());     
        try{
            this.checkEmptyFields();
            int idAcademicProblemFollowUp = this.academicProblem.getAcademicProblemFollowUp().getIdAcademicProblemFollowUp();
            this.academicProblem.getAcademicProblemFollowUp().setDate(todayDate);
            this.academicProblem.getAcademicProblemFollowUp().setDescription(
                    this.descriptionFollowUpTextArea.getText());
            AcademicProblemDAO.ModifyAcademicProblemFollowUp(this.academicProblem, idAcademicProblemFollowUp );
        }catch(DataFormatException dfException){
            MessagesAlerts.showBlankFieldsAlert();
        }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }

    @FXML
    private void backButtonClick(ActionEvent event) {        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryAcademicProblemFollowUpFXML.fxml"));
        try{
            Parent root = loader.load();
            Scene queryFollowUpOnAcademicProblemsList = new Scene(root);
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsList);
            stage.show();
        }catch(IOException ioException){
            MessagesAlerts.showFailureLoadWindow();
        }
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {
        this.updateFollowUp();
        MessagesAlerts.showAlert("Se ha actualizado el seguimiento con exito", Alert.AlertType.CONFIRMATION);
        this.backButtonClick(event);
    }    
        
    private void checkEmptyFields() throws DataFormatException{
        if(this.descriptionFollowUpTextArea.getText().isEmpty()){
            throw new DataFormatException();
        }
    }
}
