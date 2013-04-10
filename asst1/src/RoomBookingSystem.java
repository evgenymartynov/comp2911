/**
 * COMP2911 Assignment 1
 *
 * Date: April 2013
 * Author: Evgeny Martynov
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Main entry point for the room booking system.
 *
 * Usage: java RoomBookingSystem FILE
 *
 */
public class RoomBookingSystem {
	/**
	 * Entry point for the room booking system.
	 *
	 * @param args
	 *            Command-line arguments. First and only argument is the name of
	 *            the input file to be processed.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Ensure we were invoked in the expected manner.
		if (args.length != 1) {
			throw new IllegalArgumentException(
					"Usage: java RoomBookingSystem FILE");
		}

		// Open up the file -- this might throw an error.
		Scanner commandStream = new Scanner(new FileReader(args[0]));

		// Wire up our internals.
		BookingSystemCore system = new BookingSystemCore();
		CommandParser parser = new CommandParser(system, commandStream);

		// And run.
		parser.execute();
	}
}
