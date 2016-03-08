import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by sifu on 2/26/16.
 */
public class SampleDataGenerator {
    private static String[] sampleProductNames = {
            "Chair",
            "Table",
            "Laptop",
            "Keyboard",
            "Monitor",
            "Collar",
            "Tile",
            "Lamp",
            "Toaster",
            "Rope",
            "Lion Biscuit",
            "Apple"
    };

    private static String[] sampleProductAdjectives = {
            "Comfy",
            "Large",
            "Moist",
            "Dried",
            "Yellow",
            "Sturdy",
            "Green",
            "Wet",
            "Juicy",
    };

    private static String[] sampleCustomerNames = {
            "John",
            "Paula",
            "Jason",
            "Rebbecca",
            "Gina",
            "Rudolf",
            "Elvis"
    };

    /**
     * Used by Launcher to fill up the stock of the webshop and all the suppliers.
     * In this version the webshop and suppliers all receive the same products with
     * the same amount per product.
     * @param webshop
     */

   public static void makeSampleData(Webshop webshop) {
       //Make suppliers
       for(int i = 0; i < 10; i++) {
           Supplier s = new Supplier();
           webshop.getSuppliers().add(s);
       }

       TreeMap<Product, Integer> sampleProducts = makeRandomProducts();
       TreeMap<Product, Integer> webshopProducts = webshop.getProductsInStock();
       ArrayList<Supplier> webshopSuppliers = webshop.getSuppliers();

       for(Map.Entry<Product, Integer> entry : sampleProducts.entrySet()) {
           for(Supplier s : webshopSuppliers) {
               s.getProductsInStock().put(entry.getKey(), entry.getValue());
           }
           webshopProducts.put(entry.getKey(), entry.getValue());
       }
   }

    private static TreeMap<Product, Integer> makeRandomProducts() {
        TreeMap<Product, Integer> map =  new TreeMap<>();
        ArrayList<String> usedIDs = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            String ID = makeRandomProductID();
            if (!usedIDs.contains(ID)) {
                Product product = new Product(makeRandomProductID(), makeRandomProductName());
                int integer = (int)(Math.random() * 10);
                map.put(product, integer);
            }
        }
        return map;
    }

    private static TreeMap<Product, Integer> makeRandomProducts(int count) {
        TreeMap<Product, Integer> map =  new TreeMap<>();
        ArrayList<String> usedIDs = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            String ID = makeRandomProductID();
            if (!usedIDs.contains(ID)) {
                Product product = new Product(makeRandomProductID(), makeRandomProductName());
                int integer = (int)(Math.random() * 10);
                map.put(product, integer);
            }
        }
        return map;
    }

    private static TreeMap<Product, Integer> makeRandomProducts(int count, int inStock) {
        TreeMap<Product, Integer> map =  new TreeMap<>();
        ArrayList<String> usedIDs = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            String ID = makeRandomProductID();
            if (!usedIDs.contains(ID)) {
                Product product = new Product(makeRandomProductID(), makeRandomProductName());
                map.put(product, inStock);
            }
        }
        return map;
    }


    public static String makeRandomProductName() {
        Random random = new Random();
        return sampleProductAdjectives[random.nextInt(sampleProductAdjectives.length)] + " " +
                sampleProductNames[random.nextInt(sampleProductNames.length)];
    }

    //Fun with ASCII
    protected static String makeRandomProductID() {
        String ID = "PROD";
        for(int i = 0; i < 4; i++) {
            int r = (int)(Math.random()*10);
            char c = (char)(65 + (int)(Math.random()* 26));
            ID = ID + Integer.toString(r) + c;
        }
        return ID;
    }

    protected static String makeRandomCustomerID() {
        String ID = "CUST";
        for(int i = 0; i < 4; i++) {
            int r = (int)(Math.random()*10);
            char c = (char)(65 + (int)(Math.random()* 26));
            ID = ID + Integer.toString(r) + c;
        }
        return ID;
    }

    protected static String makeRanndomOrderID() {
        String ID = "ORD_";
        for(int i = 0; i < 4; i++) {
            int r = (int)(Math.random()*10);
            char c = (char)(65 + (int)(Math.random()* 26));
            ID = ID + Integer.toString(r) + c;
        }
        return ID;
    }

    protected static String makeRandomCustomerName() {
        Random random = new Random();
        String name = sampleCustomerNames[random.nextInt(sampleCustomerNames.length)];
        return name;
    }
}
