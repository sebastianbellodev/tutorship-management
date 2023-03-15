/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.Roles;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class QueryAcademicProblemFollowUpFXMLController implements Initializable {
    AcademicProblem queryAcademicProblem = new AcademicProblem();
    @FXML
    private Label titleAcademicProblemLabel;
    @FXML
    private Label nameEducationalExperienceLabel;
    @FXML
    private Label nameAcademicPersonnelLabel;
    @FXML
    private Label numberStudentsLabel;
    @FXML
    private Button modifyButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionFollowUpTextArea;
    @FXML
    private Label descriptionAcademicProblemLabel;
    @FXML
    private Label dateFollowUpLabel;


   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    
    public void configureView(AcademicProblem academicProblem) {
        this.queryAcademicProblem = academicProblem;
        this.loadGUI();
        this.checkButtons();
    }
    
    private void loadGUI(){
        SessionInformation sessionInformation = getSessionInformation();
        try{
            this.queryAcademicProblem = this.queryAcademicProblemWithFollowUp();
            this.titleAcademicProblemLabel.setText(this.queryAcademicProblem.getTitle());
            this.descriptionAcademicProblemLabel.setText(this.queryAcademicProblem.getDescription());
            this.nameAcademicPersonnelLabel.setText(this.queryAcademicProblem.getAcademicOffering().getAcademicPersonnel().getFullName());
            this.nameEducationalExperienceLabel.setText(this.queryAcademicProblem.getAcademicOffering().getEducationalExperience().getName());
            this.numberStudentsLabel.setText(Integer.toString(this.queryAcademicProblem.getNumberOfStudents()));
            if(this.queryAcademicProblem.getAcademicProblemFollowUp().getIdAcademicProblemFollowUp()!=0){
                this.dateFollowUpLabel.setText(this.queryAcademicProblem.getAcademicProblemFollowUp().getDate().toString()); //Validar Parseador
                this.descriptionFollowUpTextArea.setText(this.queryAcademicProblem.getAcademicProblemFollowUp().getDescription());
            }else{
                if(sessionInformation.getAcademicPersonnel().getUser().getRole().getIdRole() == Roles.CAREER_HEAD_ID_ROLE){
                    this.callWindowRegisterAcademicProblemFollowUp();
                }
            }  
        }catch(SQLException sqlException){
            //Definir msj SQL
        }
    }
    
    private AcademicProblem queryAcademicProblemWithFollowUp() throws SQLException{
        AcademicProblem queryAcademicProblemFollowUp = new AcademicProblem();
        queryAcademicProblemFollowUp = 
            AcademicProblemDAO.queryAcademicProblemWithFollowUp(this.queryAcademicProblem.getIdAcademicProblem());
        
        return queryAcademicProblemFollowUp.getIdAcademicProblem() == 0 ? this.queryAcademicProblem: queryAcademicProblemFollowUp;
    }
    
    private void checkButtons(){
        SessionInformation sessionInformation = getSessionInformation();
        if(sessionInformation.getAcademicPersonnel().getUser().getRole().getIdRole() != Roles.CAREER_HEAD_ID_ROLE){
            this.modifyButton.visibleProperty().set(false);
        }
    }
    
    @FXML
    private void modifyButtonClick(ActionEvent event) {
        //Mandar a Llamar la otra ventana
    
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
    
    
    private void callWindowRegisterAcademicProblemFollowUp(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterAcademicProblemFollowUpFXML.fxml"));
        try{
            Parent root = loader.load();
            RegisterAcademicProblemFollowUpFXMLController controller= loader.getController();
            Scene queryAcademicProblemFollowUpView = new Scene(root);
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(queryAcademicProblemFollowUpView);
            controller.configureView(this.queryAcademicProblem);
            stage.show();
        }catch(IOException ioException){
           //Error
        }
    }
}
