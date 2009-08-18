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
package org.apache.lucene.russian.morphology.informations;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Morph {
    int[][] separators;
    short[] rulesId;
    Heuristic[][] rules;
    String[] grammaInfo;


    public Morph(String fileName) throws IOException {
        readFromFile(fileName);
    }

    public Morph(int[][] separators, short[] rulesId, Heuristic[][] rules, String[] grammaInfo) {
        this.separators = separators;
        this.rulesId = rulesId;
        this.rules = rules;
        this.grammaInfo = grammaInfo;
    }

    public int[][] getSeparators() {
        return separators;
    }

    public short[] getRulesId() {
        return rulesId;
    }

    public Heuristic[][] getRules() {
        return rules;
    }

    public String[] getGrammaInfo() {
        return grammaInfo;
    }

    public List<String> getMorhInfo(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int[] ints = RussianSuffixDecoderEncoder.encodeToArray(revertWord(s));
        int ruleId = findRuleId(ints);
        for (Heuristic h : rules[rulesId[ruleId]]) {
            System.out.println(h);
            result.add(h.transofrmWord(s));
        }
        return result;
    }

    private int findRuleId(int[] ints) {
        int low = 0;
        int high = separators.length - 1;
        int mid = 0;
        while (low <= high) {
            mid = (low + high) >>> 1;
            int[] midVal = separators[mid];

            int comResult = compareToInts(ints, midVal);
            if (comResult > 0)
                low = mid + 1;
            else if (comResult < 0)
                high = mid - 1;
            else
                break;
        }
        if (compareToInts(ints, separators[mid]) >= 0) {
            return mid;
        } else {
            return mid - 1;
        }

    }

    private int compareToInts(int[] i1, int[] i2) {
        int minLength = Math.min(i1.length, i2.length);
        for (int i = 0; i < minLength; i++) {
            int i3 = i1[i] < i2[i] ? -1 : (i1[i] == i2[i] ? 0 : 1);
            if (i3 != 0) return i3;
        }
        return i2.length - i1.length;
    }

    public void writeToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(separators.length + "\n");
        for (int[] i : separators) {
            writer.write(i.length + "\n");
            for (int j : i) {
                writer.write(j + "\n");
            }
        }
        for (short i : rulesId) {
            writer.write(i + "\n");
        }
        writer.write(rules.length + "\n");
        for (Heuristic[] heuristics : rules) {
            writer.write(heuristics.length + "\n");
            for (Heuristic heuristic : heuristics) {
                writer.write(heuristic.toString() + "\n");
            }
        }
        writer.write(grammaInfo.length + "\n");
        for (String s : grammaInfo) {
            writer.write(s + "\n");
        }
        writer.close();
    }

    public void readFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String s = bufferedReader.readLine();
        Integer amount = Integer.valueOf(s);
        separators = new int[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer wordLenght = Integer.valueOf(s1);
            separators[i] = new int[wordLenght];
            for (int j = 0; j < wordLenght; j++) {
                separators[i][j] = Integer.valueOf(bufferedReader.readLine());
            }
        }
        rulesId = new short[amount];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            rulesId[i] = Short.valueOf(s1);
        }
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        rules = new Heuristic[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer ruleLenght = Integer.valueOf(s1);
            rules[i] = new Heuristic[ruleLenght];
            for (int j = 0; j < ruleLenght; j++) {
                rules[i][j] = new Heuristic(bufferedReader.readLine());
            }
        }
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        grammaInfo = new String[amount];
        for (int i = 0; i < amount; i++) {
            grammaInfo[i] = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    private String revertWord(String s) {
        String result = "";
        for (int i = 1; i <= s.length(); i++) {
            result += s.charAt(s.length() - i);
        }
        return result;
    }
}
