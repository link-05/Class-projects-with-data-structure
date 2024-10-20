package src;
/**
 * The <code>HiringSystem</code> class will function as a menu-driven
 * application that will create the <code>HiringTable</code> object
 * and the <code>Applicant</code> objects and execute all operations
 * related to it.
 *
 * @author Kevin Lin
 *  Stony Brook ID: 116145453
 *  Email: kevin.lin.24@Stonybrook.edu
 *  Programming Assignment: hw1
 *  Course: CSE214.01
 *  Recitation: R04
 *  TA's: Veronica Oreshko, Anuj Sureshbhai
 */

import java.util.InputMismatchException;
import java.util.Scanner; //To use Scanner object for input.
public class HiringSystem {
    public static Scanner input = new Scanner(System.in);
    private static Boolean quitNotCalled = true;

	/**
     * This main method will run the menu-driven application
     */
    public static void main(String[] args) {
        HiringTable currentTable = new HiringTable();
        HiringTable backupTable = new HiringTable();
        while (quitNotCalled) {
            displayOption();
            System.out.print("Please enter a command: ");
            String userCommand = input.nextLine();
            System.out.println(); //Skips a line to maintain format
            readCommand(userCommand, currentTable, backupTable);
        }
    }

    /**
     * This method prints all the possible commands for user to select.
     */
    public static void displayOption() {
        System.out.printf("%n%-7s%-16s%n", "(A)", "Add Applicant");
        System.out.printf("%-7s%-16s%n", "(R)", "Remove Applicant");
        System.out.printf("%-7s%-16s%n", "(G)", "Get Applicant");
        System.out.printf("%-7s%-16s%n", "(P)", "Print List");
        System.out.printf("%-7s%-16s%n", "(RS)", "Refine Search");
        System.out.printf("%-7s%-16s%n", "(S)", "Size");
        System.out.printf("%-7s%-16s%n", "(B)", "Backup");
        System.out.printf("%-7s%-16s%n", "(CB)", "Compare Backup");
        System.out.printf("%-7s%-16s%n", "(RB)", "Revert Backup");
        System.out.printf("%-7s%-16s%n%n", "(Q)", "Quit");
    }

    /**
     * This will process the command and call the correct method.
     * @param command
     *    This is the input that is taken from user.
     * @param currentTable
     *    This is the <code>HiringTable</code> that is currently being
     *      worked with.
     * @exception IllegalArgumentException
     *    Indicates the user input an illegal command.
     */
    public static void readCommand(String command, HiringTable currentTable,
      HiringTable backupTable) {
        try {
            if (command.equalsIgnoreCase("A")) {
                //calls Add Applicant
                addApplicant(currentTable);
            } else if (command.equalsIgnoreCase("R")) {
                //calls Remove Applicant
                removeApplicant(currentTable);
            } else if (command.equalsIgnoreCase("G")) {
                //calls Get Applicant
                getApplicants(currentTable);
            } else if (command.equalsIgnoreCase("P")) {
                //calls Print List
                printList(currentTable);
            } else if (command.equalsIgnoreCase("RS")) {
                //calls Refine Search
                refineSearch(currentTable);
            } else if (command.equalsIgnoreCase("S")) {
                //calls Size
                size(currentTable);
            } else if (command.equalsIgnoreCase("B")) {
                //calls Backup
                backup(currentTable, backupTable);
            } else if (command.equalsIgnoreCase("CB")) {
                //calls Compare Backup
                compareBackup(currentTable, backupTable);
            } else if (command.equalsIgnoreCase("RB")) {
                //calls Revert Backup
                revertBackup(currentTable, backupTable);
            } else if (command.equalsIgnoreCase("Q")) {
                //calls Quit
                quitNotCalled = false;
            } else {
                throw new IllegalArgumentException("The command is not " +
                        "part of the list, Please try a different command.");
            }
        }
        catch (Exception IllegalArgumentException) {
            System.out.println(IllegalArgumentException.getMessage());
        }
    } //end of readCommand

    //Commands Implementation, Will follow the list in order.

