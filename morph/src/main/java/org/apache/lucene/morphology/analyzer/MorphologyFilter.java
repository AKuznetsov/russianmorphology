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

package org.apache.lucene.morphology.analyzer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.morphology.LuceneMorphology;

import java.io.IOException;
import java.util.Iterator;


public class MorphologyFilter extends TokenFilter {
    private LuceneMorphology luceneMorph;
    private Iterator<String> iterator;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);


    public MorphologyFilter(TokenStream tokenStream, LuceneMorphology luceneMorph) {
        super(tokenStream);
        this.luceneMorph = luceneMorph;
    }


   final public boolean incrementToken() throws IOException {
        while (iterator == null || !iterator.hasNext()) {
            boolean b = input.incrementToken();
            if (!b) {
                return false;
            }
            String s = new String(termAtt.buffer(),0,termAtt.length());
            if (luceneMorph.checkString(s)) {
                iterator = luceneMorph.getNormalForms(s).iterator();
            } else {
                return true;
            }
        }
        String s = iterator.next();
        termAtt.setEmpty();
        termAtt.append(s);
        return true;
    }

}
