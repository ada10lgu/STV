package votes;

/**
 * Vote.class
 * 
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 *          Represents a vote. One vote from one person.
 */
public class Vote {
	private int[] votes;

	public Vote(int[] votes) {
		this.votes = votes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vote)
			return ((Vote) obj).hashCode() == hashCode();
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(votes[0]);
		for (int i = 1; i < votes.length && votes[i] != -1; i++)
			sb.append("-").append(votes[i]);
		return sb.toString();
	}

	/**
	 * Return the person the votee voted at, round is used to select if it is
	 * the first second or so on choice.
	 * 
	 * @param round
	 *            att what round we are inspecting.
	 * @return the person the votee voted at
	 */
	public int getPerson(int round) {
		if (round >= votes.length)
			return -1;
		return votes[round];
	}
}
