package ex4;

import java.util.Scanner;

/**
 * 
 * A program that will read in 2 strings from stdin and determine if the first
 * is contained within the second.
 * 
 */
public class SubstringFinder {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String haystack = scanner.nextLine();
		String needle = scanner.nextLine();

		scanner.close();

		if (haystack.contains(needle)) {
			System.out.println(haystack + " contains " + needle);
		} else {
			System.out.println(haystack + " does not contain " + needle);
		}
	}
}