    //addApplicant Method
    /**
     * The method that will prompt to add in <code>Applicant</code>.
     * @param currentTable
     *    The <code>HiringTable</code> that is currently being
     *      worked with.
     * @exception InputMismatchException
     *    Indicates that the user did not input the right data type.
     */
    public static void addApplicant(HiringTable currentTable) {
        try {
            System.out.print("Enter Applicant Name: ");
            String applicantName = input.nextLine();
            System.out.print("Enter Applicant GPA: ");
            double applicantGPA = input.nextDouble();
            input.nextLine(); //Clear the empty space in Scanner.
            System.out.print("Enter Applicant College: ");
            String applicantCollege = input.nextLine();
            String[] companyName = new String[HiringTable.MAX_COMPANIES];
            String[] applicantSkills = new String[HiringTable.MAX_SKILLS];
            //For loop to get companies
            for(int i = 0; i < HiringTable.MAX_COMPANIES; i++) {
                int numCompanyEntered = HiringTable.MAX_COMPANIES - i;
                System.out.print("Enter up to " + numCompanyEntered +
                  " companies: ");
                String userInput = input.nextLine();
                if(userInput.isEmpty()) {
                    break;
                }
                companyName[i] = userInput;
            }
            //For loop to get skills
            for(int i = 0; i < HiringTable.MAX_SKILLS; i++) {
                int numSkillEntered = HiringTable.MAX_SKILLS - i;
                System.out.print("Enter up to " + numSkillEntered +
                  " skills: ");
                String userInput = input.nextLine();
                if(userInput.isEmpty()) {
                    break;
                }
                applicantSkills[i] = userInput;
            }
            currentTable.addApplicant(new Applicant(
              companyName, applicantSkills, applicantGPA,
              applicantName, applicantCollege));
            System.out.println("Applicant " + applicantName +
              " has been successfully added to the hiring system.\n");
        }
        catch (Exception InputMismatchException) {
            if(input.hasNext()) {
                input.nextLine();
            }
            throw new InputMismatchException("Input has error. Applicant has not been " +
			        "added to the hiring system. Try again.");
        }
    }//End of addApplicant

    //removeApplicant Method

