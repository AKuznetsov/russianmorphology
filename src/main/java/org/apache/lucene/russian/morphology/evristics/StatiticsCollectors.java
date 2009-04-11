package org.apache.lucene.russian.morphology.evristics;

import org.apache.lucene.russian.morphology.dictonary.WordProccessor;
import org.apache.lucene.russian.morphology.dictonary.WordCard;

import java.util.Map;
import java.util.HashMap;


public class StatiticsCollectors implements WordProccessor{
    Map<SuffixEvristic,SuffixCounter> statititics = new HashMap<SuffixEvristic,SuffixCounter>();

    private Integer ignoredCount = 0;

    public void proccess(WordCard wordCard) {
        for(String form:wordCard.getWordsFroms()){
            SuffixEvristic suffixEvristic = createEvristic(wordCard.getCanonicalFrom(), form);
            if (suffixEvristic == null) continue;
            SuffixCounter suffixCounter = statititics.get(suffixEvristic);
            if(suffixCounter == null){
                suffixCounter = new SuffixCounter(suffixEvristic);
                statititics.put(suffixEvristic,suffixCounter);
            }
            suffixCounter.incrementAmount();
        }
    }

    public Map<SuffixEvristic, SuffixCounter> getStatititics() {
        return statititics;
    }

    private SuffixEvristic createEvristic(String word,String form){
        int startSymbol = form.length() > RussianSuffixDecoderEncoder.SUFFIX_LENGTH ? form.length() - RussianSuffixDecoderEncoder.SUFFIX_LENGTH : 0;
        String formSuffix = form.substring(startSymbol);
        if(word.length() < startSymbol){
            ignoredCount++;
            return null;            
        }
        String wordSuffix = word.length() > startSymbol ? word.substring(startSymbol) : "";
        if (wordSuffix.length() > 12){
            System.out.println(word + " " + form);
            return null;
        }
        return new SuffixEvristic(formSuffix,wordSuffix);
    }


    public Integer getIgnoredCount() {
        return ignoredCount;
    }
}
