import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Predicate implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8078207065010651987L;
	boolean isUnified;
	boolean ifNegate;
	List<Term> termList;
	String predicateName;

	public Predicate(Predicate p) {
		// TODO Auto-generated constructor stub
		isUnified = p.isUnified;
		ifNegate = p.ifNegate;
		predicateName = p.predicateName;
		termList = new ArrayList<Term>();

	}

	public Predicate() {
		isUnified = false;
		ifNegate = false;
		predicateName = new String();
		termList = new ArrayList<Term>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String s = null;
		s = ifNegate + "Predicate Name=" + predicateName + "\n" + "no of terms"
				+ termList.size();
		for (Term t : termList) {
			s = s + t.toString();
		}
		return s;
	}

	@Override
	public Object clone() {

		Predicate copyCup = null;
		try {
			copyCup = (Predicate) super.clone();

			copyCup.ifNegate = ifNegate;
			copyCup.isUnified = isUnified;
			copyCup.predicateName = predicateName;
			copyCup.termList = new ArrayList<Term>();

			for (int i = 0; i < termList.size(); i++) {
				copyCup.termList.add((Term) termList.get(i).clone());
			}
			

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copyCup;

	}

}
