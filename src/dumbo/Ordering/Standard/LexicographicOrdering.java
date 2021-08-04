package dumbo.Ordering.Standard;


import dumbo.Ordering.OrderingBase;

import java.io.IOException;
import java.util.Arrays;

public class LexicographicOrdering extends OrderingBase {

    public LexicographicOrdering(int pivotLength) {
        super(pivotLength);
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
        return Integer.compare(stringUtils.getNormalizedValue(x, pivotLength), stringUtils.getNormalizedValue(y, pivotLength));
    }


}
