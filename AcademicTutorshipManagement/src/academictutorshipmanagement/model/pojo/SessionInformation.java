package academictutorshipmanagement.model.pojo;

public class SessionInformation {
    SchoolPeriod currentSchoolPeriod = new SchoolPeriod();
    AcademicPersonnel academicPersonnel = new AcademicPersonnel();
    SchoolPeriod schoolPeriod = new SchoolPeriod();
    User user = new User();
    
    private static SessionInformation sessionInformation = null;

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }


    public SchoolPeriod getSchoolPeriod() {
        return schoolPeriod;
    }

    public void setSchoolPeriod(SchoolPeriod schoolPeriod) {
        this.schoolPeriod = schoolPeriod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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