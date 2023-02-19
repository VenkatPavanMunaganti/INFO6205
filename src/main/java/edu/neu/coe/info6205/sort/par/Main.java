package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {
    public static void main(String[] args) {
        processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        Random random = new Random();
        HashMap<Integer, List<List<Double>>> results = new HashMap<>();
        //Different array lengths in powers of 2
        for (int a = 20; a < 24; a++) {

            //Calculating the array size
            int size = (int) Math.pow(2, a);
            int[] array = new int[size];
            ArrayList<Long> timeList = new ArrayList<>();
            List<List<Double>> runtimes = new ArrayList<>();
            System.out.println("Parallel sort for input size N="+size);

            //Different cutt off values using doubling method, starting from 10000
            for (int j = 0; j <=10; j++) {
                List<Double> runtime = new ArrayList<>();
                ParSort.cutoff = size/ (int)Math.pow(2, j);
                runtime.add((double) ParSort.cutoff);
                System.out.print(ParSort.cutoff+",");
                //Running on different threads in powers of 2 from 2^0 to 2^5 that is 1 to 32 threads
                for (int tp = 0; tp < 10; tp++) {
                    ForkJoinPool executor = new ForkJoinPool((int) Math.pow(2, tp));
                    long time;
                    long startTime = System.currentTimeMillis();

                    //10 runs to take average run time
                    for (int t = 0; t < 10; t++) {
                        //Filling the array with random values using random class
                        for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                        ParSort.sort(array, 0, array.length);
                    }
                    long endTime = System.currentTimeMillis();
                    time = (endTime - startTime);
                    timeList.add(time);
                    runtime.add((double) (time / 10));
                    System.out.print((double) (time / 10)+",");
                }
                System.out.println();
                runtimes.add(runtime);
            }
            System.out.println();
            System.out.println();
            results.put(size, runtimes);
        }

        try {
            FileOutputStream fis = new FileOutputStream("./src/parallelSort.txt");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, List<List<Double>>> e : results.entrySet()) {
                int size = e.getKey();
                List<List<Double>> runtimes = e.getValue();
                sb.append("parallel sort for input size N=" + size + "\n");
                for (List<Double> runtime : runtimes) {
                    for(var i: runtime){
                        sb.append(i+",");
                    }
                    sb.append("\n");
                }
                sb.append("\n\n");
            }
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
