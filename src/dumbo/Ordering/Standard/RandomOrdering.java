package dumbo.Ordering.Standard;

import dumbo.Ordering.OrderingBase;

import java.io.IOException;
import java.util.Arrays;

public class RandomOrdering extends OrderingBase {
    private int xor;

    public RandomOrdering(int pivotLen, int xor) {
        super(pivotLen);
        this.xor = xor;
    }

    @Override
    public void initializeRanks() throws IOException {
        Integer[] mmers = new Integer[numMmers];
        for (int i = 0; i < mmers.length; i++) {
            mmers[i] = i;
        }

        Arrays.sort(mmers, this::rawCompareMmer);
        for (int i = 0; i < mmers.length; i++) {
            mmerRanks[mmers[i]] = i;
        }
        System.out.println("finish init rank");
        isRankInitialized = true;
    }

    protected int rawCompareMmer(int x, int y) {
        int a = stringUtils.getNormalizedValue(x, pivotLength);
        int b = stringUtils.getNormalizedValue(y, pivotLength);

        if ((a ^ xor) < (b ^ xor))
            return -1;
        else if ((a ^ xor) > (b ^ xor))
            return 1;
        return 0;
    }
}