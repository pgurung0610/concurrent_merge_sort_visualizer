package sorting.algorithm;

import java.util.List;

/**
 * This class contains the implementation of merge sort and concurrent merge sort
 *
 * @author Pratik Gurung
 */
public class MergeSort {

    /**
     * Executes merge sort on the inputted array
     *
     * @param heights The input
     * @param left The start index of the sub array to be sorted
     * @param right The end index of the sub array to be sorted
     * @param events This list keeps track of each step of merge sort at the current section of the input at the current
     *               time level for visualization purposes
     */
    public static void mergeSort(double[] heights, int left, int right, List<double[]> events) {
        if (left < right) {

            int middle = (left + right) / 2;

            // First sort the two halves
            mergeSort(heights, left, middle, events);
            mergeSort(heights, middle + 1, right, events);

            // Then merge the two sorted halves
            merge(heights, left, middle, right, events);
        }
    }

    /**
     * Merges the separately sorted sections of the input as defined by the boundary integer inputs
     *
     * @param heights The input
     * @param left The start index of the first sorted section
     * @param middle The end index of the first sorted section
     * @param right The end index of the second sorted section
     * @param events This list keeps track of each step of merge sort at the current section of the input at the current
     *               time level for visualization purposes
     */
    public static void merge(double[] heights, int left, int middle, int right, List<double[]> events) {
        // Finding the sizes of two sorted sections to be merged
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        // Creating temporary arrays for the two sorted sections
        double leftSection[] = new double [leftSize];

        for (int i = 0; i < leftSize; ++i)
            leftSection[i] = heights[left + i];

        double rightSection[] = new double [rightSize];

        for (int j = 0; j < rightSize; ++j)
            rightSection[j] = heights[middle + 1+ j];

        int i = 0, j = 0;
        int k = left;

        // Adjusting unsorted values
        while (i < leftSize && j < rightSize)
        {
            events.add(new double[]{2, left + i, middle + 1 + j});
            events.add(new double[]{1, left + i, middle + 1 + j});

            if (leftSection[i] <= rightSection[j])
            {
                events.add(new double[]{3, k, k});
                events.add(new double[]{1, k, k});

                heights[k] = leftSection[i];
                events.add(new double[]{4, k, leftSection[i]});

                i++;
            }
            else
            {
                events.add(new double[]{3, k, k});
                events.add(new double[]{1, k, k});

                heights[k] = rightSection[j];
                events.add(new double[]{4, k, rightSection[j]});

                j++;
            }
            k++;
        }

        // Copy the remaining elements of leftSection[] if any
        while (i < leftSize)
        {
            events.add(new double[]{3, k, k});
            events.add(new double[]{1, k, k});

            heights[k] = leftSection[i];
            events.add(new double[]{4, k, leftSection[i]});

            i++;
            k++;
        }

        // Copy the remaining elements of rightSection[] if any
        while (j < rightSize)
        {
            events.add(new double[]{3, k, k});
            events.add(new double[]{1, k, k});

            heights[k] = rightSection[j];
            events.add(new double[]{4, k, rightSection[j]});

            j++;
            k++;
        }
    }

    /**
     * The implementation of merge sort using multi-threading
     *
     * @param heights The array of the heights of the rectangles being displayed
     * @param numberOfThreads The number of threads to be used while sorting
     * @param events This list keeps track of each step of merge sort at the current section of the input at the current
     *               time level for visualization purposes
     */
    public static void concurrentMergeSort(double[] heights, int numberOfThreads, List<double[]> events) {
        if(heights == null) {
            throw new IllegalArgumentException("IllegalArgumentException: heights was not instantiated");
        }

        try {
            concurrentMergeSort(heights, 0, heights.length - 1, numberOfThreads, events);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: heights was not instantiated");
        }
    }

    /**
     * The actual implementation of merge sort using multi-threading
     *
     * @param heights The array of the heights of the rectangles being displayed
     * @param left The start index of the sub array to be sorted
     * @param right The end index of the sub array to be sorted
     * @param numberOfThreads The number of threads to be used while sorting
     * @param events This list keeps track of each step of merge sort at the current section of the input at the current
     *               time level for visualization purposes
     *
     * @throws IllegalArgumentException In case the input array was not instantiated
     */
    public static void concurrentMergeSort(double[] heights, int left, int right, int numberOfThreads, List<double[]> events) throws IllegalArgumentException {
        if(heights == null) {
            throw new IllegalArgumentException("IllegalArgumentException: heights was not instantiated");
        }

        int middle = (left + right) / 2;

        if(numberOfThreads > 1 && left <= middle && middle <= right) {
            try {
                // An individual thread is created to sort each half of the sub array
                Thread thread1 = new Thread(new Sorting(heights, left, middle, numberOfThreads / 2, events));
                Thread thread2 = new Thread(new Sorting(heights, middle + 1, right, numberOfThreads / 2, events));

                thread1.start();
                thread2.start();

                // Must wait for both halves to be sorted before merging the two
                thread1.join();
                thread2.join();

                merge(heights, left, middle, right, events);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: A thread was interrupted");
            }
        } else {
            mergeSort(heights, left, right, events);
        }
    }
}
