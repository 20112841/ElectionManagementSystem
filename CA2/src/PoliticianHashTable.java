public class PoliticianHashTable {

    private Politician[] table;
    private int size;

    public PoliticianHashTable(int size) {
        this.size = size;
        this.table = new Politician[size];
    }

    // hash function
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash % size;
    }

    // Insert
    public void put(Politician politician) {
        int index = hash(politician.getId());

        while (table[index] != null) {
            index = (index + 1) % size;
        }

        table[index] = politician;
    }

    //  lookup by ID
    public Politician get(String id) {
        int index = hash(id);

        while (table[index] != null) {
            if (table[index].getId().equals(id)) {
                return table[index];
            }
            index = (index + 1) % size;
        }

        return null;
    }

    // Delete using null replacement
    public void remove(String id) {
        int index = hash(id);

        while (table[index] != null) {
            if (table[index].getId().equals(id)) {
                table[index] = null;
                return;
            }
            index = (index + 1) % size;
        }
    }
}
