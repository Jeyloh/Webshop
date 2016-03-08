/**
 * Created by sifu on 2/26/16.
 */
public class Product implements Comparable<Product> {
    private String name;
    private String ID;

    Product(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }


    /**
     * This method ensures that no duplicate product will be put into any Treemaps. This is useful to keep
     * names unique.
     * @param p the product to compare with
     * @return an integer value deciding if it is a duplicate
     */
    //NOTE TO SELF: implement compareTo for both name and ID, or just ID maybe?

    @Override
    public int compareTo(Product p) {
        if(this.getName().compareTo(p.getName()) != 0) {
            return this.getName().compareTo(p.getName());
        }
        if(this.getID().compareTo(p.getID()) != 0) {
            return this.getID().compareTo(p.getID());
        }
        return 0;
    }
}
