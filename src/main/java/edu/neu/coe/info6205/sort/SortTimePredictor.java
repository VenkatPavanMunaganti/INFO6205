package edu.neu.coe.info6205.sort;

import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SorterBenchmark;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.util.Utilities;


import java.util.Random;

public class SortTimePredictor {

    public SortTimePredictor(int runs, int n, SortWithHelper<Integer> sortMethod, Helper<Integer> helper, String sortMethodName) {
        this.runs = runs;
        this.n = n;
        this.sortMethod = sortMethod;
        this.sortMethodName = sortMethodName;
        this.helper = helper;
    }

    public void runBenchmarks() {
        System.out.println("Sort method used: " + this.sortMethodName);
        System.out.println("Sort for N=" + this.n);
        Random random = new Random();
        Integer[] arr = Utilities.fillRandomArray(Integer.class, random, this.n, r -> r.nextInt());

        //Sorter for randomly ordered array
        System.out.println("Random ordered array");
        SorterBenchmark<Integer> randomBenchmark = new SorterBenchmark<>(Integer.class, null, this.sortMethod, arr, this.runs, timeLoggers);
        randomBenchmark.run(this.n);
        System.out.println(this.helper.showStats());
    }

    public static void main(String args[]) {
        Helper<Integer> helper;

        try {
            for (int nElements = 10000; nElements <= 1400000; nElements = nElements * 2) {
                helper = HelperFactory.create("Heap sort helper", nElements, false, Config.load());
                new SortTimePredictor(100, nElements, new HeapSort<>(helper), helper, "Heap Sort").runBenchmarks();
            }
            for (int nElements = 10000; nElements <= 1400000; nElements = nElements * 2) {
                helper = HelperFactory.create("Merge Sort", nElements, true, Config.load());
                new SortTimePredictor(100, nElements, new MergeSort<>(helper), helper, "Merge Sort").runBenchmarks();
            }
            for (int nElements = 10000; nElements <= 1400000; nElements = nElements * 2) {
                helper = HelperFactory.create("Quick Sort with dual pivot", nElements, false, Config.load());
                new SortTimePredictor(100, nElements, new QuickSort_DualPivot<>(helper), helper, "Quick Sort with dual pivot").runBenchmarks();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private int runs;
    private final int n;
    private final SortWithHelper sortMethod;
    private final String sortMethodName;
    private final Helper helper;
    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
    };
}
