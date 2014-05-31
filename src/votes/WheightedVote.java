package votes;

public class WheightedVote {
	private Vote v;
	private double weight;
	
	private int current = 0;
	
	public WheightedVote(Vote v,double weight) {
		this.v = v;
		this.weight= weight;
	}
	
	public int getNext() {
		return v.getPerson(current++);
	}
	
	public int lookNext() {
		return v.getPerson(current);
	}
	
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return v.toString() + " #" + weight;
	}

	public void updateWheight(double factor) {
		weight *= factor;
		
	}
}
