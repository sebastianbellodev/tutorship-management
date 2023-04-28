/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.SessionInformation;
import static academictutorshipmanagement.model.pojo.SessionInformation.getSessionInformation;
import academictutorshipmanagement.utilities.DocumentFormat.SignatureList;
import academictutorshipmanagement.utilities.MessagesAlerts;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 * FXML Controller class
 *
 * @author oband
 */
public class SaveSignatureFormatListFXMLController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField fileNameTextField;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        Stage stage = (Stage)this.cancelButton.getScene().getWindow();
        stage.close();
    }
    
    
    private void saveFile(File file){
        SessionInformation sessionInformation = getSessionInformation();
        try{    
            SignatureList signatureList = new SignatureList();
            signatureList.setAcademicPersonnel(sessionInformation.getAcademicPersonnel());
            signatureList.setSchoolPeriod(sessionInformation.getCurrentSchoolPeriod());
            signatureList.setStudentsList(StudentDAO.getStudentsByAcademicPersonnel(
                    sessionInformation.getAcademicPersonnel().getUser().getEducationalProgram().getIdEducationalProgram(), 
                    sessionInformation.getAcademicPersonnel().getIdAcademicPersonnel()));
            signatureList.generateDocument(file);
            MessagesAlerts.showAlert("Se ha guardado con exito el formato de firmas", Alert.AlertType.INFORMATION);
            this.closeWindow();
        }catch(IOException ioException){
            MessagesAlerts.showAlert("Ha ocurrido un error al momento de almacenar el archivo", Alert.AlertType.INFORMATION);
        }
    }
    
    private void checkEmptyFields() throws DataFormatException{
        if(this.fileNameTextField.getText().isEmpty()){
            throw new DataFormatException();        
        } 
    }
}
