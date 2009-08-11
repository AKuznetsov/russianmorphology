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
import org.apache.lucene.russian.morphology.dictonary.GrammaReader;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;

import java.util.HashMap;
import java.util.Map;


public class StatiticsCollectors implements WordProccessor {
    Map<SimpleSuffixHeuristic, SuffixCounter> statititics = new HashMap<SimpleSuffixHeuristic, SuffixCounter>();
    private Map<String, Double> wordsFreq;
    private GrammaReader grammaInfo;

    public StatiticsCollectors(Map<String, Double> wordsFreq, GrammaReader grammaInfo) {
        this.wordsFreq = wordsFreq;
        this.grammaInfo = grammaInfo;
    }

    private Integer ignoredCount = 0;

    public void proccess(WordCard wordCard) {
        for (FlexiaModel fm : wordCard.getWordsFroms()) {
            SimpleSuffixHeuristic simpleSuffixHeuristic = createEvristic(wordCard.getBase(), wordCard.getCanonicalSuffix(), fm);
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

    private SimpleSuffixHeuristic createEvristic(String wordBase, String canonicalSuffix, FlexiaModel fm) {
        String form = fm.create(wordBase);
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.suffixLength ? form.length() - RussianSuffixDecoderEncoder.suffixLength : 0;
        String formSuffix = form.substring(startSymbol);
        String actualSuffix = fm.getSuffix();
        Integer actualSuffixLengh = actualSuffix.length();
        return new SimpleSuffixHeuristic(formSuffix, actualSuffixLengh, canonicalSuffix, fm.getCode());
    }


    public Integer getIgnoredCount() {
        return ignoredCount;
    }
}
