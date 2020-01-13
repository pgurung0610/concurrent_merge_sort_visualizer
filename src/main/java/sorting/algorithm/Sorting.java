package sorting.algorithm;

import java.util.List;

/**
 * This class is the <code>Runnable</code> that is used to create new threads to run concurrent merge sort
 *
 * @author Pratik Gurung
 */
public class Sorting implements Runnable {
    private double[] heights;

    private int start;
    private int end;

    private int threadCount;

    private List<double[]> events;

    /**
     * Constructs an instance of this class
     *
     * @param heights The input to be sorted
     * @param start The start index of the sub array to be sorted
     * @param end The end index of the sub array to be sorted
     * @param threadCount The number of threads used to execute the sort
     * @param events This list keeps track of each step of merge sort at the current section of the input at the current
     *                 time level for visualization purposes
     */
    public Sorting(double[] heights, int start, int end, int threadCount, List<double[]> events) {
        this.heights = heights;
        this.start = start;
        this.end = end;
        this.threadCount = threadCount;
        this.events = events;
    }

    /**
     * The functionality of a thread defined by this runnable
     */
    @Override
    public void run() {
        MergeSort.concurrentMergeSort(heights, start, end, threadCount, events);
    }
}
