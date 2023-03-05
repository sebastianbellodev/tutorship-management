/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.pojo;

import java.util.ArrayList;

public class AcademicTutorshipReport {
    
    private int idAcademicTutorshipReport;
    private String generalComment;
    private int numberOfStudentsAttending;
    private int numberOfStudentsAtRisk;
    private ArrayList<AcademicProblem> academicProblems;
    private ArrayList<Student> students;
    private AcademicPersonnel academicPersonnel;
    private AcademicTutorship academicTutorship;
    private int responseCode;

    public AcademicTutorshipReport() {
    }

    public AcademicTutorshipReport(String generalComment, int numberOfStudentsAttending, int numberOfStudentsAtRisk) {
        this.generalComment = generalComment;
        this.numberOfStudentsAttending = numberOfStudentsAttending;
        this.numberOfStudentsAtRisk = numberOfStudentsAtRisk;
    }

    public int getIdAcademicTutorshipReport() {
        return idAcademicTutorshipReport;
    }

    public void setIdAcademicTutorshipReport(int idAcademicTutorshipReport) {
        this.idAcademicTutorshipReport = idAcademicTutorshipReport;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    public int getNumberOfStudentsAttending() {
        return numberOfStudentsAttending;
    }

    public void setNumberOfStudentsAttending(int numberOfStudentsAttending) {
        this.numberOfStudentsAttending = numberOfStudentsAttending;
    }

    public int getNumberOfStudentsAtRisk() {
        return numberOfStudentsAtRisk;
    }

    public void setNumberOfStudentsAtRisk(int numberOfStudentsAtRisk) {
        this.numberOfStudentsAtRisk = numberOfStudentsAtRisk;
    }

    public ArrayList<AcademicProblem> getAcademicProblems() {
        return academicProblems;
    }

    public void setAcademicProblems(ArrayList<AcademicProblem> academicProblems) {
        this.academicProblems = academicProblems;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }

    public AcademicTutorship getAcademicTutorship() {
        return academicTutorship;
    }

    public void setAcademicTutorship(AcademicTutorship academicTutorship) {
        this.academicTutorship = academicTutorship;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
}