package controller;

import sorting.algorithm.MergeSort;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the components of the display and its functionalities
 *
 * @author Pratik Gurung
 */
public class Main extends Application {

    /**
     * The minimum height of a rectangle
     */
    private static final double MIN_HEIGHT = 10;

    /**
     * The maximum height of a rectangle
     */
    private static final double MAX_HEIGHT = 650;

    /**
     * The minimum number of rectangles to display for sorting
     */
    private static final int MIN_ARR_SIZE = 5;

    /**
     * The maximum number of rectangles to display for sorting
     */
    private static final int MAX_ARR_SIZE = 128;

    /**
     * The width of the display of rectangles that are to be sorted
     */
    private static final double SORTING_DISPLAY_WIDTH = 1000;

    /**
     * The default color of the rectangles being displayed for sorting
     */
    private static final Color DEFAULT_COLOR = Color.SKYBLUE;

    /**
     * The color of the rectangles being compared while sorting
     */
    private static final Color COMPARISON_COLOR = Color.RED;

    /**
     * The color of the rectangle whose height is being changed
     */
    private static final Color CHANGE_COLOR = Color.GREEN;

    /**
     * The background color of the menu
     */
    private static final Color MENU_COLOR = Color.rgb(13, 26, 38);

    /**
     * The number of elements to be sorted
     */
    private static int arrSize = (MAX_ARR_SIZE + MIN_ARR_SIZE) / 2;

    /**
     * The width of each rectangle that is to be sorted
     */
    private static double width = (SORTING_DISPLAY_WIDTH - 2 * (arrSize - 1)) / arrSize;

    /**
     * The number of threads to be used to sort for the multi-threading section
     */
    private static int numOfThreads =  1;

    /**
     * An array of random doubles ranging from <code>MIN_HEIGHT</code> to <code>MAX_HEIGHT</code> with each double
     * representing the height of the rectangle at the corresponding index
     */
    private double[] heights;

