import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VarMap implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3514400018976814621L;
	boolean ifNegate=false;
	List<Predicate> prList;
	List<String> varList;

	public VarMap() {
		prList = new ArrayList<Predicate>();
		varList = new ArrayList<String>();

	}
	@Override
	public Object clone() {

		VarMap copyCup = null;
		try {
			copyCup = (VarMap) super.clone();

			copyCup.ifNegate =  ifNegate;
			copyCup.prList = new ArrayList<Predicate>();
			copyCup.varList = new ArrayList<String>();
			

			
			for (int i = 0; i < prList.size(); i++) {
				copyCup.prList.add((Predicate) prList.get(i).clone());
			}
			for (int i = 0; i < varList.size(); i++) {
				copyCup.varList.add( varList.get(i));
			}

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copyCup;

	}
}
