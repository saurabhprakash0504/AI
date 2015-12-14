import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2504198025352153214L;

	public Board(Map<Integer, Pit> lowList, Map<Integer, Pit> highList,
			Pit lowMancala2, Pit highMancala2) {
		this.highPits = highList;
		this.lowMancala = lowMancala2;
		this.highMancala = highMancala2;
		this.lowPits = lowList;
	}

	public static Board newInstance(Board board) {
		// this.highPits = board.highPits;
		// this.lowMancala = board.lowMancala;
		// this.highMancala = board.highMancala;
		// this.lowPits = board.lowPits;

		Board b = new Board();
		b.lowPits = new HashMap<Integer, Pit>();
		b.lowPits.putAll(board.lowPits);
		b.highPits = new HashMap<Integer, Pit>();
		b.lowMancala = new Pit(board.lowMancala.numberSeeds,
				board.lowMancala.position, board.lowMancala.player);
		b.highMancala = new Pit(board.highMancala.numberSeeds,
				board.highMancala.position, board.highMancala.player);
		b.highPits.putAll(board.highPits);
		return b;
		// TODO Auto-generated constructor stub
	}

	public Board() {
		// TODO Auto-generated constructor stub
	}

	Map<Integer, Pit> lowPits;
	Map<Integer, Pit> highPits;
	Pit lowMancala;
	Pit highMancala;

	@Override
	public Object clone() {

		Board copyCup = null;
		try {
			copyCup = (Board) super.clone();

			copyCup.lowMancala = (Pit) lowMancala.clone();
			copyCup.highMancala = (Pit) highMancala.clone();

			copyCup.lowPits = new HashMap<Integer, Pit>();
			for (int i = 0; i < lowPits.size(); i++) {
				copyCup.lowPits.put(i, (Pit) lowPits.get(i).clone());
			}
			copyCup.highPits = new HashMap<Integer, Pit>();

			for (int i = 0; i < highPits.size(); i++) {
				copyCup.highPits.put(i, (Pit) highPits.get(i).clone());
			}

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copyCup;

	}

	public int gameOver(Board board, int player) {

		// TODO Auto-generated method stub
		if (player == 1) {
			int u = 0;
			List<Pit> pits = new ArrayList<Pit>();
			pits.addAll(board.lowPits.values());
			for (Pit p : pits) {
				// Pit p = (Pit) pit.clone();

				if (p.numberSeeds > 0) {
					u++;
				}
			}
			if (u == 0)
				return 1;

			int x = 0;
			List<Pit> hpits = new ArrayList<Pit>();
			hpits.addAll(board.highPits.values());
			for (Pit p : hpits) {
				// Pit p = (Pit) pit.clone();

				if (p.numberSeeds > 0) {
					x++;
				}
			}
			if (x == 0)
				return 2;

		} else if (player == 2) {
			int x = 0;
			List<Pit> hpits = new ArrayList<Pit>();
			hpits.addAll(board.highPits.values());
			for (Pit p : hpits) {
				// Pit p = (Pit) pit.clone();

				if (p.numberSeeds > 0) {
					x++;
				}
			}
			if (x == 0)
				return 2;
			int u = 0;
			List<Pit> pits = new ArrayList<Pit>();
			pits.addAll(board.lowPits.values());
			for (Pit p : pits) {
				// Pit p = (Pit) pit.clone();

				if (p.numberSeeds > 0) {
					u++;
				}
			}
			if (u == 0)
				return 1;
		}
		return 0;
	}

}
