


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created as a mandatory task at UIA, It and Inf.sys
 * @date 2016
 * @author Jorgen
 */
public class Webshop {

    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Supplier> supplierList = new ArrayList<>();
    private HashSet<Customer> customerList = new HashSet<>();

    private PriorityQueue<Order> orderQueue = new PriorityQueue<>();


    private LinkedList<Order> orderList = new LinkedList<>();
    private Random rand = new Random();

    /**
     * Constructor used to add test data and simulate a shopping experience. A
     * call to this is to create a new webshop.
     *
     */
    public Webshop() {

        addAllTestData();
        sleep(1000);
        simulateShopping(2);

    }

    /**
     * The main run method. Creates a new webshop.
     *
     * @param args
     */
    public static void main(String[] args) {
        new Webshop();
    }

    /**
     * Calls a set of methods to simulate a shopping experience. First we
     * retrieve a random customer, this is our current customer. Then we
     * simulate this customer to add items to the cart (currently 5) Then we
     * check the quantity of products in currentCustomers cart if items are in
     * stock, add them to the finalizedOrder list if they are not, restock from
     * supplier and then add to finalizedOrder list Then we place his order and
     * ship out the items.
     */
    public void simulateShopping(int amount) {
        for (int sim = 0; sim < amount; sim++) {
            Customer currentCustomer = retrieveRandomCustomer();

            System.out.println("\nHello " + currentCustomer.getName()
                    + "! What do you need?");

            sleep(2000);


            //TODO Implement Customer.AddToCart(int amount) method in Customer.java
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                selectedProducts.add(retrieveRandomProduct());
            }

            sleep(2000);

            System.out.println("\nOrder Details: ");
            //Lag en ordre med den nåværende kunden og alle hans produkter
            Order currentOrder = createOrder(currentCustomer, selectedProducts);

            System.out.println("\nWe will now place your order and check if all"
                    + " products are in store!");
            placeOrder(currentOrder);

            shipOrder(orderQueue);

            sleep(2000);

            System.out.println("\nThank you, and have a nice day!");


        }
    }

    /**
     * Here we actually create the order. First we make a list of OrderDetails
     * which we add a product from the cart and a random quantity to.
     *
     * @param currentCustomer The Customer that is ordering items
     * @param inCartList The list of products the customer wants
     * @return The current order
     */
    public Order createOrder(Customer currentCustomer, ArrayList<Product> inCartList) {
        //Create a list to contain all orderDetais for an order
        LinkedList<OrderDetails> orderDetails = new LinkedList<>();

        //For every product in the cart, create new OrderDetails with quantities
        for (Product productInCart : inCartList) {
            //Add products in cart a list for use in BackOrder
            //generate a random int 1-3 to be the product quantity in the order
            int quantityInOrder = rand.nextInt(4) + 1;
            System.out.println(productInCart.getName()
                    + ", Quantity: " + quantityInOrder);
            orderDetails.add(new OrderDetails(productInCart, quantityInOrder));
        }
        //Create a new order with the current customer and all orderDetails
        Order currentOrder = new Order(currentCustomer, orderDetails);
        //Return this order
        return currentOrder;
    }

    /**
     * This order takes the current order and check if all of it's products are
     * in stock. If they are change quantity of the products in the store print
     * out some useful information if not call a method placeBackOrder() that
     * resupplies from the supplier
     *
     * TODO replace if/else with: while(!inStock) execute else, then add the if
     * part under there.
     * TODO Find a way to remove duplicate products from the current order
     * TODO Make the if(inStock) rerun after executing the 'else' part.
     * TODO add a list of your total order to display to the customer
     *
     * @param order
     */
    public void placeOrder(Order order) {
        boolean inStock = checkInStock(order);

        ArrayList<Product> needsBackOrder = new ArrayList<>();

        for (OrderDetails od : order.getOrderDetails()) {

            if(inStock) {
                System.out.println("------BEFORE");
                System.out.println("InStore Quantity    " + od.getProduct().getQuantity());
                System.out.println("InOrder Quantity  - " + od.getQuantity());
                //Change instore quantity with quantity of the order
                od.getProduct().setQuantity(od.getProduct().getQuantity()
                        - od.getQuantity());
                System.out.println("------ AFTER");
                System.out.println("InStore Quantity    " + od.getProduct().getQuantity());
                System.out.println("");

//                orderQueue.add(order);

            } else {
                System.out.println("\nProduct not in store! Let me ask our suppliers "
                        + "if they have anything left of: " + od.getProduct().getName());

                sleep(2000);

                System.out.println("Current instore Quantity: " +
                        od.getProduct().getQuantity());

                //Add product to a list of products that are being sent to resupply
                needsBackOrder.add(od.getProduct());

                //Create a BackOrder with the list of Products
                createBackOrder(needsBackOrder);

                System.out.println("After Resupplying Quantity: " +
                        od.getProduct().getQuantity());


            }
//            while(!inStock){ Might implement this version
//                inStock = true;
//            }


        }
    }

    /**
     * Checks if the list of Products that needs to be restocked has content,
     * then sends a request to Supplier to process the BackOrder. (which
     * adds 10 of each item)
     *
     * TODO Make it so that only items that needs resupply with get the +10 quantity
     *
     * TODO When a supplier is found, print out it's name and make sure that is
     * the supplier that refills the product (with prints).
     *
     * @param resupplyList
     * @return
     */
    public BackOrder createBackOrder(ArrayList<Product> resupplyList){

        BackOrder backOrder = null;

        ArrayList<OrderDetails> backOrderLines = new ArrayList<>();
        if(resupplyList.size() > 0){

            for(Product p : resupplyList){
                System.out.println("For every product that needs resupply");
                OrderDetails od = new OrderDetails(p);
                backOrderLines.add(od);
            }
            backOrder = new BackOrder(backOrderLines);
            for(Supplier s : supplierList){
                s.processBackOrder(backOrder);
            }
        }

        return backOrder;
    }

    public void shipOrder(PriorityQueue<Order> queue){
        System.out.println("Your order is being shipped to you! "
                + "\nIMPLEMENT QUEUE OF ORDERS TO BE SHIPPED");

        while (queue.size() > 0) {
            Order order = queue.poll();
            System.out.println("order has been sent");

            if (queue.size() == 1) {
                System.out.println("\nThere is currently " + queue.size() + " order waiting to be shipped");
            } else {
                System.out.println("\nThere are currently " + queue.size() + " orders waiting to be dispatched");
            }

        }
    }

    /**
     * What this method does is to take the Order, iterate through each Product
     * in the OrderDetails and check if the quantity instore is more than the
     * quantity inorder.
     *
     * @param order
     * @return
     */
    private boolean checkInStock(Order order) {

        for (OrderDetails od : order.getOrderDetails()) {
            Product productInOD = od.getProduct();
            int numberOfItemsInOrder = od.getQuantity();
            //If the product in the order is less than the number of items in the order
            if (productInOD.getQuantity() <= numberOfItemsInOrder) {
                return false;
            }
        }
        return true;
    }


