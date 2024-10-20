package src;
import java.util.Scanner;

/**
 * The <code>Player</code> class will create the <code>SongLinkedList</code>
 *   and run the operations to make and use the <code>Song</code>
 *   in <code>SongNode</code> stored in <code>SongLinkedList</code>.
 *   Mainly implements the menu-driven applications.
 *
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw2
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */
public class Player {
	public static Scanner input = new Scanner(System.in);
	public static boolean quitNotCalled = true;

	/**
	 * The main method will run menu-driven application for the Player.
	 */
	public static void main(String[] args) {
		SongLinkedList playlist =  new SongLinkedList();
		printOperations();
		while(quitNotCalled) {
			operationHub(playlist);
		}
	}

	/**
	 * This will print the operations for the user to input with.
	 */
	public static void printOperations() {
		System.out.println("(A) Add Song to Playlist\n" +
				"(F) Go to Next Song\n" +
				"(B) Go to Previous Song\n" +
				"(R) Remove Song from Playlist\n" +
				"(L) Play a Song\n" +
				"(C) Clear the Playlist\n" +
				"(S) Shuffle Playlist\n" +
				"(Z) Random Song\n" +
				"(P) Print Playlist\n" +
				"(T) Get the total amount of songs in the playlist\n" +
				"(Q) Exit the playlist\n");
	}

	/**
	 * This is where the user input is process and the method is called.
	 *
	 * @param playlist
	 *    This is the <code>SongLinkedList</code> that is currently being worked with.
	 */
	public static void operationHub(SongLinkedList playlist) {
		System.out.print("Enter an option: ");
		String operationCalled = input.nextLine();
		switch (operationCalled) {
			case "A", "a":
				addSong(playlist);
				break;
			case "F", "f":
				nextSong(playlist);
				break;
			case "B", "b":
				prevSong(playlist);
				break;
			case "R", "r":
				removeSong(playlist);
				break;
			case "L", "l":
				playSong(playlist);
				break;
			case "C", "c":
				clearList(playlist);
				break;
			case "S", "s":
				shuffleList(playlist);
				break;
			case "Z", "z":
				playRandom(playlist);
				break;
			case "P", "p":
				printTable(playlist);
				break;
			case "T", "t":
				getSize(playlist);
				break;
			case "Q", "q":
				quitProgram();
				break;
		}
	}


	/**
	 * Prints the size of the playlist.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void getSize(SongLinkedList playlist) {
		int sizeOfList = playlist.getSize();
		if(sizeOfList == 0) {
			System.out.println("\naYour playlist is empty.");
		} else {
			System.out.println("\nYour playlist contains " + sizeOfList + " songs.");
		}
	}

	/**
	 * Shuffle the <code>SongLinkedList</code>.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void shuffleList(SongLinkedList playlist) {
		playlist.shuffle();
		System.out.println("Playlist shuffled.");
	}

	/**
	 * Delete all song from the playlist.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void clearList(SongLinkedList playlist) {
		playlist.deleteAll();
		System.out.println("\nPlaylist cleared.");
	}

	/**
	 * Play the song indicated by user input.
	 *
	 * @param playlist
	 *   The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void playSong(SongLinkedList playlist) {
		System.out.print("Enter name of song to play: ");
		String name = input.nextLine();
		SongNode foundSong = playlist.findSong(name);
		if(foundSong != null) {
			playlist.play(name);
			System.out.println("'" + name + "'" + " by " +
			  foundSong.getData().getArtist() + "is now playing.");
		} else {
			System.out.println("'" + name + "'" + " not found.\n");
		}
	}


	/**
	 * Adds a <code>Song</code> to the <code>playlist</code>.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being added to.
	 */
	public static void addSong(SongLinkedList playlist) {
		try {
			System.out.print("Enter song title: ");
			String name = input.nextLine();
			System.out.print("Enter artist(s) of the song: ");
			String artist = input.nextLine();
			System.out.print("Enter album: ");
			String album = input.nextLine();
			System.out.print("Enter length (in second): ");
			int length = input.nextInt();
			if(input.hasNextLine()) {
				//Clears out the space entry after a nextInt
				input.nextLine();
			}
			playlist.insertAfterCursor(new Song(name, artist, album, length));
			System.out.println("\n" + "'" + name + "'" + " by " +
					artist + " is added to your playlist\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints out the playlist in a table.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void printTable(SongLinkedList playlist) {
		playlist.printPlaylist();
	}

	/**
	 * Move the cursor to the next song, unless at <code>tail</code> already.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void nextSong(SongLinkedList playlist) {
		try{
			playlist.cursorForward();
			System.out.println("Cursor moved to the next song.");
		} catch(NullPointerException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Move the cursor to the previous song, unless at <code>head</code> already.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void prevSong(SongLinkedList playlist) {
		try {
			playlist.cursorBackward();
			System.out.println("Cursor moved to the previous song.");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This will play a random song and announce it.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is currently being worked with.
	 */
	public static void playRandom(SongLinkedList playlist) {
		System.out.println("Playing a random song...");
		Song playedSong = playlist.random();
		//Will print twice due to the exception of test case in play being catched.
		System.out.println("'" + playedSong.getName() + "'" +
		  " by " + playedSong.getArtist() + " is now playing.\n");
	}

	/**
	 * Remove the <code>SongNode</code> at the <code>cursor</code>.
	 *
	 * @param playlist
	 *    The <code>SongLinkedList</code> that is being worked with.
	 */
	public static void removeSong(SongLinkedList playlist) {
		try {
			Song removedSong = playlist.removeCursor();
			System.out.println("'" + removedSong.getName() + "'" +
					" by " + removedSong.getArtist() + " was removed from the playlist.\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Quit the playlist menu driven application.
	 */
	public static void quitProgram() {
		quitNotCalled = false;
		System.out.println("Program terminated.");
	}

}