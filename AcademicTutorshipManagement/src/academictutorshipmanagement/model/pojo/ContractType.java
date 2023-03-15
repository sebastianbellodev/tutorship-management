/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 */
package academictutorshipmanagement.model.pojo;

public class ContractType {
    
    private int idContractType;
    private String name;

    public ContractType() {
    }

    public ContractType(String name) {
        this.name = name;
    }

    public int getIdContractType() {
        return idContractType;
    }

    public void setIdContractType(int idContractType) {
        this.idContractType = idContractType;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
}