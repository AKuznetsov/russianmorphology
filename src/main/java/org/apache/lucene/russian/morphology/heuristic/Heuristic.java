/**
 * Copyright 2009 Alexander Kuznetsov 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.russian.morphology.heuristic;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;


public class Heuristic {
    private TreeMap<Long, Long> encodedSuffixesPairs = new TreeMap<Long, Long>();

    public void addEvristic(SuffixHeuristic suffixHeuristic) {
        Long suffix = RussianSuffixDecoderEncoder.encode(suffixHeuristic.getFormSuffix());
        Long longs = encodedSuffixesPairs.get(suffix);
        if (longs == null) {
            encodedSuffixesPairs.put(suffix, RussianSuffixDecoderEncoder.encode(suffixHeuristic.getNormalSuffix()));
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
            if (sfns.length == 2) {
                encodedSuffixesPairs.put(Long.valueOf(sfns[0]), Long.valueOf(sfns[0]));
            }
            s = reader.readLine();
        }
        reader.close();
    }

    public void writeToFile(String file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(encodedSuffixesPairs.size() + "\n");
        for (Long k : encodedSuffixesPairs.keySet()) {
            writer.write("" + k + " " + encodedSuffixesPairs.get(k) + "\n");
        }
        writer.close();
    }
}
