import java.util.*;

/**
 * Created by sifu on 2/26/16.
 * An online shop that customers can order products from.
 */

public class Webshop {
    private TreeMap<Product, Integer> productsInStock = new TreeMap<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private PriorityQueue<Order> waitingOrders = new PriorityQueue<>();

    Webshop() {
        System.out.println("Welcome to webshop!");
    }

    /**
     * Starts a sequence of customers coming to shop.
     *
     * @param count
     */
    public void startShopping(int count) {
        for (int i = 0; i < count; i++) {
            shop();
            Utilities.sleepHere(2000);
        }
        processOrders(waitingOrders);
    }

    /**
     * Enacts a single sequence of a customer selecting products, the webshop checking the stock(and placing backorders
     * if necessary), placing an order and then reviewing it.
     */
    private void shop() {
        Customer customer = new Customer(
                SampleDataGenerator.makeRandomCustomerName(), SampleDataGenerator.makeRandomCustomerID());

        System.out.println("\nHello " + customer.getName() + ". Your Customer ID is " + customer.getID());
        System.out.println("Please have a look around, then add anything you like to your cart.\n");
        Utilities.sleepHere(3000);

        customer.selectProducts(productsInStock);
        TreeMap<Product, Integer> selectedProducts = customer.getCart();

        checkStock(selectedProducts);

        Order order = placeOrder(customer, selectedProducts, productsInStock);
        reviewOrder(order);
    }

    /**
     * Scans the webshop's productMap to see if it has the products and the required quantities to satisfy the customer.
     * If not then a backorder will be placed and the shop will be refilled. NOTE: The webshop's stock will only receive
     * an amount of products equal to the difference between available products and the products the customer wants.
     */
    private void checkStock(TreeMap<Product, Integer> selectedProducts) {
        BackOrder backOrder = new BackOrder(new ArrayList<>());
        for (Map.Entry<Product, Integer> selectedProduct : selectedProducts.entrySet()) {
            for (Map.Entry<Product, Integer> productInStock : productsInStock.entrySet()) {
                if (selectedProduct.getKey().compareTo(productInStock.getKey()) == 0) {
                    if (selectedProduct.getValue() > productInStock.getValue()) {
                        System.out.println("It seems we don't have enough " +
                                selectedProduct.getKey().getName() +
                                "s in stock. You wanted " +
                                selectedProduct.getValue() +
                                " but we had " +
                                productInStock.getValue() + "!");
                        OrderLine orderLine = new OrderLine(
                                selectedProduct.getKey(), selectedProduct.getValue() - productInStock.getValue());
                        backOrder.getOrderLines().add(orderLine);
                        Utilities.sleepHere(2000);
                    }
                }
            }
        }
        Utilities.sleepHere();
        if (backOrder.getOrderLines().size() > 0) {
            System.out.println("\nWe will now order more products from our suppliers.\nPlease wait a moment.");
            Utilities.sleepHere();
            TreeMap<Product, Integer> newProducts = backOrder.dispatch(suppliers);
            reFillShop(newProducts);
        }
    }

    /**
     * Creates an order object to be placed in the waitingOrders. Also calls updateStock to apply the change of
     * quantity in the products.
     *
     * @param customer         the customer object that the order references to
     * @param selectedProducts the products the customer have
     * @param productsInStock  the products the webshop have in stock
     * @return
     */
    private Order placeOrder(Customer customer,
                             TreeMap<Product, Integer> selectedProducts,
                             TreeMap<Product, Integer> productsInStock) {
        Order order = new Order(
                SampleDataGenerator.makeRanndomOrderID(), new ArrayList<>(), customer, (int) (Math.random() * 10));
        TreeMap<Product, Integer> tempMap = new TreeMap<>();
        for (Map.Entry<Product, Integer> selectedProductEntry : selectedProducts.entrySet()) {
            for (Map.Entry<Product, Integer> productInStockEntry : productsInStock.entrySet()) {
                if (selectedProductEntry.getKey().compareTo(productInStockEntry.getKey()) == 0) {
                    OrderLine ol = new OrderLine(productInStockEntry.getKey(), selectedProductEntry.getValue());
                    tempMap.put(productInStockEntry.getKey(),
                            productInStockEntry.getValue() -
                                    selectedProductEntry.getValue());
                    order.getOrderLines().add(ol);
                }
            }
        }
        updateStock(productsInStock, tempMap);
        waitingOrders.add(order);
        return order;
    }

    /**
     * Updates the stock of the webshop based on another TreeMap.
     *
     * @param productsInStock the current stock
     * @param newProducts     the updated stock
     */
    private void updateStock(TreeMap<Product, Integer> productsInStock, TreeMap<Product, Integer> newProducts) {
        for (Map.Entry<Product, Integer> modifiedProductEntry : newProducts.entrySet()) {
            productsInStock.put(modifiedProductEntry.getKey(), modifiedProductEntry.getValue());
        }
    }

    /**
     * Prints details about an order
     *
     * @param order the order to print details about
     */
    private void reviewOrder(Order order) {
        System.out.println("\nYour order is now waiting to be dispatched. Thank you for using Webshop!");
        System.out.println("These are your order details:");
        System.out.println("Order ID: " + order.getID());
        System.out.println("Priority level: " + order.getPriority());
        System.out.println("Products: ");
        order.showAllProducs();
        Utilities.sleepHere();
    }

    /**
     * "Send orders" by printing messages. Polls and removes the head of the queue until it is empty
     *
     * @param queue
     */
    private void processOrders(PriorityQueue<Order> queue) {
        while (queue.size() > 0) {
            Order order = queue.poll();
            order.dispatch();

            if (queue.size() == 1) {
                System.out.println("\nThere is currently " + queue.size() + " order waiting to be dispatched");
            } else {
                System.out.println("\nThere are currently " + queue.size() + " orders waiting to be dispatched");
            }
            order.showAllProducs();
            Utilities.sleepHere();
        }
    }

    /**
     * Fills up the stock of the webshop. Differs from updateStock in that it adds to the quantity of each product
     * instead of just setting it.
     *
     * @param newProducts
     */
    private void reFillShop(TreeMap<Product, Integer> newProducts) {
        TreeMap<Product, Integer> tempTree = new TreeMap<>();
        for (Map.Entry<Product, Integer> newProduct : newProducts.entrySet()) {
            for (Map.Entry<Product, Integer> productInStock : productsInStock.entrySet()) {
                if (productInStock.getKey().compareTo(newProduct.getKey()) == 0) {
                    tempTree.put(newProduct.getKey(), newProduct.getValue() + productInStock.getValue());
                }
            }
        }
        for (Map.Entry<Product, Integer> entry : tempTree.entrySet()) {
            productsInStock.put(entry.getKey(), entry.getValue());
        }
        Utilities.sleepHere();
    }

    /**
     * Get the productMap of the webstore
     *
     * @return productMap containt product keys and quantity values.
     */
    public TreeMap<Product, Integer> getProductsInStock() {
        return productsInStock;
    }

    /**
     * Get the suppliers
     *
     * @return all the suppliers webshop can refer to.
     */
    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }
}
