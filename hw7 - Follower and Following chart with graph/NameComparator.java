import java.util.*;
/**
 * The <code>NameComparator</code> class implements the
 *   Comparator interface in order to sort in alphabetical order
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class NameComparator implements Comparator {
	//From the sample Comparable code in CSE214 Homework 7 word doc.
	public int compare(Object o1, Object o2) {
		User u1 = (User) o1;
		User u2 = (User) o2;
		return u1.getUserName().compareTo(u2.getUserName());
	}
}
