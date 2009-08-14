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
import org.apache.lucene.russian.morphology.dictonary.FlexiaModel;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;

import java.util.HashMap;
import java.util.Map;


public class StatiticsCollectors implements WordProccessor {
    Map<SimpleSuffixHeuristic, SuffixCounter> statititics = new HashMap<SimpleSuffixHeuristic, SuffixCounter>();
    private Map<String, Double> wordsFreq;


    public StatiticsCollectors(Map<String, Double> wordsFreq) {
        this.wordsFreq = wordsFreq;
    }

    private Integer ignoredCount = 0;

    public void proccess(WordCard wordCard) {
        String normalStringMorph = wordCard.getWordsFroms().get(0).getCode();
        for (FlexiaModel fm : wordCard.getWordsFroms()) {
            SimpleSuffixHeuristic simpleSuffixHeuristic = createEvristic(wordCard.getBase(), wordCard.getCanonicalSuffix(), fm, normalStringMorph);
            if (simpleSuffixHeuristic == null) continue;
            SuffixCounter suffixCounter = statititics.get(simpleSuffixHeuristic);
            if (suffixCounter == null) {
                suffixCounter = new SuffixCounter(simpleSuffixHeuristic);
                statititics.put(simpleSuffixHeuristic, suffixCounter);
            }
            Double freq = wordsFreq.get(wordCard.getCanonicalFrom());
            if (freq != null) {
                suffixCounter.incrementAmount(1 + Math.log(freq));
            } else {
                suffixCounter.incrementAmount();
            }

        }
    }

    public Map<SimpleSuffixHeuristic, SuffixCounter> getStatititics() {
        return statititics;
    }

    private SimpleSuffixHeuristic createEvristic(String wordBase, String canonicalSuffix, FlexiaModel fm, String normalSuffixForm) {
        String form = fm.create(wordBase);
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.suffixLength ? form.length() - RussianSuffixDecoderEncoder.suffixLength : 0;
        String formSuffix = form.substring(startSymbol);
        String normalForm = wordBase + canonicalSuffix;
        Integer length = getCommonLength(form, normalForm);
        Integer actualSuffixLengh = form.length() - length;
        String actualNormalSuffix = normalForm.substring(length);
        return new SimpleSuffixHeuristic(formSuffix, actualSuffixLengh, actualNormalSuffix, fm.getCode(), normalSuffixForm);
    }

    public static Integer getCommonLength(String s1, String s2) {
        Integer length = Math.min(s1.length(), s2.length());
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return length;
    }


    public Integer getIgnoredCount() {
        return ignoredCount;
    }
}
