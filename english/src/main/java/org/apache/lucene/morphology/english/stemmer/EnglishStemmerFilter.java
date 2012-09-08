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
package org.apache.lucene.morphology.english.stemmer;


import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

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
        s = englishStemmer.getStemmedWord(s);
        termAtt.setEmpty();
        termAtt.append(s);
        return true;
    }

}