package dumbo;

public class StringUtils {

    public final static int[] valTable = new int[]{0,-1,1,-1,-1,-1,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3};
    public final static char[] twinTable = new char[]{'T','0','G','0','0','0','C','0','0','0','0','0','0','0','0','0','0','0','0','A'};

    public int getDecimal(char[] a, int from, int to){

        int val=0;

        for(int i=from; i<to; i++){
            val = val<<2;
            val += valTable[a[i]-'A'];
        }

        return val;
    }

    public long getLDecimal(char[] a, int from, int to){

        long val=0;

        for(int i=from; i<to; i++){
            val = val<<2;
            val += valTable[a[i]-'A'];
        }

        return val;
    }

    public boolean isReadLegal(char[] line){
        int Len = line.length;
        for(int i=0; i<Len; i++){
            if(line[i]!='A' && line[i]!='C' && line[i]!='G' && line[i]!='T')
                return false;
        }
        return true;
    }

    public char[] getReversedRead(char[] lineCharArray){
        int len = lineCharArray.length;
        char[] revCharArray = new char[len];
        for(int i=0; i<len; i++){
            revCharArray[i] = twinTable[lineCharArray[len-1-i]-'A'];
        }
        return revCharArray;
    }

    public int getReversedMmer(int x, int length) {
        int rev = 0;
        int immer = ~x;
        for (int i = 0; i < length; ++i) {
            rev <<= 2;
            rev |= immer & 0x3;
            immer >>= 2;
        }
        return rev;
    }

    public String getCanonical(String line) {
        String x = new String(getReversedRead(line.toCharArray()));
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) < x.charAt(i))
                return line;
            else if (line.charAt(i) > x.charAt(i))
                return x;
        }
        return x;
    }

    public int getNormalizedValue(int minValue, int pivotLength) {
        return Math.min(minValue, getReversedMmer(minValue, pivotLength));
    }
}
