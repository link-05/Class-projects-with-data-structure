import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.lang.StringBuilder;

/**
 * The <code>SongLinkedList</code> class implements an object
 *   that connects and navigate stored <code>SongNode</code>
 *   as a Linked List.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw2
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */
public class SongLinkedList {
	//Created to allow stop to work on clip.
	private Clip c = null;
	//The first SongNode in the Sequence.
	private SongNode head;
	//The last SongNode in the Sequence.
	private SongNode tail;
	//The current SongNode that is selected.
	private SongNode cursor;
	//The size of the SongLinkedList.
	private int size;

	/**
	 * Returns an instance of <code>SongLinkedList</code> object.
	 */
	public SongLinkedList() {
		head = null;
		tail = null;
		cursor = null;
		size = 0;
	}

	/**
	 * Plays the song indicated by <code>name</code>.
	 * @param name
	 *    The name of the song looked for to play.
	 *
	 * <dt> Precondition:
	 *    <dd> Name must match to a song name in playlist,
	 *      and a file must be associated with it.
	 *
	 * <dt> Postcondition:
	 *    <dd> The song is now playing.
	 *
	 * @throws IllegalArgumentException
	 *    Indicates the song name is not found or wav file could not be found.
	 * @throws LineUnavailableException
	 *    Indicates the audio is in use.
	 * @throws IOException
	 *    Indicates an error in IO.
	 * @throws UnsupportedAudioFileException
	 *    Indicates that the file is unrecognized.
	 */
	public void play(String name)
	  throws IllegalArgumentException {
		SongNode temp = findSong(name);
		if(temp == null) {
			throw new IllegalArgumentException("Song does not exist");
		}
		try {
			if(c != null) {
				c.stop();
			}
			String songName = temp.getData().getName();
			AudioInputStream AIS = AudioSystem.getAudioInputStream(
					new File(/*"src/songs/" +*/ songName + ".wav"));
			//starting the clip
			c = AudioSystem.getClip();
			c.open(AIS);
			c.start();
		} catch (UnsupportedAudioFileException e) {
			throw new IllegalArgumentException("wav file could not be found");
		}
		catch (Exception ex) {
//			System.out.println("'" + temp.getData().getName() + "'" +
//			  " by " + temp.getData().getArtist() + " is now playing.\n");
		}

	}

	/**
	 * Helper method to return the <code>SongNode</code> with the <code>name</code>
	 *   in the <code>Song</code> associated with the node.
	 *
	 * @param name
	 *    The name of the <code>Song</code> that is looked for.
	 *
	 * @return
	 *    The <code>SongNode</code> with the <code>Song</code>, or
	 *      null if not found.
	 */
	public SongNode findSong(String name) {
		SongNode nodePtr = head;
		while(nodePtr != null) {
			if(nodePtr.getData().getName().equals(name)) {
				return nodePtr;
			}
			nodePtr = nodePtr.nextLink();
		}
		return null;
	}

	/**
	 * Move the cursor to the next <code>SongNode</code>.
	 *
	 * <dt> Precondition
	 *    <dd> The cursor cannot be null, the list is not empty.
	 *
	 * <dt> Post condition:
	 *    <dd> Cursor is moved to next <code>SongNode</code> or stays at tail.
	 *
	 * @throws NullPointerException
	 *    Indicates that the cursor is null.
	 */
	public void cursorForward() {
		if(cursor != null) {
			if(cursor.nextLink() != null) {
				cursor = cursor.nextLink();
			} else {
				throw new NullPointerException("Already at the end of the playlist");
			}
		} else {
			throw new NullPointerException("Cursor is null");
		}
	}

	/**
	 * Move the cursor to the previous <code>SongNode</code>.
	 *
	 * <dt> Precondition:
	 *    <dd> The cursor cannot be null, the list is not empty.
	 *
	 * <dt> Post condition:
	 *    <dd> Cursor is moved to previous <code>SongNode</code> or stays at head.
	 *
	 * @throws NullPointerException
	 *    Indicates that the cursor is null.
	 */
	public void cursorBackward() {
		if(cursor != null) {
			if(cursor.prevLink() != null) {
				cursor = cursor.prevLink();
			} else {
				throw new NullPointerException("Already at the beginning of the playlist");
			}
		} else {
			throw new NullPointerException("Cursor is null, list cannot be empty");
		}
	}

	/**
	 * Inserts a <code>SongNode</code> with <code>Song</code> after the cursor.
	 *
	 * @param newSong
	 *    The <code>data</code> that the <code>SongNode</code> object will have.
	 *
	 * <dt> Precondition:
	 *    <dd> The <code>newSong</code> object has to be instantiated.
	 *
	 * <dt> Postconditions:
	 *    <dd> newNode is inserted into position after cursor.
	 *      Order is preserved and all <code>Song</code> object is
	 *      still in list. Cursor is pointed at the inserted node.
	 *
	 * @throws IllegalArgumentException
	 *    Indicates that the newSong is null
	 *
	 * @throws NullPointerException
	 *    Indicates that the cursor is null when the list is instantiated.
	 */
	public void insertAfterCursor(Song newSong) {
		if(newSong == null) {
			throw new IllegalArgumentException("newSong is null");
		}
		SongNode newNode = new SongNode(newSong);
		if(tail == null && head == null) {
			head = newNode;
			tail = newNode;
		} else {
			//States that the list is instantiated with nodes at head and tail
			//  but the cursor is null
			if(cursor == null) {
				throw new NullPointerException("cursor is null");
			}
			newNode.setPrev(cursor);
			newNode.setNext(cursor.nextLink());
			if(newNode.nextLink() != null) {
				newNode.nextLink().setPrev(newNode);
			}
			cursor.setNext(newNode);
		}
		cursor = newNode;
		size++;
		if(cursor.nextLink() == null) {
			tail = cursor;
		}
	}

