package org.apache.lucene.morphology.english.stemmer;


import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.morphology.LuceneMorphology;

import java.io.IOException;
import java.util.Iterator;

public class EnglishStemmerFilter extends TokenFilter {
    private EnglishStemmer englishStemmer;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public EnglishStemmerFilter(TokenStream input, EnglishStemmer englishStemmer) {
        super(input);
        this.englishStemmer = englishStemmer;
    }


    final public boolean incrementToken() throws IOException {

        boolean b = input.incrementToken();
        if (!b) {
            return false;
        }
        String s = new String(termAtt.buffer(), 0, termAtt.length());
        termAtt.setEmpty();
        termAtt.append(s);
        return true;
    }

}