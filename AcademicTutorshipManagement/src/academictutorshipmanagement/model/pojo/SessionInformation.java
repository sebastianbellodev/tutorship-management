package academictutorshipmanagement.model.pojo;

public class SessionInformation {
    SchoolPeriod currentSchoolPeriod = new SchoolPeriod();
    AcademicPersonnel academicPersonnel = new AcademicPersonnel();
    private static SessionInformation sessionInformation = null;

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }

    public SchoolPeriod getCurrentSchoolPeriod() {
        return currentSchoolPeriod;
    }

    public void setCurrentSchoolPeriod(SchoolPeriod currentSchoolPeriod) {
        this.currentSchoolPeriod = currentSchoolPeriod;
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