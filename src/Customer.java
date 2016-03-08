

import java.util.ArrayList;

/**
 *
 * @author Jorgen
 */
public class Customer {
    private String name;
    private String mail;
    private Webshop webshop;
    
    
    public Customer(String name, String mail){
        this.name = name;
        this.mail = mail;
    }
    
    /**
     * Simple method that simulates a Customer adding a product to a list of
     * products, also known as the 'shopping cart'
     *
     * @param amount the amount of items we want to add to the cart
     * @return requestedProducts A list of the products in the cart
     */
    public ArrayList<Product> addToCart(int amount) {
        ArrayList<Product> requestedProducts = new ArrayList<>();
        

        for (int i = 0; i < amount; i++) {
            requestedProducts.add(webshop.retrieveRandomProduct());
        }
        
        
        
        return requestedProducts;
    }
    
    public void retrieveRandomProductFromStore(){
        
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
