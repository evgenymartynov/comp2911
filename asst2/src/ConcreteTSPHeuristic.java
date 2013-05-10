/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.ArrayList;
import java.util.List;

/**
 * An example of a TSP heuristic used in A* search.
 */
public class ConcreteTSPHeuristic implements TSPHeuristic {
    /**
     * Computes estimate of distance to the goal. Is guaranteed to be
     * admissible.
     *
     * @return Estimate to the goal state.
     */
    @Override
    public int computeEstimate(Point point, CompletedJobSet completedSet,
            List<Job> jobs) {
        List<Job> remainingJobs = getRemainingJobs(completedSet, jobs);
        int estimate = 0;

        // Find closest incomplete job to us, and add it to our estimate.
        int shorestDistanceFromPoint = INFINITY;
        for (Job job : remainingJobs) {
            shorestDistanceFromPoint = Math.min(shorestDistanceFromPoint, job
                    .getStart().distanceTo(point));
        }
        if (shorestDistanceFromPoint < INFINITY) {
            estimate += shorestDistanceFromPoint;
        }

        // Add up all remaining intra-job distances.
        for (Job job : remainingJobs) {
            estimate += job.getLength();
        }

        // For each job, find closest job to go to.
        // Use all but the largest distance for the estimate.
        int largestInterjobDistance = 0;
        for (Job from : remainingJobs) {
            int nearestJobDistance = INFINITY;

            for (Job to : remainingJobs) {
                if (from != to) {
                    nearestJobDistance = Math.min(nearestJobDistance, from
                            .getEnd().distanceTo(to.getStart()));
                }
            }

            if (nearestJobDistance < INFINITY) {
                estimate += nearestJobDistance;
                largestInterjobDistance = Math.max(largestInterjobDistance,
                        nearestJobDistance);
            }
        }
        estimate -= largestInterjobDistance;

        return estimate;
    }

    /**
     * Computes remaining jobs from the given set of remaining jobs.
     *
     * @param completedSet
     *            Set of completed jobs.
     * @param jobs
     *            All jobs that must be done, including completed jobs.
     * @return List of jobs that are yet to be done.
     */
    private List<Job> getRemainingJobs(CompletedJobSet completedSet, List<Job> jobs) {
        ArrayList<Job> result = new ArrayList<Job>(jobs.size());

        for (int i = 0; i < jobs.size(); i++) {
            if (!completedSet.isCompleted(i)) {
                result.add(jobs.get(i));
            }
        }

        return result;
    }

    /**
     * Something large, used for initialising aggregate-minimum values. Should
     * be bigger than any possible distance for a legal ordering of jobs.
     */
    private final int INFINITY = (int) 1e10;
}
