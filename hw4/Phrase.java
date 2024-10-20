/**
 * The <code>Phrase</code> class will implement the <code>Queue</code>
 *   interface through extending it. It interacts with the
 *   <code>KeyTable</code> class to encrypt and decrypt the
 *   phrase.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw4
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

import java.util.LinkedList;
import java.util.Queue;
import java.lang.StringBuilder;

public class Phrase extends LinkedList<Bigram> implements Queue<Bigram> {
	private static Queue<Bigram> phrase = new LinkedList<>();

	/**
	 * Returns an instance of <code>Phrase</code> that removes duplicates and fill in x.
	 * @param s
	 *   The string that converts into a <code>Phrase</code>.
	 * @return
	 *  The <code>Phrase</code> that is created.
	 */
	public static Phrase buildPhraseFromStringForEnc(String s) {
		StringBuilder str = new StringBuilder(
		  s.replaceAll("\\s", "").toUpperCase().replaceAll("J", "I"));
		//Go through the phrase and remove all the numbers
		for(int x = 0; x < str.length(); x++) {
			//If the character is a number, remove it
			if("0123456789".contains(str.substring(x, x + 1))) {
				str.deleteCharAt(x);
			}
		}
		Phrase builtPhrase = new Phrase();
		while(!str.isEmpty()) {
			//If length is at 1 then add an X to the end
			if(str.length() == 1) {
				builtPhrase.enqueue(new Bigram(str.charAt(str.length() - 1), 'X'));
				break;
			}
			char first = str.charAt(0);
			char second = str.charAt(1);
			if(first == second) {
				second = 'X';
				str.deleteCharAt(1);
			}
			//add two character to phrase
			builtPhrase.enqueue(new Bigram(first, second));
			str.delete(0, 2);
		}
		return builtPhrase;
	}

	public Phrase encrypt(KeyTable key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("KeyTable is null.");
		}
		Phrase encrypted = new Phrase();
		while(!this.isEmpty()) {
			Bigram b = this.dequeue();
			int c1Row = key.findRow(b.getFirst());
			int c2Row = key.findRow(b.getSecond());
			int c1Col = key.findCol(b.getFirst());
			int c2Col = key.findCol(b.getSecond());
		}

		return encrypted;
	}

	private static Bigram sameRow(KeyTable key, Bigram b,
	  int c1Row, int c2Row, int c1Col, int c2Col) {
		char[][] table = key.getKeyTable();

		return bigram()
	}

	public Phrase decrypt(KeyTable key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("KeyTable is null.");
		}
		Phrase decrypted = new Phrase();

		return decrypted;
	}

	/**
	 * Adds a <code>Bigram</code> into the <code>phrase</code>.
	 * @param b
	 *    The <code>Bigram</code> that will be added.
	 */
	public void enqueue(Bigram b) {
		phrase.add(b);
	}

	/**
	 * Removes the <code>Bigram</code> at the front of the <code>Phrase</code>
	 *   then return the value too.
	 * @return
	 *   The <code>Bigram</code> at the front of the <code>Phrase</code>.
	 */
	public Bigram dequeue() {
		return phrase.poll();
	}

	/**
	 * Returns the <code>Bigram</code> at the front of the <code>Phrase</code>
	 *   without removing it.
	 * @return
	 *  The <code>Bigram</code> at the front of the <code>Phrase</code>.
	 */
	public Bigram peek() {
		return phrase.peek();
	}

	/**
	 * Returns the <code>phrase</code> queue.
	 * @return
	 *   The linked list queue with Bigram.
	 */
	public Queue<Bigram> getPhrase() {
		return phrase;
	}
}