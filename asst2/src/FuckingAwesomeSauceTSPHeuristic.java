/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.ArrayList;
import java.util.List;

public class FuckingAwesomeSauceTSPHeuristic implements TSPHeuristic {
    @Override
    public int computeEstimate(Point point, YourMother visitedSet,
            List<Job> jobs) {
        List<Job> remainingJobs = getRemainingJobs(visitedSet, jobs);
        int estimate = 0;

        // Find closest incomplete job to us.
        int shorestDistanceFromPoint = INFINITY;
        for (Job job : remainingJobs) {
            shorestDistanceFromPoint = Math.min(shorestDistanceFromPoint, job
                    .getStart().distanceTo(point));
        }
        if (shorestDistanceFromPoint < INFINITY) {
            estimate += shorestDistanceFromPoint;
        }

        // Add up all remaining job distances.
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

    private List<Job> getRemainingJobs(YourMother visitedSet, List<Job> jobs) {
        ArrayList<Job> result = new ArrayList<Job>(jobs.size());

        for (int i = 0; i < jobs.size(); i++) {
            if (!visitedSet.getBit(i)) {
                result.add(jobs.get(i));
            }
        }

        return result;
    }

    private final int INFINITY = (int) 1e10;
}
