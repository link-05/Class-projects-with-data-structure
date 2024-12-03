import java.io.*;
import java.util.*;
/**
 * The <code>FollowGraphDriver</code> class implements
 *   the <code>FollowGraph</code> and runs all the menu option
 *   to display the graph and change it.
 *
 * @author Kevin Lin
 *    Stony Brook ID: 116145453
 *    Email: kevin.lin.24@stonybrook.edu
 *    Programming assignment: hw7
 *    Course: CSE214.01
 *    Recitation: R04
 *    TA's: Veronica Oreshko, Kevin Zheng
 */
public class FollowGraphDriver {
	public static boolean running = true;
	public static Scanner in = new Scanner(System.in);
	public static FollowGraph graph = new FollowGraph();

	public static void main(String[] args) {
		try {
			//From the CSE214 Homework 7 html.
			FileInputStream file = new FileInputStream("FollowGraph.obj");
			ObjectInputStream inStream = new ObjectInputStream(file);
			graph = (FollowGraph) inStream.readObject();
			int userCount = 0;
			for(User u : graph.getUsers()) {
				if(u != null) {
					userCount++;
				}
			}
			User.setUserCount(userCount);
		} catch(Exception e) {
			System.out.println("File not found. Creating new graph.");
		}
		while(running) {
			printMenu();
			System.out.print("Enter a selection: ");
			String selection = in.nextLine();
			controlHub(selection);
		}
	}

	/**
	 * Prints the menu for the user to see.
	 */
	public static void printMenu() {
		System.out.println("\n************ Menu ************");
		System.out.println("(U) Add User");
		System.out.println("(C) Add Connection");
		System.out.println("(AU) Load All Users");
		System.out.println("(AC) Load All Connections");
		System.out.println("(P) Print All Users");
		System.out.println("(L) Print All Loops");
		System.out.println("(RU) Remove User");
		System.out.println("(RC) Remove Connection");
		System.out.println("(SP) Find Shortest Path");
		System.out.println("(AP) Find All Paths");
		System.out.println("(Q) Quit\n");
	}

