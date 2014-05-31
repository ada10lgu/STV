package votes;

import java.util.ArrayList;

/**
 * Seat.class
 * 
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 *          The representation of a candidate going for election. It holds
 *          whether or not a candidate is elected or not as well as distributing
 *          the votes when the candidate won or lost.
 * 
 */
public class Seat {

	private String name;
	private ArrayList<WeightedVote> votes;

	public enum Status {
		WON, LOST, PENDING;
	}

	private Status status = Status.PENDING;

	/**
	 * 
	 * @param name
	 *            the name of the cadidate
	 */
	public Seat(String name) {
		this.name = name;
		votes = new ArrayList<>();
	}

	/**
	 * Adds a vote to this candidate
	 * 
	 * @param v
	 *            a wheighted vote
	 */
	public void addVote(WeightedVote v) {
		votes.add(v);
	}

	/**
	 * The ammount of votes this candidate has rounded down to nearest integer
	 * 
	 * @return the ammount of votes
	 */
	public int votes() {
		double n = 0;
		for (WeightedVote v : votes)
			n += v.getWeight();
		return (int) n;
	}

	/**
	 * Resdistribute the votes, if lost, or transfer votes if won.
	 * 
	 * @param quota
	 *            the quaota given needed to win
	 * @param seats
	 *            list of all the seats availabe, used to give other candidates
	 *            votes
	 * 
	 */
	public void redistributeVotes(int quota, Seat[] seats) {
		if (votes() >= quota) {
			status = Status.WON;

			double transferable = 0;
			for (WeightedVote v : votes)
				if (v.lookNext() != -1)
					transferable += v.getWeight();
			double factor = (votes() - quota) / transferable;

			for (WeightedVote v : votes)
				v.updateWheight(factor);

		} else {
			status = Status.LOST;
		}

		for (WeightedVote v : votes) {
			int next;
			while (true) {
				next = v.getNext();
				if (next == -1)
					break;
				if (seats[next].status == Status.PENDING) {
					seats[next].addVote(v);
					break;
				}

			}
		}
	}

	/**
	 * Returns the name of the candidate and its status. If it is still in the
	 * running the ammount of current votes are shown.
	 */
	@Override
	public String toString() {
		switch (status) {
		case PENDING:
			return name + " got " + votes() + " votes";
		case WON:
			return name + " got elected";
		default:
			return name + " lost";
		}
	}

	/**
	 * Returns the name of the candidate
	 * 
	 * @return the name of the candidate
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return true if the candidate has neither won nor lost yet
	 */
	public boolean isPending() {
		return status == Status.PENDING;
	}

	/**
	 * 
	 * @return true if the candidate has lost
	 */
	public boolean lost() {
		return status == Status.LOST;
	}

}
