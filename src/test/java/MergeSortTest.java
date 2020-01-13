import sorting.algorithm.MergeSort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure that methods in the <code>main.MergeSort</code> calss are working properly
 *
 * @author Pratik Gurung
 */
public class MergeSortTest {

    /**
     * Tests whether <code>main.MergeSort.mergeSort()</code> works as it should
     */
    @Test
    public void testMergeSort() {
        double[] empty = {};
        MergeSort.mergeSort(empty, 0, empty.length - 1, new ArrayList<>());
        double[] oneEl = {0};
        MergeSort.mergeSort(oneEl, 0, oneEl.length - 1, new ArrayList<>());
        double[] negatives = {-1, 0, -3};
        MergeSort.mergeSort(negatives, 0, negatives.length - 1, new ArrayList<>());

        double[] sorted = {1, 2, 3};
        MergeSort.mergeSort(sorted, 0, sorted.length - 1, new ArrayList<>());
        double[] unsorted = {1, 3, 2};
        MergeSort.mergeSort(unsorted, 0, unsorted.length - 1, new ArrayList<>());

        double[] random10 = randomDoubleArray(10);
        MergeSort.mergeSort(random10, 0, random10.length - 1, new ArrayList<>());
        double[] random20 = randomDoubleArray(20);
        MergeSort.mergeSort(random20, 0, random20.length - 1, new ArrayList<>());
        double[] random50 = randomDoubleArray(50);
        MergeSort.mergeSort(random50, 0, random50.length - 1, new ArrayList<>());
        double[] random100 = randomDoubleArray(100);
        MergeSort.mergeSort(random100, 0, random100.length - 1, new ArrayList<>());
        double[] random1000 = randomDoubleArray(1000);
        MergeSort.mergeSort(random1000, 0, random1000.length - 1, new ArrayList<>());

        assertAll(
                () -> assertTrue(isSorted(empty), doubleArrAsString(empty)),
                () -> assertTrue(isSorted(negatives), doubleArrAsString(negatives)),
                () -> assertTrue(isSorted(oneEl), doubleArrAsString(oneEl)),
                () -> assertTrue(isSorted(sorted), doubleArrAsString(sorted)),
                () -> assertTrue(isSorted(unsorted), doubleArrAsString(unsorted)),
                () -> assertTrue(isSorted(random10), doubleArrAsString(random10)),
                () -> assertTrue(isSorted(random20), doubleArrAsString(random20)),
                () -> assertTrue(isSorted(random50), doubleArrAsString(random50)),
                () -> assertTrue(isSorted(random100), doubleArrAsString(random100)),
                () -> assertTrue(isSorted(random1000), doubleArrAsString(random1000))
        );
    }

    /**
     * Tests whether <code>main.MergeSort.merge()</code> works as it should
     */
    @Test
    public void testMerge() {
        double[] oneEl = {0};
        MergeSort.merge(oneEl, 0, 0, oneEl.length - 1, new ArrayList<>());
        double[] negatives = {-1, 0, -3};
        MergeSort.merge(negatives, 0, 1, negatives.length - 1, new ArrayList<>());

        double[] sorted = {1, 2, 3};
        MergeSort.merge(sorted, 0, 1, sorted.length - 1, new ArrayList<>());

        double[] tenEl = {3, 5, 9, 13, 15, 1, 2, 3, 7, 12};
        MergeSort.merge(tenEl, 0, 4, tenEl.length - 1, new ArrayList<>());

        double[] fifteenEl = {2, 3, 5, 6, 9, 13, 15, 22, 2, 3, 7, 10, 12, 15, 27};
        MergeSort.merge(fifteenEl, 0, 7, fifteenEl.length - 1, new ArrayList<>());

        assertAll(
                () -> assertTrue(isSorted(negatives), doubleArrAsString(negatives)),
                () -> assertTrue(isSorted(oneEl), doubleArrAsString(oneEl)),
                () -> assertTrue(isSorted(sorted), doubleArrAsString(sorted)),
                () -> assertTrue(isSorted(tenEl), doubleArrAsString(tenEl)),
                () -> assertTrue(isSorted(fifteenEl), doubleArrAsString(fifteenEl))
        );
    }

