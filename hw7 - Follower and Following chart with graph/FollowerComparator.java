import java.util.*;
/**
 * The <code>FollowerComparator</code> class implements the
 *   Comparator interface in order to sort by descending order
 *   of the number of followers the user has.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FollowerComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		User u1 = (User) o1;
		User u2 = (User) o2;
		//Get follower count of each
		int u1Followers = u1.getFollowerCount();
		int u2Followers = u2.getFollowerCount();
		if(u1Followers > u2Followers) {
			return -1;
		} else if(u1Followers < u2Followers) {
			return 1;
		} else {
			return 0;
		}
	}
}
