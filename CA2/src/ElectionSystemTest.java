import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElectionSystemTest {

    private ElectionSystem system;

    @BeforeEach
    public void setup() {
        system = new ElectionSystem();
    }


       //1. Add Politician

    @Test
    public void testAddPolitician() {
        Politician p = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        system.addPolitician(p);

        Politician retrieved = system.getPoliticianById("P1");
        assertNotNull(retrieved);
        assertEquals("Leo Varadkar", retrieved.getName());
        assertEquals("1979-01-18", retrieved.getDob());
    }


      // 2. Add Election

    @Test
    public void testAddElection() {
        Election e = new Election("E1", ElectionType.GENERAL, "Ireland", 2020, 160);
        system.addElection(e);

        Election retrieved = system.getElectionById("E1");
        assertNotNull(retrieved);
        assertEquals(2020, retrieved.getYear());
    }


    //   3. Add Candidate to Election

    @Test
    public void testAddCandidate() {
        Politician p = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        system.addPolitician(p);
        Election e = new Election("E1", ElectionType.GENERAL, "Ireland", 2020, 160);
        system.addElection(e);

        system.addCandidateToElection("P1", "E1", "Fine Gael", 500000);

        ElectionCandidate[] candidates = e.getCandidates();
        assertEquals(1, e.getCandidateCount());
        assertEquals("P1", candidates[0].getPoliticianId());
        assertEquals(500000, candidates[0].getVotes());

        // Politician should also have this candidacy
        assertEquals(1, p.getCandidacyCount());
    }


    //   4. Delete Politician

    @Test
    public void testDeletePolitician() {
        Politician p = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        system.addPolitician(p);
        system.deletePolitician("P1");

        assertNull(system.getPoliticianById("P1"));
    }


    //   5. Delete Election

    @Test
    public void testDeleteElection() {
        Election e = new Election("E1", ElectionType.GENERAL, "Ireland", 2020, 160);
        system.addElection(e);
        system.deleteElection("E1");

        assertNull(system.getElectionById("E1"));
    }


     //  6. Search Politicians by Name

    @Test
    public void testSearchPoliticiansByName() {
        Politician p1 = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        Politician p2 = new Politician("P2", "Micheál Martin", "1960-07-01", "Fianna Fáil", "Cork", "url");
        system.addPolitician(p1);
        system.addPolitician(p2);

        // Search for "Leo"
        Politician[] results = system.searchPoliticiansByName("Leo");
        assertEquals(1, results.length);
        assertEquals("Leo Varadkar", results[0].getName());

        // Search for "Micheál"
        results = system.searchPoliticiansByName("Micheál");
        assertEquals(1, results.length);
        assertEquals("Micheál Martin", results[0].getName());

        // Search for "" should return all
        results = system.searchPoliticiansByName("");
        assertEquals(2, results.length);
    }



   //    7. Sort Politicians by Name

    @Test
    public void testSortPoliticiansByName() {
        Politician p1 = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        Politician p2 = new Politician("P2", "Micheál Martin", "1960-07-01", "Fianna Fáil", "Cork", "url");
        system.addPolitician(p1);
        system.addPolitician(p2);

        Politician[] arr = system.searchPoliticiansByName(""); // all
        system.sortPoliticiansByName(arr);

        assertEquals("Leo Varadkar", arr[0].getName());
        assertEquals("Micheál Martin", arr[1].getName());
    }


      // 8. Candidate Sorting by Votes

    @Test
    public void testSortCandidatesByVotes() {
        Politician p1 = new Politician("P1", "Leo Varadkar", "1979-01-18", "Fine Gael", "Dublin", "url");
        Politician p2 = new Politician("P2", "Micheál Martin", "1960-07-01", "Fianna Fáil", "Cork", "url");
        system.addPolitician(p1);
        system.addPolitician(p2);

        Election e = new Election("E1", ElectionType.GENERAL, "Ireland", 2020, 160);
        system.addElection(e);

        system.addCandidateToElection("P1", "E1", "Fine Gael", 500000);
        system.addCandidateToElection("P2", "E1", "Fianna Fáil", 600000);

        ElectionCandidate[] cands = e.getCandidates();
        system.sortCandidatesByVotes(cands);

        assertEquals("P2", cands[0].getPoliticianId()); // highest votes first
        assertEquals("P1", cands[1].getPoliticianId());
    }
}
