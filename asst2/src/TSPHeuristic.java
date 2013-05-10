/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.List;

/**
 * An interface describing heuristics used by the Solver. This is called a TSP
 * heuristic since the assignment problem is a type of the Traveling Salesman
 * Problem.
 *
 * As long as the concrete heuristic is admissible, given our state, it may be
 * used by the Solver. For further details, see the Solver class.
 */
public interface TSPHeuristic {
    /**
     * Estimate distance to the goal state. The result must always be
     * admissible.
     *
     * @param point
     *            Our current position.
     * @param completedSet
     *            Data structure containing information on which jobs we have
     *            completed.
     * @param jobs
     *            List of all jobs. Includes both completed and incomplete jobs.
     *
     * @return Heuristic estimate to the goal state.
     */
    public int computeEstimate(Point point, CompletedJobSet completedSet,
            List<Job> jobs);
}
