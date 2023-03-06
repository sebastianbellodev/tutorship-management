/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.model.pojo;

import academictutorshipmanagement.utilities.Utilities;
import java.util.ArrayList;
import java.util.Date;

public class SchoolPeriod {

    private int idSchoolPeriod;
    private Date startDate;
    private Date endDate;
    private ArrayList<AcademicTutorship> academicTutorships;
    private int responseCode;

    public SchoolPeriod() {
    }

    public SchoolPeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getIdSchoolPeriod() {
        return idSchoolPeriod;
    }

    public void setIdSchoolPeriod(int idSchoolPeriod) {
        this.idSchoolPeriod = idSchoolPeriod;
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

    public ArrayList<AcademicTutorship> getAcademicTutorships() {
        return academicTutorships;
    }

    public void setAcademicTutorships(ArrayList<AcademicTutorship> academicTutorships) {
        this.academicTutorships = academicTutorships;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return Utilities.changeDateFormat(getStartDate()) + " - " + Utilities.changeDateFormat(getStartDate());
    }
    
}