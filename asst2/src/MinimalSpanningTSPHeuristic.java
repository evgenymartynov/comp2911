/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.ArrayList;
import java.util.List;

/**
 * A TSP heuristic that is seemingly good. Based on an idea dealing with MSTs.
 */
public class MinimalSpanningTSPHeuristic implements TSPHeuristic {
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

        // Just a quick check, saves headaches later.
        // Trust me, it's neater.
        if (remainingJobs.size() == 0) {
            return 0;
        }

        // Add up all remaining intra-job distances.
        for (Job job : remainingJobs) {
            estimate += job.getLength();
        }

        // Run a pretty cool but slowish heuristic.
        estimate += computeInterjobEstimate(point, remainingJobs);

        return estimate;
    }

    /**
     * Estimates the distance needed to travel between all remaining jobs.
     *
     * It considers using each job as the next job to go to, and then do a
     * greedy choice of "how do I get to each [other] job". This is minimised
     * over all first jobs, leading to an admissible heuristic that's pretty
     * damn decent in our tests.
     *
     * Think of it as:
     * "pick next job, then find shortest edges to every other job". Minimising
     * that over all "first jobs", we get an admissible estimate.
     *
     * @param currentPosition
     *            Current position of the courier.
     * @param remainingJobs
     *            Jobs left to be completed. Must have at least one job.
     * @return An admissible estimate for inter-job travel.
     */
    private int computeInterjobEstimate(Point currentPosition,
            List<Job> remainingJobs) {
        // Work out smallest distances to each job, from some other job.
        // Also sum up these smallest distances, for reasons which will become
        // apparent in the next section.
        int smallestDistanceTo[] = new int[remainingJobs.size()];
        int sum = 0;
        for (int i = 0; i < remainingJobs.size(); i++) {
            // Work out the shortest distance from some other job that leads to
            // starting point of job i.
            int smallestDistance = Integer.MAX_VALUE;

            for (int k = 0; k < remainingJobs.size(); k++) {
                if (i != k) {
                    Point start = remainingJobs.get(i).getStart();
                    Point end = remainingJobs.get(k).getEnd();
                    smallestDistance = Math.min(smallestDistance,
                            start.distanceTo(end));
                }
            }

            smallestDistanceTo[i] = smallestDistance;
            sum += smallestDistance;
        }

        int bestEstimate = Integer.MAX_VALUE;

        // Now, since we must somehow get to the start of each job, we consider
        // all possible first jobs. Then, we go to that job from the current
        // position, and add up the shortest ways to get to every other job.
        //
        // A little bit of maths shows that it boils down to a one-liner
        // below...
        for (int i = 0; i < remainingJobs.size(); i++) {
            int currentEstimate = sum
                    - smallestDistanceTo[i]
                    + currentPosition.distanceTo(remainingJobs.get(i)
                            .getStart());

            if (bestEstimate > currentEstimate) {
                bestEstimate = currentEstimate;
            }
        }

        return bestEstimate;
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
    private List<Job> getRemainingJobs(CompletedJobSet completedSet,
            List<Job> jobs) {
        ArrayList<Job> result = new ArrayList<Job>(jobs.size());

        for (int i = 0; i < jobs.size(); i++) {
            if (!completedSet.isCompleted(i)) {
                result.add(jobs.get(i));
            }
        }

        return result;
    }
}
