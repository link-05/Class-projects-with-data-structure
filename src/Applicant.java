/**
 * The <code>Applicant</code> class implements an object
 * with all the information regarding an applicant.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@Stonybrook.edu
 *    Programming Assignment: hw1
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

import java.lang.StringBuilder;
public class Applicant {
    //Members
    private String[] companyName; //An Array that hold prior company names
    //companyName cannot hold more than a max constant count of company
    private String[] applicantSkills; //An Array that holds applicant skills
    //applicantSkills cannot hold more than a max constant count of skills
    private String applicantName; //The name of the applicant
    private String applicantCollege; //College that the applicant is from
    private double applicantGPA; //The grade point average of the applicant
    private static int applicantCount = 0; //The amount of applicant created.

    //Constructors
    /**
     * Returns an instance of <code>Applicant</code>.
     */
    public Applicant() {
        companyName = new String[HiringTable.MAX_COMPANIES];
        applicantSkills = new String[HiringTable.MAX_SKILLS];
        applicantName = "";
        applicantCollege = "";
        applicantGPA = 0.0;
        applicantCount++;
    }

    /**
     * Returns an instance of <code>Applicant</code>.
     *
     * @param companyName
     *     The array holding names of company that applicant has worked with.
     * @param applicantSkills
     *     The array holding the skills of the Applicant.
     * @param applicantGPA
     *     The double value of the applicant's grade point average.
     * @param applicantName
     *     The name of the applicant.
     * @param applicantCollege
     *     The name of the applicant's college.
     * <dt> Precondition
     *    <dd><code>applicantGPA</code> must be greater than or equal to 0.
     *    <code>applicantName</code> cannot be an empty String.
     * @exception IllegalArgumentException
     *     Indicate that the <code>applicantGPA</code> is less than zero
     *     or <code>applicantName</code> is empty.
     */
    public Applicant(String[] companyName, String[] applicantSkills,
      double applicantGPA, String applicantName, String applicantCollege) {
        try {
            if (applicantGPA < 0.0 || applicantName == null || applicantName.isEmpty()) {
                throw new IllegalArgumentException("Applicant GPA must" +
                  " be a positive number and Applicant must have a name.");
            }
            this.companyName = companyName;
            this.applicantSkills = applicantSkills;
            this.applicantName = applicantName;
            this.applicantCollege = applicantCollege;
            this.applicantGPA = applicantGPA;
            applicantCount++;
        }
        catch (Exception IllegalArgumentException) {
            System.out.println(IllegalArgumentException.getMessage());
        }
    }

    //Getter Methods
    /**
     * Returns the array of <code>companyName</code>.
     * @return
     *    returns a String array of company names.
     */
    public String[] getCompanyName() {
        return companyName;
    }

    /**
     * Returns the array of <code>applicantSkills</code>.
     * @return
     *    returns a String array of applicant skills.
     */
    public String[] getApplicantSkills() {
        return applicantSkills;
    }

    /**
     * Returns the <code>applicantName</code> String.
     * @return
     *    returns the name of the applicant.
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * Returns the <code>applicantCollege</code> String.
     * @return
     *    Returns the name of the College.
     */
    public String getApplicantCollege() {
        return applicantCollege;
    }

    /**
     * Returns a double representing the applicant's gpa.
     * @return
     *    Returns the applicants grade point average
     */
    public double getApplicantGPA() {
        return applicantGPA;
    }

    /**
     * Returns the count of Applicant object created
     * @return
     *    Returns the amount of applicants
     */
    public static int getApplicantCount() {
        return applicantCount;
    }

    //Setter methods

    /**
     * Set a new array for <code>companyName</code>
     * @param companyName
     *    An array holding the new names of company
     */
    public void setCompanyName(String[] companyName) {
        this.companyName = companyName;
    }

    /**
     * Set a new array for <code>applicantSkills</code>
     * @param applicantSkills
     *    An array holding the new skills
     */
    public void setApplicantSkills(String[] applicantSkills) {
        this.applicantSkills = applicantSkills;
    }

    /**
     * Set the applicant's name
     * @param applicantName
     *    A String that represents the name of the Applicant
     */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    /**
     * Set the applicant's college
     * @param applicantCollege
     *    A String that represents the name of the college
     */
    public void setApplicantCollege(String applicantCollege) {
        this.applicantCollege = applicantCollege;
    }

    /**
     * Set the applicant's Grade Point Average
     * @param applicantGPA
     *    A double that represents the Applicant's GPA
     * <dt> Precondition
     *    <dd><code>applicantGPA</code> must be greater than or equal to 0.
     * @exception IllegalArgumentException
     *     Indicate that the <code>applicantGPA</code> is less than zero
     */
    public void setApplicantGPA(double applicantGPA) {
        try {
            if (applicantGPA < 0.0) {
                throw new IllegalArgumentException();
            }
            this.applicantGPA = applicantGPA;
        }
        catch (Exception IllegalArgumentException) {
            throw new IllegalArgumentException("The GPA must be positive");
        }
    }

    /**
     * Set the applicant count
     * @param applicantCount
     *    An int that represents the amount of applicant.
     * <dt> Precondition
     *    <dd><code>applicantCount</code> must be positive.
     * @exception IllegalArgumentException
     *    Indicate that the <code>applicantCount</code> is less than zero.
     */
    public void setApplicantCount(int applicantCount) {
        try {
            if (applicantCount < 0) {
                throw new IllegalArgumentException();
            }
            Applicant.applicantCount = applicantCount;
        }
        catch (Exception IllegalArgumentException) {
            throw new IllegalArgumentException("The number of applicants must be positive.");
        }
    }

    //Methods
    //clone method
    /**
     * This method will clone an object copy of the Applicant
     * @exception RuntimeException
     *    Indicates that there is a problem in this section of the code.
     *      This is mostly in place for the style of try-catch for a clone.
     * @return
     *    Returns a cloned object with same instances as original.
     */
    public Object clone() {
        Applicant copy = null;
        try {
            copy = new Applicant(this.companyName, this.applicantSkills,
              this.applicantGPA, this.applicantName, this.applicantCollege);
        } catch (Exception RunTimeException) {
            System.out.println(RunTimeException.getMessage());
        }
        return copy;
    }

    //equals method
    /**
     * This method will compare the two objects for equality
     * @param obj
     *    <code>obj</code> is the object that will be compared to with this
     *      applicant object
     * @return
     *    Returns a boolean for equality of the objects.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Applicant) {
            Applicant thatObject = (Applicant) obj;
            //O(n)
            //To avoid calling the getCompanyName method in loop
            String[] thatCompany = thatObject.getCompanyName();
            for(int i = 0; i < HiringTable.MAX_COMPANIES; i++) {
                if(thatCompany[i] != null && this.companyName[i] != null) {
                    if (!(this.companyName[i].equals(thatCompany[i]))) {
                        return false;
                    }
                } else if(thatCompany[i] == null && this.companyName[i] == null) {
                    continue;
                } else {
                    return false;
                }
            }
            //To avoid calling the getApplicant Skill method in loop
            String[] thatSkills = thatObject.getApplicantSkills();
            for(int i = 0; i < HiringTable.MAX_SKILLS; i++) {
                if(this.applicantSkills[i] != null && thatSkills[i] != null) {
                    if (!(this.applicantSkills[i].equals(thatSkills[i]))) {
                        return false;
                    }
                } else if(thatSkills[i] == null && this.applicantSkills[i] == null) {
                    continue;
                } else {
                    return false;
                }
            }
            return this.applicantName.equals(thatObject.applicantName) &&
              this.applicantCollege.equals(thatObject.applicantCollege) &&
              this.applicantGPA == thatObject.applicantGPA;

        } else {
            return false;
        }
    }

    /**
     * <code>toString</code> method will override the print
     *   when printing an applicant and display their information.
     * @return
     *    Returns a String with Applicant information
     */
    public String toString() {
        String companyLine = arrayToString(companyName);
        String skillsLine = arrayToString(applicantSkills);
        return String.format("%-33s%-16s%-11.2f%-17s%-21s",
          companyLine, applicantName, applicantGPA, applicantCollege,
          skillsLine);
        //Company name 33 spaces
        // Applicant name 16 spaces
        // GPA 11 spaces
        // College 17 spaces
        // Skill 21 spaces
    }

    /**
     * Static/Helper method to put the array into String
     * @return
     *    Returns a string with all index value in a comma separated String
     *      using StringBuilder.
     * @param arr
     *    The array that will be extracted into Strings
     */
    public static String arrayToString(String[] arr) {
        StringBuilder builder = new StringBuilder();
        builder.append("");
	    for (String currentArrStr : arr) {
		    if (currentArrStr == null || currentArrStr.isEmpty()) {
			    continue;
		    }
		    builder.append(currentArrStr);
		    builder.append(", ");
	    }
        //Remove the ending comma space while assigning first index to the builder string
        String wholeString = builder.toString();
        if(wholeString.length() > 2) {
            return wholeString.substring(
                    0, wholeString.length() - 2);
        } else {
            return wholeString;
        }
    }

}
