/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class RegisterAcademicProblemFollowUpFXMLController implements Initializable {
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
    private TextArea descriptionFollowUpTextArea;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label descriptionAcademicProblemLabel;
    
    /**
     * Initializes the controller class.
     */
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
    
    private void registerFollowUp(){
        Date todayDate = Date.valueOf(LocalDate.now()); //Verificar Parseador
        try{
            this.checkEmptyFields();
            this.academicProblem.getAcademicProblemFollowUp().setDate(todayDate);
            this.academicProblem.getAcademicProblemFollowUp().setDescription(
                    this.descriptionFollowUpTextArea.getText());
            AcademicProblemDAO.registerAcademicProblemFollowUp(this.academicProblem);
        }catch(DataFormatException dfException){
            
        }catch(SQLException sqlException){
            System.out.print(sqlException.getMessage());
        }
    }
    
    @FXML
    private void backButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryFollowUpOnAcademicProblemsListFXML.fxml"));
        try{
            Parent root = loader.load();
            Scene queryFollowUpOnAcademicProblemsList = new Scene(root);
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsList);
            stage.show();
        }catch(IOException ioException){
            
        }
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {
        this.registerFollowUp();
    }
    
    
    private void checkEmptyFields() throws DataFormatException{
        if(this.descriptionFollowUpTextArea.getText().isEmpty()){
            throw new DataFormatException();
        }
    }
    
    
}
