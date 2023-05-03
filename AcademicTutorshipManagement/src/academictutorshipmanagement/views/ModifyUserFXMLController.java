/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.SessionInformation;
import academictutorshipmanagement.model.pojo.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class ModifyUserFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paternalSurnameTextField;
    @FXML
    private TextField maternalSurnameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationPasswordField;
    
    AcademicPersonnel academicPersonnel = SessionInformation.getSessionInformation().getAcademicPersonnel();
    User user = SessionInformation.getSessionInformation().getUser();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameTextField.setText(academicPersonnel.getName());
        paternalSurnameTextField.setText(academicPersonnel.getPaternalSurname());
        maternalSurnameTextField.setText(academicPersonnel.getMaternalSurname());
        emailAddressTextField.setText(academicPersonnel.getEmailAddress());
        usernameTextField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
    }    

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }
    
}
