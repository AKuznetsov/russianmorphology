package org.apache.lucene.russian.morphology.dictonary;

import java.util.List;
import java.util.ArrayList;


public class WordCard {
    private String canonicalFrom;
    private List<String> wordsFroms = new ArrayList<String>();

    public WordCard(String canonicalFrom) {
        this.canonicalFrom = canonicalFrom;
    }

    public void addFrom(String word){
        wordsFroms.add(word);
    }

    public String getCanonicalFrom() {
        return canonicalFrom;
    }

    public List<String> getWordsFroms() {
        return wordsFroms;
    }
}
