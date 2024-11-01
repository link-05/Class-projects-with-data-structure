/**
 * The <code>pyramidException</code> class will implement
 *   all the custom exceptions for the <code>OrganismNode</code>.
 *   It extends the <code>Exception</code> class.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw5
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class PyramidExceptions {
	/**
	 * Returns an instance of <code>PositionNotAvailableException</code>.
	 *   This exception is used when the child position is not available
	 *   for the organism.
	 */
	public static class PositionNotAvailableException extends Exception {
		public PositionNotAvailableException() {
			super("\nERROR: There is no more room for more prey for this predator");
		}
	}

	/**
	 * Returns an instance of <code>IsPlantException</code>.
	 *   This exception is used when the organism is a plant
	 *   when it is not supposed to be.
	 */
	public static class IsPlantException extends Exception {
		public IsPlantException() {
			super("\nERROR: The cursor is at a plant node. Plants cannot be predators.");
		}
	}

	/**
	 * Returns an instance of <code>DietMismatchException</code>.
	 *   This exception is used when the organism is not preying on
	 *   the right type of organism.
	 */
	public static class DietMismatchException extends Exception{
		public DietMismatchException() {
			super("\nThis prey cannot be added as it does not match the diet of the predator.");
		}
	}
}