import java.util.Stack;
/**
 * The <code>Block</code> class implements an object
 *   representing a block in the blockTracer.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw3
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Anuj Sureshbhai
 */

public class Block {
	private final int MAX_SIZE = 10;
	private Variable[] content;

	/**
	 * Returns an instance of <code>Block</code>.
	 */
	public Block() {
		content = new Variable[MAX_SIZE];
	}

	/**
	 * Adds a <code>Variable</code> into <code>content</code>
	 * @param var
	 *    The <code>variable</code> that will be added.
	 * <dt> Precondition:
	 *    <dd> There is no more than 10 variables in the stack.
	 * @throws IllegalArgumentException
	 *    Indicates the block is full.
	 */
	public void addVariable(Variable var) throws IllegalArgumentException {
		if(content[MAX_SIZE-1] != null) {
			throw new IllegalArgumentException("Block is full.");
		}
		for(int i = 0; i < MAX_SIZE; i++) {
			if(content[i] == null) {
				content[i] = var;
				break;
			}
		}
	}

	/**
	 * Print all the variables for the entire <code>Block</code>.
	 *   Display each Variable's <code>name</code> and <code>initialValue</code>.
	 */
	public void printBlock() {
		//The tabs are to pass the test case.
		if(content[0] == null) {
			System.out.println("No local variables to print.");
			return;
		}
		System.out.print("Variable Name\t" + "Initial Value\n");
		for(Variable var: content) {
			if(var != null) {
				System.out.print(var.getName()+ "\t\t" +
				  "        " + var.getInitialValue() + "\n");
			} else {
				break;
			}
		}
	}

	/**
	 * Returns the single variable from the block.
	 * @param target
	 *   The name of the variable to be looked for.
	 * @return
	 *   The <code>Variable</code> object that was found, else null.
	 */
	public Variable getSingleVar(String target) {
		for(Variable var: content) {
			if(var==null) {
				break;
			}
			if(var.getName().equals(target)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * Returns the array with <code>Variable</code>.
	 * @return
	 *   The <code>content</code> array.
	 */
	public Variable[] getContent() {
		return content;
	}

}
