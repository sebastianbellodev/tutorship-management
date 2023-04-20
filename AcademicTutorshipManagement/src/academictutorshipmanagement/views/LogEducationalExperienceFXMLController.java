/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: April 18, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LogEducationalExperienceFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<EducationalProgram> educationalProgramsTableView;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn associatedToTableColumn;

    private ObservableList<EducationalProgram> educationalPrograms;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    private int idEducationalExperience;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        educationalPrograms = FXCollections.observableArrayList();
        configureEducationalProgramsTableViewColumns();
        loadEducationalPrograms();
    }

    private void configureEducationalProgramsTableViewColumns() {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        associatedToTableColumn.setCellValueFactory(new PropertyValueFactory("associatedToCheckBox"));
    }

    private void loadEducationalPrograms() {
        ArrayList<EducationalProgram> educationalProgramsResultSet = EducationalProgramDAO.getEducationalPrograms();
        if (!educationalProgramsResultSet.isEmpty()) {
            educationalPrograms.addAll(educationalProgramsResultSet);
            educationalProgramsTableView.setItems(educationalPrograms);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
            goToEducationalProgramAdministrationMenu();
        }
    }
    
    private void goToEducationalProgramAdministrationMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EducationalProgramAdministrationMenuFXML.fxml"));
        try {
            Parent root = loader.load();
            EducationalProgramAdministrationMenuFXMLController educationalProgramAdministrationMenuFXMLController = loader.getController();
            educationalProgramAdministrationMenuFXMLController.configureView(schoolPeriod, academicPersonnel);
            Scene educationalProgramAdministrationMenuView = new Scene(root);
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.setScene(educationalProgramAdministrationMenuView);
            stage.setTitle("Administración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }    

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
    }

    @FXML
    private void acceptButtonClick(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        if (!name.isEmpty()) {
            EducationalExperience educationalExperience = EducationalExperienceDAO.checkEducationalExperienceExistence(name);
            boolean isRegistered = educationalExperience != null;
            if (!isRegistered) {
                educationalExperience = new EducationalExperience();
                educationalExperience.setName(name);
                educationalExperience.setAvailable(Constants.AVAILABLE);
                logEducationalExperience(educationalExperience);
            } else {
                boolean isAvailable = educationalExperience.isAvailable();
                if (!isAvailable) {
                    educationalExperience.setAvailable(Constants.AVAILABLE);
                    updateEducationalExperience(educationalExperience);
                } else {
                    Utilities.showAlert("La información ingresada corresponde a una experiencia educativa que ya se encuentra registrada en el sistema.\n\n"
                            + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                            Alert.AlertType.WARNING);
                }
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private void logEducationalExperience(EducationalExperience educationalExperience) {
        int responseCode = EducationalExperienceDAO.logEducationalExperience(educationalExperience);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            String name = educationalExperience.getName();
            idEducationalExperience = EducationalExperienceDAO.getEducationalExperience(name).getIdEducationalExperience();
            assignEducationalExperienceToEducationalPrograms(false);
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        goToEducationalProgramAdministrationMenu();
    }

    private void assignEducationalExperienceToEducationalPrograms(boolean isUpdate) {
        educationalPrograms.forEach(educationalProgram -> {
            boolean associatedTo = educationalProgram.getAssociatedToCheckBox().isSelected();
            int idEducationalProgram = educationalProgram.getIdEducationalProgram();
            if (isUpdate) {
                EducationalProgramDAO.assignEducationalExperieceToEducationalProgram(idEducationalProgram, idEducationalExperience, associatedTo);
            } else {
                if (associatedTo) {
                    EducationalProgramDAO.assignEducationalExperieceToEducationalProgram(idEducationalProgram, idEducationalExperience);
                }
            }
        });
    }

    private void updateEducationalExperience(EducationalExperience educationalExperience) {
        int responseCode = EducationalExperienceDAO.updateEducationalExperience(educationalExperience);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            idEducationalExperience = educationalExperience.getIdEducationalExperience();
            assignEducationalExperienceToEducationalPrograms(true);
            Utilities.showAlert("La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        goToEducationalProgramAdministrationMenu();
    }

    @FXML
    private void cancelButtonClick(ActionEvent actionEvent) {
        goToEducationalProgramAdministrationMenu();
    }

}