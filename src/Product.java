

import java.util.HashMap;

/**
 *
 * @author Jorgen
 */
public class Product {
    
    private int id;
    private String name;
    private int quantity;
    private Supplier supplier;
    
    private HashMap<Integer, String> productInfo;
    
    
    /**
     * Normal constructor with ID and name
     * @param id
     * @param name
     * @param quantity
     * @param supplier 
     */
    public Product(int id, String name, int quantity, Supplier supplier){
        
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
        
    }
    
    public Product(int id, String name, int quantity){
        
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        
    }

    public int getId() {
        return id;  
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
