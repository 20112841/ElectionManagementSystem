import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.*;


public class ElectionSystem {

    private Politician[] politicians;
    private int politicianCount;

    private Election[] elections;
    private int electionCount;

    private PoliticianHashTable politicianTable;
    private ElectionHashTable electionTable;

    public ElectionSystem() {
        politicians = new Politician[50];
        elections = new Election[50];
        politicianCount = 0;
        electionCount = 0;

        politicianTable = new PoliticianHashTable(101);
        electionTable = new ElectionHashTable(101);
    }


    // POLITICIANS


    public void addPolitician(Politician p) {
        if (politicianCount == politicians.length) resizePoliticians();
        politicians[politicianCount++] = p;
        politicianTable.put(p);
    }

    public Politician getPoliticianById(String id) {
        return politicianTable.get(id);
    }

    public Politician[] getAllPoliticians() {
        Politician[] result = new Politician[politicianCount];

        for (int i = 0; i < politicianCount; i++) {
            result[i] = politicians[i];
        }

        return result;
    }


    public void deletePolitician(String id) {
        for (int i = 0; i < politicianCount; i++) {
            if (politicians[i].getId().equals(id)) {

                // move everyone to the left
                for (int j = i; j < politicianCount - 1; j++) politicians[j] = politicians[j + 1];
                // reduce the count and clear the last slot
                politicians[--politicianCount] = null;
                break;
            }
        }
        politicianTable.remove(id);
    }


    //ELECTIONS


    public void addElection(Election e) {
        if (electionCount == elections.length) resizeElections();
        elections[electionCount++] = e;
        electionTable.put(e);
    }

    public Election getElectionById(String id) {
        return electionTable.get(id);
    }

    public Election[] getAllElections() {
        Election[] result = new Election[electionCount];

        for (int i = 0; i < electionCount; i++) {
            result[i] = elections[i];
        }

        return result;
    }


    public void deleteElection(String id) {
        for (int i = 0; i < electionCount; i++) {
            if (elections[i].getId().equals(id)) {

                //move everything to the left
                for (int j = i; j < electionCount - 1; j++) elections[j] = elections[j + 1];
                //refuce count and clear slot
                elections[--electionCount] = null;
                break;
            }
        }
        electionTable.remove(id);
    }


    //      CANDIDATES


    public void addCandidateToElection(String politicianId, String electionId, String partyAtElection, int votes) {
        Politician p = politicianTable.get(politicianId);
        Election e = electionTable.get(electionId);
        if (p == null || e == null) return;

        ElectionCandidate c = new ElectionCandidate(politicianId, partyAtElection, votes);
        e.addCandidate(c);
        p.addCandidacy(c);
    }

    public void deleteCandidateFromElection(String politicianId, String electionId) {
        // 1️ Find the election using the ID (hashing used internally)
        Election election = getElectionById(electionId);
        if (election == null) {
            System.out.println("Election not found.");
            return;
        }

        // 2️ Get the array of candidates and the current count
        ElectionCandidate[] candidates = election.getCandidates();
        int count = election.getCandidateCount();
        boolean removed = false;

        // 3️ Loop through candidates to find the one to delete
        for (int i = 0; i < count; i++) {
            if (candidates[i].getPoliticianId().equals(politicianId)) {
                // 4️ Shift all candidates after this one left by 1
                for (int j = i; j < count - 1; j++) {
                    candidates[j] = candidates[j + 1];
                }
                candidates[count - 1] = null; // clear last slot
                election.setCandidateCount(count - 1); // update count
                removed = true;
                break;
            }
        }

        // 5️ Print result
        if (removed) {
            System.out.println("Candidate removed from election.");
        } else {
            System.out.println("Candidate not found in this election.");
        }
    }




    // SEARCH METHODS


    public Politician[] searchPoliticiansByName(String namePart) {
        Politician[] temp = new Politician[politicianCount];
        int count = 0;

        for (int i = 0; i < politicianCount; i++) {
            if (politicians[i].getName().contains(namePart)) {
                temp[count++] = politicians[i];
            }
        }

        Politician[] results = new Politician[count];
        for (int i = 0; i < count; i++) results[i] = temp[i];
        return results;
    }

