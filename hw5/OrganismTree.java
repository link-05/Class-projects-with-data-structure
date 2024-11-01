import java.lang.StringBuilder;
/**
 * The <code>OrganismTree</code> class will implement the
 *   <code>OrganismNode</code> for a ternary tree. It will have all
 *   necessary methods to manipulate and display the ternary tree.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw5
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */

public class OrganismTree {
	private OrganismNode root;
	private OrganismNode cursor;
	//This flag is for the listFoodChain method
	private boolean foundFlag = false;

	/**
	 * Returns an instance of the tree with the <code>apexPredator</code>.
	 * @param apexPredator
	 *   The <code>OrganismNode</code> that represents the apex predator
	 * <dt> Precondition:
	 *   <dd>The <code>apexPredator</code> must be an animal.
	 * <dt> Post condition:
	 *   <dd>The <code>OrganismTree</code> is made, with <code>apexPredator</code>
	 *         representing the apex predator/root of the tree. The <code>root</code>
	 *         and <code>cursor</code> both point to this.
	 * @throws PyramidExceptions.IsPlantException
	 *   Indicates that the <code>apexPredator</code> is a plant.
	 */
	public OrganismTree(OrganismNode apexPredator) throws PyramidExceptions.IsPlantException {
		if(apexPredator.getIsPlant()) {
			throw new PyramidExceptions.IsPlantException();
		}
		root = apexPredator;
		cursor = apexPredator;
	}

	/**
	 * Move the <code>cursor</code> to <code>root</code>.
	 */
	public void cursorReset() {
		cursor = root;
		System.out.println("\nCursor successfully reset to root!");
	}

	/**
	 * Move the <code>cursor</code> to a child of the <code>OrganismNode</code> that
	 *   the <code>cursor</code> points to.
	 * @param name
	 *   The name of the child node that <code>cursor</code> will be moved to.
	 * <dt> Precondition:
	 *   <dd> <code>name</code> references a valid name of one of <code>cursor</code>'s children.
	 * <dt> Post condition:
	 *   <dd> Either an exception is thrown, or <code>cursor</code> now points to
	 *          the node whose name is referenced by <code>name</code>,
	 *          and <code>cursor</code> points at a direct child node of
	 *          original referenced <code>cursor</code>.
	 * @throws IllegalArgumentException
	 *   Indicates the <code>name</code> does not reference a direct,
	 *     valid child of <code>cursor</code>.
	 */
	public void moveCursor(String name) throws IllegalArgumentException{
		//Ensures that it is not null before calling method on it.
		if(cursor.getLeft() != null) {
			if(cursor.getLeft().getName().equalsIgnoreCase(name)) {
				cursor = cursor.getLeft();
			}
		} else if(cursor.getMiddle() != null) {
			if(cursor.getMiddle().getName().equalsIgnoreCase(name)) {
				cursor = cursor.getMiddle();
			}
		} else if(cursor.getRight() != null) {
			if(cursor.getRight().getName().equalsIgnoreCase(name)) {
				cursor = cursor.getRight();
			}
		} else {
			throw new IllegalArgumentException("\nERROR: This prey does not exist for this predator");
		}
		System.out.println("\nCursor successfully moved to " + name + "!");
	}

	/**
	 * Returns a String that represents the organism and its preys.
	 * @return
	 *   A String that has the organism at <code>cursor</code> and all of its possible prey.
	 * <dt> Post condition:
	 *   <dd> <code>cursor</code> has not been moved.
	 * @throws PyramidExceptions.IsPlantException
	 *   Indicates that the <code>cursor</code>'s organism is a plant.
	 */
	public String listPrey() throws PyramidExceptions.IsPlantException{
		if(cursor.getIsPlant()) {
			throw new PyramidExceptions.IsPlantException();
		}
		StringBuilder sb = new StringBuilder();
		if(cursor.getLeft() != null) {
			sb.append(", ").append(cursor.getLeft().getName());
		}
		if(cursor.getMiddle() != null) {
			sb.append(", ").append(cursor.getMiddle().getName());
		}
		if(cursor.getRight() != null) {
			sb.append(", ").append(cursor.getRight().getName());
		}
		if(!sb.isEmpty()) {
			return cursor.getName() + " -> " + sb.substring(2);
		}
		return cursor.getName() + " -> ";
	}

	/**
	 * List from the Apex Predator to the <code>cursor</code> organism.
	 * <dt> Post condition:
	 *   <dd> The <code>cursor</code> has not moved.
	 * @return
	 *   A string of the food chain showing the Apex Predator to the <code>cursor</code>.
	 */
	public String listFoodChain() {
		StringBuilder sb = new StringBuilder();
		setFoundFlag(false);
		findCursor(root, sb);
		return sb.toString();
	}

	/**
	 * Change the boolean foundFlag.
	 * @param found
	 *   the condition that the foundFlag will be set to.
	 */
	private void setFoundFlag(Boolean found) {
		foundFlag = found;
	}

