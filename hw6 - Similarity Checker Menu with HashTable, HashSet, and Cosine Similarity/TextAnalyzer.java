import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 * The <code>TextAnalyzer</code> class will allow user to input
 *   the desired percentage for viewing and the file path to
 *   create all the <code>Passage</code> and store them into a list
 *   to create the <code>FrequencyTable</code> to assist in outputting
 *   the similarity percentage between texts.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw6
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class TextAnalyzer {
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.print("Enter the similarity percentage: ");
			double similarPer = in.nextDouble();
			System.out.print("Enter the directory of a folder of text files: ");
			in.nextLine();
			String filepath = in.nextLine();
			System.out.println("Reading texts...");
			File[] directoryOfFiles = new File(filepath).listFiles();
			ArrayList<Passage> pList = new ArrayList<Passage>();
			//Sort the files.
			for(int i = 0; i < directoryOfFiles.length; i++) {
				for(int j = i + 1; j < directoryOfFiles.length; j++) {
					if(directoryOfFiles[i].getName().compareToIgnoreCase(directoryOfFiles[j].getName()) > 0) {
						File temp = directoryOfFiles[i];
						directoryOfFiles[i] = directoryOfFiles[j];
						directoryOfFiles[j] = temp;
					}
				}
			}
			for(File i : directoryOfFiles) {
				if(i.getName().equalsIgnoreCase("StopWords.txt")) Passage.setStopWordPath(i);
			}
			for(File i : directoryOfFiles) {
				if(i.getName().equalsIgnoreCase("StopWords.txt")) continue;
				String title = i.getName();
				Passage p = new Passage(title.substring(0, title.lastIndexOf(".")), i);
				pList.add(p);
			}
			FrequencyTable table = FrequencyTable.buildTable(pList);
			//Create the similarity table.
			int pLen = pList.size();
			for(int i = 0; i < pLen; i++) {
				for(int j = i+1; j < pLen; j++) {
					if(i != j) {
						Passage.cosineSimilarity(pList.get(i), pList.get(j), table);
					}
				}
			}
			printMenu(pList);
			suspectText(pList, similarPer);
			System.out.println("Program terminating...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void printPartition() {
		System.out.println("\n"
		  + "--------------------------------------------------------------------------------");
	}

	/**
	 * Helper method to print the menu with the text and similarities.
	 * @param pList
	 *    The ArrayList with all the passages.
	 */
	public static void printMenu(ArrayList<Passage> pList){
		int pLen = pList.size();
		//Print heading
		System.out.printf("%-25s%-55s", "Text (title)", "| Similarities (%)");
		//For every passage in arraylist.
		//Will make the entire table for text and similarity
		for(Passage p: pList) {
			printPartition();
			System.out.print(p);
		}
		System.out.println();
	}

	/**
	 * Helper method to print the suspected texts.
	 * @param pList
	 *    The ArrayList with all the passages.
	 * @param similarPer
	 *    The similarity percent that the user wants to look at.
	 */
	public static void suspectText(ArrayList<Passage> pList, double similarPer) {
		System.out.print("\nSuspected Texts With Same Authors");
		printPartition();
		int pLen = pList.size();
		for(int i = 0; i < pLen; i++) {
			Passage p = pList.get(i);
			Hashtable<String, Double> currentPassage = p.getSimilarTitles();
			for(int x = i + 1; x < pLen; x++) {
				double per = (currentPassage.get(pList.get(x).getTitle()) + 0.005);

				if(per >= similarPer) {
					System.out.println("'" + p.getTitle() + "'" + " and " + "'" +
					  pList.get(x).getTitle() + "'" + " may have the same author ("
					  + (int)(per * 100) + "% similar).");
				}
			}
		}
	}
}
