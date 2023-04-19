/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

import javafx.scene.control.CheckBox;

public class Role {    
    
    private int idRole;
    private String name;
    private CheckBox roleByCheckBox;

    public Role() {
        roleByCheckBox = new CheckBox();
    }
    
    public Role(String name) {
        this.name = name;
    }

    public Role(int idRole, String name) {
        this.idRole = idRole;
        this.name = name;
    }
    
    public CheckBox getRoleByCheckBox() {
        return roleByCheckBox;
    }

    public void setRoleByCheckBox(CheckBox roleByCheckBox) {
        this.roleByCheckBox = roleByCheckBox;
    }
    
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
   
}