public class ElectionCandidate {

    private String politicianId;
    private String partyAtElection;
    private int votes;

    public ElectionCandidate(String politicianId, String partyAtElection, int votes) {
        this.politicianId = politicianId;
        this.partyAtElection = partyAtElection;
        this.votes = votes;
    }

    public String getPoliticianId() { return politicianId; }
    public String getPartyAtElection() { return partyAtElection; }
    public int getVotes() { return votes; }



}
