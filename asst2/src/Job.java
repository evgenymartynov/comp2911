public class Job {
    public Job(int startX, int startY, int endX, int endY) {
        start = new Point(startX, startY);
        end = new Point(endX, endY);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public String toString() {
        return "<Job from " + start + " to " + end + ">";
    }

    private Point start, end;
}
