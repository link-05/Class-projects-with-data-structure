/**
 * The <code>PlayFairEncryptionEngine</code> class will implement
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


public class PlayFairEncryptionEngine {
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
				encrypt();
				break;
			case "DE":
				decrypt();
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
	public static void changeKey(KeyTable currentKey) {
		try {
			System.out.print("Enter key phrase: ");
			currentKey = KeyTable.buildFromString(input.nextLine());
			System.out.println("Generation success!");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Helper method to print the key.
	 * @param currentKey
	 *    The current <code>KeyTable</code> that will be printed.
	 */
	public static void printKey(KeyTable currentKey) {
		System.out.println(currentKey.toString());
	}

}