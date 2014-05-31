package votes;

/**
 * VoteReader.class
 * 
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 *          A extension of a vote where the vote is also weighted.
 */
public class WeightedVote {
	private Vote v;
	private double weight;

	private int current = 0;

	public WeightedVote(Vote v, double weight) {
		this.v = v;
		this.weight = weight;
	}

	/**
	 * Pops the queue of votes and returns the current.
	 * 
	 * @return the top of the queue.
	 */
	public int getNext() {
		return v.getPerson(current++);
	}

	/**
	 * Peaks the queue of votes and returns the current.
	 * 
	 * @return the top of the queue.
	 */
	public int lookNext() {
		return v.getPerson(current);
	}

	/**
	 * Returns the weight of the vote
	 * 
	 * @return the weight of the vote
	 */
	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return v.toString() + " #" + weight;
	}

	/**
	 * Updates the weight with a factor so the new weight is the old one
	 * multiplied by the factor
	 * 
	 * @param factor
	 *            the factor to change the vote with
	 */
	public void updateWheight(double factor) {
		weight *= factor;

	}
}
