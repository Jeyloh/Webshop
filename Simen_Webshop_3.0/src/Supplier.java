

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sifu on 2/26/16.
 * A class that stores a map of products and quantities.
 */
public class Supplier {
    private TreeMap<Product, Integer> productsInStock = new TreeMap<>();

    Supplier() {

    }

    public TreeMap<Product, Integer> getProductsInStock() {
        return productsInStock;
    }

    //Check the stock to see if the supplier has the desired product and return it, else return null
    public Product findProduct(Product product) {
        for(Map.Entry<Product, Integer> entry : productsInStock.entrySet()) {
            if(product.compareTo(entry.getKey()) == 0) {
                return entry.getKey();
            }
        }
        return null;
    }
    //Check the stock to see the amount of a product available and return that amount, else return 0.
    public int findCount(Product product) {
        for(Map.Entry<Product, Integer> entry : productsInStock.entrySet()) {
            if(product.compareTo(entry.getKey()) == 0) {
                return entry.getValue();
            }
        }
        return 0;
    }
}
