package tedxperiments.math.entrenamente;

public class Operation {
	private int operand_1;
	private int operand_2;
	private int operator;
	private int avgtime;
	private boolean hide;
	
	public void setOp1(int O) {
		operand_1 = O;
	}
	public int getOp1() {
			return operand_1;
	}
	
	public void setOp2(int o) {
		operand_2 = o;
	}
	public int getOp2() {
			return operand_2;
	}
	
	public void setOperator(int op) {
		operator = op;
	}
	public int getOperator() {
			return operator;
	}

	public void setTimeavg(int at) {
		avgtime = at;
	}
	public int getTimeavg() {
			return avgtime;
	}
	
	public void setHide (boolean h) {
		hide = h;
	}
	public boolean getHide() {
			return hide;
	}
	
}
