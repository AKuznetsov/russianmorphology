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

package org.apache.lucene.morphology.dictionary;


import org.apache.lucene.morphology.Heuristic;
import org.apache.lucene.morphology.LetterDecoderEncoder;
import org.apache.lucene.morphology.MorphologyImpl;

import java.io.IOException;
import java.util.*;


//todo made refactoring this class
public class StatiticsCollector implements WordProccessor {
    private TreeMap<String, Set<Heuristic>> inversIndex = new TreeMap<String, Set<Heuristic>>();
    private Map<Set<Heuristic>, Integer> ruleInverIndex = new HashMap<Set<Heuristic>, Integer>();
    private List<Set<Heuristic>> rules = new ArrayList<Set<Heuristic>>();
    private GrammaReader grammaReader;
    private LetterDecoderEncoder decoderEncoder;


    public StatiticsCollector(GrammaReader grammaReader, LetterDecoderEncoder decoderEncoder) {
        this.grammaReader = grammaReader;
        this.decoderEncoder = decoderEncoder;
    }

    public void proccess(WordCard wordCard) throws IOException {
        cleanWordCard(wordCard);
        String normalStringMorph = wordCard.getWordsFroms().get(0).getCode();
        String word = wordCard.getBase() + wordCard.getCanonicalSuffix();
        if (word.contains("-")) return;
        if (!decoderEncoder.checkString(word)) return;

        for (FlexiaModel fm : wordCard.getWordsFroms()) {
            if (!decoderEncoder.checkString(fm.create(wordCard.getBase()))) continue;
            Heuristic heuristic = createEvristic(wordCard.getBase(), wordCard.getCanonicalSuffix(), fm, normalStringMorph);
            String form = revertWord(fm.create(wordCard.getBase()));
            Set<Heuristic> suffixHeuristics = inversIndex.get(form);
            if (suffixHeuristics == null) {
                suffixHeuristics = new HashSet<Heuristic>();
                inversIndex.put(form, suffixHeuristics);
            }
            suffixHeuristics.add(heuristic);
        }
    }

    private void cleanWordCard(WordCard wordCard) {
        wordCard.setBase(cleanString(wordCard.getBase()));
        wordCard.setCanonicalFrom(cleanString(wordCard.getCanonicalFrom()));
        wordCard.setCanonicalSuffix(cleanString(wordCard.getCanonicalSuffix()));
        List<FlexiaModel> models = wordCard.getWordsFroms();
        for (FlexiaModel m : models) {
            m.setSuffix(cleanString(m.getSuffix()));
            m.setPrefix(cleanString(m.getPrefix()));
        }
    }


    public void saveHeuristic(String fileName) throws IOException {

        Map<Integer, Integer> dist = new TreeMap<Integer, Integer>();
        Set<Heuristic> prevSet = null;
        int count = 0;
        for (String key : inversIndex.keySet()) {
            Set<Heuristic> currentSet = inversIndex.get(key);
            if (!currentSet.equals(prevSet)) {
                Integer d = dist.get(key.length());
                dist.put(key.length(), 1 + (d == null ? 0 : d));
                prevSet = currentSet;
                count++;
                if (!ruleInverIndex.containsKey(currentSet)) {
                    ruleInverIndex.put(currentSet, rules.size());
                    rules.add(currentSet);
                }
            }
        }
        System.out.println("Word with diffirent rules " + count);
        System.out.println("All ivers words " + inversIndex.size());
        System.out.println(dist);
        System.out.println("diffirent rule count " + ruleInverIndex.size());
        Heuristic[][] heuristics = new Heuristic[ruleInverIndex.size()][];
        int index = 0;
        for (Set<Heuristic> hs : rules) {
            heuristics[index] = new Heuristic[hs.size()];
            int indexj = 0;
            for (Heuristic h : hs) {
                heuristics[index][indexj] = h;
                indexj++;
            }
            index++;
        }

        int[][] ints = new int[count][];
        short[] rulesId = new short[count];
        count = 0;
        prevSet = null;
        for (String key : inversIndex.keySet()) {
            Set<Heuristic> currentSet = inversIndex.get(key);
            if (!currentSet.equals(prevSet)) {
                int[] word = decoderEncoder.encodeToArray(key);
                ints[count] = word;
                rulesId[count] = (short) ruleInverIndex.get(currentSet).intValue();
                count++;
                prevSet = currentSet;
            }
        }
        MorphologyImpl morphology = new MorphologyImpl(ints, rulesId, heuristics, grammaReader.getGrammaInfoAsArray());
        morphology.writeToFile(fileName);
    }

    private String revertWord(String s) {
        String result = "";
        for (int i = 1; i <= s.length(); i++) {
            result += s.charAt(s.length() - i);
        }
        return result;
    }


    private Heuristic createEvristic(String wordBase, String canonicalSuffix, FlexiaModel fm, String normalSuffixForm) {
        String form = fm.create(wordBase);
        String normalForm = wordBase + canonicalSuffix;
        Integer length = getCommonLength(form, normalForm);
        Integer actualSuffixLengh = form.length() - length;
        String actualNormalSuffix = normalForm.substring(length);
        Integer integer = grammaReader.getGrammInversIndex().get(fm.getCode().substring(0, 2));
        Integer nf = grammaReader.getGrammInversIndex().get(normalSuffixForm.substring(0, 2));
        return new Heuristic((byte) actualSuffixLengh.intValue(), actualNormalSuffix, (short) integer.intValue(), (short) nf.intValue());
    }

    public static Integer getCommonLength(String s1, String s2) {
        Integer length = Math.min(s1.length(), s2.length());
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return length;
    }

    private String cleanString(String s) {
        return decoderEncoder.cleanString(s);
    }

}
