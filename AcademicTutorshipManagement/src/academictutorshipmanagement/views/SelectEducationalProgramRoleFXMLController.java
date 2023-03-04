/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IEducationalProgram;
import academictutorshipmanagement.interfaces.IRole;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;
import academictutorshipmanagement.model.dao.RoleDAO;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.Role;
import academictutorshipmanagement.model.pojo.User;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SelectEducationalProgramRoleFXMLController implements Initializable {

    @FXML
    private ComboBox<EducationalProgram> educationalProgramComboBox;
    @FXML
    private ComboBox<Role> roleComboBox;

    private IEducationalProgram educationalProgramInterface;
    private IRole roleInterface;
    private User user;
    private ObservableList<EducationalProgram> educationalPrograms;
    private ObservableList<Role> roles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        educationalPrograms = FXCollections.observableArrayList();
        roles = FXCollections.observableArrayList();
    }

    public void configureView(IEducationalProgram educationalProgramInterface, IRole roleInterface, User user) {
        this.educationalProgramInterface = educationalProgramInterface;
        this.roleInterface = roleInterface;
        this.user = user;
        try {
            loadEducationalPrograms();
        } catch (SQLException exception) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    private void loadEducationalPrograms() throws SQLException {
        String username = user.getUsername();
        ArrayList<EducationalProgram> educationalProgramsResultSet = EducationalProgramDAO.getEducationalProgramsByUser(username);
        educationalPrograms.addAll(educationalProgramsResultSet);
        educationalProgramComboBox.setItems(educationalPrograms);
        educationalProgramComboBox.valueProperty().addListener(new ChangeListener<EducationalProgram>() {
            @Override
            public void changed(ObservableValue<? extends EducationalProgram> observable, EducationalProgram oldEducationalProgram, EducationalProgram newEducationalProgram) {
                loadRolesByEducationalProgram(newEducationalProgram, username);
            }
        });
    }

    private void loadRolesByEducationalProgram(EducationalProgram educationalProgram, String username) {
        int idEducationalProgram = educationalProgram.getIdEducationalProgram();
        ArrayList<Role> rolesResultSet = RoleDAO.getRolesByEducationalProgram(idEducationalProgram, username);
        roles.clear();
        roles.addAll(rolesResultSet);
        roleComboBox.setItems(roles);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (!validateEmptyFields()) {
            EducationalProgram educationalProgram = educationalProgramComboBox.getSelectionModel().getSelectedItem();
            Role role = roleComboBox.getSelectionModel().getSelectedItem();
            educationalProgramInterface.configureEducationalProgram(educationalProgram);
            roleInterface.configureRole(role);
            closePopUpWindow();
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return educationalProgramComboBox.getSelectionModel().isEmpty() || roleComboBox.getSelectionModel().isEmpty();
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) educationalProgramComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }

}