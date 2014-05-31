package votes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * VoteReader.class
 * 
 * @author Lars "mezz" Gustafson (mezz@dsek.lth.se)
 * @version 1
 * 
 * Class for reading votes from the vote-file.
 * Will also group them together as weighted votes.
 * 
 * The file have to be formated in the following manner:
 * n v
 * name_1
 * name_2
 * name_3
 * ...
 * name_n
 * vote_1
 * vote_2
 * vote_3
 * ...
 * vote_v
 * 
 * Where:
 * n is the number of people running for election.
 * v is the number of votes collected.
 * 
 */
public class VoteReader {

	private String[] names;
	private Vote[] votes;
	private HashMap<Vote, Integer> groupedVotes;

	/**
	 * Reads the data from the file given
	 * @param data file to read from
	 * @throws IOException if there is a problem reading the file
	 */
	public VoteReader(File data) throws IOException {
		Scanner s = new Scanner(data);

		int names = s.nextInt();
		int votes = s.nextInt();
		s.nextLine();

		this.names = new String[names];
		for (int i = 0; i < names; i++) {
			this.names[i] = s.nextLine();
		}

		this.votes = new Vote[votes];

		for (int i = 0; i < votes; i++) {
			int[] v = new int[names];
			for (int j = 0; j < names; j++) {
				v[j] = s.nextInt();
			}
			this.votes[i] = new Vote(v);
		}

		s.close();

		groupedVotes = new HashMap<>();

		for (Vote v : this.votes) {
			Integer n = groupedVotes.get(v);
			if (n == null)
				n = 0;
			n++;
			groupedVotes.put(v, n);
		}
	}

	/**
	 * Returns a list of all names in the election
	 * @return a list of all names in the election
	 */
	public String[] getNames() {
		return names;
	}

	
	/**
	 * Returns a list of all votes weighted.
	 * So if two people have voted exactly the same they are counted as one vote but with a weight of 2.
	 * 
	 * @return a list of all votes weighted 
	 */
	public ArrayList<WeightedVote> getGroupedVotes() {
		ArrayList<WeightedVote> wv = new ArrayList<>();

		for (Vote v : groupedVotes.keySet())
			wv.add(new WeightedVote(v, groupedVotes.get(v)));

		return wv;
	}

	/**
	 * Returns the quota by the following algoritm
	 * 
	 * (number of votes/(seats to fill + 1)) + 1
	 * 
	 * @param seats the amount of seats to fill
	 * @return the quota needed to be elected
	 */
	public int getQuota(int seats) {
		return (votes.length / (seats + 1)) + 1;
	}

}
