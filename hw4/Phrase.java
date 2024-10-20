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

	/**
	 * Returns an instance of <code>Phrase</code> that removes duplicates and fill in x.
	 * @param s
	 *   The string that converts into a <code>Phrase</code>.
	 * @return
	 *  The <code>Phrase</code> that is created.
	 */
	public static Phrase buildPhraseFromString(String s) {
		StringBuilder str =
		  new StringBuilder(s.replaceAll("\\s", "").toUpperCase().replaceAll("J", "I"));
		//Go through the phrase and remove all the non letter characters.
		for(int x = 0; x < str.length(); x++) {
			//If the character is not a letter, remove it
			if(!Character.isLetter(str.charAt(x))) {
				str.deleteCharAt(x);
			}
		}
		Phrase builtPhrase = new Phrase();
		while(!str.isEmpty()) {
			//If length is at 1 then add an X to the end
			if(str.length() == 1) {
				builtPhrase.enqueue(new Bigram(str.charAt(0), 'X'));
				break;
			}
			char first = str.charAt(0);
			char second = str.charAt(1);
			if(first == second) {
				second = 'X';
				builtPhrase.enqueue(new Bigram(first, second));
				str.deleteCharAt(0);
				continue;
			}
			//add a bigram to queue
			builtPhrase.enqueue(new Bigram(first, second));
			str.delete(0, 2);
		}
		return builtPhrase;
	}

	/**
	 * Encrypt the <code>Phrase</code> by using the <code>KeyTable</code> to
	 *   encrypt each <code>Bigram</code>.
	 * @param key
	 *    The <code>KeyTable</code> that is used to encrypt the <code>Bigram</code>.
	 * @return
	 *    The <code>Phrase</code> that is decrypted.
	 * <dt> Precondition:
	 *    <dd> The <code>KeyTable</code> is not null.
	 * @throws IllegalArgumentException
	 *    Indicates the <code>KeyTable</code> is null.
	 */
	public Phrase encrypt(KeyTable key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("KeyTable is null.");
		}
		Phrase encrypted = new Phrase();
		//Repeats until the queue is empty.
		while(!this.isEmpty()) {
			Bigram b = this.dequeue();
			int c1Row = key.findRow(b.getFirst());
			int c2Row = key.findRow(b.getSecond());
			int c1Col = key.findCol(b.getFirst());
			int c2Col = key.findCol(b.getSecond());
			if(c1Row == c2Row || c1Col == c2Col) {
				encrypted.enqueue(sameRowColEnc(key, b, c1Row, c2Row, c1Col, c2Col, true));
			} else {
				encrypted.enqueue(cornerEnc(key, b, c1Row, c2Row, c1Col, c2Col));
			}
		}
		return encrypted;
	}

	/**
	 * Encrypts the <code>Bigram</code>, if they are in the same column by
	 *   shifting the character in the key down a row or wraps to top if it
	 *   is in the last row. The opposite is done for decryption.
	 *   If they are in the same row, Encrypts the <code>Bigram</code> by shifting the column
	 *   character in the key right or wraps to the left if it is in the last column.
	 *   The opposite is done for decryption.
	 * @param key
	 *    The <code>KeyTable</code> that is used to encrypt the <code>Bigram</code>.
	 * @param b
	 *    The <code>Bigram</code> that will be encrypted.
	 * @param c1Row
	 *    The row of the first character in the <code>Bigram</code>.
	 * @param c2Row
	 *    The row of the second character in the <code>Bigram</code>.
	 * @param c1Col
	 *    The column of the first character in the <code>Bigram</code>.
	 * @param c2Col
	 *    The column of the second character in the <code>Bigram</code>.
	 * @param enc
	 *    Boolaen that tell if it is encrypting, otherwise decrypt.
	 * @return
	 *   The <code>Bigram</code> that is encrypted.
	 */
	private static Bigram sameRowColEnc(KeyTable key, Bigram b,
	  int c1Row, int c2Row, int c1Col, int c2Col, boolean enc) {
		char[][] table = key.getKeyTable();
		if(c1Col == c2Col) {
			//Same logic as a circular array, it will wrap to the top if the index is at the last row.
			if (enc) {
				b.setFirst(table[(c1Row + 1) % KeyTable.MATRIX_SIZE][c1Col]);
				b.setSecond(table[(c2Row + 1) % KeyTable.MATRIX_SIZE][c2Col]);
			} else {
				//The + KeyTable.MATRIX_SIZE will avoid negative index if it is at the first row.
				b.setFirst(table[(c1Row - 1 + KeyTable.MATRIX_SIZE) % KeyTable.MATRIX_SIZE][c1Col]);
				b.setSecond(table[(c2Row - 1 + KeyTable.MATRIX_SIZE) % KeyTable.MATRIX_SIZE][c2Col]);
			}
		}
		if(c1Row == c2Row) {
			//Same logic as a circular array, it will wrap to the left if the index is at the end of the row.
			if(enc) {
				b.setFirst(table[c1Row][(c1Col + 1) % KeyTable.MATRIX_SIZE]);
				b.setSecond(table[c2Row][(c2Col + 1) % KeyTable.MATRIX_SIZE]);
			} else {
				//The + KeyTable.MATRIX_SIZE will avoid negative index if it is at the first column.
				b.setFirst(table[c1Row][(c1Col - 1 + KeyTable.MATRIX_SIZE) % KeyTable.MATRIX_SIZE]);
				b.setSecond(table[c2Row][(c2Col - 1 + KeyTable.MATRIX_SIZE) % KeyTable.MATRIX_SIZE]);
			}
		}
		return b;
	}

	/**
	 * Encrypts the <code>Bigram</code> by switching the column of the two characters
	 * @param key
	 *    The <code>KeyTable</code> that is used to encrypt the <code>Bigram</code>.
	 * @param b
	 *    The <code>Bigram</code> that will be encrypted.
	 * @param c1Row
	 *    The row of the first character in the <code>Bigram</code>.
	 * @param c2Row
	 *    The row of the second character in the <code>Bigram</code>.
	 * @param c1Col
	 *    The column of the first character in the <code>Bigram</code>.
	 * @param c2Col
	 *    The column of the second character in the <code>Bigram</code>.
	 * @return
	 *    The <code>Bigram</code> that is encrypted.
	 */
	private static Bigram cornerEnc(KeyTable key, Bigram b,
	  int c1Row, int c2Row, int c1Col, int c2Col) {
		char[][] table = key.getKeyTable();
		//The position of the character will be the column index
		//  of the other character and vice versa, but the row maintains the same.
		b.setFirst(table[c1Row][c2Col]);
		b.setSecond(table[c2Row][c1Col]);
		return b;
	}

	/**
	 * Decrypts the <code>Phrase</code> by using the <code>KeyTable</code> to
	 *   decrypt each <code>Bigram</code>.
	 * @param key
	 *    The <code>KeyTable</code> that is used to decrypt the <code>Bigram</code>.
	 * @return
	 *    The <code>Phrase</code> that is decrypted.
	 * <dt> Precondition:
	 *    <dd> The <code>KeyTable</code> is not null.
	 * @throws IllegalArgumentException
	 *    Indicates the <code>KeyTable</code> is null.
	 */
	public Phrase decrypt(KeyTable key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("KeyTable is null.");
		}
		Phrase decrypted = new Phrase();
		//Repeats until the queue is empty.
		//The true false is whether it is encrypting or not.
		//  If it is false, then it is decrypting so it does opposite of encrypting.
		while(!this.isEmpty()) {
			Bigram b = this.dequeue();
			int c1Row = key.findRow(b.getFirst());
			int c2Row = key.findRow(b.getSecond());
			int c1Col = key.findCol(b.getFirst());
			int c2Col = key.findCol(b.getSecond());
			if(c1Row == c2Row || c1Col == c2Col) {
				decrypted.enqueue(sameRowColEnc(key, b, c1Row, c2Row, c1Col, c2Col, false));
			} else {
				decrypted.enqueue(cornerEnc(key, b, c1Row, c2Row, c1Col, c2Col));
			}
		}
		return decrypted;
	}

	/**
	 * Returns the <code>Phrase</code> as a string.
	 * @return
	 *   The <code>Phrase</code> as a string.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		Phrase temp = new Phrase();
		while(!this.isEmpty()) {
			Bigram tempBigram = this.dequeue();
			str.append(tempBigram.toString());
			temp.enqueue(tempBigram);
		}
		while(!temp.isEmpty()) {
			this.enqueue(temp.dequeue());
		}
		return str.toString();
	}

	/**
	 * Adds a <code>Bigram</code> into the <code>phrase</code>.
	 * @param b
	 *    The <code>Bigram</code> that will be added.
	 */
	public void enqueue(Bigram b) {
		this.add(b);
	}

	/**
	 * Removes the <code>Bigram</code> at the front of the <code>Phrase</code>
	 *   then return the value too.
	 * @return
	 *   The <code>Bigram</code> at the front of the <code>Phrase</code>.
	 */
	public Bigram dequeue() {
		return this.poll();
	}

}