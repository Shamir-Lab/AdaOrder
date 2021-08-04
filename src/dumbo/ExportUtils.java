package dumbo;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;

public class ExportUtils {
    public void exportOrderingForCpp(long[] currentOrdering) {
        File file = new File("ranks.txt");

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < currentOrdering.length; i++) {
                bf.write(Long.toString(currentOrdering[i]));
                bf.newLine();
            }
            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                //always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }
    }

//    public long[] importOrdering(String fileName, int pivotLength) throws Exception {
//        String line;
//        LinkedList<Long> ranks = new LinkedList<>();
//
//        File file = new File(fileName);
//        BufferedReader bfr = null;
//
//        try {
//            bfr = new BufferedReader(new FileReader(file));
//            while ((line = bfr.readLine()) != null) {
//                ranks.add(Long.getLong(line));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            bfr.close();
//        }
//
//        if (ranks.size() != (int) Math.pow(4, pivotLength)) {
//            throw new Exception("rank file of wrong size");
//        }
//        int i = 0;
//        long[] ordering = new long[(int) Math.pow(4, pivotLength)];
//        while (ranks.size() > 0) {
//            ordering[i] = ranks.pop();
//            i++;
//        }
//        return ordering;
//
//    }

    public void exportBinningForCpp(long[] statFrequency) {
        File file = new File("freq.txt");

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < statFrequency.length; i++) {
                bf.write(Long.toString(statFrequency[i]));
                bf.newLine();
            }
            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                //always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }
    }

    public HashMap<Long, Long> getBytesPerFile() {
        File folder = new File("./Nodes");
        File[] listOfFiles = folder.listFiles();

        HashMap<Long, Long> bytesPerFile = new HashMap<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile())
                bytesPerFile.put(Long.parseLong(listOfFiles[i].getName().replace("nodes", "")), listOfFiles[i].length());
        }
        return bytesPerFile;
    }

    public void writeToFile(long[] arr, String fileName) {
        HashMap<Long, Long> map = new HashMap<>();
        for (long i = 0; i < arr.length; i++) {
            map.put(i, arr[(int)i]);
        }
        writeToFile(map, fileName);
    }

    public void writeToFile(AbstractMap<Long, Long> data, String fileName) {
        File file = new File(fileName);

        BufferedWriter bf = null;


        try {
            bf = new BufferedWriter(new FileWriter(file));

            bf.write("x = {");
            bf.newLine();

            //iterate map entries
            for (java.util.Map.Entry<Long, Long> entry : data.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue() + ",");
                bf.newLine();
            }
            bf.write("}");
            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                //always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }

    }
}
