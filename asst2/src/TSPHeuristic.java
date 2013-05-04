/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.List;

public interface TSPHeuristic {
    public int computeEstimate(Point point, YourMother visitedSet,
            List<Job> jobs);
}
