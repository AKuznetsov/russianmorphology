package org.apache.lucene.morphology.english.stemmer;


import org.apache.lucene.morphology.english.EnglishLuceneMorphology;

import java.util.List;

public class EnglishStemmer {
    private EnglishLuceneMorphology englishLuceneMorphology;

    public EnglishStemmer(EnglishLuceneMorphology englishLuceneMorphology) {
        this.englishLuceneMorphology = englishLuceneMorphology;
    }

    public String getStemmedWord(String word){
        if(!englishLuceneMorphology.checkString(word)){
            return word;
        }
        List<String> normalForms = englishLuceneMorphology.getNormalForms(word);
        if(normalForms.size() == 1){
            return normalForms.get(0);
        }
        normalForms.remove(word);
        if(normalForms.size() == 1){
            return normalForms.get(0);
        }
        return word;
    }

}
