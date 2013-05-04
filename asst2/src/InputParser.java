/**
 * COMP2911 Assignment 2
 *
 * Author: Evgeny Martynov
 * Date: April-May 2013
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class converts between the file-format description of jobs and our
 * internal job representation. It strictly enforces the format specified in
 * assignment spec.
 */
public class InputParser {
    /**
     * @param input
     *            Input stream to read and parse.
     */
    public InputParser(Scanner input) {
        this.inputStream = input;
    }

    /**
     * Read all of the input stream and return parsed data. May throw an
     * exception if the input is malformed.
     *
     * @return List of jobs, converted into our internal format.
     */
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
     * to be and consumes it. Will throw an exception on mismatch or read error.
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

    /**
     * Input stream of commands.
     */
    private Scanner inputStream;
}
