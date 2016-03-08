/**
 * Created by sifu on 2/12/16.
 * An order intended for suppliers.
 */

import java.util.ArrayList;
import java.util.TreeMap;

public class BackOrder {
    private ArrayList<OrderLine> orderLines;

    /**
     * Any backorder must have an orderline which contains the product
     *
     * @param orderLine contains the product and total cost
     */
    BackOrder(ArrayList<OrderLine> orderLine) {

        this.orderLines = orderLine;
    }

    protected ArrayList<OrderLine> getOrderLines() {

        return orderLines;
    }

    protected void addProduct(Product product) {
        OrderLine orderLine = new OrderLine(product);
        orderLines.add(orderLine);
    }

    /**
     * For each orderline, check with each supplier if they have the desired product in stock. Whenever a number
     * of products is found this way the count in orderline is subtracted. If the count is 0 then the search for the
     * next product will begin. When the orderLine count is 0 is means that the product is found and we then restore it
     * to the old value.
     *
     * @param suppliers
     * @return
     */
    protected TreeMap<Product, Integer> dispatch(ArrayList<Supplier> suppliers) {
        TreeMap<Product, Integer> newProducts = new TreeMap<>();
        ArrayList<Product> foundProducts = new ArrayList<>();
        for (OrderLine ol : orderLines) {
            //Need to use a temporary variable so that we don't lose track of what to subtract the orderLine with.
            int tempCount = 0;
            for (Supplier s : suppliers) {
                //Remember the ol count value when restoring.
                int tempOlCount = ol.getCount();
                if (ol.getCount() > 0) {
                    Product p = s.findProduct(ol.getProduct());
                    int productCount = 0;

                    if (p != null) {
                        productCount = s.findCount(p);
                    } else {
                        System.out.println("Product not found, try another supplier");
                        break;
                    }
                    //If the supplier has more or equal to the amount of products we need
                    if (productCount >= ol.getCount() && ol.getCount() != 0) {
                        tempCount = tempCount + ol.getCount();
                        newProducts.put(p, tempCount);
                        ol.setCount(0);
                    //If the supplier has less than what we need but more than 0
                    } else if (productCount < ol.getCount() && productCount > 0) {
                        tempCount = tempCount + productCount;
                        newProducts.put(p, tempCount);
                        ol.setCount(ol.getCount() - productCount);
                    //If no suppliers have the product then good old Freddy will simply get some
                    //Expect to implement handling 0 values in later versions.
                    } else {
                        System.out.println("Our suppliers didn't have enough " + p.getName() + "s in stock!");
                        System.out.println("Luckily Freddy had some lying around so everything is fine\n");
                        newProducts.put(p, ol.getCount());
                        ol.setCount(0);
                    }
                //Restore the orderLine count
                } else {
                    ol.setCount(tempOlCount);
                }
            }
        }
        return newProducts;
    }
}
