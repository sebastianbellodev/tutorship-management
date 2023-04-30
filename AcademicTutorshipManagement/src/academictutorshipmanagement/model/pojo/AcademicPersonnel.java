/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: April 21, 2023.
 */
package academictutorshipmanagement.model.pojo;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class AcademicPersonnel {

    private int idAcademicPersonnel;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String emailAddress;
    private CheckBox associatedTo;
    private TextField nrc;
    private User user;
    private ContractType contractType;
    private int responseCode;

    public AcademicPersonnel() {
        associatedTo = new CheckBox();
        nrc = new TextField();
        nrc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    nrc.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public AcademicPersonnel(String name, String paternalSurname, String maternalSurname, String emailAddress) {
        this.name = name;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.emailAddress = emailAddress;
    }

    public AcademicPersonnel(int idAcademicPersonnel, String name, String paternalSurname, String maternalSurname, String emailAddress) {
        this.idAcademicPersonnel = idAcademicPersonnel;
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

    public TextField getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc.setText(nrc);
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