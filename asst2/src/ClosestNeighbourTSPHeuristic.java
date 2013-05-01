import java.util.List;
import java.util.PriorityQueue;

public class ClosestNeighbourTSPHeuristic implements TSPHeuristic {
    @Override
    public int computeEstimate(Point point, YourMother visitedSet,
            List<Job> jobs) {
        int estimate = 0;

        // Find closest incomplete job to us, and count total number of
        // remaining jobs.
        int closestDistance = INFINITY;
        int numRemainingJobs = 0;
        for (int i = 0; i < jobs.size(); i++) {
            if (visitedSet.getBit(i)) {
                continue;
            }

            numRemainingJobs++;

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

        // Calculate all distances in the complete digraph of remaining jobs.
        PriorityQueue<Integer> distances = new PriorityQueue<Integer>();
        for (int i = 0; i < jobs.size(); i++) {
            if (visitedSet.getBit(i)) {
                continue;
            }

            for (int k = i+1; k < jobs.size(); k++) {
                if (visitedSet.getBit(k)) {
                    continue;
                }

                Job from = jobs.get(i);
                Job to = jobs.get(k);

                distances.add(-from.getEnd().distanceTo(to.getStart()));
                distances.add(-to.getEnd().distanceTo(from.getStart()));
                while (distances.size() > numRemainingJobs - 1) {
                    distances.poll();
                }
            }
        }

        // Now add up the (numRemainingJobs-1) smallest distances.
        for (int distance : distances) {
            estimate += -distance;
        }

        return estimate;
    }

    private final int INFINITY = (int) 1e10;
}
