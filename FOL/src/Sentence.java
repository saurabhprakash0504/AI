import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sentence implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1249577528052710818L;
	boolean isLiteral;
	Predicate conclusion;
	List<Predicate> pList = new ArrayList<Predicate>();

	@Override
	public String toString() {
		String s = null;
		s = isLiteral + "conclusion " + conclusion.toString() + "\n"
				+ "no of predicates" + pList.size();
		for (Predicate t : pList) {
			s = s + t.toString();
		}
		return s;
	}
}