	/**
	 * Removes the <code>SongNode</code> at <code>cursor</code>, then
	 *   advance cursor if not at tail, go to previous <code>SongNode</code>
	 *   if at tail, returns <code>Song</code> of removed node.
	 *
	 * @return
	 *    Returns the <code>Song</code> object of removed <code>SongNode</code>
	 *      object.
	 *
	 * <dt> Precondition
	 *    <dd> The cursor is not null.
	 *
	 * <dt> Postconditions
	 *    <dd> The referenced <code>SongNode</code> at the <code>cursor</code>
	 *      has been removed from the playlist. The cursor is moved to the next
	 *      node or the previous node, if next does not exist.
	 */
	public Song removeCursor() {
		if(cursor == null) {
			throw new NullPointerException("Your playlist is empty");
		}
		Song backValue = cursor.getData();
		if(cursor.nextLink() == null && cursor.prevLink() == null) {
			cursor = null;
			head = null;
			tail = null;
		}
		else if(cursor.prevLink() == null) {
			head = cursor.nextLink();
			cursor = cursor.nextLink();
			cursor.setPrev(null);
		} else if(cursor.nextLink() == null) {
			cursor.prevLink().setNext(null);
			cursor = cursor.prevLink();
		} else {
			cursor.prevLink().setNext(cursor.nextLink());
			cursor.nextLink().setPrev(cursor.prevLink());
			cursor = cursor.nextLink();
		}
		if(cursor != null && cursor.nextLink() == null) {
			tail = cursor;
		}
		size--;
		return backValue;
	}

	/**
	 * Returns the number of <code>Song</code> in playlist.
	 * @return
	 *    Returns the number representing the amount of songs in list.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the size of the <code>SongLinkedList</code>.
	 * @param size
	 *    The int representing the new size value.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Plays a song at random and return it.
	 *
	 * @return
	 *    Returns the Song that is being played
	 */
	public Song random() throws IllegalArgumentException, NullPointerException {
		SongNode originalCursor = cursor;
		while(originalCursor == cursor) {
			int random = (int) (Math.random() * getSize());
			resetCursor();
			for (int i = 0; i < random; i++) {
				cursorForward();
			}
		}
		Song selectedSong = cursor.getData();
		cursor = originalCursor;
		play(selectedSong.getName());
		return selectedSong;
	}

	/**
	 * Shuffles the order of the songs contained in the playlist.
	 *
	 * <dt> Postcondition:
	 *    <dd> The playlist is randomly reordered. The <code>cursor</code> references
	 *      to the <code>SongNode</code> that the method originally entered.
	 */
	public void shuffle() {
		int listSize = getSize();
		if(listSize > 0) {
			SongLinkedList shuffledList = new SongLinkedList();
			Song tempAt = cursor.getData();
			for (int i = 0; i < listSize; i++) {
				int nodeAtIndex = (int) (Math.random() * (listSize - i));
				resetCursor();
				for(int j = 0; j < nodeAtIndex; j++) {
					cursorForward();
				}
				shuffledList.insertAtTail(removeCursor());
			}
			head = shuffledList.head;
			tail = shuffledList.tail;
			cursor = shuffledList.findSong(tempAt.getName());
			setSize(shuffledList.getSize());
		}
	}

	/**
	 * Prints the playlist in a formatted table.
	 */
	public void printPlaylist() {
		System.out.println(this.toString());
	}

	/**
	 * Returns a formatted String representation of the playlist.
	 * @return
	 *    Returns a tabular form of the playlist as a String
	 */
	public String toString() {
		Song selectedSong = null;
		SongNode tempCursor = cursor;
		if(cursor != null) {
			//To get the song that the cursor should be at.
			selectedSong = tempCursor.getData();
		}
		resetCursor();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-25s%-27s%-27s%-12s%n", "Song", "| Artist",
		  "| Album", "| Length (s)"));
		sb.append(("--------------------------------------------" +
		  "-----------------------------------------------\n"));
		StringBuilder lengthStr = new StringBuilder();
		while(cursor != null) {
			Song currentSong = cursor.getData();
			lengthStr.setLength(0);
			if(currentSong.getLength() < 10)
				lengthStr.append(" ");
			lengthStr.append(currentSong.getLength());
			//Checks if it is the song currently chosen by cursor.
			if(currentSong == selectedSong) {
				if(currentSong.getLength() > 10) {
					lengthStr.append("  <-");
				} else  {
					//to maintain format of right align for number
					lengthStr.append("   <-");
				}
			}
			sb.append(String.format("%-26s%-27s%-31s%-12s%n", currentSong.getName(),
					currentSong.getArtist(), currentSong.getAlbum(), lengthStr.toString()));
			cursor =  cursor.nextLink();
		}
		cursor = tempCursor;
		return sb.toString();
	}

	/**
	 * Delete all Songs from playlist
	 *
	 * <dt> Postcondition:
	 *    <dd> All songs have been removed.
	 */
	public void deleteAll() {
		cursor = null;
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Helper method to insert for shuffle.
	 *
	 * @param newSong
	 *    The <code>SongNode</code> that will be added to the shuffle.
	 */
	private void insertAtTail(Song newSong) {
		SongNode songToAdd = new SongNode(newSong);
		if(cursor == null) {
			head = songToAdd;
			tail = songToAdd;
		} else {
			tail.setNext(songToAdd);
			songToAdd.setPrev(tail);
			tail = songToAdd;
		}
		cursor = songToAdd;
		size++;
	}

	/**
	 * Helper method to move cursor to head for shuffle.
	 */
	private void resetCursor() {
		cursor = head;
	}
}
