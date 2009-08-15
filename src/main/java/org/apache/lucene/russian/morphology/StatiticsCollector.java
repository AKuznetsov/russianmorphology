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

package org.apache.lucene.russian.morphology;


import org.apache.lucene.russian.morphology.dictonary.FlexiaModel;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;
import org.apache.lucene.russian.morphology.informations.Splitter;

import java.io.IOException;
import java.util.*;


public class StatiticsCollector implements WordProccessor {
    private TreeMap<String, Set<Heuristic>> inversIndex = new TreeMap<String, Set<Heuristic>>();
    private Set<Heuristic> noramlSuffix = new HashSet<Heuristic>();

    public void proccess(WordCard wordCard) throws IOException {
        String normalStringMorph = wordCard.getWordsFroms().get(0).getCode();
        String word = wordCard.getBase() + wordCard.getCanonicalSuffix();
        if (word.contains("-")) return;
        //if(wordCard.getBase()+)
        for (FlexiaModel fm : wordCard.getWordsFroms()) {
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


    public void printInfo() throws IOException {

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
                for (Heuristic h : currentSet) {
                    noramlSuffix.add(h);
                }
            }
        }
        System.out.println("Word with diffirent rules " + count);
        System.out.println("All ivers words " + inversIndex.size());
        System.out.println(dist);
        System.out.println("Diffirent suffix counts " + noramlSuffix.size());

        int maxLegth = Integer.MIN_VALUE;
        for (Heuristic n : noramlSuffix) {
            if (n.actualNormalSuffix.length() > maxLegth) maxLegth = n.actualNormalSuffix.length();
        }
        ArrayList<Heuristic> list = new ArrayList<Heuristic>(noramlSuffix);
        //new FileWriter()
        System.out.println("Max lenght " + maxLegth);

        int[][] ints = new int[count][];
        count = 0;
        prevSet = null;
        for (String key : inversIndex.keySet()) {
            Set<Heuristic> currentSet = inversIndex.get(key);
            if (!currentSet.equals(prevSet)) {
                ints[count] = RussianSuffixDecoderEncoder.encodeToArray(key);
                count++;
                prevSet = currentSet;
            }
        }
        Splitter splitter = new Splitter(ints);
        splitter.writeToFile("sep.txt");

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
        return new Heuristic(actualSuffixLengh, actualNormalSuffix, fm.getCode(), normalSuffixForm);
    }

    public static Integer getCommonLength(String s1, String s2) {
        Integer length = Math.min(s1.length(), s2.length());
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return length;
    }


    private class Heuristic {
        Integer actualSuffixLengh;
        String actualNormalSuffix;
        String formMorphInfo;
        String normalSuffixForm;

        private Heuristic(Integer actualSuffixLengh, String actualNormalSuffix, String formMorphInfo, String normalSuffixForm) {
            this.actualSuffixLengh = actualSuffixLengh;
            this.actualNormalSuffix = actualNormalSuffix;
            this.formMorphInfo = formMorphInfo;
            this.normalSuffixForm = normalSuffixForm;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Heuristic heuristic = (Heuristic) o;

            if (actualNormalSuffix != null ? !actualNormalSuffix.equals(heuristic.actualNormalSuffix) : heuristic.actualNormalSuffix != null)
                return false;
            if (actualSuffixLengh != null ? !actualSuffixLengh.equals(heuristic.actualSuffixLengh) : heuristic.actualSuffixLengh != null)
                return false;
            if (formMorphInfo != null ? !formMorphInfo.equals(heuristic.formMorphInfo) : heuristic.formMorphInfo != null)
                return false;
            if (normalSuffixForm != null ? !normalSuffixForm.equals(heuristic.normalSuffixForm) : heuristic.normalSuffixForm != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = actualSuffixLengh != null ? actualSuffixLengh.hashCode() : 0;
            result = 31 * result + (actualNormalSuffix != null ? actualNormalSuffix.hashCode() : 0);
            result = 31 * result + (formMorphInfo != null ? formMorphInfo.hashCode() : 0);
            result = 31 * result + (normalSuffixForm != null ? normalSuffixForm.hashCode() : 0);
            return result;
        }
    }
}
