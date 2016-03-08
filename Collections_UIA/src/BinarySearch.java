import java.util.Random;
/**
 * Write a description of class BinarySearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BinarySearch
{
    
    public static int count = 0;
    
    public static int binarySearch(Integer v, ArrayList<Integer> data) {
        int min=0;
        int max=data.size();
        int mid=(min+max)/2;
        
        while (min<max) {
            count++;
            mid = (min+max)/2;
            
            if (v < data.get(mid)) max = mid;
            else if (v > data.get(mid)) min = mid+1;
            else return mid;
        }
        return -1;
    }
    
    private static Random random = new Random();
    public static void testBinarySearch(int n) {
        count = 0;
        ArrayList<Integer> data = new ArrayList<>();
        for (int i=0; i<n; i++) data.add(i);
        
        for (int i=0; i<n; i++) binarySearch(i, data);
        
        System.out.println("Gj.snitt iterasjoner = " + (count/n));
    }        
}
