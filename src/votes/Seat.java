package votes;

import java.util.ArrayList;

public class Seat {
	private String name;
	private ArrayList<WheightedVote> votes;

	public enum Status {
		WON, LOST, PENDING;
	}

	private Status status = Status.PENDING;

	public Seat(String name) {
		this.name = name;
		votes = new ArrayList<>();
	}

	public void addVote(WheightedVote v) {
		votes.add(v);
	}

	public ArrayList<WheightedVote> getExcessVotes(int quota) {
		return null;
	}

	public int votes() {
		double n = 0;
		for (WheightedVote v : votes)
			n += v.getWeight();
		return (int) n;
	}

	public void redistributeVotes(int quota, Seat[] seats) {
		if (votes() >= quota) {
			status = Status.WON;
			
			double transferable = 0;
			for (WheightedVote v : votes)
				if (v.lookNext() != -1)
					transferable += v.getWeight();
			double factor = (votes()-quota)/transferable;
			System.out.println(transferable);
			System.out.println(factor);
			
			for (WheightedVote v : votes)
				v.updateWheight(factor);
			
			
		} else {
			status = Status.LOST;
		}

		for (WheightedVote v : votes) {
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

	public String getName() {
		return name;
	}

	public String votesToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(votes.get(0));
		for (int i = 1; i < votes.size(); i++)
			sb.append("\n").append(votes.get(i));

		return sb.toString();
	}
	
	public boolean isPending() {
		return status == Status.PENDING;
	}


	public boolean lost() {
		return status == Status.LOST;
	}

}