    /**
     * The method will take a name and remove the <code>Applicant</code>
     *   with the same name from the <code>HiringTable</code>.
     * @param currentTable
     *    The <code>HiringTable</code> that is currently being worked with.
     */
    public static void removeApplicant(HiringTable currentTable) {
        try {
            System.out.print("Enter Applicant Name: ");
            String applicantName = input.nextLine();
            System.out.println();
            currentTable.removeApplicant(applicantName);
            System.out.println("Applicant " + applicantName +
              " has been successfully removed from the hiring system.\n");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Display information for searched Applicant.
     * @param currentTable
     *    The <code>HiringTable</code> that is currently being worked with.
     * @exception Exception
     *    <code>getApplicant</code> will indicate when Applicant is not found.
     */
    //getApplicant Method
    public static void getApplicants(HiringTable currentTable) {
        try {
            System.out.print("Enter Applicant Name: ");
            String applicantName = input.nextLine();
            Applicant foundApplicant = currentTable.getApplicant(applicantName);
            if (foundApplicant != null) {
                String companyName = Applicant.arrayToString(
                        foundApplicant.getCompanyName());
                String applicantSkills = Applicant.arrayToString(
                        foundApplicant.getApplicantSkills());
                System.out.println("Applicant Name: " +
                        foundApplicant.getApplicantName());
                System.out.println("Applicant Applying From: " + companyName);
                System.out.println("Applicant GPA: " +
                        foundApplicant.getApplicantGPA());
                System.out.println("Applicant College: " +
                        foundApplicant.getApplicantCollege());
                System.out.println("Applicant Skills: " + applicantSkills + "\n");
            }
        } catch (Exception e) {
	        System.out.println(e.getMessage());
        }
    }//end of getApplicant

    /**
     * Prints the <code>HiringTable</code> Applicants in organized format.
     * @param currentTable
     *   The <code>HiringTable</code> that is currently being worked with.
     */
    public static void printList(HiringTable currentTable) {
        currentTable.printApplicantTable();
    }//End of printList

    /**
     * Method to print a refined search of <code>Applicant</code>
     *   in <code>HiringTable</code>.
     * @param currentTable
     *    The <code>HiringTable</code> that is currently being worked with
     * @exception Exception
     *    Exception will be thrown by <code>refineSearch</code> method
     *      in <code>HiringTable</code> class,
     *      if <code>HiringTable</code> is not instantiated.
     */
    public static void refineSearch(HiringTable currentTable) throws Exception {
        try {
            System.out.print("Enter a company to filter for: ");
            String companyName = input.nextLine();
            System.out.print("Enter a skill to filter for: ");
            String applicantSkills = input.nextLine();
            System.out.print("Enter a college to filter for: ");
            String applicantCollege = input.nextLine();
            System.out.print("Enter the minimum GPA to filter for:");
            String applicantGPAString = input.nextLine();
            double applicantGPA;
            if(applicantGPAString.isEmpty()) {
                applicantGPA = 0.0;
            } else {
                applicantGPA = Double.parseDouble(applicantGPAString);
            }
            System.out.println();
            HiringTable.refineSearch(currentTable, companyName, applicantSkills, applicantCollege, applicantGPA);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the number of applicants in this <code>HiringTable</code>.
     * @param currentTable
     *    The <code>HiringTable</code> that is currently being worked with.
     */
    public static void size(HiringTable currentTable) {
        System.out.println("There are " + currentTable.size() +
          " applicants in the hiring system.");
    }

    /**
     * Create a backup of <code>HiringTable</code> <code>currentTable</code>
     * object.
     * @param currentTable
     *    The <code>hiringTable</code> that is currently being worked with.
     * @exception Exception
     *    Indicates <code>getData</code> method from <code>HiringTable</code>
     *      is getting from <code>HiringTable</code> that is not instantiated.
     */
    public static void backup(HiringTable currentTable, HiringTable backupTable) {
        try {
            HiringTable tempBackupTable = (HiringTable) currentTable.clone();
            backupTable.setData(tempBackupTable.getData());
            backupTable.setApplicantCounter(tempBackupTable.size());
            System.out.println("Successfully created backup.");
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Compares and prints if the two <code>HiringTable</code> are equal.
     * @param currentTable
     *    The <code>hiringTable</code> that is currently being worked with.
     * @param backupTable
     *    The <code>hiringTable</code> that is stored in backup.
     * @exception Exception
     *    Indicates <code>getData</code> method from <code>HiringTable</code>
     *      is getting from <code>HiringTable</code> that is not instantiated.
     */
    public static void compareBackup(HiringTable currentTable, HiringTable backupTable) {
        try {
            Applicant[] currentList = currentTable.getData();
            Applicant[] backupList = backupTable.getData();
            for(int i = 0; i < HiringTable.MAX_APPLICANTS; i++) {
                if(currentList[i] == null && backupList[i] == null) {
                    continue;
                }
                if (currentList[i] == null && backupList[i] != null ||
                  currentList[i] != null && backupList[i] == null ||
                  !(currentList[i].equals(backupList[i]))) {
                    System.out.println(i);
                    System.out.println("Current list is not the same as"
                      + " the backup copy");
                    System.out.println();
                    System.out.println();
                    return;
                }
            }
            System.out.println("Current list is the same as the backup copy.");
            System.out.println();
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reverts <code>currentTable</code> to <code>backupTable</code>.
     * @param currentTable
     *    The <code>hiringTable</code> that is currently being worked with.
     * @param backupTable
     *    The <code>hiringTable</code> that is stored in backup.
     * @exception Exception
     *    Indicates <code>getData</code> method from <code>HiringTable</code>
     *      is getting from <code>HiringTable</code> that is not instantiated.
     */
    public static void revertBackup(HiringTable currentTable, HiringTable backupTable) {
        try {
            HiringTable tempCurrentTable = (HiringTable) backupTable.clone();
            currentTable.setData(tempCurrentTable.getData());
            currentTable.setApplicantCounter(tempCurrentTable.size());
            System.out.println("Successfully reverted to the backup copy.");
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
