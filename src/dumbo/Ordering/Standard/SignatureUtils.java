package dumbo.Ordering.Standard;

public class SignatureUtils {

    private int len;
    protected byte[] isPmerAllowed;

    public SignatureUtils(int len){
        this.len = len;
        isPmerAllowed = new byte[(int)Math.pow(4, len)];
    }

    public boolean isAllowed(int mmer)
    {
        int isAllowed = isPmerAllowed[mmer];
        if(isAllowed != 0){
            return isAllowed == 1;
        }

        if ((mmer & 0x3f) == 0x3f)            // TTT suffix
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }

        if ((mmer & 0x3f) == 0x3b)            // TGT suffix
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }
        if ((mmer & 0x3c) == 0x3c)            // TG* suffix !!!! consider issue #152
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }

        for (int j = 0; j < len - 3; ++j)
            if ((mmer & 0xf) == 0)                // AA inside
            {
                isPmerAllowed[mmer] = -1;
                return false;
            }
            else
                mmer >>= 2;

        if (mmer == 0)            // AAA prefix
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }
        if (mmer == 0x04)        // ACA prefix
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }
        if ((mmer & 0xf) == 0)    // *AA prefix
        {
            isPmerAllowed[mmer] = -1;
            return false;
        }

        isPmerAllowed[mmer] = 1;
        return true;
    }

    public boolean isAllowed(char[] a, int from, int aDecimal) {
        int isAllowed = isPmerAllowed[aDecimal];
        if(isAllowed != 0){
            return isAllowed == 1;
        }

        int lastIndex = from + len - 1;
        if (a[from] == 'A' && a[from + 2] == 'A') {
            if (a[from + 1] <= 'C') { // C or A
                isPmerAllowed[aDecimal] = -1;
                return false;
            }
        } else if (a[lastIndex] == 'T' && a[lastIndex - 2] == 'T') {
            if (a[lastIndex - 1] >='G') { // G or T
                isPmerAllowed[aDecimal] = -1;
                return false;
            }
        }

        for (int i = from + 2; i < lastIndex; i++) {
            if (a[i] == 'A' && a[i + 1] == 'A') {
                isPmerAllowed[aDecimal] = -1;
                return false;
            }
        }
        isPmerAllowed[aDecimal] = 1;
        return true;
    }

}
