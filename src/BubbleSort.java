package phonebook;

public class BubbleSort {
    public static long[] bubbleSort(String[] array, long limit) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            if ( System.currentTimeMillis() - startTime > limit * 10) {
                return new long[] {0L, System.currentTimeMillis() - startTime};
            }
        }
        return new long[] {1L, System.currentTimeMillis() - startTime};
    }
}
