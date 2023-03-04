/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.model.pojo;

import academictutorshipmanagement.utilities.Utilities;
import java.util.Date;

public class AcademicTutorshipSession {

    private int idAcademicTutorshipSession;
    private Date startDate;
    private Date endDate;
    private Date closingDateReportSubmission;
    private int sessionNumber;
    private int responseCode;

    public AcademicTutorshipSession() {
    }

    public AcademicTutorshipSession(Date startDate, Date endDate, Date closingDateReportSubmission, int sessionNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.closingDateReportSubmission = closingDateReportSubmission;
        this.sessionNumber = sessionNumber;
    }

    public int getIdAcademicTutorshipSession() {
        return idAcademicTutorshipSession;
    }

    public void setIdAcademicTutorshipSession(int idAcademicTutorshipSession) {
        this.idAcademicTutorshipSession = idAcademicTutorshipSession;
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

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return Utilities.changeDateFormat(getStartDate()) + " - " + Utilities.changeDateFormat(getEndDate());
    }

}