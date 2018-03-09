import java.util.*;
import java.math.BigInteger;

public class BigIncrementorTest {
    public static void main(String[] args) {
        run(0);
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

    public static void run(long i_count) {
        String start = "123456789123456789123456789123456789";

        System.out.println("\nCount " + start + " + " + i_count + " = " + (new BigInteger(start)).add(new BigInteger(Long.toString(i_count))));


        // int num = 0;
        // System.out.print("Start int...");
        // long timeElapsed = 0L;
        // long startTime = System.currentTimeMillis();
        // for (int i = 0; i < i_count; i++) {
        //     num++;
        // }
        // timeElapsed = (new Date()).getTime() - startTime;
        // System.out.println("END: " + timeElapsed + "ms");
        // System.out.println(num);


        BigInteger bigInt = new BigInteger(start);
        System.out.print("Start BigInteger...");
        long timeElapsed = 0L;
        long startTime = System.currentTimeMillis();
        for (long i = 0; i < i_count; i++) {
            bigInt = bigInt.add(new BigInteger("1"));
        }
        timeElapsed = (new Date()).getTime() - startTime;
        System.out.println("END: " + timeElapsed + "ms");


        BigIncrementor incr = new BigIncrementor(start);
        System.out.print("Start BigIncrementor...");
        timeElapsed = 0L;
        startTime = System.currentTimeMillis();
        for (long i = 0; i < i_count; i++) {
            incr.increment();
        }
        timeElapsed = (new Date()).getTime() - startTime;
        System.out.println("END: " + timeElapsed + "ms");
        if (bigInt.toString().equals(incr.toString())) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
    }
}

