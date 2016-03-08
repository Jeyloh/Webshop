

/**
 *
 * @author Jorgen
 */
public class OrderDetails {
    
    //TODO If HashMap doesn't work, replace all with int 
    //and change productID to productID
    private Product product;
    private int quantity;
    
    
    /**
     * For Orders
     * @param product
     * @param quantity 
     */
    public OrderDetails(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        
    }
    
    /**
     * For BackOrdering
     * @param product
     * @param quantity 
     */
    public OrderDetails(Product product){
        this.product = product;
        
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
