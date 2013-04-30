import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {
    public InputParser(Scanner input) {
        this.inputStream = input;
    }

    public List<Job> parseJobs() {
        List<Job> jobs = new ArrayList<Job>();

        while (inputStream.hasNext()) {
            ensureNext("Job");
            int startX = inputStream.nextInt();
            int startY = inputStream.nextInt();

            ensureNext("to");
            int endX = inputStream.nextInt();
            int endY = inputStream.nextInt();

            jobs.add(new Job(startX, startY, endX, endY));
        }

        return jobs;
    }

    /**
     * Ensures that next token in the input stream is exactly what we expect it
     * to be. Will throw an exception on mismatch or read error.
     *
     * @param token
     *            Expected token, which input must match exactly.
     */
    private void ensureNext(String token) {
        String command = inputStream.next();
        if (!command.equals(token)) {
            throw new IllegalArgumentException("Expected token " + token
                    + ", got " + command);
        }
    }

    private Scanner inputStream;
}
