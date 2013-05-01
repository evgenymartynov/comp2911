import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
    public Solver(List<Job> jobs, TSPHeuristic heuristic) {
        this.jobs = jobs;
        this.heuristic = heuristic;
    }

    public void solve() {
        HashSet<TSPState> visited = new HashSet<TSPState>();
        HashMap<TSPState, Job> prevJob = new HashMap<TSPState, Job>();
        PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();

        // Initialise for the starting point.
        pq.add(makeNode(0, new Point(0, 0), new YourMother(), null));

        TSPState finalState = null;
        int exploredNodeCount = 0;

        System.out.println(jobs);

        while (!pq.isEmpty()) {
            TSPState state = pq.poll();

            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            exploredNodeCount++;
            prevJob.put(state, state.getPrevJob());

            YourMother visitedSet = state.getVisitedSet();
            if (visitedSet.numSet() == jobs.size()) {
                finalState = state;
                break;
            }

            for (int i = 0; i < jobs.size(); i++) {
                if (visitedSet.getBit(i)) {
                    continue;
                }

                Job job = jobs.get(i);
                int newDistance = state.getDistance()
                        + state.getPoint().distanceTo(job.getStart())
                        + job.getStart().distanceTo(job.getEnd());
                YourMother newVisitedSet = visitedSet.setBitAndCopy(i);
                TSPState next = makeNode(newDistance, job.getEnd(),
                        newVisitedSet, job);
                pq.add(next);
            }
        }

        System.out.println(finalState);
        System.out.println(exploredNodeCount + " nodes explored");
        // System.out.println(finalState.summariseActions());
    }

    private TSPState makeNode(int distance, Point point, YourMother visitedSet,
            Job prevJob) {
        int estimate = heuristic.computeEstimate(point, visitedSet, jobs);
        return new TSPState(point, distance, estimate, visitedSet, jobs,
                prevJob);
    }

    private List<Job> jobs;
    private TSPHeuristic heuristic;
}
