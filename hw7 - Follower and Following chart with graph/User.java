import java.io.Serializable;

/**
 * The <code>User</code> class stores all the information related
 *   to a user. Each user has their own name and an index that indicates
 *   where the user will be on the graph and a total count of user is stored.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class User implements Serializable {
	//The name of the user
	private String userName;
	//The index position they are placed on the graph.
	private int indexPos;
	//The total number of User that is created
	private int followerCount;
	private int followingCount;
	private static int userCount = 0;

	/**
	 * Returns a User object that has a userName according to what is
	 *   passed in and an <code>indexPos</code> that is equal to the userCount
	 *   before incrementing.
	 * @param userName
	 *   The String that represents the username of the <code>User</code>
	 *     being created.
	 */
	public User(String userName) {
		this.userName = userName;
		this.indexPos = userCount;
		userCount++;
	}

	/**
	 * Returns a User object that has a userName according to what is
	 *   passed in and an <code>indexPos</code> that is equal to the userCount
	 *   before incrementing.
	 * @param userName
	 *   The String that represents the username of the <code>User</code>
	 *     being created.
	 * @param indexPos
	 *   The int that represents the index position of the User object.
	 */
	public User(String userName, int indexPos) {
		this.userName = userName;
		this.indexPos = indexPos;
		userCount++;
	}

	/**
	 * Returns the amount of User objects that exists.
	 * @return
	 *    Returns the int that represents the number of User.
	 */
	public static int getUserCount() {
		return userCount;
	}

	/**
	 * Changes the total count of the <code>User</code> objects that exist.
	 * @param userCount
	 *    The number that represents the new count of User.
	 */
	public static void setUserCount(int userCount) {
		User.userCount = userCount;
	}

	/**
	 * Returns the name of the user.
	 * @return
	 *    Returns the String that represents the name of the User.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the index position of the User object.
	 * @return
	 *    Returns the int that represents the index of the User object
	 *      on the graph.
	 */
	public int getIndexPos() {
		return indexPos;
	}

	/**
	 * Returns the String representation of the User object when it is printed.
	 * @return
	 *    Returns the String representation of the <code>User</code> object when printed.
	 */
	public String toString() {
		return userName;
	}

	/**
	 * Returns the number of followers the user has.
	 * @return
	 *    Returns the int that represents the number of followers the user has.
	 */
	public int getFollowerCount() {
		return followerCount;
	}

	/**
	 * Returns the number of users the user is following.
	 * @return
	 *    Returns the int that represents the number of users the user is following.
	 */
	public int getFollowingCount() {
		return followingCount;
	}

	/**
	 * Changes the number of followers the user has.
	 * @param followerCount
	 *    The int that represents the new number of followers the user has.
	 */
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * Changes the number of users the user is following.
	 * @param followingCount
	 *   The int that represents the new number of users the user is following.
	 */
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	/**
	 * Changes the <code>userName</code> of the User object to the new userName.
	 * @param userName
	 *    The String representation of the new userName that will
	 *      replace the old one.
	 */
	public void setUsername(String userName) {
		this.userName = userName;
	}

}