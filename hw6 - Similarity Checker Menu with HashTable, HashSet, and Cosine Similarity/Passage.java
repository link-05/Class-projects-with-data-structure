import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.lang.StringBuilder;
import java.util.Hashtable;
import java.util.Set;
//ArrayList is for the combination of keyset to use FrequencyTable and search for.
import java.util.HashSet;
/**
 * The <code>Passage</code> class will map
 *   String to Integer value, and it represents
 *   one text file and all of its word occurrence
 *   and its similarity to other <code>Passage</code>.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw6
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class Passage {
	//Title of the Passage
	private String title;
	//Total word count, excluding stop words
	private int wordCount;
	//Collection of other Passages and the similarity of this and each Passage
	private Hashtable<String, Double> similarTitles;
	//A Hashtable that counts the occurrence of every non-stop word in the passage.
	private Hashtable<String, Integer> occurrences;
	//A hash table to speed up check for stop word
	private static Hashtable<String, Boolean> stopWord;
	private static String stopWordPath;

	/**
	 * Returns an instance of <code>Passage</code>
	 * @param title
	 *    The title of the passage.
	 * @param file
	 *    The <code>File</code> that will be parsed.
	 */
	public Passage(String title, File file) throws IOException{
		this.title = title;
		similarTitles = new Hashtable<String, Double>();
		occurrences = new Hashtable<String, Integer>();
		wordCount = 0;
		if(stopWord == null) {
			setStopWord(new Hashtable<String, Boolean>());
			BufferedReader stop = new BufferedReader(new FileReader(stopWordPath));
			String line = "";
			while ((line = stop.readLine()) != null) {
				stopWord.put(line, true);
			}
		}
		parseFile(file);
	}

	/**
	 * Reads the <code>file</code> and count all non
	 *   -stopping words to insert into the table.
	 * @param file
	 *    The <code>File</code> that is parsed into the table.
	 * @Exception exception
	 *    Indicates that there was an error with reading the file.
	 */
	public void parseFile(File file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = "";
		StringBuilder sb = new StringBuilder();
		//Read line in text file
		while((line = in.readLine()) != null) {
			int index = 0;
			int prevIndex = 0;
			int len = line.length();
			String word = "";
			while(prevIndex < len) {
				//Separate each word by space
				index = line.indexOf(" ", index + 1);
				//if reach end of line then use max length;
				if(index == -1) {
					index = len;
				}
				word = (fixWord(line.substring(prevIndex, index))).toLowerCase();
				prevIndex = index + 1;
				//Enter loop if it is not a stop word and if its not an empty string.
				if(!containStopWord(word) && !word.isEmpty()) {
					wordCount++;
					//if exist then frequency up one else, create it.
					if(occurrences.containsKey(word)) {
						occurrences.put(word, occurrences.get(word) + 1);
					} else {
						occurrences.put(word, 1);
					}
				}
			}
		}
	}

	/**
	 * Returns if the <code>word</code> is a stop word.
	 * @param word
	 *    A String that is the word being checked.
	 * @return
	 *    Returns true, if word is stop word, else false.
	 */
	private boolean containStopWord(String word) {
		return stopWord.containsKey(word);
	}

	/**
	 * Helper method to get the word that is without any special character.
	 * @param word
	 *    The String that is being checked if there is any non-letter character.
	 * @return
	 *    Returns a String of the word that contain no special character.
	 */
	private String fixWord(String word) {
		StringBuilder sb = new StringBuilder(word);
		for(int i = sb.length() - 1; i >= 0; i--) {
			if(!Character.isLetter(sb.charAt(i))) {
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}

	/**
	 * Calculate the similiarity between the two <code>Passage</code>
	 *   object and modify <code>similarTitles</code>.
	 * @return
	 *    Returns the double that represent the calculated similarity.
	 */
	public static double cosineSimilarity(Passage passage1, Passage passage2, FrequencyTable table) {
		Set<String> p1KeySet = passage1.getWords();
		Set<String> p2KeySet = passage2.getWords();
		HashSet<String> keySetCombo = new HashSet<String>();
		//Adds both keys to the key set, only add if it does not exist already.
		for(String word: p1KeySet) {
			keySetCombo.add(word);
		}
		for(String word: p2KeySet) {
			if(!keySetCombo.contains(word)) {
				keySetCombo.add(word);
			}
		}
		double sumOfUTimesV = 0;
		double uSquareTotal = 0;
		double vSquareTotal = 0;
		//Iterate through the keySetCombo, it will use FrequencyTable to find the frequency of word for both passages
		//u will be passage1, v will be passage2.
		for(String word: keySetCombo) {
			//The frequency are frequency/totalWordCount of the passage so < 1
			double u = table.getFrequency(word, passage1);
			double v = table.getFrequency(word, passage2);
			sumOfUTimesV += (u * v);
			uSquareTotal += (u * u);
			vSquareTotal += (v * v);
		}
		//Calculates the difference then add the similarTitles which will be used later for printing out the menu.
		double similarity = sumOfUTimesV / ((Math.sqrt(uSquareTotal)) * (Math.sqrt(vSquareTotal)));
		passage1.addSimilarTitles(passage2.getTitle(), similarity);
		passage2.addSimilarTitles(passage1.getTitle(), similarity);
		return similarity;
	}

	/**
	 * Returns the relative frequency of a given word in <code>Passage</code>.
	 * @param word
	 *    The word that is being searched for.
	 * @return
	 *    Return the double that represents the frequency of occurrence.
	 */
	public double getWordFrequency(String word) {
		if(occurrences.containsKey(word)) {
			return (occurrences.get(word) * 1.0) / wordCount;
		} else {
			return 0.0;
		}
	}

	/**
	 * Return a set representing all the words in this <code>Passage</code>.
	 * @return
	 *    returns a <code>HashSet</code> with all the words.
	 */
	public Set<String> getWords() {
		return occurrences.keySet();
	}

	/**
	 * Returns all the <code>similarTitles</code>
	 *   and their percentage similarity.
	 * @return
	 *    Returns a string representation of the titles and their percentage.
	 */
	public String toString() {
		int numPrint = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-25s", title));
		String[] sortedSimilarTitlesKey = new String[similarTitles.size()];
		int i = 0;
		for(String key: similarTitles.keySet()) {
			sortedSimilarTitlesKey[i] = key;
			i++;
		}
		int sLen = sortedSimilarTitlesKey.length;
		//Sort the similarTitles in an array due to keySet being random order.
		for(i = 0; i < sLen; i++) {
			for(int j = i + 1; j < sLen; j++) {
				if(sortedSimilarTitlesKey[i].compareToIgnoreCase(sortedSimilarTitlesKey[j]) > 0) {
					String tempKey = sortedSimilarTitlesKey[i];
					sortedSimilarTitlesKey[i] = sortedSimilarTitlesKey[j];
					sortedSimilarTitlesKey[j] = tempKey;
				}
			}
		}
		for(String key: sortedSimilarTitlesKey) {
			if(numPrint == 2) {
				sb.append("\n");
				sb.append(String.format("%-25s", " "));
			}
			if(numPrint % 2 == 0) {
				sb.append("| ");
			}
			sb.append(key).append("(").append((int)((similarTitles.get(key) + 0.005) * 100)).append("%)");
			if(numPrint < 3) {
				sb.append(", ");
			}
			numPrint++;
		}
		return sb.toString();
	}

	/**
	 * Returns the <code>wordCount</code>.
	 * @return
	 *    Returns the int representing the <code>wordCount</code>.
	 *
	 */
	public int getWordCount() {
		return wordCount;
	}

	/**
	 * Set the <code>wordCount</code>.
	 * @param wordCount
	 *    The int representing the new <code>wordCount</code>.
	 *
	 */
	public void setWordCount(int wordCount) {
		 this.wordCount = wordCount;
	}

	/**
	 * Returns the <code>HashTable</code> with the passage and similarity.
	 * @return
	 *    Returns the collection of the <code>Passage</code> and their similarity.
	 */
	public Hashtable<String, Double> getSimilarTitles() {
		return similarTitles;
	}

	/**
	 * Sets the <code>similarTitle</code> <code>HashTable</code> to the new one.
	 * @param similarTitles
	 *    The <code>HashTable</code> that will be the new <code>similarTitles</code>.
	 */
	public void setSimilarTitles(Hashtable<String, Double> similarTitles) {
		this.similarTitles = similarTitles;
	}

	/**
	 * Add the <code>Passage</code> title and the percentage of cosine similarity.
	 * @param title
	 *    The title of the passage being added.
	 * @param percent
	 *    The percent of the similarity.
	 */
	public void addSimilarTitles(String title, double percent) {
		similarTitles.put(title, percent);
	}

	/**
	 * Returns the <code>HashTable</code> with the word and its frequency.
	 * @return
	 *    Returns the word and frequency.
	 */
	public Hashtable<String, Integer> getOccurrences() {
		return occurrences;
	}

	/**
	 * Sets the <code>occurrences</code> <code>HashTable</code> to the new one.
	 * @param occurrences
	 *    The <code>HashTable</code> that will be the new <code>occurrences</code>.
	 */
	public void setOccurrences(Hashtable<String, Integer> occurrences) {
		this.occurrences = occurrences;
	}

	/**
	 * Returns the <code>title</code> of the <code>Passage</code>.
	 * @return
	 *    Returns the String representing the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the <code>Passage</code> to a new one.
	 * @param title
	 *    The String that is the new <code>title</code>.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set the Hashtable for the stopWord.
	 * @param table
	 *    The Hashtable that is the new stopWord Hashtable.
	 */
	public void setStopWord(Hashtable<String, Boolean> table) {
		stopWord = table;
	}

	/**
	 * Sets the path of the stop word from the codegrade's path.
	 * @param path
	 *    The string of the path to the StopWord.txt.
	 */
	public static void setStopWordPath(String path) {
		stopWordPath = path;
	}
}