    /**
     * Where the application is started
     *
     * @param primaryStage The stage on which the application is being displayed
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        generateRandomHeights();
        root.setBottom(createSortingDisplay());
        root.setTop(createMenu(root));

        primaryStage.setTitle("Concurrent Merge Sort Visualizer");
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    /**
     * Generates the user options
     *
     * @param root The parent of all nodes on the display
     *
     * @return The menu bar with user options
     */
    private HBox createMenu(BorderPane root) {
        HBox menu = new HBox();

        menu.setPadding(new Insets(30, 30, 30, 30));
        menu.setSpacing(30);
        menu.setBackground(new Background(new BackgroundFill(MENU_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        menu.getChildren().addAll(createNewArrayButton(root), createArrSizeSliderWrapper(root),
                createNumOfThreadsSpinnerWrapper(), createExecuteMergeSortButton(root));

        return menu;
    }

    /**
     * Generates a button that allows the user to generate a random array of size <code>arrSize</code>
     *
     * @param root The parent of all nodes on the display
     *
     * @return The button that allows the user to generate a random array of size <code>arrSize</code>
     */
    private Button createNewArrayButton(BorderPane root) {
        Button newArr = new Button("Generate New Array");

        newArr.setOnMouseClicked(event -> {
            ((HBox) root.getTop()).getChildren().get(2).setDisable(false);
            ((HBox) root.getTop()).getChildren().get(3).setDisable(false);

            generateRandomHeights();
            root.setBottom(createSortingDisplay());
        });

        return newArr;
    }

    /**
     * Instantiates <code>heights</code> to an array of random doubles ranging from <code>MIN_HEIGHT</code> to
     * <code>MAX_HEIGHT</code>
     */
    private void generateRandomHeights() {
        heights = new double[arrSize];

        for(int i = 0; i < arrSize; i++) {
            double height = Math.random() * MAX_HEIGHT;
            if(height < MIN_HEIGHT)
                height += MIN_HEIGHT;
            heights[i] = height;
        }
    }

    /**
     * Generates the section in the menu where the user can adjust the number of rectangles to be sorted
     *
     * @param root The parent of all nodes on the display
     *
     * @return The section in the menu where the user can adjust the number of rectangles to be sorted
     */
    private HBox createArrSizeSliderWrapper(BorderPane root) {
        HBox arrSizeSliderWrapper = new HBox();

        arrSizeSliderWrapper.setSpacing(10);
        Label arrSizeSliderText = new Label("Change Array Size:");
        arrSizeSliderText.setTextFill(Color.WHITE);
        arrSizeSliderWrapper.getChildren().addAll(arrSizeSliderText, createArrSizeSlider(root));

        return arrSizeSliderWrapper;
    }

    /**
     * Generates a slider that allows the user to change the size of the array to be sorted
     *
     * @param root The parent of all nodes on the display
     *
     * @return The slider that allows the user to change the size of the array to be sorted
     */
    private Slider createArrSizeSlider(BorderPane root) {
        Slider arrSizeSlider = new Slider(MIN_ARR_SIZE, MAX_ARR_SIZE, arrSize);

        arrSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ((HBox) root.getTop()).getChildren().get(2).setDisable(false);
            ((HBox) root.getTop()).getChildren().get(3).setDisable(false);

            arrSize = newValue.intValue();
            width = (SORTING_DISPLAY_WIDTH - 2 * (arrSize - 1)) / arrSize;

            generateRandomHeights();
            root.setBottom(createSortingDisplay());
        });

        return arrSizeSlider;
    }

    /**
     * Generates the section in the menu where the user can adjust the number of threads used to execute the merge sort
     *
     * @return The section in the menu where the user can adjust the number of threads used to execute the merge sort
     */
    private HBox createNumOfThreadsSpinnerWrapper() {
        HBox numOfThreadsSpinnerWrapper = new HBox();

        numOfThreadsSpinnerWrapper.setSpacing(10);
        Label numOfThreadsSpinnerText = new Label("Change Number of Threads:");
        numOfThreadsSpinnerText.setTextFill(Color.WHITE);
        numOfThreadsSpinnerWrapper.getChildren().addAll(numOfThreadsSpinnerText, createNumOfThreadsSpinner());

        return numOfThreadsSpinnerWrapper;
    }

    /**
     * Generates a spinner that allows the user to change the number of threads used to sort the rectangles
     *
     * @return The spinner that allows the user to change the number of threads used to sort the rectangles
     */
    private Spinner<Integer> createNumOfThreadsSpinner() {
        Spinner<Integer> numOfThreadsSpinner = new Spinner<Integer> (
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 128) {
                    @Override
                    public void decrement(int i) {
                        if(getValue() != null) {
                            setValue(getValue() / 2);
                            numOfThreads = getValue();
                        }
                    }

                    @Override
                    public void increment(int i) {
                        if(getValue() != null) {
                            setValue(getValue() * 2);
                            numOfThreads = getValue();
                        }
                    }
                });

        return numOfThreadsSpinner;
    }

    /**
     * Generates a button that runs merge sort on the rectangles in the sorting sections
     *
     * @param root The parent of all nodes on the display
     *
     * @return The button that runs merge sort on the rectangles in the sorting sections
     */
    private Button createExecuteMergeSortButton(BorderPane root) {
        Button executeMergeSortButton = new Button("Execute Merge Sort");

        executeMergeSortButton.setOnMouseClicked(event -> {
            // The list of rectangles being displayed to be sorted
            ObservableList<Node> recs = ((HBox) root.getBottom()).getChildren();

            // A separate thread is required to show the progress of the sort because the JavaFX thread can't run
            // animations and update the screen at the same time
            Service<Void> sortingThread = new Service<Void>() {

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {

                        @Override
                        protected Void call() {
                            // The user is not allowed to change the number of threads or click the "Execute Merge Sort"
                            // button while sorting is in progress
                            ((HBox) root.getTop()).getChildren().get(2).setDisable(true);
                            ((HBox) root.getTop()).getChildren().get(3).setDisable(true);

                            // The array of rectangle heights is sorted and a list of animations for the rectangles
                            // on the screen is created as the sorting executes
                            List<List<List<double[]>>> animations = new ArrayList<List<List<double[]>>>();
                            runMergeSort(heights, numOfThreads, animations);
                            runAnimations(recs, animations);

                            return null;
                        }
                    };
                }
            };

            sortingThread.setOnSucceeded(workerStateEvent -> {
                ((HBox) root.getTop()).getChildren().get(2).setDisable(false);
                ((HBox) root.getTop()).getChildren().get(3).setDisable(false);
            });

            sortingThread.restart();
        });

