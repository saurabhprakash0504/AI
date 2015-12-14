import java.io.Serializable;

public class Pit implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7880489696145719261L;

	public Pit(int x, int i, int p) {
		position = i;
		numberSeeds = x;
		player = p;
		// TODO Auto-generated constructor stub
	}

	public Pit(Pit pit) {
		position = pit.position;
		numberSeeds = pit.numberSeeds;
		player = pit.player;
		cost = pit.cost;

		// TODO Auto-generated constructor stub
	}

	int player;
	int cost;
	int position;
	int numberSeeds;
	int temp;

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// This should never happen
			throw new InternalError(e.toString());
		}
	}

	@Override
	public String toString() {
		String s = "";
		if (this.player == 1) {
			s = "B" + (this.position + 2) + "," + this.numberSeeds;
		} else {
			s = "A" + (this.position + 2) + "," + this.numberSeeds;
			

		}
		return s;

	}

}
