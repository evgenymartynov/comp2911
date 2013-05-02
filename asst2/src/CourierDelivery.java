import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class CourierDelivery {
    public static void main(String[] args) throws FileNotFoundException {
        // Ensure we were invoked in the expected manner.
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Usage: java CourierDelivery FILE");
        }

        // This might throw.
        Scanner inputStream = new Scanner(new FileReader(args[0]));

        List<Job> jobs = (new InputParser(inputStream)).parseJobs();
        TSPHeuristic heuristic = new FuckingAwesomeSauceTSPHeuristic();
        Solver solver = new Solver(jobs, heuristic);
        solver.solve();
    }
}
