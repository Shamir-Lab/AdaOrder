package dumbo.Ordering;

import dumbo.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public abstract class OrderingBase {

    protected int pivotLength;
    protected int numMmers;
    protected int mask;

    protected StringUtils stringUtils;

    protected int[] mmerRanks;
    protected boolean isRankInitialized;

    public OrderingBase(int pivotLength) {
        this.pivotLength = pivotLength;
        this.numMmers = (int) Math.pow(4, pivotLength);
        this.mask = numMmers - 1;
        this.stringUtils = new StringUtils();
        this.mmerRanks = new int[numMmers];
        this.isRankInitialized = false;
    }


    public abstract void initializeRanks() throws Exception;

    public int compareMmer(int x, int y) throws Exception {
        if (!isRankInitialized)
            throw new Exception("problema - rank not initialized");

        int a = stringUtils.getNormalizedValue(x, pivotLength);
        int b = stringUtils.getNormalizedValue(y, pivotLength);

        if (a == b) return 0;
        if (mmerRanks[a] < mmerRanks[b]) return -1;
        return 1;
    }

    public int[] getRanks() {
        return mmerRanks.clone();
    }

    public int findSmallest(char[] a, int from, int to) throws Exception {
        int min_pos = from;
        int minValue = stringUtils.getDecimal(a, min_pos, min_pos + pivotLength);
        int currentValue = minValue;
        for (int i = from + 1; i <= to - pivotLength; i++) {
            currentValue = ((currentValue << 2) + StringUtils.valTable[a[i + pivotLength - 1] - 'A']) & mask;
            if (compareMmer(minValue, currentValue) > 0) {
                min_pos = i;
                minValue = currentValue;
            }
        }

        return min_pos;
    }

    protected void normalize() {
        Integer[] temp = new Integer[mmerRanks.length];
        for (int i = 0; i < temp.length; i++)
            temp[i] = i;

        Arrays.sort(temp, Comparator.comparingLong(a -> mmerRanks[a]));
        for (int i = 0; i < temp.length; i++) {
            mmerRanks[temp[i]] = i;
        }
    }
}
