package phonebook;

class TableEntry<T, U> {
    U key;
    T value;
    TableEntry(U key, T value) {
        this.key = key;
        this.value = value;
    }
    public U getKey() {
        return key;
    }
    public T getValue() {
        return value;
    }
}

class HashTable<T, U> {
    TableEntry[] table;
    int scaleFactor;
    int size;

    HashTable(int size) {
        this.size = size;
        table = new TableEntry[size];
        scaleFactor = 2;
    }
    private int findKey(U key) {
        int hash = Math.abs(key.hashCode()) % size;
        while (!(table[hash] == null || table[hash].getKey().equals(key))) {
            hash = (hash + 1) % size;
            if (hash == Math.abs(key.hashCode()) % size) {
                return -1;
            }
        }
        return hash;
    }
    public boolean put(U key, T value) {
        int index = findKey(key);
        if (index == -1) {
            rehash();
            index = findKey(key);
        } else if (table[index] != null) {
            return false;
        }
        table[index] = new TableEntry<>(key, value);
        return true;
    }
    private void rehash() {
        TableEntry[] tableOld = table;
        table = new TableEntry[size * scaleFactor];
        for (var record : tableOld) {
            this.put((U) record.getKey(), (T) record.getValue());
        }
        size *= scaleFactor;
    }
    public int search(String[] find) {
        int count = 0;
        for (String key : find) {
            int hash = findKey((U) key);
            if (hash != -1 && table[hash].getKey().equals(key)) {
                count++;
            }
        }
        return count;
    }
}
