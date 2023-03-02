/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

import java.util.Date;

public class AcademicTutorshipSession {
    
    private Date startDate;
    private Date endDate;
    private Date closingDateReportSubmission;
    private int sessionNumber;

    public AcademicTutorshipSession() {
    }

    public AcademicTutorshipSession(Date startDate, Date endDate, Date closingDateReportSubmission, int sessionNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.closingDateReportSubmission = closingDateReportSubmission;
        this.sessionNumber = sessionNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getClosingDateReportSubmission() {
        return closingDateReportSubmission;
    }

    public void setClosingDateReportSubmission(Date closingDateReportSubmission) {
        this.closingDateReportSubmission = closingDateReportSubmission;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }
   
}