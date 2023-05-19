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
    private ArrayList<AcademicProblem> academicProblems;

    private IAcademicProblem academicProblemInterface;
    
    private AcademicProblem academicProblem;
    private int idEducationalProgram;
    private int idSchoolPeriod;
    private int idEducationalExperience;
    private int index;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        educationalExperiences = FXCollections.observableArrayList();
        academicPersonnel = FXCollections.observableArrayList();
        academicOfferings = FXCollections.observableArrayList();
    }

    public void configureView(IAcademicProblem academicProblemInterface, int idSchoolPeriod, int idEducationalProgram, int numberOfStudentsByAcademicPersonnel,ArrayList<AcademicProblem> academicProblems,int index) {
        this.academicProblemInterface = academicProblemInterface;
        this.idSchoolPeriod = idSchoolPeriod;
        this.idEducationalProgram = idEducationalProgram;
        this.academicProblem = academicProblems.get(index);
        this.index = index;
        this.academicProblems = academicProblems;
        this.configureAcademicPersonnelInformation(numberOfStudentsByAcademicPersonnel);
        this.loadEducationalExperiencesByEducationalProgram();        
        this.loadAcademicProblemData();
    }

    public void loadAcademicProblemData(){
        this.titleTextField.setText(this.academicProblem.getTitle());
        this.descriptionTextArea.setText(this.academicProblem.getDescription());
        this.numberOfStudentsSpinner.getValueFactory().setValue(this.academicProblem.getNumberOfStudents());
        this.educationalExperienceComboBox.getSelectionModel().select(this.searchEducationalExperience(this.academicProblem.getAcademicOffering().getEducationalExperience()));
        this.academicPersonnelComboBox.getSelectionModel().select(this.searchAcademicPersonnel(this.academicProblem.getAcademicOffering().getAcademicPersonnel()));
        this.nrcComboBox.getSelectionModel().select(this.searcAcademicOffering(this.academicProblem.getAcademicOffering()));
    }
    
    private int searchEducationalExperience(EducationalExperience educationalExperience){
        int index = 0;
        int iterator = 0;
        for(EducationalExperience educational: this.educationalExperiences){
            if(educational.getIdEducationalExperience() == educationalExperience.getIdEducationalExperience()){
                index = iterator;
            }
            iterator++;        
        }
        return index;
    }
    
    private int searcAcademicOffering(AcademicOffering academicOffering){
        int index = 0;
        int iterator = 0;
        for(AcademicOffering academic: this.academicOfferings){
            if(academic.getIdAcademicOffering() == academicOffering.getIdAcademicOffering()){
                index = iterator;
            }
            iterator++;        
        }
        return index;
    }
    
    private int searchAcademicPersonnel(AcademicPersonnel academicPersonnel){
        int index = 0;
        int iterator = 0;
        for(AcademicPersonnel academic: this.academicPersonnel){
            if(academic.getIdAcademicPersonnel() == academicPersonnel.getIdAcademicPersonnel()){
                index = iterator;
            }
            iterator++;        
        }
        return index;
    }
    
    private void configureAcademicPersonnelInformation(int numberOfStudentsByAcademicPersonnel) {
        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.MINIUM_NUMBER_OF_STUDENTS_PER_ACADEMIC_PROBLEM, numberOfStudentsByAcademicPersonnel);
        numberOfStudentsSpinner.setValueFactory(spinnerValueFactory);
    }

    private void loadEducationalExperiencesByEducationalProgram() {
        ArrayList<EducationalExperience> educationalExperiencesResultSet = EducationalExperienceDAO.getEducationalExperiencesByEducationalProgram(idEducationalProgram, idSchoolPeriod);
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
        ArrayList<AcademicPersonnel> academicPersonnelsResultSet = AcademicPersonnelDAO.getAcademicPersonnelByEducationalExperience(idEducationalExperience, idSchoolPeriod);
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
        ArrayList<AcademicOffering> academicOfferingsResultSet = AcademicOfferingDAO.getAcademicOfferingsByAcademicPersonnel(idEducationalExperience, idSchoolPeriod, idAcademicPersonnel);
        academicOfferings.addAll(academicOfferingsResultSet);
        nrcComboBox.setItems(academicOfferings);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (!validateEmptyFields()) {
            String title = titleTextField.getText();
            String description = descriptionTextArea.getText();
            int numberOfStudents = numberOfStudentsSpinner.getValue();
            this.academicProblem.setDescription(description);
            this.academicProblem.setTitle(title);
            this.academicProblem.setNumberOfStudents(numberOfStudents);
            AcademicOffering academicOffering = nrcComboBox.getValue();
            academicOffering.setAcademicPersonnel(this.academicPersonnelComboBox.getValue());
            academicOffering.setEducationalExperience(this.educationalExperienceComboBox.getValue());
            this.academicProblem.setAcademicOffering(academicOffering);
            this.academicProblems.set(this.index, academicProblem);
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
                || nrcComboBox.getValue() == null
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