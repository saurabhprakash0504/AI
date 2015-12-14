import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rule implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8800936836849885513L;
	List<String> variables;
	List<Predicate> posLiterals;
	List<Predicate> negLiterals;
	// Map<List<Predicate>,List<String>> premises;
	List<VarMap> premises;

	// List<VarMap> premises;
	public Rule() {
		this.posLiterals = new ArrayList<Predicate>();
		this.negLiterals = new ArrayList<Predicate>();
		// this.premises = new HashMap<List<Predicate>,List<String>>();
		this.premises = new ArrayList<VarMap>();
		this.variables = new ArrayList<String>();
	}
}
