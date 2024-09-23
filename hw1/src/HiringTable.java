/**
 * The <code>HiringTable</code> class will hold the list
 * and implement all the functions for an
 * <code>Applicant</code> objects
 *
 * @author Kevin Lin
 *  Stony Brook ID: 116145453
 *  Email: kevin.lin.24@Stonybrook.edu
 *  Programming Assignment: hw1
 *  Course: CSE214.01
 *  Recitation: R04
 *  TA's: Veronica Oreshko, Anuj Sureshbhai
 */

public class HiringTable {
	//Static Constants
	public static final int MAX_SKILLS = 3; //Max number of skills a user can have
	public static final int MAX_COMPANIES = 3; //Max number
	public static final int MAX_APPLICANTS = 50; //Max count of Applicant
	//Member Variables
	private Applicant[] data;
	private int applicantCounter = 0;

	//Constructors
	/**
	 * Returns an instance of <code>HiringTable</code> object.
	 * <dt> Post condition:
	 *    <dd><code>data</code> will have an empty list of <code>Applicant</code>.
	 */
	public HiringTable() {
		data = new Applicant[MAX_APPLICANTS];
	}

	/**
	 * return an instance of <code>HiringTable</code> object
	 *   with a data set to another data.
	 * @param data
	 *    The <code>Applicant</code> array that data will be set to.
	 *      Used for clone method.
	 * @param applicantCounter
	 *    The counter for the amount of <code>Applicant</code> object.
	 * <dt> Post condition:
	 * 	  <dd><code>data</code> will have a list of <code>Applicant</code>.
	 */
	public HiringTable(Applicant[] data, int applicantCounter) {
		this.data = data;
		this.applicantCounter = applicantCounter;
	}

	//Getter Method

	/**
	 * To get the <code>data</code> from <code>HiringTable</code>
	 *   for use in refined search.
	 * @return
	 *     Returns the array with the <code>Applicant</code> for
	 *       <code>HiringTable</code>.
	 * <dt>Precondition:
	 * <dd>The <code>data</code> must be instantiated for <code>HiringTable</code>.
	 * @exception Exception
	 *    Indicates that the <code>HiringTable</code> is not instantiated yet.
	 */
	public Applicant[] getData() throws Exception {
		if(data == null) {
			throw new Exception("Hiring Table is not instantiated");
		}
		return data;
	}

	/**
	 * Sets the <code>data</code> of <code>HiringTable</code>.
	 * @param data
	 *    The <code>Applicant</code> array that will be input.
	 */
	public void setData(Applicant[] data) {
		this.data = data;
	}

	/**
	 * Sets the <code>applicantCounter</code> of <code>HiringTable</code>.
	 * @param applicantCounter
	 *    The number of <code>Applicant</code> in the <code>data</code>.
	 */
	public void setApplicantCounter(int applicantCounter) {
		this.applicantCounter = applicantCounter;
	}

	//Methods
	/**
	 * Return the number of Applicant in the list.
	 * @return
	 *    Returns an integer representing the number of Applicants in list.
	 */
	public int size() {
		return Applicant.getApplicantCount();
	}

