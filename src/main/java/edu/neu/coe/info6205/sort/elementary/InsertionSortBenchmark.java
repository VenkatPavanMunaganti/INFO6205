package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.UnaryOperator;

public class InsertionSortBenchmark {
    public InsertionSortBenchmark(int runs, int n) {
        this.runs = runs;
        this.n = n;
    }

    private void runBenchmarks() {
        System.out.println("Insertion sort for N=" + this.n);
        InsertionSort<Integer> insertionSort = new InsertionSort<>();
        Random random = new Random();
        Integer[] arr = Utilities.fillRandomArray(Integer.class, random, this.n, r -> r.nextInt());

//        //Sorter for randomly ordered array
        runBenchmark("Insertion sort for random ordered array", null, insertionSort, arr);

        //Sorter for ordered array
        runBenchmark("Insertion sort for ordered array", a-> {
            Arrays.sort(a);
            return a;
        }, insertionSort, arr);

        //Sorter for partially ordered array
        runBenchmark("Insertion sort for partially ordered array", a-> {
            Arrays.sort(a, 0, a.length/2);
            return a;
        }, insertionSort, arr);

        //Sorter for reverse ordered array
        runBenchmark("Insertion sort for reverse ordered array", a-> {
            Arrays.sort(a, Collections.reverseOrder());
            return a;
        }, insertionSort, arr);
    }

    private void runBenchmark(String description, UnaryOperator<Integer[]> preFun, SortWithHelper insertionSort, Integer[] arr){
        System.out.println(description);
        SorterBenchmark<Integer> randomBenchmark = new SorterBenchmark<>(Integer.class,preFun,insertionSort,null,arr,this.runs,timeLoggers);
        randomBenchmark.run(this.n);
    }

    private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
    };

    public static void main(String[] args) {
        new InsertionSortBenchmark(100, 250).runBenchmarks();
        new InsertionSortBenchmark(100, 500).runBenchmarks();
        new InsertionSortBenchmark(100, 1000).runBenchmarks();
        new InsertionSortBenchmark(100, 2000).runBenchmarks();
        new InsertionSortBenchmark(100, 4000).runBenchmarks();
        new InsertionSortBenchmark(100, 8000).runBenchmarks();
        new InsertionSortBenchmark(100, 16000).runBenchmarks();
    }

    private final int runs;
    private final int n;
}
