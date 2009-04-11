package org.apache.lucene.russian.morphology.evristics;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.util.*;
import java.io.*;


public class Evristic {
    private TreeMap<Long, Long> encodedSuffixesPairs = new TreeMap<Long, Long>();

    public void addEvristic(SuffixEvristic suffixEvristic) {
        Long suffix = RussianSuffixDecoderEncoder.encode(suffixEvristic.getFormSuffix());
        Long longs = encodedSuffixesPairs.get(suffix);
        if (longs == null) {
            encodedSuffixesPairs.put(suffix, RussianSuffixDecoderEncoder.encode(suffixEvristic.getNormalSuffix()));
        }
    }

    public String getNormalForm(String form) {
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.SUFFIX_LENGTH ? form.length() - RussianSuffixDecoderEncoder.SUFFIX_LENGTH : 0;
        Long suffix = RussianSuffixDecoderEncoder.encode(form.substring(startSymbol));

        Long normalSuffix = encodedSuffixesPairs.get(suffix);
        if (normalSuffix != null) {
            String nSuffix = RussianSuffixDecoderEncoder.decode(normalSuffix);
            return startSymbol > 0 ? form.substring(0, startSymbol) + nSuffix : nSuffix;

        }
        return form;
    }

    public void readFromFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s = reader.readLine();
        while (s != null) {
            String[] sfns = s.split(" ");
            if(sfns.length == 2){
                encodedSuffixesPairs.put(Long.valueOf(sfns[0]), Long.valueOf(sfns[0]));
            }
            s = reader.readLine();
        }
        reader.close();
    }

    public void writeToFile(String file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(encodedSuffixesPairs.size()+"\n");
        for(Long k:encodedSuffixesPairs.keySet()){
            writer.write("" + k + " " + encodedSuffixesPairs.get(k) + "\n");
        }
        writer.close();
    }
}
