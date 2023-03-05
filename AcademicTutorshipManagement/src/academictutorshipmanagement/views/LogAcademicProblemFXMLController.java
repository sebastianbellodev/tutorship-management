/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 04, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IAcademicProblem;
import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicProblem;
import academictutorshipmanagement.model.pojo.EducationalExperience;
import academictutorshipmanagement.model.pojo.EducationalProgram;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Constants;
import academictutorshipmanagement.utilities.Utilities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogAcademicProblemFXMLController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<EducationalExperience> educationalExperienceComboBox;
    @FXML
    private ComboBox<AcademicPersonnel> academicPersonnelComboBox;
    @FXML
    private Spinner<Integer> numberOfStudentsSpinner;
    @FXML
    private ComboBox<Integer> nrcComboBox;
    @FXML
    private TextArea descriptionTextArea;

    private ObservableList<EducationalExperience> educationalExperiences;
    private ObservableList<AcademicPersonnel> academicPersonnels;
    private ObservableList<Integer> nrcs;
    
    private IAcademicProblem academicProblemInterface;


    private int idEducationalProgram;
    private int idSchoolPeriod;
    private int idEducationalExperience;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        educationalExperiences = FXCollections.observableArrayList();
        academicPersonnels = FXCollections.observableArrayList();
        nrcs = FXCollections.observableArrayList();
    }

    public void configureView(IAcademicProblem academicProblemInterface, SchoolPeriod schoolPeriod, EducationalProgram educationalProgram, int numberOfStudentsByAcademicPersonnel) {
        this.academicProblemInterface = academicProblemInterface;
        idSchoolPeriod = schoolPeriod.getIdSchoolPeriod();
        idEducationalProgram = educationalProgram.getIdEducationalProgram();
        configureAcademicPersonnelInformation(numberOfStudentsByAcademicPersonnel);
        loadCurrentEducationalExperiencesByEducationalProgram();
    }

    private void configureAcademicPersonnelInformation(int numberOfStudentsByAcademicPersonnel) {
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.MINIUM_NUMBER_OF_STUDENTS_PER_ACADEMIC_PROBLEM, numberOfStudentsByAcademicPersonnel);
        numberOfStudentsSpinner.setValueFactory(spinnerValueFactory);
    }

    private void loadCurrentEducationalExperiencesByEducationalProgram() {
        ArrayList<EducationalExperience> educationalExperiencesResultSet = EducationalExperienceDAO.getEducationalExperiencesByEducationalProgram(idSchoolPeriod, idEducationalProgram);
        educationalExperiences.addAll(educationalExperiencesResultSet);
        educationalExperienceComboBox.setItems(educationalExperiences);
        educationalExperienceComboBox.valueProperty().addListener((ObservableValue<? extends EducationalExperience> observable, EducationalExperience oldValue, EducationalExperience newValue) -> {
            if (newValue != null) {
                academicPersonnels.clear();
                nrcs.clear();
                idEducationalExperience = newValue.getIdEducationalExperience();
                loadAcademicPersonnelByEducationalExperience(idEducationalExperience);
            }
        });
    }

    private void loadAcademicPersonnelByEducationalExperience(int idEducationalExperience) {
        ArrayList<AcademicPersonnel> academicPersonnelResultSet = AcademicPersonnelDAO.getAcademicPersonnelByEducationalExperience(idSchoolPeriod, idEducationalExperience);
        academicPersonnels.addAll(academicPersonnelResultSet);
        academicPersonnelComboBox.setItems(academicPersonnels);
        academicPersonnelComboBox.valueProperty().addListener((ObservableValue<? extends AcademicPersonnel> observable, AcademicPersonnel oldValue, AcademicPersonnel newValue) -> {
            if (newValue != null) {
                nrcs.clear();
                int idAcademicPersonnel = newValue.getIdAcademicPersonnel();
                loadNrcByAcademicOfferings(idAcademicPersonnel);
            }
        });
    }

    private void loadNrcByAcademicOfferings(int idAcademicPersonnel) {
        ArrayList<Integer> nrcsResultSet = EducationalExperienceDAO.getNrcByAcademicOffering(idEducationalExperience, idAcademicPersonnel, idSchoolPeriod);
        nrcs.addAll(nrcsResultSet);
        nrcComboBox.setItems(nrcs);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (!validateEmptyFields()) {
            String title = titleTextField.getText();
            String description = descriptionTextArea.getText();
            int numberOfStudents = numberOfStudentsSpinner.getValue();
            AcademicProblem academicProblem = new AcademicProblem(title, description, numberOfStudents);
            EducationalExperience educationalExperience = educationalExperienceComboBox.getValue();
            int nrc = nrcComboBox.getValue();
            educationalExperience.setNrc(nrc);
            academicProblem.setEducationalExperience(educationalExperience);
            AcademicPersonnel academicPersonnel = academicPersonnelComboBox.getValue();
            academicProblem.setAcademicPersonnel(academicPersonnel);
            academicProblemInterface.configureAcademicProblem(academicProblem);
            closePopUpWindow();
        } else {
            Utilities.showAlert("No se puede dejar ningún campo vacío.\n\n"
                    + "Por favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        }
    }

    private boolean validateEmptyFields() {
        return titleTextField.getText().isEmpty()
                || nrcComboBox.getSelectionModel().isEmpty()
                || descriptionTextArea.getText().isEmpty();
    }

    private void closePopUpWindow() {
        Stage stage = (Stage) titleTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closePopUpWindow();
    }

}