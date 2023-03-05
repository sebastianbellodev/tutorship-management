/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class AcademicProblem {

    private String title;
    private String description;
    private int numberOfStudents;
    private AcademicOffering academicOffering;

    public AcademicProblem() {
    }

    public AcademicProblem(String title, String description, int numberOfStudents) {
        this.title = title;
        this.description = description;
        this.numberOfStudents = numberOfStudents;
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

    public AcademicOffering getAcademicOffering() {
        return academicOffering;
    }

    public void setAcademicOffering(AcademicOffering academicOffering) {
        this.academicOffering = academicOffering;
    }
    
}