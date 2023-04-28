/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 02, 2023.
 */
package academictutorshipmanagement.model.pojo;

import javafx.scene.control.CheckBox;

public class AcademicPersonnel {
    
    private int idAcademicPersonnel;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String emailAddress;
    private CheckBox associatedTo;
    private User user;
    private ContractType contractType;
    private int responseCode;
    
    public AcademicPersonnel() {
        associatedTo = new CheckBox();
    }

    public AcademicPersonnel(String name, String paternalSurname, String maternalSurname, String emailAddress) {
        this.name = name;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.emailAddress = emailAddress;
    }

    public int getIdAcademicPersonnel() {
        return idAcademicPersonnel;
    }

    public void setIdAcademicPersonnel(int idAcademicPersonnel) {
        this.idAcademicPersonnel = idAcademicPersonnel;
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

    public CheckBox getAssociatedTo() {
        return associatedTo;
    }

    public void setAssociatedTo(boolean associatedTo) {
        this.associatedTo.setSelected(associatedTo);
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getFullName(){
        return this.paternalSurname + " " + this.maternalSurname + " " + this.name;
    }
            
    @Override
    public String toString() {
        return getName() + " " + getPaternalSurname() + " " + getMaternalSurname();
    }
    
}