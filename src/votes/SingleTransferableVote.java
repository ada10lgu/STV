package votes;

public class SingleTransferableVote {

	private Seat[] seats;
	private int quota;
	private int seatsToFill;

	public SingleTransferableVote(VoteReader vr, int seats) {
		seatsToFill = seats;
		this.seats = new Seat[vr.getNames().length];

		quota = vr.getQuota(seats);

		for (int i = 0; i < vr.getNames().length; i++)
			this.seats[i] = new Seat(vr.getNames()[i]);
		for (WheightedVote v : vr.getGroupedVotes())
			this.seats[v.getNext()].addVote(v);
	}

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

			current = getHeighest();
			if (current.votes() >= quota) {
				STV.printf("%s is declared a winner and its access votes will be redistributed\n",
								current.getName());
				seatsFilled++;
				peopleLeft--;
			} else {
				current = getLowest();
				STV.printf("Since no winner existed we remove the one with fewest votes, in this case %s\n",
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

	private void printSeats() {
		for (Seat s : this.seats)
			STV.println(s);
	}

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

	private Seat getHeighest() {
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
