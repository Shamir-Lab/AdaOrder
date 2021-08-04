package dumbo.Ordering.Standard;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class LexicographicSignatureOrdering extends LexicographicOrdering {
    protected SignatureUtils signatureUtils;

    public LexicographicSignatureOrdering(int pivotLen) throws IOException {
        super(pivotLen);
        signatureUtils = new SignatureUtils(pivotLen);
    }

    @Override
    public void initializeRanks() throws IOException {
        for (int i = 0; i < numMmers; i++) {
            int canonical = Math.min(i, stringUtils.getReversedMmer(i, pivotLength));
            mmerRanks[i] = canonical;
            mmerRanks[stringUtils.getReversedMmer(i, pivotLength)] = canonical;
        }
        for (int i = 0; i < numMmers; i++) {
            if (!signatureUtils.isAllowed(i) && i < stringUtils.getReversedMmer(i, pivotLength)) {
                mmerRanks[i] += numMmers;
                mmerRanks[stringUtils.getReversedMmer(i, pivotLength)] += numMmers;
            }
        }

        normalize();



        isRankInitialized = true;
    }

    @Override
    protected int rawCompareMmer(int x, int y) {
        int a = stringUtils.getNormalizedValue(x, pivotLength);
        int b = stringUtils.getNormalizedValue(y, pivotLength);

        boolean aAllowed = signatureUtils.isAllowed(a);
        boolean bAllowed = signatureUtils.isAllowed(b);

        if (!aAllowed && bAllowed) {
            return 1;
        } else if (!bAllowed && aAllowed) {
            return -1;
        }

        return Integer.compare(a, b);
    }
}
