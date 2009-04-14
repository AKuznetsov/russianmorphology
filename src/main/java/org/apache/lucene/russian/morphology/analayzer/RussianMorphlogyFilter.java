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

package org.apache.lucene.russian.morphology.analayzer;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;


public class RussianMorphlogyFilter extends TokenFilter {
    private SuffixEvristics suffixEvristics;

    public RussianMorphlogyFilter(TokenStream tokenStream, SuffixEvristics suffixEvristics) {
        super(tokenStream);
        this.suffixEvristics = suffixEvristics;
    }

    public Token next(final Token reusableToken) throws IOException {
        Token nextToken = input.next(reusableToken);
        if (nextToken == null || nextToken.term().length() == 0) return nextToken;
        String word = nextToken.term();
        Character testC = word.charAt(0);
        if (Character.UnicodeBlock.of(testC) != Character.UnicodeBlock.CYRILLIC) {
            return nextToken;
        }
        Token current = (Token) nextToken.clone();
        return createToken(suffixEvristics.getCanonicalForm(word), current, reusableToken);
    }

    protected Token createToken(String synonym, Token current, final Token reusableToken) {
        reusableToken.reinit(current, synonym);
        reusableToken.setTermBuffer(synonym);
        reusableToken.setPositionIncrement(0);
        return reusableToken;
    }
}
