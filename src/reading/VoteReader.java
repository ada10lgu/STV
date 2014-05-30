package reading;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import votes.Vote;

public class VoteReader {

	private String[] names;
	private Vote[] votes;
	private HashMap<Vote, Integer> groupedVotes;

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

	public Vote[] getVotes() {
		return votes;
	}

	public String[] getNames() {
		return names;
	}

	public HashMap<Vote, Integer> getGroupedVotes() {
		return groupedVotes;
	}

}
