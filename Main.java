package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            String[] findData = readFile("/home/stellarloony/find.txt").split("\r\n");
            String[] directoryData = readFile("/home/stellarloony/directory.txt").split("\r\n");
            int[] phone = new int[directoryData.length];
            for (int i = 0; i < directoryData.length; i++) {
                String[] name = directoryData[i].split(" ");
                phone[i] = Integer.parseInt(name[0]);
                if (name.length > 2) {
                    directoryData[i] = name[1] + " " + name[2];
                } else {
                    directoryData[i] = name[1];
                }
            }

            long limit = linearSearch(findData, directoryData);
            bubbleSortJumpSearch(findData, directoryData, limit);
            quickSortBinarySearch(findData, directoryData);
            hashTable(findData, directoryData, phone);

        } catch (IOException e) {
            System.out.printf("An error occurred: %s", e.getMessage());
            e.printStackTrace();
        }
    }

    static long linearSearch(String[] namesToFind, String[] directoryToSearch) {
        System.out.println("Start searching (linear search)...");
        long startTime = System.currentTimeMillis();
        int foundCount = LinearSearch.linearSearch(namesToFind, directoryToSearch);
        long stopTime = System.currentTimeMillis() - startTime;
        printInfo(0L, stopTime, foundCount,namesToFind.length, TYPE.LINEAR_SEARCH);
        return stopTime;
    }

    static void bubbleSortJumpSearch(String[] namesToFind, String[] directoryToSearch, long limit) {
        System.out.println("\n\nStart searching (bubble sort + jump search)...");
        String[] directoryDataForBubbleSort = directoryToSearch.clone();
        long[] data = BubbleSort.bubbleSort(directoryDataForBubbleSort, limit);
        boolean sortDone = data[0] == 1L;
        long sortTime = data[1];
        long searchTime;
        if (!sortDone) {
            long startTime = System.currentTimeMillis();
            int foundCount = LinearSearch.linearSearch(namesToFind, directoryToSearch);
            searchTime = System.currentTimeMillis() - startTime;
            printInfo(sortTime, searchTime, foundCount, namesToFind.length, TYPE.BUBBLE_SORT_AND_JUMP_SEARCH);
            System.out.print(" - STOPPED, moved to linear search");
        } else {
            int foundCount = 0;
            long startTime = System.currentTimeMillis();
            for (String name : namesToFind) {
                if ( JumpSearch.jumpSearch(directoryDataForBubbleSort, name) > -1) {
                    foundCount++;
                }
            }
            searchTime = System.currentTimeMillis() - startTime;
            printInfo(sortTime, searchTime, foundCount, namesToFind.length, TYPE.BUBBLE_SORT_AND_JUMP_SEARCH);
        }
        System.out.printf("\nSearching time: %s", getTime(searchTime));

    }

    static void quickSortBinarySearch(String[] namesToFind, String[] directoryToSearch) {
        System.out.println("\n\nStart searching (quick sort + binary search)...");
        String[] directoryDataForQuickSort = directoryToSearch.clone();
        long startTime = System.currentTimeMillis();
        QuickSort.quickSort(directoryDataForQuickSort, 0, directoryDataForQuickSort.length - 1);
        long sortTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        int foundCount = 0;
        for (String name : namesToFind) {
            if (BinarySearch.binarySearch(name, directoryDataForQuickSort) > -1) {
                foundCount++;
            }
        }
        long searchTime = System.currentTimeMillis() - startTime;
        printInfo(sortTime, searchTime, foundCount, namesToFind.length, TYPE.QUICK_SORT_AND_BINARY_SEARCH);
    }

    static void hashTable(String[] namesToFind, String[] directoryToSearch, int[] phone) {
        System.out.println("\n\nStart searching (hash table)...");
        HashTable<Integer, String> hash = new HashTable<>(directoryToSearch.length * 2);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < directoryToSearch.length; i++) {
            hash.put(directoryToSearch[i], phone[i]);
        }
        long hashTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        int foundCount = hash.search(namesToFind);
        long searchTime = System.currentTimeMillis() - startTime;
        printInfo(hashTime, searchTime, foundCount, namesToFind.length, TYPE.HASH_TABLE);
    }

    static String getTime(long time) {
        StringBuilder t = new StringBuilder();
        t.append(time / 60000).append(" min. ");
        time %= 60000;
        t.append(time / 1000).append(" sec. ");
        time %= 1000;
        t.append(time).append(" ms.");
        return t.toString();
    }

    static void printInfo(long time1, long time2, int foundCount, int searchLength, TYPE T) {
        System.out.printf("Found %d / %d entries. Time taken: %s", foundCount, searchLength, getTime(time1 + time2));
        if (T == TYPE.LINEAR_SEARCH) {
            System.out.printf("\nSearching time: %s", getTime(time2));
        } else if (T == TYPE.BUBBLE_SORT_AND_JUMP_SEARCH) {
            System.out.printf("\nSorting time: %s", getTime(time1));
        } else if (T == TYPE.QUICK_SORT_AND_BINARY_SEARCH) {
            System.out.printf("\nSorting time: %s", getTime(time1));
            System.out.printf("\nSearching time: %s", getTime(time2));
        } else {
            System.out.printf("\nCreating time: %s", getTime(time1));
            System.out.printf("\nSearching time: %s", getTime(time2));
        }
    }

    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

}
