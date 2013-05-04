/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.List;

/**
 * Internal representation of our A* search state.
 *
 * Note that comparison and hashing of this class ignores some of its fields.
 * See notes on its methods.
 */
public class TSPState implements Comparable<TSPState> {
    /**
     * Creates a new state with the given parameters.
     *
     * @param point
     *            Current location of the courier.
     * @param distance
     *            Distance traveled so far by the courier.
     * @param estimate
     *            An admissible estimate from some heuristic.
     * @param visitedSet
     *            Set of completed jobs.
     * @param jobs
     *            List of all jobs.
     * @param prevJob
     *            The last job completed before getting into this state, or
     *            {@code null}.
     * @param prevState
     *            Previous state that lead to exploring this state, or
     *            {@code null}.
     */
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

    /**
     * @return Current position.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @return Distance traveled so far.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * @return Set of completed jobs.
     */
    public YourMother getVisitedSet() {
        return visitedSet;
    }

    /**
     * @return Last completed job, or {@code null}.
     */
    public Job getPrevJob() {
        return prevJob;
    }

    /**
     * @return Previous state that lead to exploring this state.
     */
    public TSPState getPrevState() {
        return prevState;
    }

    /**
     * Compares two states by their priority in the A* search.
     *
     * This enables ordering states according to A* order by standard Java
     * containers.
     */
    @Override
    public int compareTo(TSPState other) {
        return priority - other.priority;
    }

    /**
     * Summarises state information, used for debugging.
     */
    @Override
    public String toString() {
        return "<State at=" + point + ", cost=" + distance + ">";
    }

    /**
     * Hashes this state using its point and visited set.
     *
     * Note that we want to consider two states to be equal if and only if they
     * describe the same node in the state-exploded graph. See Solver comments.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        result = prime * result
                + ((visitedSet == null) ? 0 : visitedSet.hashCode());
        return result;
    }

    /**
     * Compares this state against another, based on their locations and visited
     * sets.
     *
     * See Solver comments.
     */
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

    /**
     * Current location of the courier; used in differentiating between states.
     */
    private Point point;
    /**
     * Set of visited nodes; used in differentiating between states.
     */
    private YourMother visitedSet;

    /**
     * Distance traveled to get to this node.
     */
    private int distance;
    /**
     * Priority of this state, calculated from the traveled distance and a
     * heuristic estimate.
     */
    private int priority;
    /**
     * Last job completed to get to this state, or {@code null}.
     */
    private Job prevJob;
    /**
     * Last state that led to exploring this state, or {@code null}.
     */
    private TSPState prevState;
}
