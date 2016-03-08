import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by sifu on 2/26/16.
 * A customer using the webshop to buy products.
 */
public class Customer implements Comparable<Customer> {
    private String name;
    private String ID;
    private TreeMap<Product, Integer> cart = new TreeMap<>();

    Customer(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    /**
     * A customer selects a random number of random products.
     * @param productsInStock
     */
    public void selectProducts(TreeMap<Product, Integer> productsInStock) {
        ArrayList<Product> availableProducts = new ArrayList<>(productsInStock.keySet());
        Random random = new Random();
        for (int i = 0; i < random.nextInt(10) + 1; i++) {
            Product randomProduct = availableProducts.get(random.nextInt(availableProducts.size()));
            availableProducts.remove(randomProduct);
            int randomQuantity = random.nextInt(10) + 1;
            cart.put(randomProduct, randomQuantity);
            System.out.println("You added " + randomQuantity +"x " + randomProduct.getName() + " to your cart.");
            Utilities.sleepHere(2000);
        }
    }

    public TreeMap<Product, Integer> getCart() {
        return cart;
    }

    @Override
    public int compareTo(Customer o) {
        return this.getID().compareTo(o.getID());
    }
}
