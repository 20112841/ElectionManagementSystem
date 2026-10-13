public class ElectionHashTable {

    private Election[] table;
    private int size;

    public ElectionHashTable(int size) {
        this.size = size;
        this.table = new Election[size];
    }

    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash % size;
    }

    public void put(Election election) {
        int index = hash(election.getId());

        while (table[index] != null) {
            index = (index + 1) % size;
        }

        table[index] = election;
    }

    public Election get(String id) {
        int index = hash(id);

        while (table[index] != null) {
            if (table[index].getId().equals(id)) {
                return table[index];
            }
            index = (index + 1) % size;
        }

        return null;
    }

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
