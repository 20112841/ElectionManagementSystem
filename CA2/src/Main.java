import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ElectionSystem system = new ElectionSystem();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== IRISH ELECTION SYSTEM ===");
            System.out.println("1. Add Politician");
            System.out.println("2. Add Election");
            System.out.println("3. Add Candidate to Election");
            System.out.println("4. Search Politicians by ID");
            System.out.println("5. Search Elections by ID");
            System.out.println("6. Show All Politicians");
            System.out.println("7. Show All Elections");
            System.out.println("8. Save Data");
            System.out.println("9. Load Data");
            System.out.println("10. Delete Politician");
            System.out.println("11. Delete Election");
            System.out.println("12. Delete Candidate from Election");
            System.out.println("0. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("DOB (year): ");
                    String dob = sc.nextLine();
                    System.out.print("Party: ");
                    String party = sc.nextLine();
                    System.out.print("County: ");
                    String county = sc.nextLine();
                    System.out.print("Photo URL: ");
                    String url = sc.nextLine();

                    system.addPolitician(
                            new Politician(id, name, dob, party, county, url)
                    );
                    System.out.println("Politician added.");
                }

                case 2 -> {
                    System.out.print("Election ID: ");
                    String id = sc.nextLine();
                    System.out.print("Type (GENERAL, LOCAL, EUROPEAN, PRESIDENTIAL): ");
                    ElectionType type = ElectionType.valueOf(sc.nextLine());
                    System.out.print("Location: ");
                    String location = sc.nextLine();
                    System.out.print("Year: ");
                    int year = sc.nextInt();
                    System.out.print("Seats: ");
                    int seats = sc.nextInt();
                    sc.nextLine();

                    system.addElection(
                            new Election(id, type, location, year, seats)
                    );
                    System.out.println("Election added.");
                }

                case 3 -> {
                    System.out.print("Politician ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Election ID: ");
                    String eid = sc.nextLine();
                    System.out.print("Party at election: ");
                    String party = sc.nextLine();
                    System.out.print("Votes: ");
                    int votes = Integer.parseInt(sc.nextLine());
                    system.addCandidateToElection(pid, eid, party, votes);

                    System.out.println("Candidate added.");
                }

                case 4 -> {
                    System.out.print("Enter Politician ID to search: ");
                    String id = sc.nextLine();

                    Politician p = system.getPoliticianById(id);

                    if (p != null) {
                        System.out.println("Found Politician:");
                        System.out.println("ID: " + p.getId());
                        System.out.println("Name: " + p.getName());
                        System.out.println("DOB: " + p.getDob());
                        System.out.println("Party: " + p.getParty());
                        System.out.println("County: " + p.getCounty());
                        System.out.println("Photo URL: " + p.getPhotoUrl());
                    } else {
                        System.out.println("Politician not found.");
                    }
                }

                case 5 -> {
                    System.out.print("Enter Election ID to search: ");
                    String id = sc.nextLine();

                    Election e = system.getElectionById(id);

                    if (e != null) {
                        System.out.println("Election Found:");
                        System.out.println("ID: " + e.getId());
                        System.out.println("Type: " + e.getType());
                        System.out.println("Year: " + e.getYear());
                        System.out.println("Location: " + e.getLocation());
                        System.out.println("Seats: " + e.getSeats());

                        ElectionCandidate[] cands = e.getCandidates();
                        system.sortCandidatesByVotes(cands);

                        System.out.println("Candidates (sorted by votes):");
                        for (int i = 0; i < e.getCandidateCount(); i++) {
                            ElectionCandidate c = cands[i];
                            System.out.println(
                                    c.getPoliticianId() + " (" + c.getPartyAtElection() + ") - Votes: " + c.getVotes()
                            );
                        }
                    } else {
                        System.out.println("Election not found.");
                    }
                }

                case 6 -> {
                    Politician[] all = system.getAllPoliticians();
                    system.sortPoliticiansByName(all);
                    System.out.println("All Politicians:");
                    for (Politician p : all) {
                        System.out.println(
                                p.getId() + " - " +
                                        p.getName() + " (" +
                                        p.getParty() + ", " +
                                        p.getCounty() + ")"
                        );
                    }
                }

                case 7 -> {
                    Election[] all = system.getAllElections();
                    system.sortElectionsByYear(all, true); // ascending
                    System.out.println("All Elections:");
                    for (Election e : all) {
                        System.out.println(
                                e.getId() + " - " +
                                        e.getType() + " " +
                                        e.getYear() + " (" +
                                        e.getLocation() + ")"
                        );
                    }
                }

                case 8 -> {
                    system.saveToFile("elections.txt");
                    System.out.println("Data saved.");
                }

                case 9 -> {
                    system.loadFromFile("elections.txt");
                    System.out.println("Data loaded.");
                }

                // ✅ New delete options

                case 10 -> {
                    System.out.print("Enter Politician ID to delete: ");
                    String id = sc.nextLine();
                    system.deletePolitician(id);
                    System.out.println("Politician deleted (if existed).");
                }

                case 11 -> {
                    System.out.print("Enter Election ID to delete: ");
                    String id = sc.nextLine();
                    system.deleteElection(id);
                    System.out.println("Election deleted (if existed).");
                }

                case 12 -> {
                    System.out.print("Enter Election ID: ");
                    String eid = sc.nextLine();
                    System.out.print("Enter Politician ID of candidate to delete: ");
                    String pid = sc.nextLine();
                    system.deleteCandidateFromElection(pid, eid);
                    System.out.println("Candidate deleted (if existed).");
                }

                case 0 -> {
                    running = false;
                    System.out.println("Exiting system.");
                }

                default -> System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}


