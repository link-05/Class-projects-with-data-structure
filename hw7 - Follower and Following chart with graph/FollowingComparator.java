import java.util.*;
/**
 * The <code>FollowingComparator</code> class implements the
 *   Comparator interface in order to sort in by descending order
 *   of the number of users the user is following.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FollowingComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		User u1 = (User) o1;
		User u2 = (User) o2;
		//Get follower count of each
		int u1Following = u1.getFollowingCount();
		int u2Following = u2.getFollowingCount();
		if(u1Following > u2Following) {
			return -1;
		} else if(u1Following < u2Following) {
			return 1;
		} else {
			return 0;
		}
	}
}
