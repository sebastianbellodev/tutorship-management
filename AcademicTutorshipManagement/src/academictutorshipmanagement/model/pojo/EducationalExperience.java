/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 04, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class EducationalExperience {

    private int idEducationalExperience;
    private String name;

    public EducationalExperience() {
    }

    public EducationalExperience(String name) {
        this.name = name;
    }

    public int getIdEducationalExperience() {
        return idEducationalExperience;
    }

    public void setIdEducationalExperience(int idEducationalExperience) {
        this.idEducationalExperience = idEducationalExperience;
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