	/**
	 * Returns the condition of the <code>foundFlag</code>.
	 * @return
	 *   The <code>foundFlag</code> which is a boolean.
	 */
	private boolean getFoundFlag() {
		return foundFlag;
	}

	/**
	 * Helper method for finding and appending the path from
	 *   the apex predator to the <code>cursor</code>.
	 * @param predator
	 *   The node that is the original predator.
	 * @param sb
	 *   The <code>StringBuilder</code> that will be modified to store the path.
	 */
	private void findCursor(OrganismNode predator, StringBuilder sb) {
		//Returning with recursion and strings did not work...
		//Backtracking with StringBuilder.
		if(predator.getName().equals(cursor.getName())) {
			sb.append(predator.getName());
			setFoundFlag(true);
			return;
		}
		int originalLengthBeforeAppend = sb.length();
		//If the found flag is true then it should not enter any of these loops
		// since it is found already.
		if(predator.getLeft() != null && !getFoundFlag()) {
			sb.append(predator.getName()).append(" -> ");
			findCursor(predator.getLeft(), sb);
		}
		if(predator.getMiddle() != null && !getFoundFlag()) {
			sb.append(predator.getName()).append(" -> ");
			findCursor(predator.getMiddle(), sb);
		}
		if(predator.getRight() != null && !getFoundFlag()) {
			sb.append(predator.getName()).append(" -> ");
			findCursor(predator.getRight(), sb);
		}
		if(getFoundFlag()) return;
		//4 is the length of the arrow appended per time.
		//If it reaches this part then the path is not right, and it will be deleted.
		sb.setLength(originalLengthBeforeAppend - 4);
	}

	/**
	 * Prints an indented organism tree from the <code>cursor</code>.
	 * <dt> Post condition:
	 *   <dd> <code>cursor</code> has not moved.
	 *   <dd> <code>root</code> has not moved.
	 */
	public void printOrganismTree() {
		StringBuilder sb = new StringBuilder();
		createOrganismTreeHelper(cursor, 0, sb);
		System.out.println(sb);
	}

	/**
	 * Helper method to recursively go through the tree in preorder
	 *   and append it to the <code>OrganismTree</code>.
	 * @param node
	 *     The <code>OrganismNode</code> that is being checked each time.
	 * @param depth
	 *     The depth of the tree to determine the number of indentations.
	 * @param sb
	 *     The <code>StringBuilder</code> that will append all the organism names.
	 */
	private void createOrganismTreeHelper(OrganismNode node, int depth, StringBuilder sb) {
		for(int i = 0; i < depth; i++) {
			sb.append("\t");
		}
		if(!node.getIsPlant()) {
			sb.append("| - ");
		} else {
			sb.append("- ");
		}
		sb.append(node.getName()).append("\n");
		if(node.getLeft() != null) {
			createOrganismTreeHelper(node.getLeft(), depth + 1, sb);
		}
		if(node.getMiddle() != null) {
			createOrganismTreeHelper(node.getMiddle(), depth + 1, sb);
		}
		if(node.getRight() != null) {
			createOrganismTreeHelper(node.getRight(), depth + 1, sb);
		}
	}

	/**
	 * Returns a list of all the plants in the food pyramid.
	 * <dt> Post condition:
	 *   <dd> <code>cursor</code> has not moved.
	 *   <dd> <code>root</code> has not moved.
	 * @return
	 *   A String containing all the list of plants in the food pyramid from
	 *     <code>cursor</code> and down.
	 */
	public String listAllPlants() {
		StringBuilder sb = new StringBuilder();
		appendAllPlants(cursor, sb);
		return sb.substring(0,sb.length() - 2);
	}

	/**
	 * Helper method to traverse in preorder and append all the plant nodes.
	 * @param node
	 *     This is the <code>OrganismNode</code> that is checked for each time.
	 * @param sb
	 *     This is the <code>StringBuilder</code> that will hold the plant list.
	 */
	private void appendAllPlants(OrganismNode node, StringBuilder sb) {
		if(node.getIsPlant()) {
			sb.append(node.getName()).append(", ");
		}
		if(node.getLeft() != null) {
			appendAllPlants(node.getLeft(), sb);
		}
		if(node.getMiddle() != null) {
			appendAllPlants(node.getMiddle(), sb);
		}
		if(node.getRight() != null) {
			appendAllPlants(node.getRight(), sb);
		}
	}

