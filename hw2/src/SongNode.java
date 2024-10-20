package src;
/**
 * The <code>SongNode</code> class implements an object
 *   representing the node in a Linked List which in
 *   this case is <code>SongNode</code> in
 *   <code>SongLinkedList</code>.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw2
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

public class SongNode {
	private SongNode next;
	private SongNode prev;
	private Song data;

	/**
	 * Returns an instance of <code>SongNode</code>.
	 */
	public SongNode() {
		next = null;
		prev = null;
		data = null;
	}

	/**
	 * Returns an instance of <code>SongNode</code>.
	 *
	 * @param data
	 *    the <code>Song</code> object that will be stored as data.
	 */
	public SongNode(Song data) {
		this.data = data;
	}

	//Getter methods

	/**
	 * Returns the <code>data</code> in this <code>SongNode</code>.
	 *
	 * @return
	 *    The <code>Song</code> that is stored in <code>data</code>.
	 */
	public Song getData() {
		return data;
	}

	/**
	 * Returns the connected <code>SongNode</code> at the next sequence.
	 *
	 * @return
	 *    The next <code>SongNode</code>.
	 */
	public SongNode nextLink() {
		return next;
	}

	/**
	 * Returns the connected <code>SongNode</code> at the previous sequence.
	 *
	 * @return
	 *    The <code>SongNode</code> at previous sequence position
	 */
	public SongNode prevLink() {
		return prev;
	}

	//Setter methods

	/**
	 * Set the link to next <code>SongNode</code> sequence.
	 *
	 * @param next
	 *    The <code>SongNode</code> that will be linked as the next node.
	 */
	public void setNext(SongNode next) {
		this.next = next;
	}

	/**
	 * Set the <code>data</code> to a new <code>Song</code> object.
	 *
	 * @param data
	 *    The <code>Song</code> object that will be set to <code>data</code>.
	 */
	public void setData(Song data) {
		this.data = data;
	}

	/**
	 * Set the link to previous <code>SongNode</code> sequence.
	 *
	 * @param prev
	 *    The <code>SongNode</code> that will be linked as the previous node.
	 */
	public void setPrev(SongNode prev) {
		this.prev = prev;
	}
}
