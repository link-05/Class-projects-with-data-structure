package src;
/**
 * The <code>Song</code> class implements an object
 * with all the information regarding a Song.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw2
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

public class Song {
	private String name;
	private String artist;
	private String album;
	private int length;

	/**
	 * Returns an instance of <code>Song</code> object.
	 */
	public Song() {
		name = null;
		artist = null;
		album = null;
		length = 0;
	}

	/**
	 * Returns an instance of <code>Song</code> object
	 *
	 * @param name
	 *    The name of the song stored as a String
	 *
	 * @param artist
	 *    The name of the artist stored as a String.
	 *
	 * @param album
	 *    The name of the song album stored as a String.
	 *
	 * @param length
	 *    The length of the song clip expressed as an int
	 */
	public Song(String name, String artist, String album, int length) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.length = length;
	}

	//Getter methods

	/**
	 * Returns the name of the song.
	 *
	 * @return
	 *    returns the <code>name</code> String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the name of the artist.
	 *
	 * @return
	 *    returns the <code>artist</code> String.
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Returns the name of the album.
	 *
	 * @return
	 *    returns the <code>album</code> String.
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Returns the length of the song clip.
	 *
	 * @return
	 *    returns the <code>length</code> integer.
	 */
	public int getLength() {
		return length;
	}

	//Setter methods

	/**
	 * Set a new <code>name</code> for the <code>Song</code> object.
	 *
	 * @param name
	 *    The String representing the new <code>name</code> of the Song.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set a new <code>artist</code> for the <code>Song</code> object.
	 *
	 * @param artist
	 *    The String representing the new <code>artist</code> name.
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Set a new <code>album</code> for the <code>Song</code> object.
	 *
	 * @param album
	 *    The new String name of the <code>album</code>.
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Set the <code>length</code> of the <code>Song</code> object
	 *
	 * @param length
	 *    The int that represents the new <code>length</code> of the
	 *      <code>Song</code> object.
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
