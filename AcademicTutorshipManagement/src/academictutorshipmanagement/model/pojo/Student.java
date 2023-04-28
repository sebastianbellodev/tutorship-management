/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.model.pojo;

import javafx.scene.control.CheckBox;

public class Student {

    private String registrationNumber;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String emailAddress;
    private EducationalProgram educationalProgram;
    private AcademicPersonnel academicPersonnel;
    private CheckBox attendedBy;
    private CheckBox atRisk;

    public Student() {
        attendedBy = new CheckBox();
        atRisk = new CheckBox();
    }

    public Student(String name, String paternalSurname, String maternalSurname, String emailAddress) {
        this.name = name;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.emailAddress = emailAddress;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternalSurname() {
        return paternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        this.paternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EducationalProgram getEducationalProgram() {
        return educationalProgram;
    }

    public void setEducationalProgram(EducationalProgram educationalProgram) {
        this.educationalProgram = educationalProgram;
    }

    public AcademicPersonnel getAcademicPersonnel() {
        return academicPersonnel;
    }

    public void setAcademicPersonnel(AcademicPersonnel academicPersonnel) {
        this.academicPersonnel = academicPersonnel;
    }
    
    public CheckBox getAttendedBy() {
        return attendedBy;
    }

    public void setAttendedBy(boolean attendedBy) {
        this.attendedBy.setSelected(attendedBy);
    }

    public CheckBox getAtRisk() {
        return atRisk;
    }

    public void setAtRisk(boolean atRisk) {
        this.atRisk.setSelected(atRisk);
    }
    
    public String getFullName(){
        return this.name + " " + this.paternalSurname + " " + this.maternalSurname;    
    }

}