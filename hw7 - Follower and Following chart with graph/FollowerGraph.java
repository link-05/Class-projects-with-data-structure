import java.io.*;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.StringBuilder;

/**
 * The <code>FollowerGraph</code> class is the data structure
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
public class FollowerGraph implements Serializable {
	private ArrayList<User> users;
	private static final int MAX_USERS = 100;
	private boolean[][] connections;

	/**
	 * Returns an instance of Follower Graph.
	 */
	public FollowerGraph() {
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
		int count = User.getUserCount();
		for(int i = 0; i < count; i++) {
			if(users.get(i) == null) {
				users.add(i, new User(user, i));
				return;
			}
		}
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
		if(next[userFromIndex][userToIndex] == null) {
			return "";
		}
		path.append(users.get(userFromIndex) + " -> ");
		while(userFromIndex != userToIndex) {
			userFromIndex = next[userFromIndex][userToIndex];
			path.append(users.get(userFromIndex) + " -> ");
		}
		return path.toString().substring(0, path.toString().length() - 4);
	}


	public ArrayList<String> allPaths(String from, String to) {
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
		//Use Floyd Warshall's shortest path algorithm to create a 2d array that has all the paths from and to.
		Integer[][] next = floydWarshallTraversal(from, to);
		int v = User.getUserCount();
		ArrayList<String> paths = new ArrayList<String>();
		StringBuilder path = new StringBuilder();
		for(int i = 0; i < v; i++) {
			if(next[userFromIndex][i] == null) {
				continue;
			}
			if (userFromIndex != i) {
				while(userFromIndex != i) {
					userFromIndex = next[userFromIndex][i];
					path.append(users.get(userFromIndex).getUserName());
				}
				paths.add(path.toString());
			}
		}
		return paths;
	}

	public void printAllUsers(Comparator type) {
		ArrayList<User> tempArr = new ArrayList<User>();
		updateAllFollowerFollowing();
		for(User u: users) {
			if(u == null) {
				continue;
			}
			tempArr.add(u);
		}
		tempArr.sort(type);
		for(User u: tempArr) {
			System.out.printf("%-32s%-23s%-23s\n", u.getUserName(), u.getFollowerCount(), u.getFollowingCount());
		}
	}

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

	public ArrayList<String> findAllLoops() {
		ArrayList<String> loops = new ArrayList<String>();
		//Loops will be found by using Depth First Traversal
		int count = User.getUserCount();
		for(int i = 0; i < count; i++) {
			boolean[] marked = new boolean[count];
			ArrayList<String> path = new ArrayList<String>();
			DFT(this, loops, path, i, i, marked);
		}
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
	private static void DFT(FollowerGraph g, ArrayList<String> loops, ArrayList<String> path, int mainTarget,
	  int currentUser, boolean[] marked) {
		//This is based off the CSE214, DFT method in the unit 10 lecture slides.
		int[] connections = fillConnection(g, currentUser);
		int i;
		int nextNeighbor;
		marked[currentUser] = true;
		for (i=0; i<connections.length; i++) {
			nextNeighbor = connections[i];
			if(nextNeighbor == mainTarget) {
				//This will add the path to the loop
				StringBuilder sb = new StringBuilder();
				for(String user: path) {
					sb.append(user + " -> ");
				}
				loops.add(path.toString().substring(0, path.toString().length() - 4));
				continue;
			}
			if (!marked[nextNeighbor]) {
				DFT(g, loops, path, mainTarget, nextNeighbor, marked);
				path.removeLast();
			}
		}
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
	private static int[] fillConnection(FollowerGraph g, int user) {
		int count = User.getUserCount();
		int[] connection = new int[count];
		int index = 0;
		for(int i = 0; i < count; i++) {
			if(g.getConnections()[user][i]) {
				connection[index] = i;
				index++;
			}
		}
		return connection;
	}

	/**
	 * Returns the connections of the <code>FollowerGraph</code>.
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
				} else if(user.getUserName().equalsIgnoreCase(fromTo[1])) {
					indexTo = user.getIndexPos();
				}
			}
			if(indexFrom == -1 || indexTo == -1) throw new NoSuchObjectException(line + " does not exist.");
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
		int v = User.getUserCount();
		double[][] dist = new double[v][v];
		Integer[][] next = new Integer[v][v];
		for(int i = 0; i < v; i++) {
			for(int j = 0; j < v; j++) {
				if(connections[i][j]) {
					dist[i][j] = 1;
					next[i][j] = j;
				} else {
					dist[i][j] = Double.POSITIVE_INFINITY;
					next[i][j] = null;
				}
			}
		}
		for(int k = 1; k < v; k++) {
			for(int i = 1; i < v; i++) {
				for(int j = 1; j < v; j++) {
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
