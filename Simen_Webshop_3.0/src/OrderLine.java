

/**
 * Created by sifu on 2/12/16.
 * OrderLine is used by both order and backorder. It serves as the common ground
 * for storing the prdouct and the cost for both kinds of orders.
 */
public class OrderLine {
    private Product product;
    private int count;

    /**
     * Orderlines use arraylist to handle the number of products that are ordered.
     * This simplifies ordering several items at once.
     * @param product the prodcut ordered or backordered
     */
    OrderLine(Product product) {
        this.product = product;
        this.count = 1;
    }

    /**
     * Same as above except with the number of each product
     * @param product
     * @param count
     */
    OrderLine(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    protected Product getProduct() {
        return  product;
    }

    protected void setCount(int count) {
        if(count >= 0) {
            this.count = count;
        } else {
            this.count = 0;
        }
    }

    protected int getCount() {
        return count;
    }
}
