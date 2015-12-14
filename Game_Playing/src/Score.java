public class Score implements Cloneable {
	Board move;
	Pit p;
	int eval;
	int depth;
	boolean repChance;
	boolean temp;
	int loser;

	Score(Board move, int eval, int depth, Pit p, boolean repChance) {
		this.move = move;
		this.eval = eval;
		this.depth = depth;
		this.p = p;
		this.repChance = repChance;

	}

	Score(Board move, int eval, int depth, Pit p, boolean repChance,
			boolean temp) {
		this.move = move;
		this.eval = eval;
		this.depth = depth;
		this.p = p;
		this.repChance = repChance;
		this.temp = temp;
	}

	public Score() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object clone() {

		Score score = null;
		try {
			score = (Score) super.clone();
			score.p = (Pit) p.clone();
			score.move = (Board) move.clone();

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;

	}



}
