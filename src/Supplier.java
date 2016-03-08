

import java.util.HashSet;

/**
 *
 * @author Jorgen
 */
public class Supplier {
    
    private String name;
    private HashSet<Product> myProducts;
    
    
    public Supplier(String name){
        this.name = name;
    }
    
    public Supplier(String name, HashSet<Product> myProducts){
        this.name = name;
        this.myProducts = myProducts;
    }

    public HashSet<Product> getMyProducts() {
        return myProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void processBackOrder(BackOrder bo){
                
        for(OrderDetails od : bo.getOrderDetails()){
            for(Product p : myProducts){
                if(od.getProduct().getName().equals(p.getName())){
                    od.getProduct().setQuantity(od.getProduct().getQuantity() + 10);
                }  
            }
        }
    }
}
