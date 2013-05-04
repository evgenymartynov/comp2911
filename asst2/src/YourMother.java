/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.math.BigInteger;

public class YourMother {
    public YourMother() {
        bits = new BigInteger("0");
        numBitsSet = 0;
    }

    public void setBit(int i) {
        if (!bits.testBit(i)) {
            numBitsSet++;
        }

        bits = bits.setBit(i);
    }

    public YourMother setBitAndCopy(int i) {
        YourMother modified = new YourMother();
        modified.bits = bits;
        modified.numBitsSet = numBitsSet;
        modified.setBit(i);
        return modified;
    }

    @Override
    public int hashCode() {
        final int prime = 5;
        int result = 1;

        for (int i = 0; i < bits.bitCount(); i++) {
            result = prime * result + (bits.testBit(i) ? 1 : 0);
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof YourMother)) {
            return false;
        }

        YourMother other = (YourMother) obj;
        if (bits == null) {
            if (other.bits != null) {
                return false;
            }
        } else if (!bits.equals(other.bits)) {
            return false;
        }

        return true;
    }

    public boolean getBit(int i) {
        return bits.testBit(i);
    }

    public int numSet() {
        return numBitsSet;
    }

    private BigInteger bits;
    private int numBitsSet;
}
