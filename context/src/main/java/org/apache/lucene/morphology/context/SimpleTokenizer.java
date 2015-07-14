/**
 * Copyright 2015 Alexander Kuznetsov
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
package org.apache.lucene.morphology.context;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SimpleTokenizer extends Tokenizer {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posAtt = addAttribute(PositionIncrementAttribute.class);
    LinkedList<String> terms;

    public final static Set<Character> SEPARATION_LETTERS = new HashSet<>(Arrays.asList(' ', '(', ')', ',', '|', '\t',
            '\n', '"', ':', '!', '?', ',', ';', '•'));

    public final static Set<Character> MEANING_CHARS = new HashSet<>(Arrays.asList('(', ')', ',', '|',
            '"', ':', '!', '?', ',', ';', '•', '.'));

    public SimpleTokenizer() {
    }

    public SimpleTokenizer(AttributeFactory factory) {
        super(factory);
    }

    @Override
    final public boolean incrementToken() throws IOException {
        if (terms == null) {
            createTeams();
        }
        if (terms.size() > 0) {
            String str = terms.poll();
            termAtt.setEmpty();
            termAtt.append(str);
            posAtt.setPositionIncrement(1);
            return true;
        }
        return false;
    }

    private void createTeams() throws IOException {
        terms = new LinkedList<>();

        BufferedReader br = new BufferedReader(input);
        StringBuilder sb = new StringBuilder();
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s).append(" ");
        }

        s = sb.toString();
        CharTermAttributeImpl currentTerm = new CharTermAttributeImpl();
        for (int i = 0; i < s.length(); i++) {
            if (checkIsCharSepartor(s, i)) {
                if (checkIsCharHasMeaning(s, i)) {
                    terms.add(s.substring(i, i + 1));
                }
                String term = currentTerm.toString();
                currentTerm.clear();
                if (term.length() > 0) {
                    terms.add(term);
                }
            } else {
                currentTerm.append(s.charAt(i));
            }
        }
    }

    private boolean checkIsCharHasMeaning(String s, int i) {
        return MEANING_CHARS.contains(s.charAt(i));
    }

    private boolean checkIsCharSepartor(String s, int i) {
        char c = s.charAt(i);
        if (SEPARATION_LETTERS.contains(c)) {
            return true;
        }
        if ('.' == c
                && s.length() > i + 1
                && SEPARATION_LETTERS.contains(s.charAt(i + 1))) {
            return true;
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        this.terms = null;
        super.reset();
    }

}