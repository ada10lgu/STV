package votes;

/**
 * SingleTransferableVote.class
 * 
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 *          The brains behind the operation that does the selection of the
 *          winners
 * 
 */
public class SingleTransferableVote {

	private Seat[] seats;
	private int quota;
	private int seatsToFill;

	/**
	 * The brains behind the operation that does the selection of the winners
	 * 
	 * @param vr
	 *            a votereader with vote-data
	 * @param seats
	 *            the number of seats to be filled
	 */
	public SingleTransferableVote(VoteReader vr, int seats) {
		seatsToFill = seats;
		this.seats = new Seat[vr.getNames().length];

		quota = vr.getQuota(seats);

		for (int i = 0; i < vr.getNames().length; i++)
			this.seats[i] = new Seat(vr.getNames()[i]);
		for (WeightedVote v : vr.getGroupedVotes())
			this.seats[v.getNext()].addVote(v);
	}

	/**
	 * Calculates the winners
	 * 
	 * @return a list of Seats that represents the winners of the election
	 */
	public Seat[] calculateWinners() {
		STV.printf("Quota is %d\n\n", quota);

		int seatsFilled = 0;
		int peopleLeft = seats.length;
		Seat current;
		int round = 0;
		while (peopleLeft + seatsFilled != seatsToFill) {
			STV.println("Round " + ++round);
			printSeats();
			STV.println();

			current = getHighest();
			if (current.votes() >= quota) {
				STV.printf(
						"%s is declared a winner and its access votes will be redistributed\n",
						current.getName());
				seatsFilled++;
				peopleLeft--;
			} else {
				current = getLowest();
				STV.printf(
						"Since no winner existed we remove the one with fewest votes, in this case %s\n",
						current.getName());
				peopleLeft--;
			}
			current.redistributeVotes(quota, seats);
			STV.println();
			STV.println();
		}

		Seat[] winners = new Seat[seatsToFill];
		int n = 0;
		for (Seat s : seats)
			if (!s.lost())
				winners[n++] = s;
		return winners;
	}

	/**
	 * Prints the seats, if the user selected verbose output, and its statuses
	 */
	private void printSeats() {
		for (Seat s : this.seats)
			STV.println(s);
	}

	/**
	 * Returns the seat with the lowest votes that still haven't neither won nor
	 * lost
	 * 
	 * @return the seat with the lowest votes that still haven't neither won nor
	 *         lost
	 */
	private Seat getLowest() {
		int lowest = Integer.MAX_VALUE;
		Seat current = null;
		for (Seat s : seats) {
			if (s.isPending() && s.votes() < lowest) {
				lowest = s.votes();
				current = s;
			}
		}
		return current;
	}

	/**
	 * Returns the seat with the highest votes that still haven't neither won
	 * nor lost
	 * 
	 * @return the seat with the highest votes that still haven't neither won
	 *         nor lost
	 */
	private Seat getHighest() {
		int lowest = Integer.MIN_VALUE;
		Seat current = null;
		for (Seat s : seats) {
			if (s.isPending() && s.votes() > lowest) {
				lowest = s.votes();
				current = s;
			}
		}
		return current;
	}

}
