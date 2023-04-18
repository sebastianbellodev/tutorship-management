/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: April 18, 2023.
 * Date of update: April 18, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.EducationalProgram;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LogEducationalExperienceFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<EducationalProgram> educationalProgramsTableView;
    @FXML
    private TableColumn educationalProgramTableColumn;
    @FXML
    private TableColumn associatedToTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }    

    @FXML
    private void acceptButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent actionEvent) {
    }
    
}