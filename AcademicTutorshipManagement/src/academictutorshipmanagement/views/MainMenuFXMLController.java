/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class MainMenuFXMLController implements Initializable {

    private User user;
    private EducationalProgram educationalProgram;
    private Role role;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configureView(EducationalProgram educationalProgram, Role role, User user) {
        this.educationalProgram = educationalProgram;
        this.role = role;
        this.user = user;
    }

}