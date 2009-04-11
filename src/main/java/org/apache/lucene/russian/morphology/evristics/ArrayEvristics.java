package org.apache.lucene.russian.morphology.evristics;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;


public class ArrayEvristics {
    private long[] keys;
    private long[] values;

    public void readFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
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
        Long suffix = RussianSuffixDecoderEncoder.encodeLong(form.substring(startSymbol));

        int index = Arrays.binarySearch(keys,suffix);
        if(index == -1){
            return form;
        }else{
            String nSuffix = RussianSuffixDecoderEncoder.decodeLong(values[index]);
            return startSymbol > 0 ? form.substring(0, startSymbol) + nSuffix : nSuffix;
        }
    }
}