        return executeMergeSortButton;
    }

    /**
     * Executes merge sort on the inputted array and keeps track of the steps so as to create the visual effects on the
     * rectangles on the screen as they are sorted
     *
     * @param heights The array of the heights of the rectangles being displayed
     * @param numberOfThreads The number of threads to be used while sorting
     * @param animations The list of animations to be executed on the rectangles to show the steps of merge sort
     */
    private void runMergeSort(double[] heights, int numberOfThreads, List<List<List<double[]>>> animations) {
        // Merge sort splits the input into separate sections to sorted separately and then merged, so this list keeps
        // track of the starting index of each section of the input
        List<List<Integer>> starts = new ArrayList<List<Integer>>();
        starts.add(new ArrayList<Integer>());

        // The number of rectangles separating the start of each section created by the first stage of merge sort
        int interval;
        if(numberOfThreads > arrSize) {
            interval = 1;
        } else {
            interval = (int) Math.ceil((double) arrSize / (double) numberOfThreads);
        }

        /*
            The first stage of concurrent merge sort is to sort each separate section of the input with an individual
            thread. Then each subsequent stage consists of merging the separate sections such that the number of
            separately sorted sections is halved until the input is fully sorted. For the purposes of visualization,
            each stage in the process is represented as a time level in the animations list in the form of a list.
            Each separate section of the input at a given time level is represented as a list which is nested inside of
            the list corresponding to the time level at which the section (location) exists. Each list of locations
            holds arrays of doubles with each array representing some step in the process of merge sort (such as the
            comparison of two values, or the changing of some value in the input).
         */
        List<List<double[]>> firstTimeLevel = new ArrayList<List<double[]>>();
        animations.add(firstTimeLevel);

        // main.Sorting the separate sections of the input as defined by the number of threads being used
        for(int i = 0; i < arrSize - interval; i += interval) {
            List<double[]> location = new ArrayList<double[]>();
            firstTimeLevel.add(location);
            int start = i;
            int end = (i + interval - 1) > (arrSize - 1) ? arrSize - 1: i + interval - 1;
            starts.get(0).add(start);
            MergeSort.mergeSort(heights, start, end, location);
        }

        List<double[]> location = new ArrayList<double[]>();
        firstTimeLevel.add(location);
        int start = (arrSize - interval) >= 0 ? arrSize - interval : 0;
        int end = arrSize - 1;
        starts.get(0).add(start);
        MergeSort.mergeSort(heights, start, end, location);

        runMerge(heights, numberOfThreads / 2, starts, animations);
    }

    /**
     * Merges the separately sorted sections of the inputted array
     *
     * @param heights The array of the heights of the rectangles being displayed
     * @param numberOfThreads The number of threads to be used while sorting
     * @param starts The starting index of each separately sorted section at each time level of merge sort
     * @param animations The list of animations to be executed on the rectangles to show the steps of merge sort
     */
    private void runMerge(double[] heights, int numberOfThreads, List<List<Integer>> starts, List<List<List<double[]>>> animations) {
        // A counter to keep track of the time level
        int inc = 0;

        while(numberOfThreads >= 1) {
            // Each round of merging occurs at separate separate time levels
            List<List<double[]>> timeLevel = new ArrayList<List<double[]>>();
            animations.add(timeLevel);

            // Each time level has a different set of sorted sections
            List<Integer> startLevel = new ArrayList<Integer>();
            startLevel.add(0);
            starts.add(startLevel);

            List<Integer> currentStartLevel = starts.get(inc);

            // Executing merge for the current time level and generating the corresponding animations
            for(int iteration = 0; iteration < currentStartLevel.size() - 1; iteration += 2) {
                List<double[]> location = new ArrayList<double[]>();
                timeLevel.add(location);

                int startIndex = currentStartLevel.get(iteration);
                int middleIndex = currentStartLevel.get(iteration + 1) - 1;
                int endIndex;

                if(iteration == currentStartLevel.size() - 2) {
                    endIndex = arrSize - 1;
                } else {
                    endIndex = currentStartLevel.get(iteration + 2) - 1;
                    startLevel.add(endIndex + 1);
                }

                MergeSort.merge(heights, startIndex, middleIndex, endIndex, location);
            }

            inc++;

            // The number of separately sorted sections is halved after ever stage of sorting
            numberOfThreads /= 2;
        }
    }

    /**
     * Executes the animations generated while actually running merge sort to provide the steps visually
     *
     * @param recs The list of rectangles being displayed and sorted
     * @param animations The steps to execute on <code>recs</code> in order to visually display the steps of merge sort
     */
    private void runAnimations(ObservableList<Node> recs, List<List<List<double[]>>> animations) {
        // The amount of time to pause between each step of animations
        long delay = 1000 / arrSize;

        // Time levels must occur in order
        for(int timeLevelIndex = 0; timeLevelIndex < animations.size(); timeLevelIndex++) {
            List<List<double[]>> timeLevel = animations.get(timeLevelIndex);

            int maxEvents = findMaxEvents(timeLevel);

            // Displaying each step of merge sort at the corresponding location of the input
            for(int eventIndex = 0; eventIndex < maxEvents; eventIndex++) {
                for(int locationIndex = 0; locationIndex < timeLevel.size(); locationIndex++) {
                    List<double[]> location = timeLevel.get(locationIndex);

                    if(eventIndex < location.size()) {
                        double[] event = location.get(eventIndex);

                        switch ((int) event[0]) {
                            case 1:
                                ((Rectangle) recs.get((int) event[1])).setFill(DEFAULT_COLOR);
                                ((Rectangle) recs.get((int) event[2])).setFill(DEFAULT_COLOR);
                                break;
                            case 2:
                                ((Rectangle) recs.get((int) event[1])).setFill(COMPARISON_COLOR);
                                ((Rectangle) recs.get((int) event[2])).setFill(COMPARISON_COLOR);
                                break;
                            case 3:
                                ((Rectangle) recs.get((int) event[1])).setFill(CHANGE_COLOR);
                                ((Rectangle) recs.get((int) event[2])).setFill(CHANGE_COLOR);
                                break;
                            case 4:
                                ((Rectangle) recs.get((int) event[1])).setHeight(event[2]);
                                break;
                        }
                    }
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finds the largest number of events that occur at any location of a given time level
     *
     * @param timeLevel The given time level
     *
     * @return The largest number of events that occur at any location of a given time level
     */
    private int findMaxEvents(List<List<double[]>> timeLevel) {
        int max = 0;

        for(List<double[]> locations : timeLevel) {
            if (locations.size() > max) {
                max = locations.size();
            }
        }

        return max;
    }

    /**
     * Generates the display of the rectangles that are to be sorted
     *
     * @return The display of the rectangles that are to be sorted
     */
    private HBox createSortingDisplay() {
        HBox sortingDisplay = new HBox();
        sortingDisplay.setPadding(new Insets(0, 0, 50, 0));
        sortingDisplay.setSpacing(2);
        sortingDisplay.setMaxHeight(MAX_HEIGHT);
        sortingDisplay.setAlignment(Pos.BASELINE_CENTER);

        for(int i = 0; i < arrSize; i++) {
            Rectangle rec = new Rectangle(width, heights[i], DEFAULT_COLOR);
            sortingDisplay.getChildren().add(rec);
        }

        return sortingDisplay;
    }

    /**
     * Where the application is run
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
