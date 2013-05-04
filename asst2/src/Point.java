/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

/**
 * Representation of a point in Z^2 space.
 *
 * It provides a way to compare and hash points and perform Manhattan-distance
 * calculations.
 */
public class Point {
    /**
     * Constructs a new Z^2 point at the given coordinates.
     *
     * @param x
     *            First ordinate.
     * @param y
     *            Second ordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates Manhattan distance to another point.
     *
     * @param other
     *            Point to measure to.
     * @return Shortest Manhattan distance to the given point.
     */
    public int distanceTo(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    /**
     * @return X-coordinate of this point.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y-coordinate of this point.
     */
    public int getY() {
        return y;
    }

    /**
     * Converts the point to an assignment-friendly output format (two
     * space-separated integers).
     */
    @Override
    public String toString() {
        return x + " " + y;
    }

    /**
     * Hashes this point based on its (x,y) coordinates.
     */
    @Override
    public int hashCode() {
        final int prime = 31337;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /**
     * Checks equality with another point based on its (x.y) coordinates.
     *
     * They are equal iff their coordinates match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    /**
     * X,Y coordinates of this point.
     */
    private int x, y;
}
