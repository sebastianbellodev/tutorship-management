/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: April 18, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class QueryEducationalExperienceFXMLController implements Initializable {

    @FXML
    private ComboBox<EducationalExperience> educationalExperienceComboBox;
    @FXML
    private TableView<AcademicPersonnel> academicPersonnelTableView;
    @FXML
    private TableColumn nameTableColumn;
    @FXML
    private TableColumn paternalSurnameTableColumn;
    @FXML
    private TableColumn maternalSurnameTableColumn;
    @FXML
    private TableColumn emailAddressTableColumn;
    @FXML
    private TableColumn nrcTableColumn;

    private ObservableList<EducationalExperience> educationalExperiences;
    private ObservableList<AcademicOffering> academicOfferings;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        educationalExperiences = FXCollections.observableArrayList();
        configureAcademicPersonnelTableViewColumns();
    }

    private void configureAcademicPersonnelTableViewColumns() {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        paternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("paternalSurname"));
        maternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("maternalSurname"));
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory("emailAddress"));
        nrcTableColumn.setCellValueFactory(new PropertyValueFactory("nrc"));
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        this.academicPersonnel = academicPersonnel;
        loadEducationalExperiencesByEducationalProgram();
    }

    private void loadEducationalExperiencesByEducationalProgram() {
        int idSchoolPeriod = schoolPeriod.getIdSchoolPeriod();
        int idEducationalProgram = academicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram();
        ArrayList<EducationalExperience> educationalExperiencesResultSet = EducationalExperienceDAO.getEducationalExperiencesByEducationalProgram(idSchoolPeriod, idEducationalProgram);
        if (!educationalExperiencesResultSet.isEmpty()) {
            educationalExperiences.addAll(educationalExperiencesResultSet);
            educationalExperienceComboBox.setItems(educationalExperiences);
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
            Stage stage = (Stage) academicPersonnelTableView.getScene().getWindow();
            stage.setScene(educationalProgramAdministrationMenuView);
            stage.setTitle("Administración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent actionEvent) {
        goToEducationalProgramAdministrationMenu();
    }

}