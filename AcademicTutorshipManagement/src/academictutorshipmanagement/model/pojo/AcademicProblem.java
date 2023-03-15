/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 15, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class AcademicProblem {
    private int idAcademicProblem;
    private String title;
    private String description;
    private int numberOfStudents;
    private int idAcademicOffering;
    private AcademicOffering academicOffering;
    private AcademicProblemFollowUp academicProblemFollowUp;

    public AcademicProblem() {
        this.academicOffering = new AcademicOffering();
        this.academicProblemFollowUp = new AcademicProblemFollowUp();
    }

    public AcademicProblem(String title, String description, int numberOfStudents) {
        this.title = title;
        this.description = description;
        this.numberOfStudents = numberOfStudents;
        this.academicOffering = new AcademicOffering();
        this.academicProblemFollowUp = new AcademicProblemFollowUp();
    }

    
    public int getIdAcademicProblem() {
        return idAcademicProblem;
    }

    public void setIdAcademicProblem(int idAcademicProblem) {
        this.idAcademicProblem = idAcademicProblem;
    }
    

    public int getIdAcademicProblem() {
        return idAcademicProblem;
    }

    public void setIdAcademicProblem(int idAcademicProblem) {
        this.idAcademicProblem = idAcademicProblem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getIdAcademicOffering() {
        return idAcademicOffering;
    }

    public void setIdAcademicOffering(int idAcademicOffering) {
        this.idAcademicOffering = idAcademicOffering;
    }

    public AcademicOffering getAcademicOffering() {
        return academicOffering;
    }

    public void setAcademicOffering(AcademicOffering academicOffering) {
        this.academicOffering = academicOffering;
    }

    public AcademicProblemFollowUp getAcademicProblemFollowUp() {
        return academicProblemFollowUp;
    }

    public void setAcademicProblemFollowUp(AcademicProblemFollowUp academicProblemFollowUp) {
        this.academicProblemFollowUp = academicProblemFollowUp;
    }
}