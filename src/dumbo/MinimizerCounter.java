package dumbo;

import dumbo.Ordering.OrderingBase;

import java.io.*;
import java.util.*;

public class MinimizerCounter {

    private int k;
    private String kmerSetFile;
    private int pivotLen;
    private int bufSize;

    private FileReader frG;
    private BufferedReader bfrG;

    private OrderingBase ordering;

    private StringUtils stringUtils;

    private long[] minimizerCounters;


    public MinimizerCounter(int kk, String kmerSetFile, int pivotLength, int bufferSize, OrderingBase ordering) {
        this.k = kk;
        this.kmerSetFile = kmerSetFile;
        this.pivotLen = pivotLength;
        this.bufSize = bufferSize;
        this.ordering = ordering;
        this.stringUtils = new StringUtils();
        minimizerCounters = new long[(int) Math.pow(4, pivotLength)];
    }


    private long[] getMinimizersCounters() throws Exception {
        frG = new FileReader(kmerSetFile);
        bfrG = new BufferedReader(frG, bufSize);


        String describeline, line;

        int minPos;
        char[] lineCharArray;


        int minValue, minValueNormalized, currentValue, start;
        while ((describeline = bfrG.readLine()) != null) {

//            bfrG.read(lineCharArray, 0, k);
//            bfrG.read();

            line = bfrG.readLine();
            int readLen = line.length();
            if(readLen != k)
                throw new Exception("Input row is not of length k");
            lineCharArray = line.toCharArray();

            if (stringUtils.isReadLegal(lineCharArray)) {
                minPos = ordering.findSmallest(lineCharArray, 0, k);
                minValue = stringUtils.getDecimal(lineCharArray, minPos, minPos + pivotLen);
                minValueNormalized = stringUtils.getNormalizedValue(minValue, pivotLen);
                minimizerCounters[minValueNormalized]++;
            }
        }

        bfrG.close();
        frG.close();
        return minimizerCounters.clone();
    }

    public long[] Run() throws Exception {
        long time1 = 0;
        long t1 = System.currentTimeMillis();
        System.out.println("Minimizers counting Begin!");
        long[] counters = getMinimizersCounters();

        long t2 = System.currentTimeMillis();
        time1 = (t2 - t1) / 1000;
        System.out.println("Time used for counting minimizers appearances: " + time1 + " seconds!");
        return counters;
    }

}