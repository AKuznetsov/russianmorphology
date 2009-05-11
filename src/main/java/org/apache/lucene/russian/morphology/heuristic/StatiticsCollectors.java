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
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;

import java.util.HashMap;
import java.util.Map;


public class StatiticsCollectors implements WordProccessor {
    Map<SuffixHeuristic, SuffixCounter> statititics = new HashMap<SuffixHeuristic, SuffixCounter>();
    private Map<String, Double> wordsFreq;

    public StatiticsCollectors(Map<String, Double> wordsFreq) {
        this.wordsFreq = wordsFreq;
    }

    private Integer ignoredCount = 0;

    public void proccess(WordCard wordCard) {
        for (String form : wordCard.getWordsFroms()) {
            SuffixHeuristic suffixHeuristic = createEvristic(wordCard.getCanonicalFrom(), form);
            if (suffixHeuristic == null) continue;
            SuffixCounter suffixCounter = statititics.get(suffixHeuristic);
            if (suffixCounter == null) {
                suffixCounter = new SuffixCounter(suffixHeuristic);
                statititics.put(suffixHeuristic, suffixCounter);
            }
            Double freq = wordsFreq.get(wordCard.getCanonicalFrom());
            if (freq != null) {
                suffixCounter.incrementAmount(1 + Math.log(freq));
            } else {
                suffixCounter.incrementAmount();
            }

        }
    }

    public Map<SuffixHeuristic, SuffixCounter> getStatititics() {
        return statititics;
    }

    private SuffixHeuristic createEvristic(String word, String form) {
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.SUFFIX_LENGTH ? form.length() - RussianSuffixDecoderEncoder.SUFFIX_LENGTH : 0;
        String formSuffix = form.substring(startSymbol);
        if (word.length() < startSymbol) {
            ignoredCount++;
            return null;
        }
        String wordSuffix = word.length() > startSymbol ? word.substring(startSymbol) : "";
        if (wordSuffix.length() > 12) {
            System.out.println(word + " " + form);
            return null;
        }
        return new SuffixHeuristic(formSuffix, wordSuffix);
    }


    public Integer getIgnoredCount() {
        return ignoredCount;
    }
}
