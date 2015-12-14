
public class VarSub {
	public VarSub(String var, String val) {
		super();
		this.var = var;
		this.val = val;
	}

	String var;
	String val;

	@Override
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;

		result = (result * PRIME)
				+ (this.var == null ? 43 : this.var.hashCode());
		result = (result * PRIME)
				+ (this.val == null ? 43 : this.val.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof VarSub))
			return false;
		VarSub other = (VarSub) o;
		if (!other.canEqual((Object) this))
			return false;
		if (!super.equals(o))
			return false;
		if (this.var.equals(other.var))
			return false;
		if (this.val.equals(other.val))
			return false;
		return true;
	}

	protected boolean canEqual(Object other) {
		return other instanceof VarSub;
	}

}