    public Politician[] searchPoliticiansByParty(String party) {
        Politician[] temp = new Politician[politicianCount];
        int count = 0;

        for (int i = 0; i < politicianCount; i++) {
            if (politicians[i].getParty().equals(party)) {
                temp[count++] = politicians[i];
            }
        }

        Politician[] results = new Politician[count];
        for (int i = 0; i < count; i++) results[i] = temp[i];
        return results;
    }

    public Election[] searchElectionsByType(ElectionType type) {
        Election[] temp = new Election[electionCount];
        int count = 0;

        for (int i = 0; i < electionCount; i++) {
            if (elections[i].getType() == type) {
                temp[count++] = elections[i];
            }
        }

        Election[] results = new Election[count];
        for (int i = 0; i < count; i++) results[i] = temp[i];
        return results;
    }

    public Election[] searchElectionsByYear(int year) {
        Election[] temp = new Election[electionCount];
        int count = 0;

        for (int i = 0; i < electionCount; i++) {
            if (elections[i].getYear() == year) {
                temp[count++] = elections[i];
            }
        }

        Election[] results = new Election[count];
        for (int i = 0; i < count; i++) results[i] = temp[i];
        return results;
    }


    // SORT METHODS - insertion sort - shifts larger elements to the right


    public void sortPoliticiansByName(Politician[] arr) {
        for (int i = 1; i < arr.length && arr[i] != null; i++) {
            Politician key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] != null && arr[j].getName().compareTo(key.getName()) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public void sortElectionsByYear(Election[] arr, boolean ascending) {
        for (int i = 1; i < arr.length && arr[i] != null; i++) {
            Election key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] != null &&
                    (ascending ? arr[j].getYear() > key.getYear() : arr[j].getYear() < key.getYear())) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public void sortCandidatesByVotes(ElectionCandidate[] arr) {
        for (int i = 1; i < arr.length && arr[i] != null; i++) {
            ElectionCandidate key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] != null && arr[j].getVotes() < key.getVotes()) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }


    //  PERSISTENCE


    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < politicianCount; i++) {
                Politician p = politicians[i];
                writer.write(String.format("POLITICIAN|%s|%s|%s|%s|%s|%s\n",
                        p.getId(), p.getName(), p.getDob(), p.getParty(),
                        p.getCounty(), p.getPhotoUrl()));
            }

            for (int i = 0; i < electionCount; i++) {
                Election e = elections[i];
                writer.write(String.format("ELECTION|%s|%s|%s|%d|%d\n",
                        e.getId(), e.getType(), e.getLocation(),
                        e.getYear(), e.getSeats()));

                ElectionCandidate[] cands = e.getCandidates();
                for (int j = 0; j < e.getCandidateCount(); j++) {
                    ElectionCandidate c = cands[j];
                    writer.write(String.format("CANDIDATE|%s|%s|%s|%d\n",
                            e.getId(), c.getPoliticianId(), c.getPartyAtElection(), c.getVotes()));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                switch (parts[0]) {
                    case "POLITICIAN":
                        Politician p = new Politician(parts[1], parts[2], parts[3],
                                parts[4], parts[5], parts[6]);
                        addPolitician(p);
                        break;
                    case "ELECTION":
                        Election e = new Election(parts[1], ElectionType.valueOf(parts[2]),
                                parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                        addElection(e);
                        break;
                    case "CANDIDATE":
                        addCandidateToElection(parts[2], parts[1], parts[3], Integer.parseInt(parts[4]));
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //    RESIZE HELPERS


    private void resizePoliticians() {
        Politician[] temp = new Politician[politicians.length * 2];
        for (int i = 0; i < politicians.length; i++) temp[i] = politicians[i];
        politicians = temp;
    }

    private void resizeElections() {
        Election[] temp = new Election[elections.length * 2];
        for (int i = 0; i < elections.length; i++) temp[i] = elections[i];
        elections = temp;
    }

}

















