package org.apache.lucene.russian.morphology.analayzer;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.io.*;
import java.util.Arrays;


public class ArrayEvristics {
    private long[] keys;
    private long[] values;

    public void readFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        readFromBufferedRreader(reader);
    }


    public ArrayEvristics() throws IOException {
        readFromResource();
    }

    public ArrayEvristics(String fileName) throws IOException {
        readFromFile(fileName);
    }

    public void readFromResource() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/arrayEvritics.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        readFromBufferedRreader(bufferedReader);
    }

    private void readFromBufferedRreader(BufferedReader reader) throws IOException {
        int size = Integer.valueOf(reader.readLine());
        keys = new long[size];
        values = new long[size];
        for (int i = 0; i < size; i++) {
            String[] s = reader.readLine().split(" ");
            keys[i] = Long.valueOf(s[0]);
            values[i] = Long.valueOf(s[1]);
        }
    }

    public String getCanonicalForm(String form) {
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.SUFFIX_LENGTH ? form.length() - RussianSuffixDecoderEncoder.SUFFIX_LENGTH : 0;
        Long suffix = RussianSuffixDecoderEncoder.encode(form.substring(startSymbol));

        int index = Arrays.binarySearch(keys,suffix);
        if(index < -1){
            System.out.println(" " + form);
            return form;
        }else{
            String nSuffix = RussianSuffixDecoderEncoder.decode(values[index]);
            return startSymbol > 0 ? form.substring(0, startSymbol) + nSuffix : nSuffix;
        }
    }
}
