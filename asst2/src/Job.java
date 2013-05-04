/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

public class Job {
    public Job(int startX, int startY, int endX, int endY) {
        start = new Point(startX, startY);
        end = new Point(endX, endY);

        length = start.distanceTo(end);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "<Job from " + start + " to " + end + ">";
    }

    private Point start, end;
    private int length;
}
