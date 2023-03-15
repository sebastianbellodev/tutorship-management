/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academictutorshipmanagement.model.pojo;

/**
 *
 * @author oband
 */
public class SessionInformation {
    
    AcademicPersonnel academicPersonnel = new AcademicPersonnel();
    private static SessionInformation sessionInformation = null;

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }

    
    public static SessionInformation getSessionInformation(){
        if(sessionInformation == null){
            sessionInformation = new SessionInformation();
        }
        return sessionInformation;
    }
    
    private SessionInformation(){
        
    }


    
    
}
