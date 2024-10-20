/**
 * The <code>Bigram</code> class will implement the two char for
 *   the two letters in a Bigram. It wil have all necessary methods
 *   to get and set the data.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw4
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

public class Bigram {
	private char first;
	private char second;

	/**
	 * Returns an instance of <code>Bigram</code>.
	 */
	public Bigram() {
		first = '\0';
		second = '\0';
	}

	/**
	 * Returns an instance of <code>Bigram</code>.
	 * @param first
	 *    the first <code>char</code> that will be stored as data.
	 * @param second
	 *    the second <code>char</code> that will be stored as data.
	 */
	public Bigram(char first, char second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Returns the first <code>char</code> in this <code>Bigram</code>.
	 * @return
	 *    The <code>char</code> that is stored in <code>first</code>.
	 */
	public char getFirst() {
		return first;
	}

	/**
	 * Returns the second <code>char</code> in this <code>Bigram</code>.
	 * @return
	 *    The <code>char</code> that is stored in <code>second</code>.
	 */
	public char getSecond() {
		return second;
	}

	//Setter methods

	/**
	 * Sets the first <code>char</code> in this <code>Bigram</code>.
	 * @param first
	 *    the <code>char</code> that will be stored as data.
	 */
	public void setFirst(char first) {
		this.first = first;
	}

	/**
	 * Sets the second <code>char</code> in this <code>Bigram</code>.
	 * @param second
	 *    the <code>char</code> that will be stored as data.
	 */
	public void setSecond(char second) {
		this.second = second;
	}

	/**
	 * Returns the two characters of the <code>Bigram</code>
	 *   in a concatenated String.
	 * @return
	 *    The <code>String</code> representation of this <code>Bigram</code>.
	 */
	public String toString() {
		return first + "" + second;
	}
}