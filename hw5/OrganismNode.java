/**
 * The <code>OrganismNode</code> class will implement the plant and
 *   animal node for the tree. It wil have all necessary methods
 *   to get and set the data.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw5
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */

public class OrganismNode {
	//The name of the organism
	private String name;
	//A plant that will not eat other organisms
	private boolean isPlant;
	//An animal that has special diet plants
	private boolean isHerbivore;
	//An animal that has special diet animals
	private boolean isCarnivore;
	//The left child of the node
	private OrganismNode left;
	//The middle child of the node
	private OrganismNode middle;
	//The right child of the node
	private OrganismNode right;

	/**
	 * Returns an instance of <code>OrganismNode</code>.
	 */
	public OrganismNode() {
		name = "";
		isPlant = false;
		isHerbivore = false;
		isCarnivore = false;
		left = null;
		middle = null;
		right = null;
	}

	/**
	 * Returns an instance of <code>OrganismNode</code>.
	 * @param name
	 *    the name of the organism.
	 * @param isPlant
	 *    whether the organism is a plant.
	 * @param isHerbivore
	 *    whether the organism is a herbivore.
	 * @param isCarnivore
	 *    whether the organism is a carnivore.
	 */
	public OrganismNode(String name, boolean isPlant,
	  boolean isHerbivore, boolean isCarnivore) {
		this.name = name;
		this.isPlant = isPlant;
		this.isHerbivore = isHerbivore;
		this.isCarnivore = isCarnivore;
		left = null;
		middle = null;
		right = null;
	}

	//Getter methods

	/**
	 * Returns the name of the organism.
	 * @return
	 *   A string representing the name of the organism.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the organism's <code>name</code> to the new name.
	 * @param name
	 *   The new name of the organism.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns whether the organism is a plant.
	 * @return
	 *   A boolean representing whether the organism is a plant.
	 */
	public boolean getIsPlant() {
		return isPlant;
	}

	/**
	 * Returns whether the organism is a herbivore.
	 * @return
	 *   A boolean representing whether the organism is a herbivore.
	 */
	public boolean getIsHerbivore() {
		return isHerbivore;
	}

	/**
	 * Returns whether the organism is a carnivore.
	 * @return
	 *   A boolean representing whether the organism is a carnivore.
	 */
	public boolean getIsCarnivore() {
		return isCarnivore;
	}

	/**
	 * Returns the left child of the node.
	 * @return
	 *   The left child of the <code>OrganismNode</code>.
	 */
	public OrganismNode getLeft() {
		return left;
	}

	/**
	 * Sets the left child of the node.
	 * @param left
	 *   The <code>OrganismNode</code> that will be set as the left child.
	 */
	public void setLeft(OrganismNode left) {
		this.left = left;
	}

	/**
	 * Returns the middle child of the node.
	 * @return
	 *   The middle child of the <code>OrganismNode</code>.
	 */
	public OrganismNode getMiddle() {
		return middle;
	}

	/**
	 * Sets the middle child of the node.
	 * @param middle
	 *   The <code>OrganismNode</code> that will be set as the middle child.
	 */
	public void setMiddle(OrganismNode middle) {
		this.middle = middle;
	}

	/**
	 * Returns the right child of the node.
	 * @return
	 *   The right child of the <code>OrganismNode</code>.
	 */
	public OrganismNode getRight() {
		return right;
	}

	/**
	 * Sets the right child of the node.
	 * @param right
	 *   The <code>OrganismNode</code> that will be set as the right child.
	 */
	public void setRight(OrganismNode right) {
		this.right = right;
	}

	//wording for precondition comes right from assignment.
	/**
	 * Adds a prey to the organism. Filling in order from left, middle, and right on
	 *   the tree.
	 * <dt>Precondition:
	 *   <dd> The node is not a plant.
	 *   <dd> At least one of the three child node positions of this node is available.
	 *   <dd> The type of prey correctly corresponds to the diet of this node.
	 * <dt>Post condition:
	 *   <dd> Either an exception is thrown, or preyNode is added as a child of this node.
	 * @param preyNode
	 *   The <code>OrganismNode</code> that will be added as a prey/child node.
	 * @throws PyramidExceptions.PositionNotAvailableException
	 *   Indicates no child position is available for the <code>preyNode</code>.
	 * @throws PyramidExceptions.IsPlantException
	 *   Indicates the current organism is a plant.
	 * @throws PyramidExceptions.DietMismatchException
	 *   Indicates the organism is not preying on the right type of organism.
	 */
	public void addPrey(OrganismNode preyNode) throws PyramidExceptions.PositionNotAvailableException,
	  PyramidExceptions.IsPlantException, PyramidExceptions.DietMismatchException {
		if(this.getIsPlant()) {
			//A plant cannot eat other organisms.
			throw new PyramidExceptions.IsPlantException();
		}
		//If preyNode is a plant and this is a carnivore
		// or preyNode is an animal and this is a herbivore throw exception.
		if(preyNode.getIsPlant() && this.getIsCarnivore() || !preyNode.getIsPlant() && this.getIsHerbivore()) {
			//A carnivore cannot eat plants.
			throw new PyramidExceptions.DietMismatchException();
		}
		if(this.getLeft() == null) {
			this.setLeft(preyNode);
		} else if(this.getMiddle() == null) {
			this.setMiddle(preyNode);
		} else if(this.getRight() == null) {
			this.setRight(preyNode);
		} else {
			//If all three child nodes are filled, throw exception.
			throw new PyramidExceptions.PositionNotAvailableException();
		}
	}
}