package Model;
/**
 * @author Gabriel Fernandez Patak
 */
public class Outsourced extends Part{
    public String companyName;

    /**
     * Constructor for outsourced object
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part stock
     * @param min the part min stock
     * @param max the part max stock
     * @param companyName the part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return name of the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method sets the name of the company for the part object.
     * @param companyName the outsourced part company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
