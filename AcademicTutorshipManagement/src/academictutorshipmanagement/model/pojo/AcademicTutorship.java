/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 05, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class AcademicTutorship {

    private int idAcademicTutorship;
    private EducationalProgram educationalProgram;
    private AcademicTutorshipSession academicTutorshipSession;
    private int responseCode;

    public AcademicTutorship() {
    }

    public int getIdAcademicTutorship() {
        return idAcademicTutorship;
    }

    public void setIdAcademicTutorship(int idAcademicTutorship) {
        this.idAcademicTutorship = idAcademicTutorship;
    }

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public AcademicTutorshipSession getAcademicTutorshipSession() {
        return academicTutorshipSession;
    }

    public void setAcademicTutorshipSession(AcademicTutorshipSession academicTutorshipSession) {
        this.academicTutorshipSession = academicTutorshipSession;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
}