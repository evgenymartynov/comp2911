import java.util.List;

public interface TSPHeuristic {
    public int computeEstimate(Point point, List<Job> remainingJobs);
}
