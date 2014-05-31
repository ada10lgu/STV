package votes;

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

	public int getPerson(int round) {
		if (round >= votes.length)
			return -1;
		return votes[round];
	}
}
