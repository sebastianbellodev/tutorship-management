/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: April 18, 2023.
 * Date of update: April 21, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicOfferingDAO;
import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.dao.EducationalProgramDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
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
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyEducationalExperienceFXMLController implements Initializable {

    @FXML
    private ComboBox<EducationalExperience> educationalExperienceComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<EducationalProgram> educationalProgramsTableView;
    @FXML
    private TableColumn educationalProgramNameTableColumn;
    @FXML
    private TableColumn associatedToEducationalProgramTableColumn;
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
    private TableColumn associatedToTableColumn;
    @FXML
    private TableColumn nrcTableColumn;

    private ObservableList<EducationalProgram> educationalPrograms;
    private ObservableList<EducationalExperience> educationalExperiences;
    private ObservableList<AcademicPersonnel> academicPersonnels;
    private ObservableList<AcademicOffering> academicOfferings;

    private SchoolPeriod schoolPeriod;
    private AcademicPersonnel academicPersonnel;
    private EducationalExperience educationalExperience;

    private int idSchoolPeriod;
    private int idEducationalExperience;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        educationalPrograms = FXCollections.observableArrayList();
        educationalExperiences = FXCollections.observableArrayList();
        academicPersonnels = FXCollections.observableArrayList();
        academicOfferings = FXCollections.observableArrayList();
        configureEducationalProgramsTableViewColumns();
        configureAcademicPersonnelTableViewColumns();
        loadEducationalPrograms();
    }

    public void loadEducationalPrograms() {
        ArrayList<EducationalProgram> educationalProgramsResultSet = EducationalProgramDAO.getEducationalPrograms();
        if (!educationalProgramsResultSet.isEmpty()) {
            educationalPrograms.addAll(educationalProgramsResultSet);
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
            Stage stage = (Stage) educationalExperienceComboBox.getScene().getWindow();
            stage.setScene(educationalProgramAdministrationMenuView);
            stage.setTitle("Administración del programa educativo.");
            stage.show();
        } catch (IOException exception) {
            System.err.println("The 'EducationalProgramAdministrationMenuFXML.fxml' file could not be open. Please try again later.");
        }
    }

    public void configureView(SchoolPeriod schoolPeriod, AcademicPersonnel academicPersonnel) {
        this.schoolPeriod = schoolPeriod;
        idSchoolPeriod = schoolPeriod.getIdSchoolPeriod();
        this.academicPersonnel = academicPersonnel;
        loadEducationalExperiences();
    }

    private void loadEducationalExperiences() {
        ArrayList<EducationalExperience> educationalExperiencesResultSet = EducationalExperienceDAO.getEducationalExperiences();
        educationalExperiences.addAll(educationalExperiencesResultSet);
        educationalExperienceComboBox.setItems(educationalExperiences);
        educationalExperienceComboBox.valueProperty().addListener((ObservableValue<? extends EducationalExperience> observable, EducationalExperience oldValue, EducationalExperience newValue) -> {
            if (newValue != null) {
                academicPersonnels.clear();
                academicOfferings.clear();
                educationalExperience = newValue;
                idEducationalExperience = educationalExperience.getIdEducationalExperience();
                String name = newValue.getName();
                nameTextField.setText(name);
                loadEducationalProgramsByEducationalExperience();
                loadAcademicPersonnelByEducationalExperience();
            }
        });
    }

    private void loadEducationalProgramsByEducationalExperience() {
        ArrayList<EducationalProgram> educationalProgramsByEducationalExperience = EducationalProgramDAO.getEducationalProgramsByEducationalExperience(idEducationalExperience);
        this.educationalPrograms.forEach(educationalProgram -> {
            boolean isEnabled = false;
            educationalProgram.setAssociatedTo(isEnabled);
            educationalProgramsByEducationalExperience.forEach(educationalProgramByEducationalExperience -> {
                int idEducationalProgram = educationalProgramByEducationalExperience.getIdEducationalProgram();
                boolean isAssociatedTo = educationalProgram.getIdEducationalProgram() == idEducationalProgram;
                if (isAssociatedTo) {
                    educationalProgram.getAssociatedTo().setDisable(isAssociatedTo);
                    educationalProgram.getAssociatedTo().setStyle("-fx-opacity: 1");
                    educationalProgram.setAssociatedTo(isAssociatedTo);
                }
            });
        });
        educationalProgramsTableView.setItems(educationalPrograms);
    }

    private void loadAcademicPersonnelByEducationalExperience() {
        ArrayList<AcademicPersonnel> academicPersonnelsResultSet = AcademicPersonnelDAO.getAcademicPersonnelByEducationalExperience(idEducationalExperience, idSchoolPeriod);
        academicPersonnels.addAll(academicPersonnelsResultSet);
        this.academicPersonnels.forEach(academicPersonnel -> {
            boolean isAssociatedTo = true;
            academicPersonnel.getAssociatedTo().setDisable(isAssociatedTo);
            academicPersonnel.getAssociatedTo().setStyle("-fx-opacity: 1");
            academicPersonnel.setAssociatedTo(isAssociatedTo);
            loadAcademicOfferingsByEducationalExperience();
        });
    }

    private void loadAcademicOfferingsByEducationalExperience() {
        ArrayList<AcademicOffering> academicOfferingsResultSet = AcademicOfferingDAO.getAcademicOfferingsByEducationalExperience(idEducationalExperience, idSchoolPeriod);
        academicOfferings.addAll(academicOfferingsResultSet);
        academicPersonnels.forEach(academicPersonnel -> {
            academicOfferings.forEach(academicOffering -> {
                int idAcademicPersonnel = academicOffering.getAcademicPersonnel().getIdAcademicPersonnel();
                if (academicPersonnel.getIdAcademicPersonnel() == idAcademicPersonnel) {
                    String nrc = String.valueOf(academicOffering.getNrc());
                    academicPersonnel.setNrc(nrc);
                }
            });
        });
        academicPersonnelTableView.setItems(academicPersonnels);
    }

    private void configureEducationalProgramsTableViewColumns() {
        educationalProgramNameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        associatedToEducationalProgramTableColumn.setCellValueFactory(new PropertyValueFactory("associatedTo"));
    }

    private void configureAcademicPersonnelTableViewColumns() {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        paternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("paternalSurname"));
        maternalSurnameTableColumn.setCellValueFactory(new PropertyValueFactory("maternalSurname"));
        emailAddressTableColumn.setCellValueFactory(new PropertyValueFactory("emailAddress"));
        nrcTableColumn.setCellValueFactory(new PropertyValueFactory("nrc"));
        associatedToTableColumn.setCellValueFactory(new PropertyValueFactory("associatedTo"));
    }

    @FXML
    private void acceptButtonClick(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        if (!name.isEmpty()) {
            if (!educationalExperience.getName().contentEquals(name)) {
                EducationalExperience educationalExperience = EducationalExperienceDAO.checkEducationalExperienceExistence(name);
                boolean isRegistered = educationalExperience != null;
                if (!isRegistered) {
                    this.educationalExperience.setName(name);
                    updateEducationalExperience();
                } else {
                    Utilities.showAlert("La información ingresada corresponde a una experiencia educativa que ya se encuentra registrada en el sistema.\n\n"
                            + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                            Alert.AlertType.WARNING);
                }
            } else {
                updateEducationalExperience();
            }
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private void updateEducationalExperience() {
        int responseCode = EducationalExperienceDAO.updateEducationalExperience(educationalExperience);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            idEducationalExperience = educationalExperience.getIdEducationalExperience();
            Utilities.showAlert("La información se modificó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            assignEducationalExperienceToEducationalPrograms();
            assignEducationalExperienceToAcademicPersonnel();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        goToEducationalProgramAdministrationMenu();
    }

    private void assignEducationalExperienceToEducationalPrograms() {
        educationalPrograms.forEach(educationalProgram -> {
            boolean isAssociatedTo = educationalProgram.getAssociatedTo().isSelected();
            if (isAssociatedTo) {
                int idEducationalProgram = educationalProgram.getIdEducationalProgram();
                EducationalProgramDAO.assignEducationalExperieceToEducationalProgram(idEducationalProgram, idEducationalExperience);
            }
        });
    }

    private void assignEducationalExperienceToAcademicPersonnel() {
        academicPersonnels.forEach(academicPersonnel -> {
            academicOfferings.forEach(academicOffering -> {
                int idAcademicPersonnel = academicOffering.getAcademicPersonnel().getIdAcademicPersonnel();
                if (academicPersonnel.getIdAcademicPersonnel() == idAcademicPersonnel) {
                    String nrc = academicPersonnel.getNrc().getText();
                    if (String.valueOf(academicOffering.getNrc()) != nrc) {
                        academicOffering.setNrc(Integer.valueOf(nrc));
                        AcademicOfferingDAO.updateAcademicOffering(academicOffering);
                    }
                }
            });
        });
    }

    @FXML
    private void deleteEducationalExperienceButtonClick(ActionEvent actionEvent) {
        educationalExperience.setAvailable(Constants.NOT_AVAILABLE);
        int responseCode = EducationalExperienceDAO.updateEducationalExperience(educationalExperience);
        if (responseCode == Constants.CORRECT_OPERATION_CODE) {
            Utilities.showAlert("La información se eliminó correctamente del sistema.\n",
                    Alert.AlertType.INFORMATION);
            goToEducationalProgramAdministrationMenu();
        } else {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelButtonClick(ActionEvent actionEvent) {
        goToEducationalProgramAdministrationMenu();
    }

}