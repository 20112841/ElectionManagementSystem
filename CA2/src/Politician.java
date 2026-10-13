public class Politician {

    private String id;
    private String name;
    private String dob;        // Changed from int to String
    private String party;
    private String county;
    private String photoUrl;

    // Array of candidacies
    private ElectionCandidate[] candidacies;
    private int candidacyCount;

    public Politician(String id, String name, String dob, String party, String county, String photoUrl) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.party = party;
        this.county = county;
        this.photoUrl = photoUrl;

        this.candidacies = new ElectionCandidate[10];
        this.candidacyCount = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getParty() { return party; }
    public String getCounty() { return county; }
    public String getPhotoUrl() { return photoUrl; }

    // Add a candidacy to this politician
    public void addCandidacy(ElectionCandidate c) {
        if (candidacyCount == candidacies.length) {
            ElectionCandidate[] temp = new ElectionCandidate[candidacies.length * 2];
            for (int i = 0; i < candidacies.length; i++) temp[i] = candidacies[i];
            candidacies = temp;
        }
        candidacies[candidacyCount++] = c;
    }

    public ElectionCandidate[] getCandidacies() { return candidacies; }
    public int getCandidacyCount() { return candidacyCount; }
}
