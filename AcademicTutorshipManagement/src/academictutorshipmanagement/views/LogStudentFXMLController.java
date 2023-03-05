/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academictutorshipmanagement.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sebtr
 */
public class LogStudentFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField firstSurnameTextField;
    @FXML
    private TextField secondSurnameTextField;
    @FXML
    private TextField registrationNumberTextField;
    @FXML
    private TextField educativeProgramTextField;
    @FXML
    private TextField emailAddressTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }
    
}
