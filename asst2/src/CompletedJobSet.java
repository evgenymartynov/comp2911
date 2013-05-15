/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.math.BigInteger;

/**
 * Keeps track of which jobs have been completed.
 *
 * We use a single integer to identify jobs, and implement this as a bitfield
 * indexed by that integer. That is significantly faster than using some kind of
 * a boolean array or a HashSet.
 */
public class CompletedJobSet {
    /**
     * Initialises a new, empty, set of completed jobs.
     */
    public CompletedJobSet() {
        bits = BigInteger.ZERO;
        numBitsSet = 0;
    }

    /**
     * Marks a job as having been completed.
     *
     * @param i
     *            Index of the completed job.
     */
    public void markCompleted(int i) {
        if (!bits.testBit(i)) {
            numBitsSet++;
        }

        bits = bits.setBit(i);
    }

    /**
     * Computes whether or not a job with the given index has been completed.
     *
     * @param i
     *            Index of job to check.
     * @return Whether or not the given job has been completed.
     */
    public boolean isCompleted(int i) {
        return bits.testBit(i);
    }

    /**
     * Gets the number of completed jobs.
     *
     * @return Number of completed jobs.
     */
    public int numCompleted() {
        return numBitsSet;
    }

    /**
     * Generates hash for the set of jobs using our own logic to ensure
     * consistency.
     */
    @Override
    public int hashCode() {
        final int prime = 5;
        int result = 1;

        for (int i = 0; i < bits.bitCount(); i++) {
            result = prime * result + (bits.testBit(i) ? 1 : 0);
        }

        return result;
    }

    /**
     * Performs object comparison, requiring internal bitfields to match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompletedJobSet)) {
            return false;
        }

        CompletedJobSet other = (CompletedJobSet) obj;
        if (bits == null) {
            if (other.bits != null) {
                return false;
            }
        } else if (!bits.equals(other.bits)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a clone of the current object.
     */
    @Override
    public CompletedJobSet clone() {
        CompletedJobSet result = new CompletedJobSet();

        // BigIntegers are immutable, this works fine.
        result.bits = bits;
        result.numBitsSet = numBitsSet;

        return result;
    }

    /**
     * Internal storage for the bitfield.
     */
    private BigInteger bits;

    /**
     * Keeps track of number of completed jobs.
     */
    private int numBitsSet;
}
