import java.io.Serializable;

public class Term implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8521195396504539194L;
	boolean isVariable;
	boolean isConstant;
	String variableName;
	String value;

	/**
	 * @param term
	 */
	public Term(Term term) {

		isVariable = term.isVariable;
		isConstant = term.isConstant;
		variableName = term.variableName;
		value = term.value;
	}

	public Term() {
		isVariable = false;
		isConstant = false;
		variableName = "";
		value = "";

	}

	@Override
	public String toString() {
		String s = null;
		if (isVariable) {
			s = "Variable" + variableName + "=" + value;
		}
		if (isConstant) {
			s = "Constant" + "=" + value;
		}
		return s;
	}
	@Override
	public Object clone() {

		Term copyCup = null;
		try {
			copyCup = (Term) super.clone();

			copyCup.isVariable = isVariable;
			copyCup.isConstant = isConstant;
			copyCup.variableName = variableName;
			copyCup.value = value;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return copyCup;

	}

}
