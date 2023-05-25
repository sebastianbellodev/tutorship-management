/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicProblemDAO;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.Roles;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class QueryAcademicProblemFollowUpFXMLController implements Initializable {
    AcademicProblem queryAcademicProblem = new AcademicProblem();
    
    
    @FXML
    private Button modifyButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionFollowUpTextArea;
    @FXML
    private Label dateFollowUpLabel;
    @FXML
    private TextField titleAcademicProblemTextField;
    @FXML
    private TextField nameEducationalExperienceTextField;
    @FXML
    private TextField nameAcademicPersonnelTextField;
    @FXML
    private TextField numberStudentsTextField;
    @FXML
    private TextArea descriptionAcademicProblemTextArea;


   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    
    public void configureView(AcademicProblem academicProblem, Boolean pop) {
        
        this.queryAcademicProblem = academicProblem;
        this.loadGUI();
        this.checkButtons();
        if(pop){
         this.backButton.setOnAction(new EventHandler<ActionEvent>(){
                 @Override
                 public void handle(ActionEvent event){
                        Stage stage = (Stage) backButton.getScene().getWindow();
                        stage.close();
                 
                 }       
             });
         
        }
    }
    
    private void loadGUI(){
        SessionInformation sessionInformation = getSessionInformation();
        try{
            this.queryAcademicProblem = this.queryAcademicProblemWithFollowUp();
            this.titleAcademicProblemTextField.setText(this.queryAcademicProblem.getTitle());
            this.descriptionAcademicProblemTextArea.setText(this.queryAcademicProblem.getDescription());
            this.nameAcademicPersonnelTextField.setText(this.queryAcademicProblem.getAcademicOffering().getAcademicPersonnel().toString());
            this.nameEducationalExperienceTextField.setText(this.queryAcademicProblem.getAcademicOffering().getEducationalExperience().getName());
            this.numberStudentsTextField.setText(Integer.toString(this.queryAcademicProblem.getNumberOfStudents()));
            if(this.queryAcademicProblem.getAcademicProblemFollowUp().getIdAcademicProblemFollowUp()!=0){
                this.dateFollowUpLabel.setText(this.queryAcademicProblem.getAcademicProblemFollowUp().getDate().toString()); //Validar Parseador
                this.descriptionFollowUpTextArea.setText(this.queryAcademicProblem.getAcademicProblemFollowUp().getDescription());
            }else{
                if(sessionInformation.getAcademicPersonnel().getUser().getRole().getIdRole() == Roles.CAREER_HEAD_ID_ROLE){
                    this.callWindowRegisterAcademicProblemFollowUp();
                }
            }
            
            this.checkButtons();
        }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAcademicProblemFollowUpFXML.fxml"));
        try{
            Parent root = loader.load();       
            ModifyAcademicProblemFollowUpFXMLController controller = loader.getController();
            Scene logAcademicTutorshipDatesView = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(logAcademicTutorshipDatesView);
            controller.configureView(this.queryAcademicProblem);
            stage.setTitle("Editar seguimiento a problemática académica");
            stage.show();          
        }catch (IOException exception){
            MessagesAlerts.showFailureLoadWindow();
        }

    
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QueryFollowUpOnAcademicProblemsListFXML.fxml"));
        try{
            Parent root = loader.load();
            Scene queryFollowUpOnAcademicProblemsList = new Scene(root);
            QueryFollowUpOnAcademicProblemsListFXMLController controller = 
                    loader.getController();
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsList);
            controller.configureView();
            stage.setTitle("Lista de Problemáticas Académicas");
            stage.show();
        }catch(IOException ioException){
            MessagesAlerts.showFailureLoadWindow();
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
            stage.setTitle("Registro de Seguimiento A Problemática Académica");
            stage.show();
        }catch(IOException ioException){
           MessagesAlerts.showFailureLoadWindow();
        }
    }
}