	/**
	 * Adds an animal child to the <code>OrganismTree</code> as a child of <code>cursor</code>.
	 * <dt> Precondition:
	 *   <dd> <code>name</code> does not reference another direct child of the <code>cursor</code>.
	 *   <dd> <code>cursor</code> has an available position for another child node.
	 * <dt> Post condition:
	 *   <dd> Either an exception is thrown,  or the new animal node is added as a child
	 *          of the <code>cursor</code> with a specific diet.
	 *   <dd> The <code>cursor</code> does not move.
	 * @param name
	 *   The name of the <code>OrganismNode</code> child.
	 * @param isHerbivore
	 *   Value that determines whether the animal consumes plant.
	 * @param isCarnivore
	 *   Value that determines whether the animal consumes other animal.
	 * @throws IllegalArgumentException
	 *   Indicates name references an exact name of an existing child.
	 * @throws PyramidExceptions.PositionNotAvailableException
	 *   Indicates there is not a child position available for the new node to be added.
	 */
	public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore)
			throws IllegalArgumentException, PyramidExceptions.PositionNotAvailableException,
			  PyramidExceptions.DietMismatchException, PyramidExceptions.IsPlantException {
		//false is the condition for isPlant which it should not be.
		OrganismNode temp = new OrganismNode(name, false, isHerbivore, isCarnivore);
		//If empty, set child to temp, otherwise test if the name of it matches existing
		//if no, continue until finding an empty otherwise throw exception.
		if(cursor.getLeft() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getLeft().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else if(cursor.getMiddle() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getMiddle().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else if(cursor.getRight() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getRight().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else {
			throw new PyramidExceptions.PositionNotAvailableException();
		}
		System.out.println("\nA(n) " + name +
		  " has successfully been added as prey for the " + cursor.getName() + "!");
	}

	/**
	 * Adds a plant child to the <code>OrganismTree</code> as a child of <code>cursor</code>.
	 * <dt> Precondition:
	 *   <dd> <code>name</code> does not reference another direct child of the <code>cursor</code>.
	 *   <dd> <code>cursor</code> has an available position for another child node.
	 * <dt> Post condition:
	 *   <dd> Either an exception is thrown, or the new plant node is added as a child
	 *          of the <code>cursor</code>.
	 *   <dd> The <code>cursor</code> does not move.
	 * @param name
	 *   The name of the <code>OrganismNode</code> child.
	 * @throws IllegalArgumentException
	 *   Indicates name references an exact name of an existing child.
	 * @throws PyramidExceptions.PositionNotAvailableException
	 *   Indicates there is not a child position available for the new node to be added.
	 */
	public void addPlantChild(String name)
			throws IllegalArgumentException, PyramidExceptions.PositionNotAvailableException,
			  PyramidExceptions.DietMismatchException, PyramidExceptions.IsPlantException {
		//true is the condition for isPlant and false is the condition for type of eater.
		OrganismNode temp = new OrganismNode(name, true, false, false);
		//If empty, set child to temp, otherwise test if the name of it matches existing
		//if no, continue until finding an empty otherwise throw exception.
		//Same code as addAnimalChild however since the uml model says it needs to throw exception
		//so I won't make a separate method to do both. Could be one method.
		if(cursor.getLeft() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getLeft().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else if(cursor.getMiddle() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getMiddle().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else if(cursor.getRight() == null) {
			cursor.addPrey(temp);
		} else if(cursor.getRight().getName().equals(name)) {
			throw new IllegalArgumentException(
			  "\nERROR: This prey already exists for this predator");
		} else {
			throw new PyramidExceptions.PositionNotAvailableException();
		}
		System.out.println("\nA(n) " + name +
		  " has successfully been added as prey for the " + cursor.getName() + "!");
	}

	/**
	 * Removes the child node of <code>cursor</code> with the same name,
	 *   then shifts all siblings. Removes all descendent of deleted child.
	 * <dt>Precondition:
	 *   <dd> <code>name</code> should reference a direct child fo the cursor.
	 * <dt>Post condition:
	 *   <dd> The child node of <code>cursor</code> has been removed
	 *          and its descendents too.
	 *   <dd> The deleted node's siblings are shifted if necessary.
	 *   <dd> <code>cursor</code> has not moved.
	 * @param name
	 *   The name of the child node that will be deleted
	 * @throws IllegalArgumentException
	 *   Indicates the name does not reference a direct child of
	 *     the <code>cursor</code>.
	 */
	public void removeChild(String name) throws IllegalArgumentException {
		//First checks if it is not null, will short circuit if it is
		// then if child has the matching name, set child to null.
		if(cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) {
			cursor.setLeft(null);
		} else if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) {
			cursor.setMiddle(null);
		} else if(cursor.getRight() != null && cursor.getRight().getName().equals(name)) {
			cursor.setRight(null);
		} else {
			throw new IllegalArgumentException(
			  "\nERROR: This prey does not exist for this predator");
		}
		//Checks if the left is null and middle is not, perform a swap in position.
		if(cursor.getLeft() == null && cursor.getMiddle() != null) {
			cursor.setLeft(cursor.getMiddle());
			cursor.setMiddle(null);
		}
		if(cursor.getMiddle() == null && cursor.getRight() != null) {
			cursor.setMiddle(cursor.getRight());
			cursor.setRight(null);
		}
		System.out.println("\nA(n) " + name +
		  " has been successfully removed as prey for the " + cursor.getName() + "!");
	}


}