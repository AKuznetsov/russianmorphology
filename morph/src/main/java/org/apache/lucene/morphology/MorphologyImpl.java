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
package org.apache.lucene.morphology;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MorphologyImpl implements Morphology {
    protected int[][] separators;
    protected short[] rulesId;
    protected Heuristic[][] rules;
    protected String[] grammarInfo;
    protected LetterDecoderEncoder decoderEncoder;


    public MorphologyImpl(String fileName, LetterDecoderEncoder decoderEncoder) throws IOException {
        readFromFile(fileName);
        this.decoderEncoder = decoderEncoder;
    }

    public MorphologyImpl(InputStream inputStream, LetterDecoderEncoder decoderEncoder) throws IOException {
        readFromInputStream(inputStream);
        this.decoderEncoder = decoderEncoder;
    }

    public MorphologyImpl(int[][] separators, short[] rulesId, Heuristic[][] rules, String[] grammarInfo) {
        this.separators = separators;
        this.rulesId = rulesId;
        this.rules = rules;
        this.grammarInfo = grammarInfo;
    }

    public List<String> getNormalForms(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int[] ints = decoderEncoder.encodeToArray(revertWord(s));
        int ruleId = findRuleId(ints);
        boolean notSeenEmptyString = true;
        for (Heuristic h : rules[rulesId[ruleId]]) {
            String e = h.transformWord(s).toString();
            if (e.length() > 0) {
                result.add(e);
            } else if (notSeenEmptyString) {
                result.add(s);
                notSeenEmptyString = false;
            }
        }
        return result;
    }

    public List<String> getMorphInfo(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int[] ints = decoderEncoder.encodeToArray(revertWord(s));
        int ruleId = findRuleId(ints);
        for (Heuristic h : rules[rulesId[ruleId]]) {
            result.add(h.transformWord(s).append("|").append(grammarInfo[h.getFormMorphInfo()]).toString());
        }
        return result;
    }

    protected int findRuleId(int[] ints) {
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
        return i1.length - i2.length;
    }

    public void writeToFile(String fileName) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
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
        writer.write(grammarInfo.length + "\n");
        for (String s : grammarInfo) {
            writer.write(s + "\n");
        }
        writer.close();
    }

    public void readFromFile(String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        readFromInputStream(inputStream);
    }

    private void readFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String s = bufferedReader.readLine();
        Integer amount = Integer.valueOf(s);

        readSeparators(bufferedReader, amount);

        readRulesId(bufferedReader, amount);

        readRules(bufferedReader);
        readGrammaInfo(bufferedReader);
        bufferedReader.close();
    }

    private void readGrammaInfo(BufferedReader bufferedReader) throws IOException {
        String s;
        Integer amount;
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        grammarInfo = new String[amount];
        for (int i = 0; i < amount; i++) {
            grammarInfo[i] = bufferedReader.readLine();
        }
    }

    protected void readRules(BufferedReader bufferedReader) throws IOException {
        String s;
        Integer amount;
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        rules = new Heuristic[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer ruleLength = Integer.valueOf(s1);
            rules[i] = new Heuristic[ruleLength];
            for (int j = 0; j < ruleLength; j++) {
                rules[i][j] = new Heuristic(bufferedReader.readLine());
            }
        }
    }

    private void readRulesId(BufferedReader bufferedReader, Integer amount) throws IOException {
        rulesId = new short[amount];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            rulesId[i] = Short.valueOf(s1);
        }
    }

    private void readSeparators(BufferedReader bufferedReader, Integer amount) throws IOException {
        separators = new int[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer wordLenght = Integer.valueOf(s1);
            separators[i] = new int[wordLenght];
            for (int j = 0; j < wordLenght; j++) {
                separators[i][j] = Integer.valueOf(bufferedReader.readLine());
            }
        }
    }

    protected String revertWord(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= s.length(); i++) {
            result.append(s.charAt(s.length() - i));
        }
        return result.toString();
    }
}
