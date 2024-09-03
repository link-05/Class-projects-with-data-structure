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
    private static final int MAX_SKILLS = 3; //constant for max skill
    private static final int MAX_COMPANIES = 3; //constant for max companies
    private String[] companyName; //An Array that hold prior company names
    //companyName cannot hold more than a max constant count of company
    private String[] applicantSkills; //An Array that holds applicant skills
    //applicantSkills cannot hold more than a max constant count of skills
    private String applicantName; //The name of the applicant
    private String applicantCollege; //College that the applicant is from
    private double applicantGPA; //The grade point average of the applicant
    private static int applicantCount = 0; //The amount of applicant created.

    /**
     * Returns an instance of <code>Applicant</code>.
     */
    public Applicant() {
        companyName = new String[MAX_COMPANIES];
        applicantSkills = new String[MAX_SKILLS];
        applicantName = null;
        applicantCollege = null;
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
        if (applicantGPA < 0.0) {
            throw new IllegalArgumentException("Applicant GPA must" +
              " be a positive number.");
        }
        if (applicantName == null || applicantName.isEmpty()) {
            throw new IllegalArgumentException("Applicant must have a name.");
        }
        this.companyName = companyName;
        this.applicantSkills = applicantSkills;
        this.applicantName = applicantName;
        this.applicantCollege = applicantCollege;
        this.applicantGPA = applicantGPA;
        applicantCount++;
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
    public int getApplicantCount() {
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
        if(applicantGPA < 0.0) {
            throw new IllegalArgumentException("The GPA must be positive.");
        }
        this.applicantGPA = applicantGPA;
    }

    /**
     * Set the applicant count
     * @param applicantCount
     *    An int that represents the amount of applicant.
     * <dt> Precondition
     *    <dd><code>applicantCount</code> must be greater than 0.
     * @exception IllegalArgumentException
     *    Indicate that the <code>applicantCount</code> is less than one.
     */
    public void setApplicantCount(int applicantCount) {
        if(applicantCount < 1) {
            throw new IllegalArgumentException("The number of applicants must"
              + "be positive.");
        }
        this.applicantCount = applicantCount;
    }

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
            RunTimeException.printStackTrace();
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
            for(int i = 0; i < MAX_COMPANIES; i++) {
                if(!(this.companyName[i].equals(thatCompany[i]))) {
                    return false;
                }
            }
            //To avoid calling the getApplicant Skill method in loop
            String[] thatSkills = thatObject.getApplicantSkills();
            for(int i = 0; i < MAX_SKILLS; i++) {
                if(!(this.applicantSkills[i].equals(
                  thatSkills[i]))) {
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
        String[] placeholder = arrayToString();
        String companyLine = placeholder[0];
        String skillsLine = placeholder[1];
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
     * Helper method to put the arrays into Strings
     * @return
     *    Returns a string array with first index being the
     *      <code>companyName</code> and second index being the
     *      <code>applicantSkills</code> using StringBuilder.
     */
    private String[] arrayToString() {
        StringBuilder builder = new StringBuilder();
        String[] combinedReturn = new String[2];
        for(int i = 0; i < MAX_COMPANIES; i++) {
            String currentCompanyName = this.companyName[i];
            if (currentCompanyName.isEmpty()) {
                break;
            }
            builder.append(companyName[i]);
            builder.append(", ");
        }
        //Remove the ending comma space while assigning first index to the builder string
        combinedReturn[1] = builder.toString().substring(
          0, builder.toString().length() - 2);
        builder.setLength(0);
        //Repeat the same action from above
        for(int i = 0; i < MAX_SKILLS; i++) {
            String currentSkill = this.applicantSkills[i];
            if (currentSkill.isEmpty()) {
                break;
            }
            builder.append(applicantSkills[i]);
            builder.append(", ");
        }
        combinedReturn[1] = builder.toString().substring(
          0, builder.toString().length() - 2);
        return combinedReturn;
    }

}
