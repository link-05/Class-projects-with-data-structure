package src;

/**
 * The <code>Variable</code> class implements an object
 *   representing the variables in the blocks.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw3
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */
public class Variable {
	private String name;
	private int initialValue;

	/**
	 * Returns an instance of <code>Variable</code>.
	 *
	 * @param name
	 *    The name of the variable.
	 */
	public Variable(String name) {
		this.name = name;
		this.initialValue = 0;
	}

	/**
	 * Returns an instance of <code>Variable</code>.
	 *
	 * @param name
	 *    The name of the variable.
	 *
	 * @param initialValue
	 *    The starting value of the variable.
	 */
	public Variable(String name, int initialValue) {
		this.name = name;
		this.initialValue = initialValue;
	}
	//getter and setter methods for the name and initialValue

	/**
	 * Returns the name of the variable.
	 *
	 * @return
	 *    returns the String representing the <code>name</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the initial value of the variable.
	 *
	 * @return
	 *    returns the int representing the <code>initialValue</code>.
	 */
	public int getInitialValue() {
		return initialValue;
	}

	/**
	 * Sets the name of the variable.
	 *
	 * @param name
	 *    The <code>Variable</code> name is stored as a String.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the initial value of the variable.
	 *
	 * @param initialValue
	 *    The initial value of the variable is stored as an int.
	 */
	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}
}