//----------FURTHER DOWN IS CODE TO ADD TEST DATA TO THE SYSTEM-----------------

    /**
     * Method to take one Product out of the ArrayList. Used in constructing
     * Orders etc.
     *
     * @return Returns a random Customer from the already created list of
     * Products.
     */
    public Product retrieveRandomProduct() {
        int i = rand.nextInt(productList.size());
        Product randProd = productList.get(i);

        return randProd;
    }

    /**
     * Find a random customer from the customerList.
     * Use HashSet to ensure there is no duplicate. Performs in O(n/2).
     *
     * @return Returns a random Customer from the already created set of
     * Customers.
     */
    public Customer retrieveRandomCustomer() {

        Customer customer = null;
        int size = customerList.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (Customer c : customerList) {
            if (i == item) {
                customer = c;
            }
            i = i + 1;
        }

        return customer;

    }

    public Supplier retrieveRandomSupplier() {

        int i = rand.nextInt(supplierList.size());
        Supplier randsup = supplierList.get(i);

        return randsup;
    }

    /**
     * Add all products, suppliers, customers and orders into one method! This
     * method is all about test data.
     */
    public void addAllTestData() {
        //Suppliers has to be before products, as products need a supplier

        makeProducts(30);
        makeSuppliers();
        makeCustomers(10);

        System.out.println("Printing out all test data:");
        System.out.println("\n---Products---");
        printTestProducts();
        System.out.println("\n---Suppliers---");
        printTestSuppliers();
        System.out.println("\n---Customers---");
        printTestCustomers();
    }

    /**
     * Create random first and last names, add them togheter for names. Then,
     * create random email addresses, based on the names. Create new customers
     * with this information.
     *
     * @param amount The amount of customers to add
     */
    public void makeCustomers(int amount) {
        String[] firstNames = {"Lena", "Olaf", "Tiffany", "Candy", "Lena",
                "Brianna", "Niels", "Ben", "Olaf", "Angela"};

        String[] lastNames = {"Allen", "Martin", "Hall", "Adams", "Dam",
                "Hinrichs", "Fuglestad", "Lee", "Vader", "Kenobi"};

        String[] mails = {"hotmail", "gmail", "fakemail"};

        for (int i = 0; i < amount; i++) {
            String first = firstNames[rand.nextInt(firstNames.length)];
            String last = lastNames[rand.nextInt(lastNames.length)];
            String mail = mails[rand.nextInt(mails.length)];
            String mailAddress = first + "." + last + "@" + mail + ".com";

            customerList.add(new Customer(first + " " + last, mailAddress));

        }
    }

    /**
     * Adds every test supplier from an array to an ArrayList of type Supplier.
     * Also, give each supplier a Product list with half the Products from
     * productList, at random
     *
     */
    public void makeSuppliers() {
        String[] sampleSuppliers = {
                "Sofa Inc.",
                "All-The-Stuff",
                "Thangs & Things",
                "Jansen and Jansen",
                "Murica Corp",
                "Gamers Supply"
        };


        for(String s : sampleSuppliers){ //this happens 6 times:

            //We use sets here as we don't want any duplicates
            HashSet<Product> suppliersProduct = new HashSet<>();

            //add amount items to this list
            for (int j = 0; j < 10 ; j++) {
                suppliersProduct.add(retrieveRandomProduct());
            }

            //Create new suppliers with s, the current supplier and a productlist
            supplierList.add(new Supplier(s, suppliersProduct));
        }
    }

    /**
     * Create new products with random values and add them to the product list.
     *
     * @param amount The number of products added
     */
    public void makeProducts(int amount) {

        for (int j = 0; j < amount; j++) {
            //id, name, quantity
            productList.add(new Product(
                            generateProductID(),
                            generateTestProductName(),
                            rand.nextInt((10) + 1)
                    )
            );

        }
    }

    /**
     * Generates random product ID
     *
     * @return A random int value betweeb 10000 - 99999
     */
    public int generateProductID() {
        int min = 10000;
        int max = 99999;
        int randomID = rand.nextInt((max - min) + 1) + min;

        return randomID;
    }

    /**
     * Generates a product from the list at random. TODO implement a way to
     * never have duplicates!
     *
     * @return A string that should be the product name
     */
    public String generateTestProductName() {
        String[] sampleProducts = {
                "Computer",
                "Scissor",
                "Calculator",
                "Skateboard",
                "Table",
                "Bed",
                "Pillow",
                "Flask",
                "Notebook",
                "Pen",
                "CD",
                "Bench"
        };

        String[] productAdjectives = {
                "Soft",
                "Huge",
                "Colorful",
                "Tiny",
                "Unworldly",
                "Cool",
                "Round",
                "Flat",
                "Blue",
                "Loud",
                "Crazy"
        };

        //Add another String[] with adjectives and a way to remove duplicates
        int i = new Random().nextInt(sampleProducts.length);
        int j = new Random().nextInt(productAdjectives.length);

        String randomProductName = (productAdjectives[j] + " " + sampleProducts[i]);

        return randomProductName;
    }

    public void printTestProducts() {
        for (Product p : productList) {
            System.out.println(p.getName());
        }
    }

    public void printTestSuppliers() {
        for (Supplier s : supplierList) {
            System.out.println(s.getName());
            for(Product p : s.getMyProducts()){
                System.out.println("    - " + p.getName());
            }
        }
    }

    public void printTestCustomers() {
        for (Customer c : customerList) {
            System.out.println(c.getName());
        }
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}