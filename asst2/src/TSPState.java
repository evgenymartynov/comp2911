import java.util.List;

public class TSPState implements Comparable<TSPState> {
    public TSPState(Point point, int distance, int estimate, List<Job> done,
            List<Job> remainingJobs) {
        this.point = point;
        this.distance = distance;
        this.estimate = estimate;
        this.completedJobs = done;
        this.remainingJobs = remainingJobs;

        this.isFinal = remainingJobs.isEmpty();
    }

    public String summariseActions() {
        String summary = "cost = " + distance + "\n";

        Point at = new Point(0, 0);
        for (Job job : completedJobs) {
            summary += "Move from " + at.spaceSeparated() + " to "
                    + job.getStart().spaceSeparated() + "\n";
            summary += "Carry from " + job.getStart().spaceSeparated() + " to "
                    + job.getEnd().spaceSeparated() + "\n";
        }

        return summary;
    }

    public Point getPoint() {
        return point;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isFinalState() {
        return isFinal;
    }

    public List<Job> getCompletedJobs() {
        return completedJobs;
    }

    public List<Job> getRemainingJobs() {
        return remainingJobs;
    }

    @Override
    public int compareTo(TSPState other) {
        return getPriority() - other.getPriority();
    }

    @Override
    public String toString() {
        return "<State at=" + point + ", cost=" + distance + ", path="
                + completedJobs + ">";
    }

    private int getPriority() {
        return distance + estimate;
    }

    private Point point;
    private int distance, estimate;
    private boolean isFinal;
    private List<Job> remainingJobs, completedJobs;
}
