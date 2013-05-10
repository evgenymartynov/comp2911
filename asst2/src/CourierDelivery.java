/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point into the program. Wires up our internals and runs the
 * solver.
 *
 * Usage: java CourierDelivery FILE
 */
public class CourierDelivery {
    /**
     * Entry point into the program.
     *
     * @param args
     *            Command-line arguments to the program. The first and only
     *            argument should be a path to an exiting file with the input
     *            data.
     * @throws FileNotFoundException
     *             If the file cannot be found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Ensure we were invoked in the expected manner.
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Usage: java CourierDelivery FILE");
        }

        // This might throw.
        Scanner inputStream = new Scanner(new FileReader(args[0]));

        // Choose the heuristic for A*, according to the Strategy pattern.
        TSPHeuristic heuristic = new ConcreteTSPHeuristic();

        // And go.
        List<Job> jobs = (new InputParser(inputStream)).parseJobs();
        Solver solver = new Solver(jobs, heuristic);
        solver.solve();
    }
}
