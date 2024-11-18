import java.util.Hashtable;
import java.util.ArrayList;
/**
 * The <code>FrequencyList</code> class will create
 *   an arraylist and map that will count and point to
 *   the frequency of a word in any passage
 *   from all added <code>Passage</code>.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw6
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FrequencyList {
	private String word;
	private ArrayList<Double> frequencies;
	private Hashtable<String, Integer> passageIndices;

	/**
	 * Return an instance of <code>FrequencyList</code> that represents
	 *    a word and the amount of time it occurs.
	 * @param word
	 *    The word that the frequency list will represent.
	 */
	public FrequencyList(String word) {
		this.word = word;
		frequencies = new ArrayList<Double>();
		passageIndices = new Hashtable<String, Integer>();
	}

	/**
	 * Add the frequency of the <code>word</code> from the <code>Passage</code>.
	 * <dt>Postconditions:
	 *   <dd> <code>passageIndices</code> now contains p's title that maps
	 *     to the next available index in the ArrayList.
	 *   <dd> The ArrayList now contains an additional index containing the
	 *     frequency of the <code>word</code> in <code>Passage</code>.
	 * @param p
	 *    The <code>Passage</code> that is being added to the list.
	 */
	public void addPassage(Passage p) {
		int count = p.getWordCount();
		double frequency = p.getWordFrequency(word);
		if(frequency > 0) {
			frequencies.add(frequency);
			passageIndices.put(p.getTitle(), frequencies.size() - 1);
		}
	}

	/**
	 * Return the occurrence of a word divided by the passages word count.
	 * @param p
	 *    The <code>Passage</code> that is being looked at.
	 * @return
	 *    Returns the frequency of the word that occurred.
	 */
	public double getFrequency(Passage p) {
		if(passageIndices.containsKey(p.getTitle())) {
			return frequencies.get(passageIndices.get(p.getTitle()));
		}
		return 0;
	}

	/**
	 * Returns the word that this <code>FrequencyList</code> represents.
	 * @return
	 *    Returns the String of the <code>word</code>.
	 */
	public String getWord() {
		return word;
	}
}