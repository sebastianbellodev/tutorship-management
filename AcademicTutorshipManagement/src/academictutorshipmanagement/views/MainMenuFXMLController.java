/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class MainMenuFXMLController implements Initializable {

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(User user) {
        this.user = user;
    }

}