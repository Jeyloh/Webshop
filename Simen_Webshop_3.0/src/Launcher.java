/**
 * Created by sifu on 2/26/16.
 * Launch the webshop and use the SampleDataGenerator to create all the necessary data
 */

public class Launcher {
    public static void main(String[] args) {
        Webshop webshop = new Webshop();
        SampleDataGenerator.makeSampleData(webshop);
        webshop.startShopping(4);
    }
}
