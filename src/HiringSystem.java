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
        while (quitNotCalled) {
            HiringTable currentTable = new HiringTable();
            displayOption();
            System.out.print("Please enter a command: ");
            String userCommand = input.nextLine();
            System.out.println(); //Skips a line to maintain format
            readCommand(userCommand, currentTable);
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
    public static void readCommand(String command, HiringTable currentTable) {
        try {
            if (command.equalsIgnoreCase("A")) {
                //calls Add Applicant
                addApplicant(currentTable);
            } else if (command.equalsIgnoreCase("R")) {
                //calls Remove Applicant
            } else if (command.equalsIgnoreCase("G")) {
                //calls Get Applicant
            } else if (command.equalsIgnoreCase("P")) {
                //calls Print List
            } else if (command.equalsIgnoreCase("RS")) {
                //calls Refine Search
            } else if (command.equalsIgnoreCase("S")) {
                //calls Size
            } else if (command.equalsIgnoreCase("B")) {
                //calls Backup
            } else if (command.equalsIgnoreCase("Compare Backup")) {
                //calls Compare Backup
            } else if (command.equalsIgnoreCase("Revert Backup")) {
                //calls Revert Backup
            } else if (command.equalsIgnoreCase("Quit")) {
                //calls Quit
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
              " has been succcessfully added to the hiring system.\n");
        }
        catch (Exception InputMismatchException) {
            if(input.hasNext()) {
                input.nextLine();
            }
            throw new InputMismatchException("Input has error. Applicant has not been " +
			        "added to the hiring system. Try again.");
        }
    }//End of addApplicant

}