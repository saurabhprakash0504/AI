import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameUtils {

	public static Board greedy(Game game) throws Exception {
		// TODO Auto-generated method stub
		Board board = null;

		Collection<Pit> pits = (game.yourPlayer == 1 ? game.board.lowPits
				.values() : game.board.highPits.values());
		List<Pit> plist = new ArrayList<Pit>();
		for (Pit p : pits) {
			Pit pit = (Pit) p.clone();
			if (p.numberSeeds > 0) {
				Board b = (Board) game.board.clone();
				Board xboard = nextState(pit, b, game.yourPlayer);
				int x = calcEval(game.yourPlayer, xboard);
				pit.cost = x;
				plist.add(pit);
			}

		}

		Pit p = lowfindMax(plist);
		Board b = (Board) game.board.clone();
		Pit pit = (Pit) p.clone();
		board = nextState(pit, b, game.yourPlayer);

		return board;
	}

	private static Pit lowfindMax(Collection<Pit> evals) {
		// TODO Auto-generated method stub
		Comparator<Pit> costComparator = new Comparator<Pit>() {
			public int compare(Pit o1, Pit o2) {
				Pit i1 = o1;
				Pit i2 = o2;

				if ((i1.cost - i2.cost) == 0) {
					return i2.position - i1.position;

				}
				return i1.cost - i2.cost;
			}
		};

		Pit maxPit = Collections.max(evals, costComparator);
		return maxPit;
	}

	@SuppressWarnings("unused")
	private static Pit highfindMax(Collection<Pit> pits) {
		// TODO Auto-generated method stub
		Comparator<Pit> costComparator = new Comparator<Pit>() {
			public int compare(Pit o1, Pit o2) {
				Pit i1 = o1;
				Pit i2 = o2;

				if ((i1.cost - i2.cost) == 0) {

					return i2.position - i1.position;

				}
				return i1.cost - i2.cost;
			}
		};

		Pit maxPit = Collections.max(pits, costComparator);
		return maxPit;
	}

	private static Board nextState(Pit pit, Board board, int yourPlayer)
			throws Exception {
		Board x = (Board) board.clone();
		int noOfManc = pit.numberSeeds;
		if (yourPlayer == 1) {
			x.lowPits.get(pit.position).numberSeeds = 0;
		} else {
			x.highPits.get(pit.position).numberSeeds = 0;

		}
		Pit p = null;
		int p2 = 0;
		if (yourPlayer == 2) {
			p = x.highMancala;
		} else {
			p = x.highPits.get(0);
			p2 = 1;
		}
		MancalaList node1 = new MancalaList(p);

		for (int i = p2; i < x.highPits.size(); i++) {
			node1.InsertNext(x.highPits.get(i));

		}
		if (yourPlayer == 1) {
			node1.InsertNext(x.lowMancala);
		}
		for (int i = x.lowPits.size() - 1; i >= 0; i--) {
			node1.InsertNext(x.lowPits.get(i));

		}

		boolean findCheck = false;
		int oppPosition = 0;
		// if (yourPlayer == 1) {
		boolean startRemoving = false;

		int pos = 0;
		while (noOfManc > 0) {
			if (!findCheck && equalPit(node1.data, pit)) {

				findCheck = true;
				startRemoving = true;
				node1 = node1.next;

				continue;
			}
			if (startRemoving) {
				noOfManc--;
				if (node1.data.position != -1) {
					if (node1.data.player == 1) {

						x.lowPits.get(node1.data.position).numberSeeds++;
					} else if (node1.data.player == 2) {
						x.highPits.get(node1.data.position).numberSeeds++;

					}
				} else {
					if (node1.data.player == 1) {
						x.lowMancala.numberSeeds++;
					} else if (node1.data.player == 2) {
						x.highMancala.numberSeeds++;

					}
				}

			}
			oppPosition = node1.data.player;
			pos = node1.data.position;
			node1 = node1.next;

		}
		int opponentPlayer = yourPlayer == 1 ? 2 : 1;
		if (yourPlayer == oppPosition && pos == -1) {
			Board y = (Board) x.clone();
			int check = y.gameOver(y, yourPlayer);
			if (check != 0) {
				if (check == 1) {
					x = doGameOver(y, check);
				}

				else if (check == 2) {
					x = doGameOver(y, check);

				}

			} else {
				Game g = new Game(yourPlayer, opponentPlayer, y.lowPits,
						y.highPits, y.lowMancala, y.highMancala, 1);

				x = greedy(g);
			}
		} else if (yourPlayer == oppPosition && pos < x.lowPits.size()) {
			int check = x.gameOver(x, yourPlayer);
			if (check != 0) {
				if (check == 1) {
					x = doGameOver(x, check);
				}

				else if (check == 2) {
					x = doGameOver(x, check);

				}

			} else if (yourPlayer == 1) {

				if (x.lowPits.get(pos).numberSeeds == 1) {
					x.lowMancala.numberSeeds = x.lowMancala.numberSeeds
							+ x.highPits.get(pos).numberSeeds + 1;
					x.highPits.get(pos).numberSeeds = 0;
					x.lowPits.get(pos).numberSeeds = 0;
				}
			} else if (yourPlayer == 2) {
				if (x.highPits.get(pos).numberSeeds == 1) {
					x.highMancala.numberSeeds = x.highMancala.numberSeeds
							+ x.lowPits.get(pos).numberSeeds + 1;
					x.highPits.get(pos).numberSeeds = 0;
					x.lowPits.get(pos).numberSeeds = 0;
				}

			}
		}

		int check = x.gameOver(x, yourPlayer);
		if (check != 0) {
			if (check == 1) {
				x = doGameOver(x, check);
			}

			else if (check == 2) {
				x = doGameOver(x, check);

			}

		}
		return x;
	}

	static int calcEval(int player, Board board) {
		int eval = 0, sumLowPits = board.lowMancala.numberSeeds, sumHighPits = board.highMancala.numberSeeds;
		/*
		 * Iterator<Pit> lowPit = board.lowPits.values().iterator();
		 * Iterator<Pit> highPit = board.highPits.values().iterator();
		 * 
		 * for (int i = 0; i < board.lowPits.size(); i++) { Pit lPit =
		 * lowPit.next(); Pit hPit = highPit.next(); sumLowPits = sumLowPits +
		 * lPit.numberSeeds; sumHighPits = sumHighPits + hPit.numberSeeds; }
		 */
		eval = sumLowPits - sumHighPits;
		if (player == 1) {
			return eval;
		} else
			return -eval;

	}

	static int calcEval2(Board board) {
		int eval = 0, sumLowPits = board.lowMancala.numberSeeds, sumHighPits = board.highMancala.numberSeeds;
		/*
		 * Iterator<Pit> lowPit = board.lowPits.values().iterator();
		 * Iterator<Pit> highPit = board.highPits.values().iterator();
		 * 
		 * for (int i = 0; i < board.lowPits.size(); i++) { Pit lPit =
		 * lowPit.next(); Pit hPit = highPit.next(); sumLowPits = sumLowPits +
		 * lPit.numberSeeds; sumHighPits = sumHighPits + hPit.numberSeeds; }
		 */
		eval = sumLowPits - sumHighPits;
		// if (player == 1) {
		return eval;
		// } else
		// return -eval;

	}

	public static boolean equalPit(Pit data, Pit pit) {
		if (data.player == pit.player && data.position == pit.position)
			// TODO Auto-generated method stub
			return true;
		return false;
	}

	public static Board doGameOver(Board b, int player) {
		// TODO Auto-generated method stub
		Collection<Pit> pits = (player == 2 ? b.lowPits.values() : b.highPits
				.values());

		if (player == 1) {
			int sum = 0;
			for (Pit p : pits) {
				sum = p.numberSeeds + sum;
				p.numberSeeds = 0;
			}
			b.highMancala.numberSeeds = sum + b.highMancala.numberSeeds;
			// b.lowMancala.numberSeeds = 0;

		}

		else {
			int sum = 0;
			for (Pit p : pits) {
				sum = p.numberSeeds + sum;
				p.numberSeeds = 0;
			}
			b.lowMancala.numberSeeds = b.lowMancala.numberSeeds + sum;
			// b.highMancala.numberSeeds = 0;
		}
		return b;
	}

	public static String printVal(int v) {
		if (v == Integer.MAX_VALUE) {
			return "Infinity";
		} else if (v == Integer.MIN_VALUE) {
			return "-Infinity";
		}
		return Integer.toString(v);

	}
}
//