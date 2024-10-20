/**
 * The <code>KeyTable</code> class will hold and create the key
 *   from the String passed to it. It interacts with the
 *   <code>Phrase</code> class to assist in encryption and decryption the
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
import java.lang.StringBuilder;

public class KeyTable {
	private char[][] key;
	private static final int MATRIX_SIZE = 5;

	/**
	 * Returns an instance of <code>KeyTable</code>.
	 */
	public KeyTable() {
		key = new char[MATRIX_SIZE][MATRIX_SIZE];
	}

	/**
	 * Returns an instance of <code>KeyTable</code>.
	 * @param phrase
	 *    The phrase that will be used to create the key.
	 * <dt> Preconditions:
	 *    <dd> Key phrase is not null.
	 * @throws IllegalArgumentException
	 *    Indicates the key phrase is null.
	 * @return
	 *    The <code>KeyTable</code> that is created.
	 */
	public static KeyTable buildFromString(String phrase) throws IllegalArgumentException {
		if (phrase == null) {
			throw new IllegalArgumentException("Key phrase is null.");
		}
		KeyTable fullTable = new KeyTable();
		StringBuilder str = new StringBuilder();
		StringBuilder abc = new StringBuilder("ABCDEFGHIKLMNOPQRSTUVWXYZ");
		if (!phrase.isEmpty()) {
			phrase = phrase.toUpperCase();
			//Replaces all the J with I
			while(str.indexOf("J") != -1) {
				str.setCharAt(phrase.indexOf("J"), 'I');
			}
			//Go through the phrase and add the character to the string while
			//removing the duplicates and the character from the abc string.
			for (int i = 0; i < phrase.length(); i++) {
				String current = phrase.substring(i, i + 1);
				if (str.indexOf(current) == -1) {
					str.append(current);
					abc.deleteCharAt(abc.indexOf(current));
				}
			}
		}
		//combine checked abc string and non-duplicate phrase string.
		str.append(abc);
		int index = 0;
		char[][] keyFull = fullTable.getKeyTable();
		//Go through each char and the char at index to fill the key.
		for(int row = 0; row < keyFull.length; row++) {
			for(int col = 0; col < keyFull[row].length; col++) {
				keyFull[row][col] = str.charAt(index);
				index++;
			}
		}
		return fullTable;
	}

	/**
	 * Returns the key.
	 * @return
	 *    The <code>key</code> from <code>KeyTable</code>.
	 */
	public char[][] getKeyTable() {
		return key;
	}

	/**
	 * Returns the Row of the character.
	 * @param c
	 *   The character that is looked for.
	 * @return
	 *   The row that the character is in.
	 */
	public int findRow(char c) {
		if(isValidChar(c)) {
			throw new IllegalArgumentException("Character c is not valid.");
		}
		for(int row = 0; row < key.length; row++) {
			for(int col = 0; col < key[0].length; col++) {
				if(key[row][col] == c) {
					return row;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the Column of the character.
	 * @param c
	 *   The character that is looked for.
	 * <dt> Preconditions:
	 *    <dd> The character is a valid letter.
	 * @throws IllegalArgumentException
	 *    Indicates the character is not valid.
	 * @return
	 *   The column that the character is in.
	 */
	public int findCol(char c) {
		if(isValidChar(c)) {
			throw new IllegalArgumentException("Character c is not valid.");
		}
		for(int row = 0; row < key.length; row++) {
			for(int col = 0; col < key[0].length; col++) {
				if(key[row][col] == c) {
					return col;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the String representation of <code>key</code>.
	 * @return
	 *    The String that is in a matrix format.
	 */
	public String toString() {
		StringBuilder concat = new StringBuilder();
		for(int row = 0; row < key.length; row++) {
			for(int col = 0; col < key[0].length; col++) {
				concat.append(key[row][col]).append(" ");
			}
			concat.append("\n");
		}
		return concat.toString();
	}

	/**
	 * Helper method that returns if a character is valid or an ABC value.
	 * @param c
	 *   The character that is being checked.
	 * <dt> Preconditions:
	 *    <dd> The character is a valid letter.
	 * @throws IllegalArgumentException
	 *    Indicates the character is not valid.
	 * @return
	 *   If the character is an ABC char.
	 */
	private static boolean isValidChar(char c) {
		return "ABCDEFGHIKLMNOPQRSTUVWXYZ".indexOf(Character.toUpperCase(c)) != -1;
	}
}