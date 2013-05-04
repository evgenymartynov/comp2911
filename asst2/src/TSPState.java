/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.List;

public class TSPState implements Comparable<TSPState> {
    public TSPState(Point point, int distance, int estimate,
            YourMother visitedSet, List<Job> jobs, Job prevJob,
            TSPState prevState) {
        this.point = point;
        this.distance = distance;
        this.priority = distance + estimate;
        this.visitedSet = visitedSet;

        this.prevJob = prevJob;
        this.prevState = prevState;
    }

    public Point getPoint() {
        return point;
    }

    public int getDistance() {
        return distance;
    }

    public YourMother getVisitedSet() {
        return visitedSet;
    }

    public Job getPrevJob() {
        return prevJob;
    }

    public TSPState getPrevState() {
        return prevState;
    }

    public boolean isFinalState() throws Exception {
        throw new Exception("nope");
    }

    @Override
    public int compareTo(TSPState other) {
        return priority - other.priority;
    }

    @Override
    public String toString() {
        return "<State at=" + point + ", cost=" + distance + ">";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        result = prime * result
                + ((visitedSet == null) ? 0 : visitedSet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TSPState)) {
            return false;
        }

        TSPState other = (TSPState) obj;
        if (point == null) {
            if (other.point != null) {
                return false;
            }
        } else if (!point.equals(other.point)) {
            return false;
        }

        if (visitedSet == null) {
            if (other.visitedSet != null) {
                return false;
            }
        } else if (!visitedSet.equals(other.visitedSet)) {
            return false;
        }

        return true;
    }

    // These are included in the overall "state".
    private Point point;
    private YourMother visitedSet;

    // These are for search's bookkeeping, and do not change the state we are
    // actually at.
    private int distance;
    private int priority;
    private Job prevJob;
    private TSPState prevState;
}
