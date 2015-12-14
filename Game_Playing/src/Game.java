import java.io.Serializable;
import java.util.Map;

public class Game implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3775061258937173436L;
	int yourPlayer;
	int oppPlayer;
	Board board;
	int depth;

	Game(int yourPlayer, int oppPlayer, Map<Integer,Pit> lowList, Map<Integer,Pit> highList,
			Pit lowMancala, Pit highMancala,int depth) {
		board = new Board(lowList, highList, lowMancala, highMancala);
		this.yourPlayer = yourPlayer;
		this.oppPlayer = oppPlayer;
		this.depth=depth;
		
	}
	@Override
	public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
            throw new InternalError(e.toString());
        }
    }
}

