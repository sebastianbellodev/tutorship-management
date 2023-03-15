/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package academictutorshipmanagement.views;

import academictutorshipmanagement.model.dao.AcademicTutorshipSessionDAO;
import academictutorshipmanagement.model.dao.SchoolPeriodDAO;
import academictutorshipmanagement.model.pojo.AcademicTutorshipSession;
import academictutorshipmanagement.model.pojo.SchoolPeriod;
import academictutorshipmanagement.utilities.Utilities;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class LogAcademicTutorshipDatesFXMLController implements Initializable {

    SchoolPeriod usablePeriod;
    
    boolean OperationResult;
    
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
        SchoolPeriod schoolPeriod = new SchoolPeriod();
        ObservableList<SchoolPeriod> periodOptions = schoolPeriodDAO.getAllPeriods();
        
        cbb_schoolPeriod.setItems(periodOptions);
        cbb_schoolPeriod.valueProperty().addListener((ov, oldValue, newValue) -> {
                usablePeriod = (SchoolPeriod) newValue;
                setSessions();
                verifyTutorships(usablePeriod);                                 
        });            
    }
    
    private void setSessions(){
       ObservableList<Integer> sessions = FXCollections.observableArrayList();
       sessions.addAll(1,2,3);
       cbb_tutorshipSession.setItems(sessions);
    }
    
    private void verifyTutorships(SchoolPeriod usableSchoolPeriod){
        AcademicTutorshipSessionDAO TutorshipSession = new AcademicTutorshipSessionDAO();
        ObservableList <AcademicTutorshipSession> response = TutorshipSession.verifyTutorships(usableSchoolPeriod.getIdSchoolPeriod());
        if(response.isEmpty()){
            OperationResult = false;
        }else{
            OperationResult = true;
            setTutorships(response);
        }     
        
    }
    
    private void setTutorships(ObservableList<AcademicTutorshipSession> response){        
        
    }
    
    private void clear(){
        cbb_schoolPeriod.valueProperty().set(null);
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
            //Periodo no seleccionado
        }else{
            if(dp_startDate.getValue() == null || dp_endDate.getValue() == null || dp_reportDate.getValue() == null){
                //Seleccionar todas las fehcas
            }
        }
        
        int session = cbb_tutorshipSession.getSelectionModel().getSelectedIndex();
        Date parseStartDate = utilities.convertLocalDateToDate(dp_startDate.getValue());
        Date parseEndDate = utilities.convertLocalDateToDate(dp_endDate.getValue());
        Date parseReportDate = utilities.convertLocalDateToDate(dp_reportDate.getValue());
        
        if(parseStartDate.after(usablePeriod.getStartDate()) && parseStartDate.before(usablePeriod.getEndDate())){
            if(parseEndDate.after(usablePeriod.getStartDate()) && parseEndDate.before(usablePeriod.getEndDate())){
                if(parseReportDate.after(usablePeriod.getStartDate()) && parseReportDate.before(usablePeriod.getEndDate())){
                    if(parseStartDate.after(parseEndDate)){
                        //Fecha de inicio no puede ser despues de la de cierre
                    }else{
                        if(OperationResult == true){
                            tutorshipSessionDAO.saveDates(parseStartDate, parseEndDate, parseReportDate, session, usablePeriod.getIdSchoolPeriod());
                            //Mensaje de exito
                            System.out.println("EXITO");
                            clear();
                        }else{
                            tutorshipSessionDAO.updateDates(parseStartDate, parseEndDate, parseReportDate, session, usablePeriod.getIdSchoolPeriod());
                            //Mensaje de exito
                            System.out.println("EXITO");
                            clear();
                        }
                    }
                }else{
                //Fecha fuera del periodo
                }
            }else{
                //Fecha fuera del periodo
            }
        }else{
            //Fecha fuera del periodo
        }
    }

   
    @FXML
    private void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getStartDates(ActionEvent event) {
        LocalDate myDate = dp_startDate.getValue();
        String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    @FXML
    private void getEndDates(ActionEvent event) {
        LocalDate myDate = dp_endDate.getValue();
        String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    @FXML
    private void getReportDates(ActionEvent event) {
        LocalDate myDate = dp_reportDate.getValue();
        String formatoDate = myDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
    
}
