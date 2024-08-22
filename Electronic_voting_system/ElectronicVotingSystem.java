import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

class Voter {
    private String id;
    private String name;
    private String password;
    
    public Voter(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

public class ElectronicVotingSystem {

    private static final Map<String, Voter> voters = new HashMap<>();
    private static final Map<String, String> votes = new HashMap<>();
    private static final String[] CANDIDATES = {"Alice", "Bob", "Charlie"};

    // Method to generate random voter ID
    public static String generateRandomId() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt(10000000));
    }

    // Method to add a voter
    public static void addVoter(String id, String name, String password) {
        if (voters.containsKey(id)) {
            System.out.println("Voter with ID: " + id + " already exists.");
        } else {
            voters.put(id, new Voter(id, name, password));
            System.out.println("Voter " + name + " added successfully with ID: " + id);
        }
    }

    // Method to authenticate a voter
    public static boolean authenticateVoter(String id, String password) {
        Voter voter = voters.get(id);
        return voter != null && voter.getPassword().equals(password);
    }

    // Method to cast a vote
    public static void castVote(String id, String candidate) {
        String candidateLower = candidate.trim().toLowerCase();

        for (String c : CANDIDATES) {
            if (c.toLowerCase().equals(candidateLower)) {
                if (!votes.containsKey(id)) {
                    votes.put(id, c);
                    System.out.println("Vote cast successfully for " + c);
                    return;
                } else {
                    System.out.println("You have already voted!");
                    return;
                }
            }
        }

        System.out.println("Invalid candidate!");
    }

    // Method to calculate and display results
    public static void calculateResults() {
        Map<String, Integer> results = new HashMap<>();

        for (String candidate : votes.values()) {
            results.put(candidate, results.getOrDefault(candidate, 0) + 1);
        }

        System.out.println("Results:");
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }

    // Method to delete a voter
    public static void deleteVoter(String id) {
        if (voters.containsKey(id)) {
            voters.remove(id);
            votes.remove(id);
            System.out.println("Voter with ID: " + id + " has been deleted.");
        } else {
            System.out.println("No voter found with ID: " + id);
        }
    }

    // Main method to drive the voting system
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nElectronic Voting System:");
            System.out.println("1. Add Voter");
            System.out.println("2. Cast Vote");
            System.out.println("3. Calculate Results");
            System.out.println("4. Delete Voter");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    String id = generateRandomId();
                    System.out.println("Generated Voter ID: " + id);
                    System.out.print("Enter Voter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    addVoter(id, name, password);
                    break;

                case "2":
                    System.out.print("Enter Voter ID: ");
                    String voterId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String voterPassword = scanner.nextLine();

                    if (authenticateVoter(voterId, voterPassword)) {
                        System.out.println("Authentication successful! Welcome, " + voters.get(voterId).getName());
                        System.out.println("Candidates: Alice, Bob, Charlie");
                        System.out.print("Enter your choice: ");
                        String candidate = scanner.nextLine();

                        castVote(voterId, candidate);
                    } else {
                        System.out.println("Authentication failed! Invalid voter ID or password.");
                    }
                    break;

                case "3":
                    calculateResults();
                    break;

                case "4":
                    System.out.print("Enter Voter ID to delete: ");
                    String deleteId = scanner.nextLine();
                    deleteVoter(deleteId);
                    break;

                case "5":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
