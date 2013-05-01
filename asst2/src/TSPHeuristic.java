import java.util.List;

public interface TSPHeuristic {
    public int computeEstimate(Point point, YourMother visitedSet,
            List<Job> jobs);
}
