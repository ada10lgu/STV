package votes;

import java.io.File;
import java.io.IOException;


/**
 * STV.class
 *  
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 * Voting system based on the Single Transferable Vote system made by Hare�Clark.
 * 
 * For wiki article about STV refer to:
 * http://en.wikipedia.org/wiki/Single_transferable_vote
 */
public class STV {

	private static boolean verbose;

	public static void main(String[] args) {
		String file = null;
		int seats = -1;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-f":
				int data = ++i;
				if (data == args.length)
					error("Missing filename");
				else
					file = args[i];
				break;
			case "-s":
				int next = ++i;
				if (next == args.length)
					error("Missing ammount of seats");
				else
					try {
						seats = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						System.out.println(args[i]);
						error("Illegal seat ammount.");
					}
				break;
			case "-v":
				verbose = true;
				break;
			default:
				error("Illegal input");
			}
		}

		if (file == null)
			error("No file");
		if (seats == -1)
			error("Ammount of seats not determined");
		VoteReader vr = null;
		try {
			vr = new VoteReader(new File(file));
		} catch (IOException e) {
			error("Error reading vote-file");
		}

		Seat[] winners = new SingleTransferableVote(vr, seats)
				.calculateWinners();

		for (Seat s : winners)
			System.out.println(s.getName());
	}

	/**
	 * Used to print information if user selected verbose output
	 */
	public static void println() {
		if (verbose)
			System.out.println();
	}

	/**
	 * Used to print information if user selected verbose output
	 */
	public static void println(Object o) {
		if (verbose)
			System.out.println(o);
	}

	/**
	 * Used to print information if user selected verbose output
	 */
	public static void printf(String s, Object... o) {
		if (verbose)
			System.out.printf(s, o);
	}

	/**
	 * Prints an error message and terminates the program if error found
	 * Will also show the correct parameters to use
	 */
	private static void error(String message) {
		System.out.println("Error when running STV-voting system");
		System.out.println("Message: " + message);
		System.out
				.println("The program should be called in the following manner");
		System.out
				.println("java -jar STV.jar ((-f \"filename\") | [-v] | (-s n))");
		System.out.println("-f indicates what file to be read from");
		System.out.println("-v verbose");
		System.out.println("-s ammount of seats to fill");
		System.exit(1);
	}

}
