import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class mancala {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<args.length;i++){
			System.out.println(args[i]);
		}
		final long xstartTime = System.currentTimeMillis();
		BufferedReader br = null;
		PrintWriter writer = null;

		//if (args.length > 1) {
			try {
				br = new BufferedReader(
						new FileReader(
								"C:\\ALL_WORK_SPACE\\AI\\Game\\Mancala\\Copy of Copy of Vocare\\src\\input_1.txt"));
				writer = new PrintWriter("next_state.txt", "UTF-8");

				String line = br.readLine();
				int method = 0;
				int depth = Integer.MAX_VALUE;
				int yourPlayer = 0;
				int opponentPlayer = 0;
				Map<Integer, Pit> highList = new HashMap<Integer, Pit>();
				Map<Integer, Pit> lowList = new HashMap<Integer, Pit>();
				Pit highMancala = null;

				Pit lowMancala = null;

				if (line.trim().matches("[0-9]+")) {

					method = Integer.parseInt(line);
				}
				if (method > 0) {
					line = br.readLine();
					yourPlayer = Integer.parseInt(line);
					opponentPlayer = yourPlayer == 1 ? 2 : 1;

					line = br.readLine();
					depth = Integer.parseInt(line);
					line = br.readLine();

					String[] tokens = line.trim().split(" ");
					for (int i = 0; i < tokens.length; i++) {
						int x = Integer.parseInt(tokens[i]);
						Pit pit = new Pit(x, i, 2);
						highList.put(i, pit);
					}
					line = br.readLine();
					tokens = line.trim().split(" ");
					for (int i = 0; i < tokens.length; i++) {
						int x = Integer.parseInt(tokens[i]);
						Pit pit = new Pit(x, i, 1);

						lowList.put(i, pit);
					}
					highMancala = new Pit(Integer.parseInt(br.readLine()), -1,
							2);
					lowMancala = new Pit(Integer.parseInt(br.readLine()), -1, 1);
				}
				Game game = new Game(yourPlayer, opponentPlayer, lowList,
						highList, lowMancala, highMancala, depth);
				Board nextMove = null;
				switch (method) {
				case 1:
					nextMove = GameUtils.greedy(game);
					break;
				case 2:
					nextMove = MiniMax.miniMax(game);
					break;
				case 3:
					nextMove = AlphaBeta.miniMax(game);
					break;
				}
				if (null != nextMove) {
					String lpitString = "", hpitString = "";
					Iterator<Pit> lowPit = nextMove.lowPits.values().iterator();
					Iterator<Pit> highPit = nextMove.highPits.values()
							.iterator();

					for (int i = 0; i < nextMove.lowPits.size(); i++) {

						Pit lPit = lowPit.next();
						Pit hPit = highPit.next();
						if (i == nextMove.lowPits.size() - 1) {
							lpitString = lpitString + lPit.numberSeeds;
							hpitString = hpitString + hPit.numberSeeds;
						} else {
							lpitString = lpitString + lPit.numberSeeds + " ";
							hpitString = hpitString + hPit.numberSeeds + " ";
						}
					}
					writer.println(hpitString + "\n" + lpitString + "\n"
							+ nextMove.highMancala.numberSeeds + "\n"
							+ nextMove.lowMancala.numberSeeds + "");

				}
				// }

				// String everything = sb.toString();
				// System.out.println("Total Cases " + cases);

				// System.out.println(everything);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e);

			} finally {
				try {
					br.close();
					writer.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println(e);
				}
			}
			final long endTime = System.currentTimeMillis();

			System.out.println("Total execution time: "
					+ (endTime - xstartTime));
		}
//	}
}
