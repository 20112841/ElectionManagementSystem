public class Election {

    private String id;
    private ElectionType type;
    private String location;
    private int year;
    private int seats;

    private ElectionCandidate[] candidates;
    private int candidateCount;

    public Election(String id, ElectionType type, String location, int year, int seats) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.year = year;
        this.seats = seats;
        this.candidates = new ElectionCandidate[10];
        this.candidateCount = 0;
    }


    public void addCandidate(ElectionCandidate c) {
        if (candidateCount == candidates.length) {
            ElectionCandidate[] temp = new ElectionCandidate[candidates.length * 2];
            for (int i = 0; i < candidates.length; i++) temp[i] = candidates[i];
            candidates = temp;
        }
        candidates[candidateCount++] = c;
    }

    public ElectionType getType() {
        return type;
    }

    public void setType(ElectionType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public ElectionCandidate[] getCandidates() {
        return candidates;
    }

    public void setCandidates(ElectionCandidate[] candidates) {
        this.candidates = candidates;
    }

    public int getCandidateCount() {
        return candidateCount;
    }

    public void setCandidateCount(int candidateCount) {
        this.candidateCount = candidateCount;
    }

}