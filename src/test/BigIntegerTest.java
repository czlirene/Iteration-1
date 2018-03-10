package test;
import java.util.*;
import java.math.BigInteger;

/**
 * Checks the runtime of repeatedly incrementing int vs. BigInteger
 */
public class BigIntegerTest {
    public static void main(String[] args) {
        run(1);
        run(10);
        run(100);
        run(1000);
        run(10000);
        run(100000);
        run(1000000);
        run(10000000);
        run(100000000);
        run(1000000000);
    }

    public static void run(int i_count) {
        System.out.println("\nTest incrementing to " + i_count);
        int num = 0;
        System.out.print("Start int...");

        String compilerOptimizer = "";

        long timeElapsed = 0L;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < i_count; i++) {
            num++;
        }
        timeElapsed = (new Date()).getTime() - startTime;
        System.out.println("END: " + timeElapsed + "ms");

        BigInteger bigInt = new BigInteger("0");
        BigInteger ONE = BigInteger.ONE;
        System.out.print("Start BigInteger...");

        timeElapsed = 0L;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < i_count; i++) {
            bigInt = bigInt.add(ONE);
        }
        timeElapsed = (new Date()).getTime() - startTime;
        System.out.println("END: " + timeElapsed + "ms");
    }
}
