

import java.util.LinkedList;

/**
 *
 * @author Jorgen
 */
public class Order {

    private Customer customer;
    private LinkedList<OrderDetails> orderDetails = new LinkedList<>();

    public Order(Customer customer, LinkedList<OrderDetails> orderDetails) {
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LinkedList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(LinkedList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    

}
