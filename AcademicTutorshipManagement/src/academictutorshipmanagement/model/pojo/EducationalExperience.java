/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: April 19, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class EducationalExperience {

    private int idEducationalExperience;
    private String name;
    private boolean available;
    private int responseCode;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}