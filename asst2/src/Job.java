/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

/**
 * Our representation of a delivery job, kept as simple as possible.
 */
public class Job {
    /**
     * Constructs a new job between the given coordinates.
     *
     * @param start
     *            Pick-up point of a job, i.e. where it starts.
     * @param end
     *            Delivery point of a job, i.e. where it ends.
     */
    public Job(Point start, Point end) {
        this.start = start;
        this.end = end;

        length = start.distanceTo(end);
    }

    /**
     * @return Start point of this job.
     */
    public Point getStart() {
        return start;
    }

    /**
     * @return End point of this job.
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Gets the shortest distance between start and end of the job.
     *
     * @return Length of the job, from start to end.
     */
    public int getLength() {
        return length;
    }

    /**
     * Start and end points of a job.
     */
    private Point start, end;

    /**
     * Shortest distance between start and end of a job, i.e. its length. Since
     * it is unaffected by the grid layout, we may calculate it once and cache
     * the result.
     */
    private int length;
}
