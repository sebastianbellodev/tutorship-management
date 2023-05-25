package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicPersonnelDAO;
import academictutorshipmanagement.model.dao.AcademicTutorshipReportDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.dao.StudentDAO;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.AcademicTutorshipReport;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.MessagesAlerts;
import academictutorshipmanagement.utilities.documentformat.AssistanceFormat;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class QueryAttendanceGroupingByAcademicTutorFXMLController implements Initializable {

    @FXML
    private ComboBox cbb_schoolPeriod;
    @FXML
    private ComboBox cbb_academicPersonnel;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField fileNameTextField;
    
    private SchoolPeriod usablePeriod;
    private AcademicPersonnel usablePersonnel;   
    private AcademicPersonnel actualAacademicPersonnel;    
    private ArrayList<Student> students;
    private int actualSession;
    private ArrayList<Student> studentsName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        students = new ArrayList<>();
    }    
    
    public void configureView(AcademicPersonnel academicPersonnel){
        this.actualAacademicPersonnel = academicPersonnel;
        try {
            this.loadSchoolPeriod();
        } catch (SQLException sqlException) {
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }
    
    private void loadSchoolPeriod()throws SQLException{
        this.cbb_schoolPeriod.setItems(this.getSchoolPeriod());
        cbb_schoolPeriod.valueProperty().addListener((ov, oldValue, newValue) -> {
            usablePeriod = (SchoolPeriod) newValue;
            try {
                this.loadAcademicPersonnels();
            } catch (SQLException sqlException) {
                MessagesAlerts.showDataBaseLostConnectionAlert();
            }
        });
    }
    
    private ObservableList<SchoolPeriod> getSchoolPeriod()throws SQLException{
        ObservableList<SchoolPeriod> schoolPeriodList = FXCollections.observableArrayList();
        ArrayList<SchoolPeriod> schoolPeriodQuery = SchoolPeriodDAO.getAllSchoolPeriods();
        for(SchoolPeriod schoolPeriod : schoolPeriodQuery){
            schoolPeriodList.add(schoolPeriod);
        }
        return schoolPeriodList;
    }
    
    private void loadAcademicPersonnels() throws SQLException {
        this.cbb_academicPersonnel.setItems(this.getAcademicPersonnels());
        cbb_academicPersonnel.valueProperty().addListener((ov, oldValue, newValue) -> {
            usablePersonnel = (AcademicPersonnel) newValue;           
        });
    }
    
    private ObservableList<AcademicPersonnel> getAcademicPersonnels()throws SQLException{
        int idSchoolPeriod = usablePeriod.getIdSchoolPeriod();
        int idEducationalProgram = actualAacademicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram();        
        ObservableList<AcademicPersonnel> academicPersonnelsList = FXCollections.observableArrayList();
        
        ArrayList<AcademicPersonnel> academicPersonnelsQuery = 
                AcademicPersonnelDAO.getAcademicPersonnelByTutorship(idSchoolPeriod,  idEducationalProgram);
        
        if(academicPersonnelsQuery.isEmpty()){
            MessagesAlerts.showAlert("No hay personales academicos cargados para el periodo seleccionado", Alert.AlertType.WARNING);
        }else{
            for (AcademicPersonnel academicPersonnel : academicPersonnelsQuery) {
                academicPersonnelsList.add(academicPersonnel);
            }
        }
        return academicPersonnelsList;
    }
    
    private void checkEmptyFields() throws DataFormatException{
        if(this.fileNameTextField.getText().isEmpty() || usablePeriod == null || usablePersonnel == null){
            throw new DataFormatException();        
        } 
    }
        
    @FXML
    private void cancelButtonClick(ActionEvent event) {
        closeWindow();
    }
    
    private void closeWindow(){
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
        stage.close(); 
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {
        FileChooser save = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files","* pdf");
        save.getExtensionFilters().add(extFilter);
        try{
            this.checkEmptyFields();
            save.setInitialFileName(this.fileNameTextField.getText()+".pdf");
            File file = save.showSaveDialog(null);
            if(file != null){
                this.saveFile(file);
            }
        }catch(DataFormatException dfException){
            MessagesAlerts.showBlankFieldsAlert();
        }
    }
    
    private void saveFile(File file){
        int idSchoolPeriod = usablePeriod.getIdSchoolPeriod();
        int idAcademicPersonnel = usablePersonnel.getIdAcademicPersonnel();
        try{    
            AssistanceFormat assistanceFormat = new AssistanceFormat();          
            assistanceFormat.addAsistance(usablePersonnel, this.getAsistance(idSchoolPeriod, idAcademicPersonnel));
            ArrayList<Student> studentsNames = getStudentsByAcademicPersonnel();
            for(Student student : studentsNames){
                assistanceFormat.addStudents(student);
            }
            getAllStudentsByAcademicTutor(idSchoolPeriod, idAcademicPersonnel, assistanceFormat);                               
            assistanceFormat.generateTutorDocument(file);
            MessagesAlerts.showAlert("Se ha guardado con exito la lista de asistencia", Alert.AlertType.INFORMATION);
            this.closeWindow();
        }catch(IOException ioException){
            MessagesAlerts.showAlert("Ha ocurrido un error al momento de almacenar el archivo", Alert.AlertType.INFORMATION);
        }catch(SQLException sqlException){
            MessagesAlerts.showDataBaseLostConnectionAlert();
        }
    }
    
    private ArrayList<Integer> getAsistance(int idSchoolPeriod, int idAcademicPersonnel) throws SQLException{
        return AcademicTutorshipReportDAO.getAsistancesOfAcademicTutorShipReport(idSchoolPeriod, idAcademicPersonnel);
    }
    
    private void getAllStudentsByAcademicTutor(int idSchoolPeriod, int idAcademicPersonnel, AssistanceFormat assistanceFormat){
        ArrayList<AcademicTutorshipReport> academicTutorshipReportsResultSet = AcademicTutorshipReportDAO.getAcademicTutorshipReports(idSchoolPeriod, idAcademicPersonnel);        
        this.getStudents(academicTutorshipReportsResultSet, assistanceFormat);
    }
    
    private void getStudents(ArrayList<AcademicTutorshipReport> academicTutorshipReport, AssistanceFormat assistanceFormat){
        for(AcademicTutorshipReport academicTutorshipReports : academicTutorshipReport){
            ArrayList<Student> studentsResultSet = StudentDAO.getStudentsByAcademicTutorshipReport(academicTutorshipReports.getIdAcademicTutorshipReport());
            loadAllStudents(studentsResultSet, academicTutorshipReports, assistanceFormat);
        }             
    }
    
    private void loadAllStudents(ArrayList<Student> studentsResultSet, AcademicTutorshipReport academicTutorshipReports, AssistanceFormat assistanceFormat){      
        int session = this.actualSession = academicTutorshipReports.getAcademicTutorship().getAcademicTutorshipSession().getIdAcademicTutorshipSession();
        for (Student student : studentsResultSet) {         
            assistanceFormat.addAllStudents(student, session);
        }    
    }               
    
    private ArrayList<Student> getStudentsByAcademicPersonnel() throws SQLException{
       return StudentDAO.getStudentsByAcademicPersonnel(actualAacademicPersonnel.getUser().getEducationalProgram().getIdEducationalProgram(), usablePersonnel.getIdAcademicPersonnel());        
    }        
}