	/**
	 * Adds an applicant to the list.
	 * @param newApplicant
	 *    The <code>Applicant</code> object that will be added to list.
	 * <dt>Precondition
	 *    <dd>The <code>Applicant</code> object has to be instantiated
	 *      and number of <code>Applicant</code> in <code>HiringTable</code>
	 *      has to be less than MAX_APPLICANTS.
	 * <dt>Post condition
	 *    <dd>The <code>Applicant</code> is inserted at the end of the list.
	 * @exception Exception
	 *    Indicates there is no room for more <code>Applicants</code>.
	 *      Should be a custom exception FullTableException however I
	 *      do not know how to make that.
	 */
	public void addApplicant(Applicant newApplicant) {
		try {
			if (newApplicant != null && applicantCounter < MAX_APPLICANTS) {
				for (int i = 0; i < MAX_APPLICANTS; i++) {
					if (data[i] == null) {
						applicantCounter++;
						//Adds to the end of the list.
						data[i] = newApplicant;
						break;
					}
				}
			} else {
				throw new Exception("There is no room for new Applicant");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	/**
	 * The applicant with the name will be removed and all applicant
	 *   in the list will be pushed down one index.
	 * @param name
	 *    The <code>applicantName</code> that will be removed from <code>data</code>
	 * @exception Exception
	 *    Indicates that the applicant name is not found in list
	 *      Should have been the custom Exception ApplicantNotFoundException
	 */
	public void removeApplicant(String name) throws Exception {
		int indexOfNameInList = indexOfApplicant(name);
		if (indexOfNameInList >= 0) {
			for(int pointer = indexOfNameInList; pointer < MAX_APPLICANTS - 1; pointer++) {
				if(data[pointer] != null) {
					data[pointer] = null;//current applicant removed
					Applicant temporaryHolder = data[pointer + 1]; //Applicant on the right
					data[pointer] = temporaryHolder; //current equals applicant on the right
				} else {
					break;
				}
			}
		} else {
			throw new Exception("Applicant is not found");
		}
	}

	/**
	 * Method to return the applicant based on a name.
	 * @param name
	 *     Name of the Applicant that is wanted
	 * @return
	 *     Returns the <code>Applicant</code> object that is a match to the name
	 * @exception Exception
	 *     Indicates that the applicant with the name is not found
	 *       This is supposed to be the custom exception
	 *       ApplicantNotFoundException.
	 */
	public Applicant getApplicant(String name) throws Exception {
		int indexOfNameInList = indexOfApplicant(name);
		if (indexOfNameInList >= 0) {
			return data[indexOfNameInList];
		} else {
			throw new Exception("Applicant is not found");
		}
	}

	/**
	 * Prints out the Applicants matching the refined search
	 * @param table
	 *    The <code>HiringTable</code> that the Applicant list is from.
	 * @param company
	 *    The name of the company that is filtered for.
	 *      if empty, then not filtered for.
	 * @param skill
	 *    The skill that is filtered for in the applicants
	 *      if empty, then not filtered for.
	 * @param college
	 *    The name of the college that is filtered for in the applicants
	 *      if empty, then not filtered for.
	 * @param GPA
	 *    The minimum GPA that is filtered for in the applicants
	 *      if empty, then not filtered for.
	 * @exception Exception
	 *    Indicates that the <code>HiringTable</code> has not been instantiated yet.
	 *      Should be a custom exception regarding HiringTable
	 */
	public static void refineSearch(HiringTable table, String company,
	  String skill, String college, double GPA) throws Exception {
		Applicant[] otherData = table.getData();
		printHeading();
		for (Applicant current : otherData) {
			if (current == null) continue;
			if (listFitSearch(current.getCompanyName(), company)) {
				if (listFitSearch(current.getApplicantSkills(), skill)) {
					if (college.isEmpty() || current.getApplicantCollege().equalsIgnoreCase(college)) {
						if (current.getApplicantGPA() >= GPA) {
							System.out.println(current);
						}
					}
				}
			}
		}

	}

	/**
	 * Creates a clone of <code>HiringTable</code>
	 * @return
	 *    Returns an object assigned with cloned <code>HiringTable</code>
	 * @exception RuntimeException
	 *    To print out the issue if a runtime exception occurs.
	 */
	public Object clone() {
		HiringTable copyOfHiringTable = null;
		try {
			Applicant[] copyOfData = new Applicant[data.length];
			for(int i = 0; i < data.length; i++) {
				if(data[i] != null) {
					copyOfData[i] = (Applicant) data[i].clone();
				}
			}
			copyOfHiringTable = new HiringTable(copyOfData, applicantCounter);
			return copyOfHiringTable;
		} catch (Exception RunTimeException) {
			RunTimeException.printStackTrace();
		}
		return copyOfHiringTable;
	}

	/**
	 * Prints out the entire formatted table with all of the applicants.
	 */
	public void printApplicantTable() {
		printHeading();
		for (Applicant partOfData : data) {
			if (partOfData != null) {
				System.out.println(partOfData);
			}
			else {
				break;
			}
		}
	}

	/**
	 * Helper method to find the index of the name of applicant.
	 * @param name
	 *    The name of applicant that is being looked for
	 * @return
	 *    Returns index value if found, otherwise -1.
	 */
	private int indexOfApplicant(String name) {
		for(int i = 0; i < MAX_APPLICANTS; i++) {
			if(data[i] == null) {
				return -1;
			}
			if (name.equalsIgnoreCase(data[i].getApplicantName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Helper method to check if array fits criteria
	 * @param list
	 *    This is the list that will be checked
	 * @param criteria
	 *    This is the String that is searched for,
	 *      if found in list, empty, or null then return true. Otherwise, false.
	 * @return
	 *    Returns true or false based on conditions.
	 */
	private static boolean listFitSearch(String[] list, String criteria) {
		if(criteria == null || criteria.isEmpty()) {
			return true;
		}
		for(String partOfList: list) {
			if(partOfList == null) continue;
			if(partOfList.equalsIgnoreCase(criteria)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to prints the heading of the formatted table.
	 */
	private static void printHeading() {
		String title = String.format("%-33s%-16s%-11s%-17s%-21s",
				"Company Name", "Applicant", "GPA", "College",
				"Skills");
		System.out.println(title);
		for(int i = 0; i < 98; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
}
