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

/**
 * Logic class that knows how to perform A-star search for the assignment
 * problem. We bring most of the search logic into here and into TSPState,
 * allowing other classes to behave in a much simpler manner. Heuristic logic
 * resides inside TSPHeuristic's implementations.
 *
 * Note that its constructor accepts a heuristic used for search-state
 * evaluation, as per the strategy design pattern. Search states are described
 * below.
 *
 * We transform the explicit grid-graph as described by the problem into a state
 * more suitable for this particular problem. Our search states are tuples of
 * the current location and the set of jobs which has been completed thus far.
 * Edges between them are directional, and defined in the standard way that one
 * would use when solving a TSP-like problem with O(2^n) DP.
 *
 * As such, this means our search states should simply be tuples of location,
 * set of completed jobs, and distance/heuristic metrics used for state
 * ordering. However, since we need to keep track of more things, such as the
 * predecessor and best-known distance thus far, we keep them inside the state
 * tuple as well and treat them as not affecting what a state actually
 * describes. That is, even though two TSPState instances may have differing
 * predecessors, we still consider them to be equal. This is important for our
 * use of HashSet below.
 *
 * We represent each set of completed jobs as a bitset, since it's fairly
 * optimal to keep these things compressed. Using anything else incurred
 * significant runtime and memory penalties.
 *
 * Unlike the lecture notes, we do not use .remove() method of PriorityQueue,
 * since that takes O(n) time. We settle for using a visited set of states
 * instead, checked on popping the queue.
 */
public class AStarTSPSolver {
    /**
     * Constructs a new A* solver for the given set of jobs and the heuristic.
     *
     * @param jobs
     *            List of deliveries that must be completed.
     * @param heuristic
     *            Heuristic used for state ordering. Must be admissible.
     */
    public AStarTSPSolver(List<Job> jobs, TSPHeuristic heuristic) {
        this.jobs = jobs;
        this.heuristic = heuristic;
    }

    /**
     * Solves the problem for the given jobs and prints out a solution.
     */
    public void solve() {
        PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();
        // This keeps track of nodes expanded so far.
        HashSet<TSPState> visited = new HashSet<TSPState>();

        // Initialise PQ for with the starting point.
        pq.add(makeNode(0, new Point(0, 0), new CompletedJobSet(), null, null));

        // The final state ends up in here.
        // We may assume that (given enough time), we will find an optimal
        // solution.
        TSPState finalState = null;

        while (!pq.isEmpty()) {
            TSPState state = pq.poll();

            // Do not re-expand processed nodes.
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);

            // Have we just completed all the jobs?
            CompletedJobSet completedJobs = state.getCompletedSet();
            if (completedJobs.numCompleted() == jobs.size()) {
                finalState = state;
                break;
            }

            // Nope -- try completing more jobs.
            for (int i = 0; i < jobs.size(); i++) {
                if (completedJobs.isCompleted(i)) {
                    continue;
                }

                Job job = jobs.get(i);
                int newDistance = state.getDistance()
                        + state.getPoint().distanceTo(job.getStart())
                        + job.getLength();
                CompletedJobSet newCompletedSet = completedJobs.clone();
                newCompletedSet.markCompleted(i);

                TSPState newState = makeNode(newDistance, job.getEnd(),
                        newCompletedSet, job, state);
                pq.add(newState);
            }
        }

        // This should never happen, but do this just to be safe.
        if (finalState == null) {
            throw new RuntimeException(
                    "Could not solve the problem for the given input.");
        }

        // Print out the formatted result.
        // Number of expanded states is the number of nodes in the visited set.
        // That includes the final state.
        printSolution(finalState, visited.size());
    }

    /**
     * Prints out the solution and required statistics in the assignment format.
     *
     * @param finalState
     *            Final goal state, with backtracking information present.
     * @param exploredNodeCount
     *            Number of nodes explored in the A* search.
     */
    private void printSolution(TSPState finalState, int exploredNodeCount) {
        System.out.println(exploredNodeCount + " nodes explored");
        System.out.println("cost = " + finalState.getDistance());

        // Trace back the path to final node, adding jobs in reverse order.
        LinkedList<Job> path = new LinkedList<Job>();
        TSPState lastJob = finalState;
        while (lastJob != null) {
            if (lastJob.getPrevJob() != null) {
                path.addFirst(lastJob.getPrevJob());
            }

            lastJob = lastJob.getPrevState();
        }

        // Walk through the completed jobs and work out the instructions.
        Point position = new Point(0, 0);
        for (Job job : path) {
            if (!position.equals(job.getStart())) {
                System.out.println("Move from " + position + " to "
                        + job.getStart());
            }

            System.out.println("Carry from " + job.getStart() + " to "
                    + job.getEnd());

            position = job.getEnd();
        }
    }

    /**
     * Helper function to instantiate a new search state.
     *
     * @param distance
     *            Distance traveled so far to get to this state.
     * @param point
     *            Point at which the courier is located.
     * @param completedSet
     *            Set of completed jobs.
     * @param prevJob
     *            Last completed job.
     * @param prevState
     *            The state from which we got to this state.
     * @return Fully instantiated search state with expected parameters.
     */
    private TSPState makeNode(int distance, Point point,
            CompletedJobSet completedSet, Job prevJob, TSPState prevState) {
        int estimate = heuristic.computeEstimate(point, completedSet, jobs);
        return new TSPState(point, distance, estimate, completedSet, jobs,
                prevJob, prevState);
    }

    /**
     * List of jobs for which we will try to find the optimal ordering.
     */
    private List<Job> jobs;

    /**
     * Heuristic used for node ordering.
     */
    private TSPHeuristic heuristic;
}
