

import java.util.ArrayList;

/**
 *
 * @author Jorgen
 */
public class BackOrder {

    private ArrayList<OrderDetails> orderDetails = new ArrayList<>();

    public BackOrder(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    

}