    /**
     * Tests whether <code>main.MergeSort.concurrentMergeSort()</code> works as it should
     */
    @Test
    public void testConcurrentMergeSort() {
        double[] empty = {};
        MergeSort.concurrentMergeSort(empty, 4, new ArrayList<>());
        double[] oneEl = {0};
        MergeSort.concurrentMergeSort(oneEl, 4, new ArrayList<>());
        double[] negatives = {-1, 0, -3};
        MergeSort.concurrentMergeSort(negatives, 4, new ArrayList<>());

        double[] sorted = {1, 2, 3};
        MergeSort.concurrentMergeSort(sorted, 4, new ArrayList<>());
        double[] unsorted = {1, 3, 2};
        MergeSort.concurrentMergeSort(unsorted, 4, new ArrayList<>());

        double[] random10 = randomDoubleArray(10);
        MergeSort.concurrentMergeSort(random10, 4, new ArrayList<>());
        double[] random20 = randomDoubleArray(20);
        MergeSort.concurrentMergeSort(random20, 4, new ArrayList<>());
        double[] random50 = randomDoubleArray(50);
        MergeSort.concurrentMergeSort(random50, 4, new ArrayList<>());
        double[] random100 = randomDoubleArray(100);
        MergeSort.concurrentMergeSort(random100, 4, new ArrayList<>());

        double[] random1000OneThread = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000OneThread, 1, new ArrayList<>());
        double[] random1000TwoThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000TwoThreads, 2, new ArrayList<>());
        double[] random1000FourThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000FourThreads, 4, new ArrayList<>());
        double[] random1000EightThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000EightThreads, 8, new ArrayList<>());
        double[] random1000SixteenThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000SixteenThreads, 16, new ArrayList<>());
        double[] random1000ThirtyTwoThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000ThirtyTwoThreads, 32, new ArrayList<>());
        double[] random1000SixtyFourThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000SixtyFourThreads, 64, new ArrayList<>());
        double[] random1000OneHundredAndTwentyEightThreads = randomDoubleArray(1000);
        MergeSort.concurrentMergeSort(random1000OneHundredAndTwentyEightThreads, 128, new ArrayList<>());

        assertAll(
                () -> assertTrue(isSorted(empty), doubleArrAsString(empty)),
                () -> assertTrue(isSorted(negatives), doubleArrAsString(negatives)),
                () -> assertTrue(isSorted(oneEl), doubleArrAsString(oneEl)),
                () -> assertTrue(isSorted(sorted), doubleArrAsString(sorted)),
                () -> assertTrue(isSorted(unsorted), doubleArrAsString(unsorted)),
                () -> assertTrue(isSorted(random10), doubleArrAsString(random10)),
                () -> assertTrue(isSorted(random20), doubleArrAsString(random20)),
                () -> assertTrue(isSorted(random50), doubleArrAsString(random50)),
                () -> assertTrue(isSorted(random100), doubleArrAsString(random100)),
                () -> assertTrue(isSorted(random1000OneThread), doubleArrAsString(random1000OneThread)),
                () -> assertTrue(isSorted(random1000TwoThreads), doubleArrAsString(random1000TwoThreads)),
                () -> assertTrue(isSorted(random1000FourThreads), doubleArrAsString(random1000FourThreads)),
                () -> assertTrue(isSorted(random1000EightThreads), doubleArrAsString(random1000EightThreads)),
                () -> assertTrue(isSorted(random1000SixteenThreads), doubleArrAsString(random1000SixteenThreads)),
                () -> assertTrue(isSorted(random1000ThirtyTwoThreads), doubleArrAsString(random1000ThirtyTwoThreads)),
                () -> assertTrue(isSorted(random1000SixtyFourThreads), doubleArrAsString(random1000SixtyFourThreads)),
                () -> assertTrue(isSorted(random1000OneHundredAndTwentyEightThreads), doubleArrAsString(random1000OneHundredAndTwentyEightThreads))
        );
    }

    /**
     * Generates a random array of doubles of the specified length
     *
     * @param length The number of doubles in the outputted array
     *
     * @return A random array of doubles of the specified length
     */
    private double[] randomDoubleArray(int length) {
        double[] arr = new double[length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Math.random() * (new Double(length));
        return arr;
    }

    /**
     * Returns whether or not the inputted array is sorted
     *
     * @param testArr The array to be tested
     *
     * @return True if the inputted array is sorted, false otherwise
     */
    private boolean isSorted(double[] testArr) {
        for(int i = 0; i < testArr.length - 1; i++)
            if (testArr[i] > testArr[i + 1])
                return false;

        return true;
    }

    /**
     * Returns the <code>String</code> representation of the inputted array of doubles
     *
     * @param arr The array of doubles whose String representation is being generated
     *
     * @return The <code>String</code> representation of the inputted array of doubles
     */
    private String doubleArrAsString(double[] arr) {
        String output = "[";

        for(int i = 0; i < arr.length; i++) {
            output += arr[i];
            if(i != arr.length - 1) {
                output += ", ";
            }
        }

        output += "]";

        return  output;
    }
}
