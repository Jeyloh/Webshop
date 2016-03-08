/**
 * Write a description of class TestUnique here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestUnique
{
    
    private static long compareCount;
    private static long outerLoopCount;
    public TestUnique()
    {

    }
    
    public static void runTest(int n) {
        int[] data = new int[n];
        for (int i=0; i<n; i++) data[i] = i;
        compareCount = outerLoopCount = 0;
        
        isUnique(data);
        System.out.println("N = "+n);
        System.out.format("%8s %10s %8s %8s\n", "Loop", "n", "count/n", "count/n^2");
        System.out.format("%8s %10d %8.2f %8.2f\n",
                "inner", compareCount, 1.0*compareCount/n, 1.0*compareCount/(n*n));
        System.out.format("%8s %10d %8.2f %8.2f\n",
                "outer", outerLoopCount, 1.0*outerLoopCount/n, 1.0*outerLoopCount/(n*n));
    }

    
    public static void runPerfTests() {
        for (int n=10; n<100000; n*=10) 
        runTest(n);
    }

    public static boolean isUnique(int[] data) {
        int n = data.length;
        
        for (int i=0; i<n; i++) {
            outerLoopCount++;
            for (int j=i+1; j<n; j++) {
                compareCount++;
                if (data[i] == data[j]) return false;
            }
        }
        return true;
    }
}
