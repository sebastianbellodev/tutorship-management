/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

import java.util.Date;

public class AcademicProblemFollowUp {
    
    private String description;
    private Date date;
    private AcademicProblem academicProblem;

    public AcademicProblemFollowUp() {
    }

    public AcademicProblemFollowUp(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AcademicProblem getAcademicProblem() {
        return academicProblem;
    }

    public void setAcademicProblem(AcademicProblem academicProblem) {
        this.academicProblem = academicProblem;
    }
    
}