package dumbo;

import dumbo.Ordering.*;
import dumbo.Ordering.Standard.LexicographicOrdering;
import dumbo.Ordering.Standard.RandomOrdering;
import dumbo.Ordering.Standard.LexicographicSignatureOrdering;

import java.util.Random;

public class OrderingOptimizer {

    public static void main(String[] args) throws Exception {

        String infile = null;

        int k = 60, m = 7, bufferSize = 81920;
        int R = 1000, elementsToPush = 1, N = 100000;
        long statSamples = 500000000;
        double p = 0.01;
        String version = "adaorder";
        String kmerSetFile = null;

        if (args.length > 0 && args[0].equals("-help")) {
            System.out.print("Usage: java -jar BuildDeBruijnGraph.jar -in InputPath -k k\n" +
                    "Options Available: \n" +
                    "[-m minimizer length] : (Integer); Default: 7" + "\n" +
                    "[-k kmer length] : (Integer); Default: 60" + "\n" +
                    "[-order order] : adaorder or lexicographic or signature or random; default adaorder" + "\n" +
                    "[-R number of AdaOrder Runs] : (Integer); Default: 1000" + "\n" +
                    "[-N number of samples per round of AdaOrder] : (Integer); Default: 100000" + "\n" +
                    "[-p penalty factor of AdaOrder] : (double); Default: 0.01" + "\n");
            return;
        }

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-in"))
                infile = args[i + 1];
            else if (args[i].equals("-order"))
                version = args[i + 1];
            else if (args[i].equals("-k"))
                k = new Integer(args[i + 1]);
            else if (args[i].equals("-kmers-file"))
                kmerSetFile = args[i + 1];
            else if (args[i].equals("-m"))
                m = new Integer(args[i + 1]);
            else if (args[i].equals("-R"))
                R = new Integer(args[i + 1]);
            else if (args[i].equals("-N"))
                N = new Integer(args[i + 1]);
            else if (args[i].equals("-statSamples"))
                statSamples = new Long(args[i + 1]);
            else if (args[i].equals("-p"))
                p = new Double(args[i + 1]);
            else {
                System.out.println("Wrong with arguments. Abort!");
                System.out.println(args[i]);
                return;
            }
        }


        System.out.print("Input File: " + infile + "\n" +
                "k-mer Length: " + k + "\n" +
                "Minimizer Length: " + m + "\n" +
                "Ordering: " + version + "\n");

        OrderingBase ordering = null;
        switch (version) {

            case "adaorder":
                System.out.print("Parameters for AdaOrder: \n" +
                        "N: " + N + "\n" +
                        "R: " + R + "\n" +
                        "p: " + p + "\n");
                IterativeOrdering iterativeSignature = new IterativeOrdering(m, infile, bufferSize, k,
                        N, R, elementsToPush, p, true);
                System.out.println("Optimizing an ordering:");
                iterativeSignature.initializeRanks();
                ordering = iterativeSignature;
                break;
            case "signature":
                ordering = new LexicographicSignatureOrdering(m);
                ordering.initializeRanks();
                break;
            case "lexicographic":
                ordering = new LexicographicOrdering(m);
                ordering.initializeRanks();
                break;
            case "random":
                Random r = new Random();
                ordering = new RandomOrdering(m, r.nextInt());
                ordering.initializeRanks();
                break;
        }


        ExportUtils exportUtils = new ExportUtils();

        int[] ranks = ordering.getRanks();
        long[] longRanks = new long[ranks.length];
        for (int i = 0; i < longRanks.length; longRanks[i] = ranks[i], i++) ;

        exportUtils.exportOrderingForCpp(longRanks);


        long[] counters;
        if (kmerSetFile != null) {
            try {

                System.out.println("Counting minimizer appearances:");
                System.out.print("Input File: " + kmerSetFile);
                MinimizerCounter minimizerCounter = new MinimizerCounter(k, kmerSetFile, m, bufferSize, ordering);
                counters = minimizerCounter.Run();

                exportUtils.writeToFile(counters, version + m + "_" + "kmers");
            } catch (Exception E) {
                System.out.println("Exception caught!");
                E.printStackTrace();
            }
        }
        if (statSamples > 0) {
            System.out.println("Collecting stats for binning");

            BinSizeCounter counter = new BinSizeCounter(m, infile, bufferSize, k, statSamples, ordering);
            counter.initFrequency();

            counters = counter.getStatistics();
            exportUtils.exportBinningForCpp(counters);
        }
    }


}