	/**
	 * Redirect the user based on their selection.
	 * @param selection
	 *    The String for the menu option they chose.
	 */
	public static void controlHub(String selection) {
		try {
		switch(selection.toUpperCase()) {
			case "U":
				addUser();
				break;
			case "C":
				addConnection();
				break;
			case "AU":
				loadAllUsers();
				break;
			case "AC":
				loadAllConnections();
				break;
			case "P":
				printAllUsers();
				break;
			case "L":
				printAllLoops();
				break;
			case "RU":
				removeUser();
				break;
			case "RC":
				removeConnection();
				break;
			case "SP":
				findShortestPath();
				break;
			case "AP":
				findAllPaths();
				break;
			case "Q":
				running = false;
				//From the CSE214 Homework 7 html.
				FileOutputStream file = new FileOutputStream("FollowGraph.obj");
				ObjectOutputStream outStream = new ObjectOutputStream(file);
				outStream.writeObject(graph);
				System.out.println("FollowGraph object saved into file FollowGraph.obj");
				break;
			default:
				System.out.println("Invalid selection. Please try again.");
		}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a user to the graph.
	 */
	public static void addUser() {
		System.out.print("Enter the username: ");
		String username = in.nextLine();
		graph.addUser(username);
	}

	/**
	 * Adds a connection between two users.
	 */
	public static void addConnection() {
		System.out.print("Please enter the source of the connection add: ");
		String from = in.nextLine();
		System.out.print("Please enter the dest of the connection add: ");
		String to = in.nextLine();
		graph.addConnection(from, to);
	}

	/**
	 * Load all the users from a file.
	 * @throws IOException
	 *    Indicates that the file is not found.
	 * @throws ClassNotFoundException
	 *    Indicates that the class is not found.
	 */
	public static void loadAllUsers() throws IOException, ClassNotFoundException {
		System.out.print("Enter the file name: ");
		String filename = in.nextLine();
		graph.loadAllUsers(filename);
	}

	/**
	 * Load all the connections from a file.
	 * @throws IOException
	 *    Indicates that the file is not found.
	 * @throws ClassNotFoundException
	 *    Indicates that the class is not found.
	 */
	public static void loadAllConnections() throws IOException, ClassNotFoundException {
		System.out.print("Enter the file name: ");
		String filename = in.nextLine();
		graph.loadAllConnections(filename);
	}

	/**
	 * Print all the users in the graph based on selection.
	 */
	public static void printAllUsers() {
		boolean runningPrint = true;
		String header = "Users:\n" +
		  String.format("%-31s%-21s%s\n", "User Name", "Number of Followers", "Number of Following");
		while(runningPrint) {
			System.out.println("(SA) Sort Users by Name");
			System.out.println("(SB) Sort Users by Number Of Followers");
			System.out.println("(SC) Sort Users by Number of Following");
			System.out.println("(Q) Quit");
			System.out.print("Enter a selection: ");
			String selection = in.nextLine();
			switch (selection.toUpperCase()) {
				case "SA":
					System.out.println(header);
					graph.printAllUsers(new NameComparator());
					break;
				case "SB":
					System.out.println(header);
					graph.printAllUsers(new FollowerComparator());
					break;
				case "SC":
					System.out.println(header);
					graph.printAllUsers(new FollowingComparator());
					break;
				case "Q":
					runningPrint = false;
					break;
				default:
					System.out.println("Invalid selection. Please try again.");
			}
		}
	}

	/**
	 * Print all the loops in the graph.
	 */
	public static void printAllLoops() {
		ArrayList<String> loops = graph.findAllLoops();
		int lLen = loops.size();
		if(lLen == 0) {
			System.out.println("There are no loops.");
			return;
		}
		if(lLen == 1) {
			System.out.println("There is " + lLen + " loop:");
		} else {
			System.out.println("There are a total of " + lLen + " loops:");
		}
		for(int i = 0; i < lLen; i++) {
			System.out.println(loops.get(i));
		}
	}

	/**
	 * Remove a user from the graph.
	 */
	public static void removeUser() {
		System.out.print("Enter the username: ");
		String username = in.nextLine();
		graph.removeUser(username);
	}

	/**
	 * Remove a connection between two users.
	 */
	public static void removeConnection() {
		ArrayList<User> users = graph.getUsers();
		int uLen = users.size();
		boolean fromNotFound = true;
		boolean toNotFound = true;
		String from = "";
		String to = "";
		while(fromNotFound) {
			System.out.print("Please enter the source of the connection to remove: ");
			from = in.nextLine();
			for (int i = 0; i < uLen; i++) {
				if (graph.getUsers().get(i).getUserName().equalsIgnoreCase(from)) {
					fromNotFound = false;
					break;
				}
			}
			if (from.equals("")) {
				System.out.println("You can not leave this field empty.");
				System.out.println("There is no user with this name, Please choose a valid user! ");
			} else if (fromNotFound) {
				System.out.println("There is no user with this name, Please choose a valid user! ");
			}
		}
		while(toNotFound) {
			System.out.print("Please enter the dest of the connection to remove: ");
			to = in.nextLine();
			for (int i = 0; i < uLen; i++) {
				if (graph.getUsers().get(i).getUserName().equalsIgnoreCase(to)) {
					toNotFound = false;
					break;
				}
			}
			if (from.equals("")) {
				System.out.println("You can not leave this field empty.");
				System.out.println("There is no user with this name, Please choose a valid user! ");
			} else if (toNotFound) {
				System.out.println("There is no user with this name, Please choose a valid user! ");
			}
		}
		graph.removeConnection(from, to);
	}

	/**
	 * Find the shortest path between two users.
	 */
	public static void findShortestPath() {
		System.out.print("Please enter the desired source: ");
		String from = in.nextLine();
		System.out.print("Please enter the desired destination: ");
		String to = in.nextLine();
		System.out.print("The shortest path is:");
		String shortestPath = graph.shortestPath(from, to);
		System.out.println(shortestPath.substring(0, shortestPath.length() - 1));
		System.out.println("The number of users in this path is: " + shortestPath.substring(shortestPath.length() - 1));
	}

	/**
	 * Find all the paths between two users.
	 */
	public static void findAllPaths() {
		ArrayList<User> users = graph.getUsers();
		int uLen = users.size();
		boolean fromNotFound = true;
		boolean toNotFound = true;
		String from = "";
		String to = "";
		while(fromNotFound) {
			System.out.print("Please enter the desired source: ");
			from = in.nextLine();
			for (int i = 0; i < uLen; i++) {
				if (graph.getUsers().get(i).getUserName().equalsIgnoreCase(from)) {
					fromNotFound = false;
					break;
				}
			}
			if (from.equals("")) {
				System.out.println("You can not leave this field empty.");
				System.out.println("There is no user with this name, Please choose a valid user!");
			} else if (fromNotFound) {
				System.out.println("There is no user with this name, Please choose a valid user!");
			}
		}
		while(toNotFound) {
			System.out.print("Please enter the desired destination: ");
			to = in.nextLine();
			for (int i = 0; i < uLen; i++) {
				if (graph.getUsers().get(i).getUserName().equalsIgnoreCase(to)) {
					toNotFound = false;
					break;
				}
			}
			if (from.equals("")) {
				System.out.println("You can not leave this field empty.");
				System.out.println("There is no user with this name, Please choose a valid user!");
			} else if (toNotFound) {
				System.out.println("There is no user with this name, Please choose a valid user!");
			}
		}
		ArrayList<String> paths = graph.allPaths(from, to);
		int lLen = paths.size();
		if(lLen == 0) {
			System.out.println("There are no paths.");
			return;
		}
		System.out.println("There are a total of " + lLen + " paths:");
		for(int i = 0; i < lLen; i++) {
			System.out.println(paths.get(i));
		}
	}




}
