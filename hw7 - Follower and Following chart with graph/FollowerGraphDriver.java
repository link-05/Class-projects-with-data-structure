import java.io.*;
import java.util.*;
public class FollowerGraphDriver {
	public static boolean running = true;
	public static Scanner in = new Scanner(System.in);
	public static FollowerGraph graph = new FollowerGraph();

	public static void main(String[] args) {
		try {
			//From the CSE214 Homework 7 html.
			FileInputStream file = new FileInputStream("FollowGraph.obj");
			ObjectInputStream inStream = new ObjectInputStream(file);
			graph = (FollowerGraph) inStream.readObject();
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

	public static void printMenu() {
		System.out.println("\n(U) Add User");
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

	public static void addUser() {
		System.out.print("Enter the username: ");
		String username = in.nextLine();
		graph.addUser(username);
	}

	public static void addConnection() {
		System.out.print("Please enter the source of the connection add: ");
		String from = in.nextLine();
		System.out.print("Please enter the dest of the connection add: ");
		String to = in.nextLine();
		graph.addConnection(from, to);
	}

	public static void loadAllUsers() throws IOException, ClassNotFoundException {
		System.out.print("Enter the file name: ");
		String filename = in.nextLine();
		graph.loadAllUsers(filename);
	}

	public static void loadAllConnections() throws IOException, ClassNotFoundException {
		System.out.print("Enter the file name: ");
		String filename = in.nextLine();
		graph.loadAllConnections(filename);
	}

	public static void printAllUsers() {
		boolean runningPrint = true;
		String header = "Users: \n" +
		  String.format("%-23s%-24s%-24s\n", "User Name", "Followers", "Following");
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

	public static void printAllLoops() {
		ArrayList<String> loops = graph.findAllLoops();
		int lLen = loops.size();
		if(lLen == 0) {
			System.out.println("There are no loops.");
			return;
		}
		System.out.println("There are " + lLen + " loops:");
		for(int i = 0; i < lLen; i++) {
			System.out.println(loops.get(i));
		}
	}

	public static void removeUser() {
		System.out.print("Enter the username: ");
		String username = in.nextLine();
		graph.removeUser(username);
	}

	public static void removeConnection() {
		System.out.print("Please enter the source of the connection to remove: ");
		String from = in.nextLine();
		System.out.print("Please enter the dest of the connection to remove: ");
		String to = in.nextLine();
		graph.removeConnection(from, to);
	}

	public static void findShortestPath() {
		System.out.print("Please enter the desired source: ");
		String from = in.nextLine();
		System.out.print("Please enter the desired destination: ");
		String to = in.nextLine();
		System.out.println();
		System.out.println(graph.shortestPath(from, to));
	}

	public static void findAllPaths() {
		System.out.print("Please enter the desired source: ");
		String from = in.nextLine();
		System.out.print("Please enter the desired destination: ");
		String to = in.nextLine();
		System.out.println();
		System.out.println(graph.allPaths(from, to));
	}




}
