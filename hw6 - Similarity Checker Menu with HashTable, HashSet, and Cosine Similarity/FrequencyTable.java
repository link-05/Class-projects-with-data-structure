import java.util.ArrayList;
import java.util.Set;
/**
 * The <code>FrequencyTable</code> class will act as
 *   an arraylist of FrequencyList that will allow access
 *   to all added passage's word frequency.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw6
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FrequencyTable extends ArrayList {
	/**
	 * Static method to implement the frequency table and add all the passages to it.
	 * <dt> Postconditions:
	 *   <dd> A new <code>FrequencyTable</code> object is constructed, contains a collection
	 *          of <code>FrequencyList</code> with words from all the <code>Passage</code>
	 *          in passages.
	 * @param passages
	 *    The ArrayList with all the <code>Passage</code>.
	 * @return
	 *    Returns a <code>FrequencyTable</code> that is filled with frequencyList
	 *      created from all the passages.
	 */
	public static FrequencyTable buildTable(ArrayList<Passage> passages) {
		FrequencyTable table = new FrequencyTable();
		for(Passage p: passages) {
			table.addPassage(p);
		}
		return table;
	}

	/**
	 * Returns an index if a word is part of an existing <code>FrequencyList</code>, a valid index
	 *   is one that exist in the <code>FrequencyTable</code>, if not found returns -1.
	 * @param word
	 *    The word that is being searched for.
	 * @param lists
	 *    The <code>FrequencyTable</code> that is being searched through.
	 * @return
	 *    Returns the index of the <code>FrequencyList</code>, if not found returns -1.
	 */
	private static int isValid(String word, FrequencyTable lists) {
		int size = lists.size();
		for(int i = 0; i < size; i++) {
			if(((FrequencyList)lists.get(i)).getWord().equals(word)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adds all the word of the passage into the <code>FrequencyTable</code> based off the <code>FrequencyList</code>.
	 * @param p
	 *    The <code>Passage</code> that is being examined.
	 * @throws IllegalArgumentException
	 *    Indicate that the given Passage is null or empty.
	 */
	public void addPassage(Passage p) throws IllegalArgumentException{
		if(p == null || p.getWordCount() == 0) throw new IllegalArgumentException();
		Set<String> keys = p.getWords();
		for(String key: keys) {
			int val = isValid(key, this);
			if(val > -1) {
				((FrequencyList)this.get(val)).addPassage(p);
			} else {
				FrequencyList word = new FrequencyList(key);
				word.addPassage(p);
				this.add(word);
			}
		}
	}

	/**
	 * Returns the frequency of the word that is looked at based off the passage.
	 * @param word
	 *    The word that is being search for in the <code>FrequencyList</code>.
	 * @param p
	 *    The <code>Passage</code> that is being looked at.
	 * @return
	 *    Returns an Integer that represent the frequency of the word in the passage.
	 */
	public double getFrequency(String word, Passage p) {
		//I would think throwing in an entire array or arraylist of passage would be the most efficient way to deal
		//this situation because that would allow quicker comparison compared to one at a time in case many are needed.
		for(Object list: this) {
			FrequencyList curr = (FrequencyList)list;
			if(curr.getWord().equals(word)) {
				return curr.getFrequency(p);
			}
		}
		return 0;
	}
}
