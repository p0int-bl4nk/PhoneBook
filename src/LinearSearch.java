package phonebook;

public class LinearSearch {
    public static int linearSearch(String[] findData, String[] directoryData) {
        int count = 0;
        for (String f : findData) {
            for (String d : directoryData) {
                if (d.equals(f)) {
                    count++;
                }
            }
        }
        return count;
    }
}
