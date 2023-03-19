package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicTutorshipSessionDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class LogAcademicTutorshipDatesFXMLController implements Initializable {

    private SchoolPeriod usablePeriod;
    
    private boolean OperationResult;
    
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_cancel;
    @FXML
    private ComboBox cbb_schoolPeriod;
    @FXML
    private ComboBox cbb_tutorshipSession;
    @FXML
    private DatePicker dp_startDate;
    @FXML
    private DatePicker dp_endDate;
    @FXML
    private DatePicker dp_reportDate;   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
            setSchoolPeriod();
        } catch (SQLException ex) {
            Logger.getLogger(LogAcademicTutorshipDatesFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void setSchoolPeriod() throws SQLException{        
        SchoolPeriodDAO schoolPeriodDAO = new SchoolPeriodDAO();
        ObservableList<SchoolPeriod> periodOptions = schoolPeriodDAO.getAllPeriods();
        if(periodOptions.isEmpty()){
            alerts(0);
        }else{
            this.cbb_schoolPeriod.setItems(periodOptions);
            cbb_schoolPeriod.valueProperty().addListener((ov, oldValue, newValue) -> {
                    usablePeriod = (SchoolPeriod) newValue;      
                    setSessions();
                    verifyTutorships();
            });       
        }
    }
    
    private void setSessions(){
       ObservableList<Integer> sessions = FXCollections.observableArrayList();
       sessions.addAll(1,2,3);
       cbb_tutorshipSession.setItems(sessions);
    }
    
    private void verifyTutorships(){
        AcademicTutorshipSessionDAO tutorshipSession = new AcademicTutorshipSessionDAO();
        ObservableList<AcademicTutorshipSession> response = tutorshipSession.verifyTutorships(usablePeriod.getIdSchoolPeriod());          
        if(response.size() == 0 || response == null){
            OperationResult = true;
        }else{
            OperationResult = false;
            alerts(3);
        }
    }
    
    private void clear(){
        try{
            cbb_schoolPeriod.valueProperty().set(null);
        }catch(NullPointerException exception){
            System.err.println("Reinicio de valores");
        }
            cbb_tutorshipSession.valueProperty().set(null);
            dp_startDate.setValue(null);
            dp_endDate.setValue(null);
            dp_reportDate.setValue(null);
        
    }

    @FXML
    private void saveDates(ActionEvent event) {
        AcademicTutorshipSessionDAO tutorshipSessionDAO = new AcademicTutorshipSessionDAO();
        Utilities utilities = new Utilities();
     
        if(usablePeriod == null){
            alerts(2);
        }else{
            if(dp_startDate.getValue() == null || dp_endDate.getValue() == null || dp_reportDate.getValue() == null){
                alerts(1);
            }
        }
        
        int session = ((cbb_tutorshipSession.getSelectionModel().getSelectedIndex()) + 1) ;  
        Date starDateSession = Date.valueOf(dp_startDate.getValue());
        Date endDateSession = Date.valueOf(dp_endDate.getValue());
        Date reportDate = Date.valueOf(dp_reportDate.getValue());
 
        if(starDateSession.after(usablePeriod.getStartDate()) && starDateSession.before(usablePeriod.getEndDate())){
            if(endDateSession.after(usablePeriod.getStartDate()) && endDateSession.before(usablePeriod.getEndDate())){
                if(reportDate.after(usablePeriod.getStartDate()) && reportDate.before(usablePeriod.getEndDate())){
                    if(starDateSession.after(endDateSession)){
                        alerts(6);
                    }else{
                        if(OperationResult == true){                               
                            tutorshipSessionDAO.saveNewDates(starDateSession, endDateSession, reportDate, session, usablePeriod.getIdSchoolPeriod());
                            alerts(4);
                            clear();
                        }else{
                            tutorshipSessionDAO.updateDates(starDateSession, endDateSession, reportDate, session, usablePeriod.getIdSchoolPeriod());
                            alerts(4);
                            clear();
                        }
                    }
                }else{
                alerts(5);
                }
            }else{
                alerts(5);
            }
        }else{
            alerts(5);
        }
    }

   
    @FXML
    private void cancel(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialSessionAdministrationMenuFXML.fxml"));
        try{
            Parent root = loader.load();
            Scene queryFollowUpOnAcademicProblemsList = new Scene(root);
            Stage stage = (Stage) this.btn_cancel.getScene().getWindow();
            stage.setScene(queryFollowUpOnAcademicProblemsList);
            stage.show();
        }catch(IOException ioException){
            
        }
    }

    @FXML
    private void getStartDates(ActionEvent event) {
        LocalDate myDate = dp_startDate.getValue();
        try{
            String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }catch(NullPointerException exception){
            System.err.println("Valores reiniciados");
        }
    }

    @FXML
    private void getEndDates(ActionEvent event) {
        LocalDate myDate = dp_endDate.getValue();
        try{
            String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }catch(NullPointerException exception){
            System.err.println("Valores reiniciados");
        }
    }

    @FXML
    private void getReportDates(ActionEvent event) {
        LocalDate myDate = dp_reportDate.getValue();
        try{
            String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }catch(NullPointerException exception){
            System.err.println("Valores reiniciados");
        }
    }
    
     private void alerts(int alerta){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch(alerta){
            case 0: 
                alert.setTitle("Sin periodos");
                alert.setHeaderText("No hay periodos registrados");
                alert.setContentText("Intentelo mas tarde");
                alert.showAndWait();
                Stage stage = (Stage)btn_cancel.getScene().getWindow();
                stage.close();
                break;
            case 1:
                alert.setTitle("Atencion");
                alert.setHeaderText("Fecha no elegida");
                alert.setContentText("Debe de introducir las tres fechas de sesion para poder guardar");
                alert.showAndWait();              
                break;
            case 2: 
                alert.setTitle("Atencion");
                alert.setHeaderText("Periodo no seleccionado");
                alert.setContentText("Debe de seleccionar un periodo para registrar las fechas");
                alert.showAndWait();          
                break;
            case 3:
                alert.setTitle("Atencion");
                alert.setHeaderText("Fechas ya registradas");
                alert.setContentText("Ya se registraron las fechas del periodo seleccionado, asi que se actualizaran estas");
                alert.showAndWait();    
                break;
            case 4:
                alert.setTitle("Fecha registrada");
                alert.setHeaderText("Fecha valida");
                alert.setContentText("La fecha ha sido registrada");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Año o mes ingresado incorrecto");
                alert.setContentText("El año o mes que ingreso no se encuentra en el periodo");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Fecha de cierre invalida");
                alert.setContentText("La fecha de inicio es despues de la fecha de cierre");
                alert.showAndWait();
                break;
            }    
        }
        
    /*
    private validateDates() throws EXception{} //DEfine excepcion que manda si las fehcas no son validas
    
    private logDates(){}; // Pasa las fechas y aqui es donde genera la lógica para ver si guarda y update
    */
}
