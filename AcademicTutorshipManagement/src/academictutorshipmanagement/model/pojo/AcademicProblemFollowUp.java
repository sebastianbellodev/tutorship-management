/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

import java.sql.Date;

public class AcademicProblemFollowUp {
    
    private int idAcademicProblemFollowUp;
    private String description;
    private Date date;
    

    public AcademicProblemFollowUp() {
    }

    public AcademicProblemFollowUp(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    public int getIdAcademicProblemFollowUp() {
        return idAcademicProblemFollowUp;
    }

    public void setIdAcademicProblemFollowUp(int idAcademicProblemFollowUp) {
        this.idAcademicProblemFollowUp = idAcademicProblemFollowUp;
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
    
}