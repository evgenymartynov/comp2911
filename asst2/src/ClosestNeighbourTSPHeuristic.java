import java.util.List;

public class ClosestNeighbourTSPHeuristic implements TSPHeuristic {
    @Override
    public int computeEstimate(Point point, List<Job> remainingJobs) {
        int estimate = 0;

        boolean completed[] = new boolean[remainingJobs.size()];
        for (int iterNum = 0; iterNum < remainingJobs.size(); iterNum++) {
            // Pick job closest to where we are.
            int bestDistance = INFINITY;
            int bestIndex = -1;

            for (int i = 0; i < remainingJobs.size(); i++) {
                int thisDistance = remainingJobs.get(i).getStart()
                        .distanceTo(point);
                if (!completed[i] && bestDistance > thisDistance) {
                    bestDistance = thisDistance;
                    bestIndex = i;
                }
            }

            completed[bestIndex] = true;
            estimate += bestDistance
                    + remainingJobs.get(bestIndex).getStart()
                            .distanceTo(remainingJobs.get(bestIndex).getEnd());
        }

        return estimate;
    }

    private final int INFINITY = (int) 1e10;
}
