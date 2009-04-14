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

package org.apache.lucene.russian.morphology.evristics;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;

import java.util.HashMap;
import java.util.Map;


public class StatiticsCollectors implements WordProccessor {
    Map<SuffixEvristic, SuffixCounter> statititics = new HashMap<SuffixEvristic, SuffixCounter>();

    private Integer ignoredCount = 0;

    public void proccess(WordCard wordCard) {
        for (String form : wordCard.getWordsFroms()) {
            SuffixEvristic suffixEvristic = createEvristic(wordCard.getCanonicalFrom(), form);
            if (suffixEvristic == null) continue;
            SuffixCounter suffixCounter = statititics.get(suffixEvristic);
            if (suffixCounter == null) {
                suffixCounter = new SuffixCounter(suffixEvristic);
                statititics.put(suffixEvristic, suffixCounter);
            }
            suffixCounter.incrementAmount();
        }
    }

    public Map<SuffixEvristic, SuffixCounter> getStatititics() {
        return statititics;
    }

    private SuffixEvristic createEvristic(String word, String form) {
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
        return new SuffixEvristic(formSuffix, wordSuffix);
    }


    public Integer getIgnoredCount() {
        return ignoredCount;
    }
}
