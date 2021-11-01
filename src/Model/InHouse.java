package Model;
/**
 * @author Gabriel Fernandez Patak
 */
public class InHouse extends Part{
    public int machineId;

    /**
     * Constructor for the class
     * @param id the part id
     * @param name the part name
     * @param price part price
     * @param stock part stock
     * @param min part min stock
     * @param max part max stock
     * @param machineId in house machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets machine ID
     * @param machineId the in house machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
