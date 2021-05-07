package phonebook;

public class BinarySearch {
    public static int binarySearch(String elem, String[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (elem.equals(array[mid])) {
                return mid;
            } else if (array[mid].compareTo(elem) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}