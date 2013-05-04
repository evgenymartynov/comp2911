/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
    public Solver(List<Job> jobs, TSPHeuristic heuristic) {
        this.jobs = jobs;
        this.heuristic = heuristic;
    }

    public void solve() {
        HashSet<TSPState> visited = new HashSet<TSPState>();
        PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();

        // Initialise for the starting point.
        pq.add(makeNode(0, new Point(0, 0), new YourMother(), null, null));

        TSPState finalState = null;
        int exploredNodeCount = 0;

        while (!pq.isEmpty()) {
            TSPState state = pq.poll();

            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            exploredNodeCount++;

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
                        + job.getLength();
                YourMother newVisitedSet = visitedSet.setBitAndCopy(i);
                TSPState next = makeNode(newDistance, job.getEnd(),
                        newVisitedSet, job, state);
                pq.add(next);
            }
        }

        System.out.println(exploredNodeCount + " nodes explored");
        System.out.println("cost = " + finalState.getDistance());

        // Trace back the path to final node.
        LinkedList<Job> path = new LinkedList<Job>();
        TSPState lastJob = finalState;
        while (lastJob != null) {
            if (lastJob.getPrevJob() != null) {
                path.addFirst(lastJob.getPrevJob());
            }

            lastJob = lastJob.getPrevState();
        }

        Point position = new Point(0, 0);
        for (Job job : path) {
            if (!position.equals(job.getStart())) {
                System.out.println("Move from " + position
                        + " to " + job.getStart());
            }

            System.out.println("Carry from " + job.getStart()
                    + " to " + job.getEnd());

            position = job.getEnd();
        }
    }

    private TSPState makeNode(int distance, Point point, YourMother visitedSet,
            Job prevJob, TSPState prevState) {
        int estimate = heuristic.computeEstimate(point, visitedSet, jobs);
        return new TSPState(point, distance, estimate, visitedSet, jobs,
                prevJob, prevState);
    }

    private List<Job> jobs;
    private TSPHeuristic heuristic;
}
