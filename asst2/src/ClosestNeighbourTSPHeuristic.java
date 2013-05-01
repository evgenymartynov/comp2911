import java.util.List;

public class ClosestNeighbourTSPHeuristic implements TSPHeuristic {
    @Override
    public int computeEstimate(Point point, YourMother visitedSet,
            List<Job> jobs) {
        int estimate = 0;

        // Find closest incomplete job to us.
        int closestDistance = INFINITY;
        for (int i = 0; i < jobs.size(); i++) {
            if (visitedSet.getBit(i)) {
                continue;
            }

            int distance = jobs.get(i).getStart().distanceTo(point);
            if (closestDistance > distance) {
                closestDistance = distance;
            }
        }
        if (closestDistance < INFINITY) {
            estimate += closestDistance;
        }

        // Add up all remaining job distances.
        for (int i = 0; i < jobs.size(); i++) {
            if (!visitedSet.getBit(i)) {
                Job job = jobs.get(i);
                estimate += job.getStart().distanceTo(job.getEnd());
            }
        }

        return estimate;
    }

    private final int INFINITY = (int) 1e10;
}
