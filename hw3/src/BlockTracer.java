/**
 * The <code>BlockTracer</code> class will create a stack that
 *   reads the c file and will create a stack holding blocks
 *   that represent the blocks of code that will hold initial values
 *   of variables.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw3
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */
import java.io.*;
import java.util.Scanner;
import java.util.Stack;


public class BlockTracer {
	/**
	 * The main method will prompt the user for a file name
	 *   then begin scanning through the file to create blocks
	 *   and variables.
	 * @throws FileNotFoundException
	 *   The file name inputted by the user is not found.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		try {
			Stack<Block> stack = new Stack<>();
			Scanner in = new Scanner(System.in);
			System.out.print("Enter C program filename: ");
			FileInputStream fis = new FileInputStream(in.nextLine());
			InputStreamReader inStream = new InputStreamReader(fis);
			BufferedReader stdin = new BufferedReader(inStream);
			//The test case fails if there isn't this new line.
			System.out.println();
			String line;
			//Read file line by line
			while((line = stdin.readLine()) != null) {
				checkForKeywords(line, stack);
			}
		} catch (FileNotFoundException e) {
			System.out.println("C File not found.");
		} catch (IOException e) {
			System.out.println("Reading file failed.");
		}
	}

	/**
	 * Checks the line for keywords and calls the corresponding methods.
	 * @param line
	 *    The line read from the BufferedReader.
	 * @param stack
	 *    The stack that holds the blocks.
	 */
	public static void checkForKeywords(String line, Stack<Block> stack) {
		//Trim the whitespace so it is easier to check for space before line
		//line had tab before so indexOf did not detect space int space.
		//Add space to allow checking for space int space for int space lines.
		line = " " + line.trim();
		//Checks if it is only whitespace
		//Sequence for ifs are based on test case.
		//In case of multiple statements in one line.
		while(!line.isBlank()) {
			if (line.contains("{")) {
				//create a new block to be pushed
				stack.push(new Block());
				line = line.substring(line.indexOf("{") + 1);
			} else if (line.contains(" int ")) {
				//create all the variable in the line.
				createVariable(line, stack);
				line = line.substring(line.indexOf(";") + 1);
			} else if (line.contains("/*$print")) {
				if (line.contains("LOCAL")) {
					//print the local variables
					localPrint(stack);
				} else {
					//print the variable
					String variable = line.substring(line.indexOf("*") + 8, line.indexOf("*/"));
					variablePrint(variable, stack);
				}
				line = line.substring(line.indexOf("*/") + 2);
			}   else if (line.contains("}")) {
				//pops the block from the stack.
				stack.pop();
				line = line.substring(line.indexOf("}") + 1);
			}else {
				line = " ";
			}
		}
	}

	/**
	 * Creates the variables in the line
	 *   and adds them to the <code>Block</code> top of the stack.
	 * @param line
	 *    The String read from the BufferedReader that
	 *      contains the variables to be created.
	 * @param stack
	 *    The stack that holds the blocks.
	 */
	public static void createVariable(String line, Stack<Block> stack) {
		//only grab the String between keyword int and the semicolon
		String base = line.replaceAll("\\s", "");
		base = base.substring(base.indexOf("int") + 3, base.indexOf(";") + 1);
		//split the string by commas to get the variable
			//If it has a comma or before statement end at semicolon
		while(base.contains(",") || base.contains(";")) {
			//s is the String being worked with
			String s = "";
			//Base will be handled and is mostly for checking whether to stop while loop
			if(base.contains(",")) {
				s = base.substring(0, base.indexOf(","));
				base = base.substring(base.indexOf(",") + 1);
			} else {
				s = base.substring(0, base.indexOf(";"));
				base = "";
			}
			if (s.contains("=")) {
				String name = s.substring(0, s.indexOf("="));
				int value = Integer.parseInt(s.substring(s.indexOf("=") + 1));
				stack.peek().addVariable(new Variable(name, value));
			} else {
				//Add the variable to the top of the stack
				stack.peek().addVariable(new Variable(s));
			}
		}
	}

	/**
	 * Prints the local variables of the block.
	 * @param stack
	 *    The stack that holds the blocks.
	 */
	public static void localPrint(Stack<Block> stack) {
		//Print the local variables
		stack.peek().printBlock();
	}

	/**
	 * Prints the specific variable and its initial value.
	 * @param variable
	 *   The variable to be printed, if found in any block, else print not found.
	 * @param stack
	 *   The stack that holds the blocks.
	 */
	public static void variablePrint(String variable, Stack<Block> stack) {
		Stack<Block> tempStack = new Stack<Block>();
		Boolean found = false;
		while(!stack.isEmpty()) {
			Block b = stack.peek();
			Variable v = b.getSingleVar(variable);
			if (v != null) {
				//the tabs are to pass the test case regarding spacing
				System.out.printf("%-20s%-20s%n", "Variable Name", "Initial Value");
				System.out.printf("%-20s%-20s%n", v.getName(), v.getInitialValue());
				found = true;
				break;
			}
			tempStack.push(stack.pop());
		}
		while(!tempStack.isEmpty()) {
			stack.push(tempStack.pop());
		}
		if(!found) System.out.println("Variable not found: " + variable);
	}
}
