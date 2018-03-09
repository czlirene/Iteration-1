import java.math.BigInteger;

public class BigIncrementor {
    private static final BigInteger maxLong = new BigInteger(Long.toString(Long.MAX_VALUE));
    private long num;
    private BigInteger numBig;

    public BigIncrementor(String start) {
        num = 0;
        numBig = new BigInteger(start);
    }

    public void increment() {
        num++;
        checkNum();
    }

    public void checkNum() {
        if (num < 0) {
            numBig = numBig.add(maxLong);
            num = 0;
        }
    }

    public BigInteger getBigInteger() {
        checkNum();
        numBig = numBig.add(new BigInteger(Long.toString(num)));
        num = 0;
        return numBig;
    }

    @Override
    public String toString() {
        return getBigInteger().toString();
    }

}

