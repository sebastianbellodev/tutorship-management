/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.model.pojo;

import javafx.scene.control.CheckBox;

public class EducationalProgram {

    private int idEducationalProgram;
    private String name;
    private boolean associatedTo;
    private CheckBox associatedToCheckBox;

    public EducationalProgram() {
        associatedToCheckBox = new CheckBox();
    }

    public EducationalProgram(String name) {
        this.name = name;
    }

    public EducationalProgram(int idEducationalProgram, String name) {
        this.idEducationalProgram = idEducationalProgram;
        this.name = name;
    }        

    public int getIdEducationalProgram() {
        return idEducationalProgram;
    }

    public void setIdEducationalProgram(int idEducationalProgram) {
        this.idEducationalProgram = idEducationalProgram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAssociatedTo() {
        return associatedTo;
    }

    public void setAssociatedTo(boolean associatedTo) {
        this.associatedTo = associatedTo;
    }

    public CheckBox getAssociatedToCheckBox() {
        return associatedToCheckBox;
    }

    public void setAssociatedToCheckBox(CheckBox associatedToCheckBox) {
        this.associatedToCheckBox = associatedToCheckBox;
    }

    @Override
    public String toString() {
        return getName();
    }

}