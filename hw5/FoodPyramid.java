import java.util.Scanner;
/**
 * The <code>FoodPyramid</code> class will implement the
 *   <code>OrganismTree</code> class to create a food pyramid
 *   and allow the user to interact with the created tree.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw5
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */

public class FoodPyramid {
	private static Scanner in = new Scanner(System.in);
	private OrganismTree tree = null;
	private static boolean isRunning = true;

	public FoodPyramid() {
		System.out.print("What is the name of the apex predator?: ");
		String name = in.nextLine().toLowerCase();
		System.out.print(
				"Is the organism an herbivore / a carnivore / an omnivore? (H / C / O) : ");
		String diet = in.nextLine().toUpperCase();
		try {
			switch (diet) {
				case "H":
					tree = new OrganismTree(new OrganismNode(name, false, true, false));
					break;
				case "C":
					tree = new OrganismTree(new OrganismNode(name, false, false, true));
					break;
				case "O":
					tree = new OrganismTree(new OrganismNode(name, false, true, true));
					break;
				default:
					//Not stated in the criteria, but I have it for organization purpose.
					throw new Exception("This is not a valid diet option. Try again");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Constructing food pyramid. . .");
	}

	/**
	 * To organize the return for an <code>OrganismTree</code>.
	 * @return
	 *   The organismTree that is being worked with in <code>FoodPyramid</code>.
	 */
	private OrganismTree getTree() {
		return tree;
	}

	/**
	 * The main method that runs the program and prompts the menu. Allows
	 *   functions to be performed on the ternary tree.
	 */
	public static void main(String[] args) {
		FoodPyramid base = new FoodPyramid();
		while(base.tree == null) {
			base = new FoodPyramid();
		}
		while(isRunning) {
			printMenu();
			menuChoice(base);
		}
	}

	/**
	 * Helper method to print the menu.
	 */
	public static void printMenu() {
		System.out.println("\n(PC) - Create New Plant Child\n" +
				"(AC) - Create New Animal Child\n" +
				"(RC) - Remove Child\n" +
				"(P) - Print Out Cursor's Prey\n" +
				"(C) - Print Out Food Chain\n" +
				"(F) - Print out Food Pyramid at Cursor\n" +
				"(LP) - List All Plants Supporting Cursor\n" +
				"(R) - Reset cursor to Root\n" +
				"(M) - Move Cursor to Child\n" +
				"(Q) - Quit\n");
	}

	/**
	 * Call the method based the user's choice on menu.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	public static void menuChoice(FoodPyramid base) {
		System.out.print("Please select an option: ");
		String choice = in.nextLine().toUpperCase();
		switch(choice) {
			case "PC":
				createPlantChild(base);
				break;
			case "AC":
				createAnimalChild(base);
				break;
			case "RC":
				removeChild(base);
				break;
			case "P":
				printPrey(base);
				break;
			case "C":
				printFoodChain(base);
				break;
			case "F":
				printFoodPyramidAtCursor(base);
				break;
			case "LP":
				printAllPlant(base);
				break;
			case "R":
				resetCursor(base);
				break;
			case "M":
				moveCursor(base);
				break;
			case "Q":
				System.out.println("Program Terminating Successfully");
				isRunning = false;
				break;
		}
	}

	/**
	 * The method for menu item (PC) to create a plant child for <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void createPlantChild(FoodPyramid base) {
		System.out.print("What is the name of the organism? ");
		String plantName = in.nextLine().toLowerCase();
		try {
			base.getTree().addPlantChild(plantName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method for menu item (AC) to create an animal child for <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void createAnimalChild(FoodPyramid base) {
		System.out.print("What is the name of the organism?: ");
		String animalName = in.nextLine().toLowerCase();
		System.out.print(
				"Is the organism an herbivore / a carnivore / an omnivore? (H / C / O) : ");
		String diet = in.nextLine().toUpperCase();
		try {
			switch (diet) {
				case "H":
					base.getTree().addAnimalChild(animalName, true, false);
					break;
				case "C":
					base.getTree().addAnimalChild(animalName, false, true);
					break;
				case "O":
					base.getTree().addAnimalChild(animalName, true, true);
					break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method for menu item (RC) to remove a child for <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void removeChild(FoodPyramid base) {
		System.out.print("What is the name of the organism to be removed?: ");
		String name = in.nextLine().toLowerCase();
		try {
			base.getTree().removeChild(name);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method for menu item (P) to print out prey for
	 *   <code>cursor</code> of <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void printPrey(FoodPyramid base) {
		try {
			System.out.println(base.getTree().listPrey());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method for menu item (C) to print out food chain, from
	 *   <code>root</code> to <code>cursor</code>, of <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void printFoodChain(FoodPyramid base) {
		System.out.println(base.getTree().listFoodChain());
	}

	/**
	 * The method for menu item (F) to print out the food pyramid from
	 *   <code>cursor</code> of <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void printFoodPyramidAtCursor(FoodPyramid base) {
		try {
			base.getTree().printOrganismTree();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The method for menu item (LP) to print out the plants from
	 *   <code>cursor</code> and down of <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void printAllPlant(FoodPyramid base) {
		System.out.println(base.getTree().listAllPlants());
	}

	/**
	 * The method for menu item (R) to reset from
	 *   <code>cursor</code> to <code>root</code> in <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void resetCursor(FoodPyramid base) {
		base.getTree().cursorReset();
	}

	/**
	 * The method for menu item (M) to move
	 *   <code>cursor</code> to a user defined organism in <code>tree</code>.
	 * @param base
	 *   The <code>FoodPyramid</code> that is being worked with.
	 */
	private static void moveCursor(FoodPyramid base) {
		System.out.print("Move to?: ");
		String name = in.nextLine();
		try {
			base.getTree().moveCursor(name);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}