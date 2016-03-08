import java.util.Map;

/**
 * Created by sifu on 2/23/2016.
 * Simple class with utility methods that are used in several classes
 */
public class Utilities {

    public static void sleepHere(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Webshop has crashed!");
            e.printStackTrace();
        }
    }

    public static void sleepHere() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println("Webshop has crashed!");
            e.printStackTrace();
        }
    }

    public static void printAllFromProductMap(Map<Product, Integer> productMap) {
        for(Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            System.out.println(entry.getValue() + "x " + entry.getKey().getName() );
        }
    }
}
