import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
    public Solver(List<Job> jobs, TSPHeuristic heuristic) {
        this.jobs = jobs;
        this.heuristic = heuristic;
    }

    public void solve() {
        PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();
        pq.add(makeNode(0, new Point(0, 0), new ArrayList<Job>(), jobs));

        TSPState finalState = null;
        int exploredNodeCount = 0;

        while (!pq.isEmpty()) {
            TSPState state = pq.poll();
            exploredNodeCount++;

            if (state.isFinalState()) {
                finalState = state;
                break;
            }

            for (Job job : state.getRemainingJobs()) {
                int newDistance = state.getDistance()
                        + state.getPoint().distanceTo(job.getStart())
                        + job.getStart().distanceTo(job.getEnd());

                // Poor man's approach of copying an array minus an element.
                List<Job> remainingJobs = new ArrayList<Job>(
                        state.getRemainingJobs());
                remainingJobs.remove(job);

                List<Job> completedJobs = new ArrayList<Job>(
                        state.getCompletedJobs());
                completedJobs.add(job);

                TSPState next = makeNode(newDistance, job.getEnd(),
                        completedJobs, remainingJobs);
                pq.add(next);
            }
        }

        System.out.println(finalState);
        System.out.println(exploredNodeCount + " nodes explored");
        System.out.println(finalState.summariseActions());
    }

    private TSPState makeNode(int distance, Point point,
            List<Job> completedJobs, List<Job> remainingJobs) {
        int estimate = heuristic.computeEstimate(point, remainingJobs);
        return new TSPState(point, distance, estimate, completedJobs,
                remainingJobs);
    }

    private List<Job> jobs;
    private TSPHeuristic heuristic;
}
