package phonebook;

public class JumpSearch {
    public static int jumpSearch(String[] ar, String value) {
        int currentRight = 0;
        int prevRight = 0;
        if (ar.length == 0) {
            return -1;
        }

        if (ar[currentRight].equals(value)) {
            return 0;
        }

        int jumpLength = (int) Math.sqrt(ar.length);
        while (currentRight < ar.length - 1) {
            currentRight = Math.min(ar.length - 1, currentRight + jumpLength);
            if (ar[currentRight].compareTo(value) >= 0) {
                break;
            }
            prevRight = currentRight;
        }
        if (currentRight == ar.length - 1 && ar[currentRight].compareTo(value) < 0) {
            return -1;
        }
        return backwardSearch(ar, prevRight, currentRight, value);
    }
    public static int backwardSearch(String[] ar, int low, int high, String value) {
        for (int i = high; i > low; i--) {
            if (ar[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
