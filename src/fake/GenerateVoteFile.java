package fake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class GenerateVoteFile {

	
	public static void main(String[] args) throws FileNotFoundException {
		
		int sead = 5;
		int namesN = 3;
		int votesN = 15;
		File f = new File("files/fake_data/data-"+namesN+"-"+votesN+"-"+sead);
		
		String[] names = getRandomNames(namesN, sead); 
		int[][] votes = getVotes(votesN, namesN, sead);
		
		PrintWriter pw = new PrintWriter(f);
		
		pw.printf("%d %d\n",namesN,votesN);
		
		for (String s: names) {
			pw.println(s);
		}
		
		for (int[] vote : votes) {
			for (int v : vote)
				pw.printf("%d ",v);
			pw.println();
		}
		pw.flush();
		pw.close();
		
	}
	
	
	public static int[][] getVotes(int nVotes, int nDifferent, int sead) {
		int[][] votes = new int[nVotes][nDifferent];
		Random r = new Random(sead);

		ArrayList<Integer> base = new ArrayList<>(nDifferent);
		for (int i = 0; i < nDifferent; i++)
			base.add(i);

		for (int i = 0; i < nVotes; i++) {
			int tempNDifferent = r.nextInt(nDifferent - 1) + 1;
			ArrayList<Integer> temp = new ArrayList<>(base);

			for (int j = 0; j < nDifferent; j++) {
				int value = -1;
				if (j <= tempNDifferent)
					value = temp.remove(r.nextInt(temp.size()));
				votes[i][j] = value;
			}
		}
		return votes;
	}

	public static String[] getRandomNames(int n, int sead) {
		ArrayList<String> randomNames = new ArrayList<>();
		randomNames.add("Sherwin Swet");
		randomNames.add("Marden Wither");
		randomNames.add("Kody Lindsay");
		randomNames.add("Brigham Morton");
		randomNames.add("Booker Stevens");
		randomNames.add("Noel Smither");
		randomNames.add("Erik Rowley");
		randomNames.add("Sherwin Lindsay");
		randomNames.add("Hugh Reeve");
		randomNames.add("Adan Smither");
		randomNames.add("Elyse Soames");
		randomNames.add("Blanca Haley");
		randomNames.add("Sophia Payton");
		randomNames.add("Layla Foy");
		randomNames.add("Carissa Breeden");
		randomNames.add("Emily Peddle");
		randomNames.add("Annika Sweet");
		randomNames.add("Kaelyn Kimberley");
		randomNames.add("Phoebe Nibley");
		randomNames.add("Katarina Allerton");

		Random r = new Random(sead);

		String[] names = new String[n];

		for (int i = 0; i < n; i++) {
			names[i] = randomNames.remove(r.nextInt(randomNames.size()));
		}

		return names;
	}
}
