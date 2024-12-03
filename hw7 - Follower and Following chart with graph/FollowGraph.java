import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.lang.StringBuilder;

/**
 * The <code>FollowGraph</code> class is the data structure
 *   stores all users in this graph up to <code>MAX_USERS</code>.
 *   It can compare and print out all the paths connecting each
 *   <code>User</code> object.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FollowGraph implements Serializable {
	private ArrayList<User> users;
	private static final int MAX_USERS = 100;
	private boolean[][] connections;

	/**
	 * Returns the list of all the users in the graph.
	 * @return
	 *    Returns the ArrayList of <code>User</code> objects.
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * Returns an instance of Follower Graph.
	 */
	public FollowGraph() {
		users = new ArrayList<User>();
		connections = new boolean[MAX_USERS][MAX_USERS];
	}
	/**
	 * Adds a new User to the <code>users</code> list.
	 * <dt> Precondition:
	 *   <dd> The user that will be added has to be a new user.
	 * @param user
	 *   The name of the User that will be added.
	 * @throws IllegalArgumentException
	 *    Indicates that the User already exists.
	 */
	public void addUser(String user) throws IllegalArgumentException{
		for(User u: users) {
			if(u == null) {
				continue;
			}
			if(u.getUserName().equals(user)) {
				throw new IllegalArgumentException("Username already exists");
			}
		}
//		int count = User.getUserCount();
//		for(int i = 0; i < count; i++) {
//			if(users.get(i) == null) {
//				users.add(i, new User(user, i));
//				return;
//			}
//		}
		users.add(new User(user));
	}

	/**
	 * Remove a User from the <code>users</code> list and update <code>Connections</code>.
	 * <dt> Precondition:
	 *   <dd> The user will be removed.
	 * @param user
	 *   The name of the User that will be added.
	 * @throws IllegalArgumentException
	 *    Indicates that the User already exists.
	 */
	public void removeUser(String user) throws IllegalArgumentException{
		User foundUser = null;
		for(User u: users) {
			if(u.getUserName().equalsIgnoreCase(user)) {
				foundUser = u;
			}
		}
		if(foundUser == null) {
			throw new IllegalArgumentException("Username does not exist");
		}
		int indexOfUser = foundUser.getIndexPos();
		users.add(foundUser.getIndexPos(), null);
		users.remove(foundUser);
		for(int i = 0; i < User.getUserCount(); i++) {
			connections[indexOfUser][i] = false;
			connections[i][indexOfUser] = false;
		}
		User.setUserCount(User.getUserCount() - 1);
	}

	/**
	 * Add a connection from a user to another in <code>connections</code>.
	 * <dt> Precondition:
	 *    <dd> The given users must be valid.
	 * @param userFrom
	 *    The name of the user to set the connection from.
	 * @param userTo
	 *    The name of the user to set the connection to.
	 * @throws IllegalArgumentException
	 *    Indicates that the user does not exist.
	 */
	public void addConnection(String userFrom, String userTo) {
		int indexOfUserFrom = -1;
		int indexOfUserTo = -1;
		int i = 0;
		for(User user: users) {
			String tempName = user.getUserName();
			if(userFrom.equalsIgnoreCase(tempName)) {
				indexOfUserFrom = i;
			} else if(userTo.equalsIgnoreCase(tempName)) {
				indexOfUserTo = i;
			}
			i++;
		}
		if(indexOfUserFrom == -1 || indexOfUserTo == -1) {
			throw new IllegalArgumentException("The users are not valid");
		} else {
			connections[indexOfUserFrom][indexOfUserTo] = true;
		}
	}

	/**
	 * Remove a connection from a user to another in <code>connections</code>.
	 * <dt> Precondition:
	 *    <dd> The given users must be valid.
	 * @param userFrom
	 *    The name of the user to remove the connection from.
	 * @param userTo
	 *    The name of the user to remove the connection to.
	 * @throws IllegalArgumentException
	 *    Indicates that the user does not exist.
	 */
	public void removeConnection(String userFrom, String userTo) {
		int indexOfUserFrom = -1;
		int indexOfUserTo = -1;
		int i = 0;
		for(User user: users) {
			if(user == null) {
				continue;
			}
			String tempName = user.getUserName();
			if(userFrom.equalsIgnoreCase(tempName)) {
				indexOfUserFrom = i;
			} else if(userTo.equalsIgnoreCase(tempName)) {
				indexOfUserTo = i;
			}
			i++;
		}
		if(indexOfUserFrom == -1 || indexOfUserTo == -1) {
			throw new IllegalArgumentException("The users are not valid");
		} else {
			connections[indexOfUserFrom][indexOfUserTo] = false;
		}
	}

	/**
	 * Find the shortest path between the users, and returns a string.
	 * <dt> Precondition:
	 *   <dd> The user has to exist in the list.
	 * @param from
	 *    The <code>User</code> that the path starts at.
	 * @param to
	 *    The <code>User</code> that the path ends at.
	 * @return
	 *    Returns a String that represents the shortest path
	 *      between <code>from</code> and <code>to</code>.
	 * @throws IllegalArgumentException
	 *    Indicates that the user does not exist.
	 */
	public String shortestPath(String from, String to) {
		//Checks if the users exist first
		int userFromIndex = -1;
		int userToIndex = -1;
		for(User user: users) {
			if(user.getUserName().equalsIgnoreCase(from)) {
				userFromIndex = user.getIndexPos();
			}
			if(user.getUserName().equalsIgnoreCase(to)) {
				userToIndex = user.getIndexPos();
			}
		}
		if(userFromIndex == -1 || userToIndex == -1) {
			throw new IllegalArgumentException("Users are not valid.");
		}
		Integer[][] next = floydWarshallTraversal(from, to);
		StringBuilder path = new StringBuilder();
		int userQuantity = 0;
		if(next[userFromIndex][userToIndex] == null) {
			return "";
		}
		path.append(from);
		userQuantity++;
		while(userFromIndex != userToIndex) {
			userFromIndex = next[userFromIndex][userToIndex];
			path.append(" -> ").append(users.get(userFromIndex).getUserName());
			userQuantity++;
		}
		return (path.toString() + userQuantity);
	}


	/**
	 * Find all the paths between the users, and returns an ArrayList of Strings representing
	 *   these paths.
	 * <dt> Precondition:
	 *   <dd> The user has to exist in the list.
	 * @param from
	 *    The <code>User</code> that the path starts at.
	 * @param to
	 *    The <code>User</code> that the path ends at.
	 * @return
	 *    Returns an ArrayList of Strings that represents all the paths
	 *      between <code>from</code> and <code>to</code>.
	 * @throws IllegalArgumentException
	 *    Indicates that the user does not exist.
	 */
	public ArrayList<String> allPaths(String from, String to) {
		ArrayList<String> loops = new ArrayList<String>();
		//Loops will be found by using Depth First Traversal
		int count = User.getUserCount();
		int fromIndex = -1;
		int toIndex = -1;
		for(User user: users) {
			if(user != null) {
				if(user.getUserName().equalsIgnoreCase(from)) {
					fromIndex = user.getIndexPos();
				}
				if(user.getUserName().equalsIgnoreCase(to)) {
					toIndex = user.getIndexPos();
				}
			}
		}
		boolean[] marked = new boolean[count];
		ArrayList<String> path = new ArrayList<String>();
		DFT(this, loops, path, toIndex, fromIndex, marked);
		return loops;
	}

	/**
	 * Print all the users in the graph, order is based on comparator.
	 * @param type
	 *   The comparator that will be used to sort the users.
	 */
	public void printAllUsers(Comparator type) {
		ArrayList<User> tempArr = new ArrayList<User>();
		updateAllFollowerFollowing();
		for(User u: users) {
			if(u == null) {
				continue;
			}
			tempArr.add(u);
		}
		if(type instanceof FollowerComparator || type instanceof FollowingComparator) {
			tempArr.sort(new NameComparator());
		}
		tempArr.sort(type);
		for(User u: tempArr) {
			System.out.printf("%-41s%-21s%s\n", u.getUserName(), u.getFollowerCount(), u.getFollowingCount());
		}
	}

	/**
	 * Print all the user's follower connections in the graph.
	 * @param user
	 *    The user that will have all follower printed.
	 */
	public void printAllFollowers(String user) {
		int index = -1;
		for(User u: users) {
			if(u == null) {
				continue;
			}
			if(u.getUserName().equalsIgnoreCase(user)) {
				index = u.getIndexPos();
				break;
			}
		}
		int count = User.getUserCount();
		for(int i = 0; i < count; i++) {
			if(connections[i][index]) {
				System.out.println(users.get(i).getUserName());
			}
		}
	}

	/**
	 * Print all the user's following connections in the graph.
	 * @param user
	 *    The user that will have all following printed.
	 */
	public void printAllFollowing(String user) {
		int index = -1;
		for(User u: users) {
			if(u == null) {
				continue;
			}
			if(u.getUserName().equalsIgnoreCase(user)) {
				index = u.getIndexPos();
				break;
			}
		}
		int count = User.getUserCount();
		for(int i = 0; i < count; i++) {
			if(connections[index][i]) {
				System.out.println(users.get(i).getUserName());
			}
		}
	}

	/**
	 * Find all the loops in the graph, and returns an ArrayList of Strings representing
	 *   these loops.
	 * @return
	 *    Returns an ArrayList of Strings that represents all the loops in the graph.
	 */
	public ArrayList<String> findAllLoops() {
		ArrayList<String> loops = new ArrayList<String>();
		//Loops will be found by using Depth First Traversal
		int count = User.getUserCount();
		boolean[] marked = new boolean[count];
		ArrayList<String> path = new ArrayList<String>();
		DFT(this, loops, path, 0, 0, marked);
		return loops;
	}

	/**
	 * Depth First Traversal of the graph while filling in the loops.
	 *   This is based off the CSE214, DFT method in the unit 10 lecture slides.
	 * @param loops
	 *   The ArrayList that will store all the loops.
	 * @param path
	 *   The ArrayList that will store the path as it goes into the DFT.
	 * @param currentUser
	 *   The current user that is being examined.
	 * @param marked
	 *   The boolean array that will keep track of the users that have been visited.
	 */
	private static void DFT(FollowGraph g, ArrayList<String> loops, ArrayList<String> path, int mainTarget,
	  int currentUser, boolean[] marked) {
		//This is based off the CSE214, DFT method in the unit 10 lecture slides.
		int[] connections = fillConnection(g, currentUser);
		int nextNeighbor;
		path.add(g.getUsers().get(currentUser).getUserName());
		if(currentUser != mainTarget) {
			marked[currentUser] = true;
		}
		for (int i=0; i<connections.length; i++) {
			if(connections[i] == -1) {
				break;
			}
			nextNeighbor = connections[i];
			if(nextNeighbor == mainTarget) {
				//This will add the path to the loop
				StringBuilder sb = new StringBuilder();
				for(String user: path) {
					sb.append(user).append(" -> ");
				}
				if(sb.toString().length() > 0) {
					sb.append(g.getUsers().get(mainTarget).getUserName());
					loops.add(sb.toString());
				}
			}
			else if (!marked[nextNeighbor]) {
				DFT(g, loops, path, mainTarget, nextNeighbor, marked);
			}
		}
		path.remove(path.size() - 1);
		marked[currentUser] = false;
	}

	/**
	 * Helper method to fill in all the connection of a user.
	 * @param g
	 *    The graph to access the connections.
	 * @param user
	 *    The user that is being filled in for.
	 * @return
	 *    Returns an array of connections.
	 */
	private static int[] fillConnection(FollowGraph g, int user) {
		int count = User.getUserCount();
		ArrayList<User> list = new ArrayList<User>();
		int[] connection = new int[count];
		for(int i = 0; i < count; i++) {
			connection[i] = -1;
		}
		int index = 0;
		ArrayList<User> users = g.getUsers();
		for(int i = 0; i < count; i++) {
			if(g.getConnections()[user][i]) {
				list.add(users.get(i));
			}
		}
		list.sort(new NameComparator());
		for(int i = 0; i < list.size(); i++) {
			connection[i] = list.get(i).getIndexPos();
		}
		return connection;
	}

	/**
	 * Returns the connections of the <code>FollowGraph</code>.
	 * @return
	 *   Returns the 2d boolean array that represents the connections.
	 */
	private boolean[][] getConnections() {
		return connections;
	}

	public void loadAllUsers(String fileName) throws IOException, ClassNotFoundException {
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		String line = "";
		while((line = bf.readLine()) != null) {
			users.add(new User(line));
			System.out.println(line + " has been added");
		}
	}

	/**
	 * Load all the connections from a file.
	 * @param fileName
	 *    The name of the file that will be loaded.
	 * @throws IOException
	 *    Indicates that the file is not found.
	 */
	public void loadAllConnections(String fileName) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		String line = "";
		String[] fromTo;
		while((line = bf.readLine()) != null) {
			int indexFrom = -1;
			int indexTo = -1;
			fromTo = line.split(", ");
			for(User user: users) {
				if(user == null) continue;
				if(user.getUserName().equalsIgnoreCase(fromTo[0])) {
					indexFrom = user.getIndexPos();
				}
				if(user.getUserName().equalsIgnoreCase(fromTo[1])) {
					indexTo = user.getIndexPos();
				}
			}
			if(indexFrom == -1 || indexTo == -1) continue;
			connections[indexFrom][indexTo] = true;
			System.out.println(line + " added");
		}
	}

	/**
	 * The array with the shortest path to every node will be given in the form
	 *   of a 2d Integer array. Floyd Warshall algorithm will achieve this.
	 * @param from
	 *    The name of the user that is starts from.
	 * @param to
	 *    The name of the user that it ends at.
	 * @return
	 *    Returns a 2d array with traversal of a graph in a next path graph.
	 */
	private Integer[][] floydWarshallTraversal(String from, String to) {
		//Floyd-Warshall Algorithm based from the CSE214 Homework html example - wikipedia.
		int v = MAX_USERS;
		double[][] dist = new double[MAX_USERS][MAX_USERS];
		Integer[][] next = new Integer[MAX_USERS][MAX_USERS];
		for(int i = 0; i < MAX_USERS; i++) {
			for(int j = 0; j < MAX_USERS; j++) {
				if(i == j) {
					dist[i][j] = 0;
				} else if (connections[i][j]) {
					dist[i][j] = 1;
				} else {
					dist[i][j] = Double.POSITIVE_INFINITY;
				}
				next[i][j] = j;
			}
		}
		for(int k = 0; k < MAX_USERS; k++) {
			for(int i = 0; i < MAX_USERS; i++) {
				for(int j = 0; j < MAX_USERS; j++) {
					if(dist[i][k] + dist[k][j] < dist[i][j]) {
						// if the sum of the two is less than the current value then it is the current shortest path
						dist[i][j] = dist[k][j] + dist[i][k];
						next[i][j] = next[i][k];
					}
				}
			}
		}
		return next;
	}

	/**
	 * Update the follower and following count of all users.
	 */
	private void updateAllFollowerFollowing() {
		for(User u: users) {
			if(u == null) {
				continue;
			}
			int connectionIndex = u.getIndexPos();
			int followerCount = 0;
			int followingCount = 0;
			for (int i = 0; i < connections.length; i++) {
				if (connections[connectionIndex][i]) followingCount++;
			}
			for (int i = 0; i < connections.length; i++) {
				if (connections[i][connectionIndex]) followerCount++;
			}
			u.setFollowerCount(followerCount);
			u.setFollowingCount(followingCount);
		}
	}

}
