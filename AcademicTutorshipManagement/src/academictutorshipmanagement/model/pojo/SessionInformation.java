package academictutorshipmanagement.model.pojo;

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