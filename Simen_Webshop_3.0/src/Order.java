import java.util.ArrayList;

/**
 * Created by sifu on 2/26/2016.
 * An order intended for a customer.
 */
public class Order implements Comparable<Order> {
    private String ID;
    private ArrayList<OrderLine> orderLines;
    private Customer customer;
    private int priority;

    /**
     *
     * @param ID
     * @param orderLines
     * @param customer
     * @param priority used by the Priority Queue in webshop. Note tha lower amount = higher priority
     */
    Order(String ID, ArrayList<OrderLine> orderLines, Customer customer, int priority) {
        this.ID = ID;
        this.orderLines = orderLines;
        this.customer = customer;
        this.priority = priority;
    }

    public void dispatch() {
        System.out.println("\nOrder NR " + ID + "has been dispatched to " + customer.getName());
    }

    public void showAllProducs() {
        System.out.println("The order consists of the following products");
        for(OrderLine ol : orderLines) {
            System.out.println(ol.getCount() + "x " + ol.getProduct().getName());
        }
    }

    protected ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    protected String getID() {
        return ID;
    }

    protected Integer getPriority() {
        return priority;
    }

    /**
     * Compare orders by their ID.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Order o) {
        return this.getPriority().compareTo(o.getPriority());
    }
}
