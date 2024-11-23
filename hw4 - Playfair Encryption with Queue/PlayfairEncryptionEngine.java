/**
 * The <code>PlayfairEncryptionEngine</code> class will implement
 *   the menu-driven application for the encryption and decryption.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw4
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */
import java.util.Scanner;


public class PlayfairEncryptionEngine {
	private static Scanner input = new Scanner(System.in);
	private static boolean isRunning = true;

	/**
	 * The main method that will control the menu-driven application.
	 */
	public static void main(String[] args) {
		System.out.print("Enter key phrase: ");
		KeyTable currentKey = KeyTable.buildFromString(input.nextLine());
		while(isRunning) {
			printMenu();
			commandHub(currentKey);
		}
	}

	/**
	 * Helper method to print the menu for user to see.
	 */
	public static void printMenu() {
		System.out.print("Menu:\n" + "(CK) - Change key\n" +
		  "(PK) - Print key\n" + "(EN) - Encrypt\n" + "(DE) - Decrypt\n" +
		  "(Q) - Quit\n");
	}

	public static void commandHub(KeyTable currentKey) {
		System.out.print("\nPlease select an option: ");
		String str = input.nextLine().toUpperCase();
		switch (str) {
			case "CK":
				changeKey(currentKey);
				break;
			case "PK":
				printKey(currentKey);
				break;
			case "EN":
				encrypt(currentKey);
				break;
			case "DE":
				decrypt(currentKey);
				break;
			case "Q":
				System.out.println("Program terminating...");
				isRunning = false;
				break;
		}
	}

	/**
	 * Helper method to change the key.
	 * @param currentKey
	 *    The current <code>KeyTable</code> that will be changed.
	 */
	public static KeyTable changeKey(KeyTable currentKey) {
		try {
			System.out.print("Enter key phrase: ");
			currentKey.setKeyTable(KeyTable.buildFromString(input.nextLine()).getKeyTable());
			System.out.println("Generation success!");
			return currentKey;
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		return currentKey;
	}

	/**
	 * Helper method to print the key.
	 * @param currentKey
	 *    The current <code>KeyTable</code> that will be printed.
	 */
	public static void printKey(KeyTable currentKey) {
		System.out.println(currentKey.toString());
	}

	/**
	 * Helper method to encrypt the phrase.
	 * @param currentKey
	 *    The current <code>KeyTable</code> that will be used to encrypt.
	 */
	public static void encrypt(KeyTable currentKey) {
		System.out.print("Please enter a phrase to encrypt: ");
		Phrase encPhrase = Phrase.buildPhraseFromString(input.nextLine());
		try {
			Phrase encrypted = encPhrase.encrypt(currentKey);
			System.out.println("Encrypted text is: " + encrypted.toString());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Helper method to decrypt the phrase.
	 * @param currentKey
	 *    The current <code>KeyTable</code> that will be used to decrypt.
	 */
	public static void decrypt(KeyTable currentKey) {
		System.out.print("Please enter a phrase to decrypt: ");
		Phrase decPhrase = Phrase.buildPhraseFromString(input.nextLine());
		try {
			System.out.println("Decrypted text is: " + decPhrase.decrypt(currentKey).toString());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}