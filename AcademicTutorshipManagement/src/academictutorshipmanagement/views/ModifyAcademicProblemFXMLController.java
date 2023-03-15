/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.interfaces.IAcademicProblem;
import academictutorshipmanagement.model.dao.AcademicOfferingDAO;
import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.EducationalExperienceDAO;
import academictutorshipmanagement.model.pojo.AcademicOffering;
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

/**
 * FXML Controller class
 *
 * @author oband
 */
public class ModifyAcademicProblemFXMLController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<EducationalExperience> educationalExperienceComboBox;
    @FXML
    private ComboBox<AcademicPersonnel> academicPersonnelComboBox;
    @FXML
    private Spinner<Integer> numberOfStudentsSpinner;
    @FXML
    private ComboBox<AcademicOffering> nrcComboBox;
    @FXML
    private TextArea descriptionTextArea;

    private ObservableList<EducationalExperience> educationalExperiences;
    private ObservableList<AcademicPersonnel> academicPersonnel;
    private ObservableList<AcademicOffering> academicOfferings;

    private IAcademicProblem academicProblemInterface;
    
    private AcademicProblem academicProblem;
    private int idEducationalProgram;
    private int idSchoolPeriod;
    private int idEducationalExperience;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        educationalExperiences = FXCollections.observableArrayList();
        academicPersonnel = FXCollections.observableArrayList();
        academicOfferings = FXCollections.observableArrayList();
    }

    public void configureView(IAcademicProblem academicProblemInterface, int idSchoolPeriod, int idEducationalProgram, int numberOfStudentsByAcademicPersonnel,AcademicProblem academicProblem) {
        this.academicProblemInterface = academicProblemInterface;
        idSchoolPeriod = idSchoolPeriod;
        idEducationalProgram = idEducationalProgram;
        configureAcademicPersonnelInformation(numberOfStudentsByAcademicPersonnel);
        loadCurrentEducationalExperiencesByEducationalProgram();
    }

    
    public void loadAcademicProblemData(){
        this.titleTextField.setText(this.academicProblem.getTitle());
        this.titleTextField.setText(this.academicProblem.getDescription());
        this.numberOfStudentsSpinner.getValueFactory().setValue(this.academicProblem.getNumberOfStudents());
        this.academicPersonnelComboBox.selectionModelProperty().get().select(this.getIndexAcademicPersonnel());
        //Pendiente seleccion combobox
        //
    }
    
    private int getIndexAcademicPersonnel(){
        int indexReturn = 1;
        int iterator = 0;
        for(AcademicPersonnel academicPersonnel: this.academicPersonnel){
            
            if(this.academicProblem.getAcademicOffering().getAcademicPersonnel().getFullName().matches(academicPersonnel.getFullName())){
                indexReturn = iterator; 
            }
            iterator++;
        }
        return indexReturn;
    }
    
    private void configureAcademicPersonnelInformation(int numberOfStudentsByAcademicPersonnel) {
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.MINIUM_NUMBER_OF_STUDENTS_PER_ACADEMIC_PROBLEM, numberOfStudentsByAcademicPersonnel);
        numberOfStudentsSpinner.setValueFactory(spinnerValueFactory);
    }

    private void loadCurrentEducationalExperiencesByEducationalProgram() {
        ArrayList<EducationalExperience> educationalExperiencesResultSet = EducationalExperienceDAO.getEducationalExperiencesByEducationalProgram(idSchoolPeriod, idEducationalProgram);
        if (educationalExperiencesResultSet.isEmpty()) {
            Utilities.showAlert("No hay conexión con la base de datos.\n\n"
                    + "Por favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
            closePopUpWindow();
        } else {
            educationalExperiences.addAll(educationalExperiencesResultSet);
            educationalExperienceComboBox.setItems(educationalExperiences);
            educationalExperienceComboBox.valueProperty().addListener((ObservableValue<? extends EducationalExperience> observable, EducationalExperience oldValue, EducationalExperience newValue) -> {
                if (newValue != null) {
                    academicPersonnel.clear();
                    academicOfferings.clear();
                    idEducationalExperience = newValue.getIdEducationalExperience();
                    loadAcademicPersonnelByEducationalExperience(idEducationalExperience);
                }
            });
        }
    }

    private void loadAcademicPersonnelByEducationalExperience(int idEducationalExperience) {
        ArrayList<AcademicPersonnel> academicPersonnelsResultSet = AcademicPersonnelDAO.getAcademicPersonnelByEducationalExperience(idSchoolPeriod, idEducationalExperience);
        academicPersonnel.addAll(academicPersonnelsResultSet);
        academicPersonnelComboBox.setItems(academicPersonnel);
        academicPersonnelComboBox.valueProperty().addListener((ObservableValue<? extends AcademicPersonnel> observable, AcademicPersonnel oldValue, AcademicPersonnel newValue) -> {
            if (newValue != null) {
                academicOfferings.clear();
                int idAcademicPersonnel = newValue.getIdAcademicPersonnel();
                loadAcademicOfferingsByAcademicPersonnel(idAcademicPersonnel);
            }
        });
    }

    private void loadAcademicOfferingsByAcademicPersonnel(int idAcademicPersonnel) {
        ArrayList<AcademicOffering> academicOfferingsResultSet = AcademicOfferingDAO.getAcademicOfferings(idEducationalExperience, idAcademicPersonnel, idSchoolPeriod);
        academicOfferings.addAll(academicOfferingsResultSet);
        nrcComboBox.setItems(academicOfferings);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (!validateEmptyFields()) {
            String title = titleTextField.getText();
            String description = descriptionTextArea.getText();
            int numberOfStudents = numberOfStudentsSpinner.getValue();
            AcademicProblem academicProblem = new AcademicProblem(title, description, numberOfStudents);
            AcademicOffering academicOffering = nrcComboBox.getValue();
            academicProblem.setAcademicOffering(academicOffering);
            academicProblemInterface.configureAcademicProblem(academicProblem);
            Utilities.showAlert("La problemática académica se actualizó correctamente en el Reporte de Tutorías Académicas.\n",
                    Alert.AlertType.INFORMATION);
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
