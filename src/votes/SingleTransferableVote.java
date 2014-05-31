package votes;

import java.io.File;
import java.io.IOException;

public class SingleTransferableVote {

	public static void main(String[] args) throws IOException {
		VoteReader vr = new VoteReader(
				new File("files/fake_data/data-35-12000-5"));
		int seats = 27;
		Seat[] winners = new SingleTransferableVote(vr, seats).calculateWinners();
		
		System.out.println("The winners are:");
		for (Seat s : winners)
			System.out.println(s.getName());
	}

	private Seat[] seats;
	private int quota;
	private int seatsToFill;

	public SingleTransferableVote(VoteReader vr, int seats) {
		
		System.out.println(vr.getGroupedVotes());
		seatsToFill = seats;
		this.seats = new Seat[vr.getNames().length];

		quota = vr.getQuota(seats);

		for (int i = 0; i < vr.getNames().length; i++)
			this.seats[i] = new Seat(vr.getNames()[i]);
		for (WheightedVote v : vr.getGroupedVotes()) {
			this.seats[v.getNext()].addVote(v);
		}
	}

	public Seat[] calculateWinners() {
		System.out.printf("Quota is %d\n\n",quota);
		
		
		int seatsFilled = 0;
		int peopleLeft = seats.length;
		Seat current;
		int round = 0;
		while (peopleLeft + seatsFilled != seatsToFill) {
			System.out.println("Round " + ++round);
			printSeats();
			System.out.println();
			
			current = getHeighest();
			if (current.votes() >= quota) {
				System.out.printf("%s is declared a winner and its access votes will be redistributed\n",current.getName());
				seatsFilled++;
				peopleLeft--;
			} else {
				current = getLowest();
				System.out.printf("Since no winner existed we remove the one with fewest votes, in this case %s\n",current.getName());
				peopleLeft--;
			}
			current.redistributeVotes(quota, seats);
			System.out.println();
			System.out.println();
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
			System.out.println(s);
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
