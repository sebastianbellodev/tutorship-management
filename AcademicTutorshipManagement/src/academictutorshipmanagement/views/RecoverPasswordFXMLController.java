/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: May 18, 2023.
 * Date of update: May 18, 2023.
 */
package academictutorshipmanagement.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RecoverPasswordFXMLController implements Initializable {

    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField randomSixDigitCodeTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void sendRandomSixDigitCode(ActionEvent event) {
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }
    
    
}