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

package org.apache.lucene.morphology.analayzer;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.morphology.LuceneMorph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RussianMorphlogyFilter extends TokenFilter {
    private LuceneMorph luceneMorph;

    public RussianMorphlogyFilter(TokenStream tokenStream, LuceneMorph luceneMorph) {
        super(tokenStream);
        this.luceneMorph = luceneMorph;
    }


    private List<String> stack = new ArrayList<String>();
    private int index = 0;
    private Token current = null;

    /**
     * Returns the next token in the stream, or null at EOS.
     */
    public Token next(final Token reusableToken) throws IOException {
        assert reusableToken != null;
        while (index < stack.size()) { // pop from stack
            Token nextToken = createToken(stack.get(index++), current, reusableToken);
            if (nextToken != null) {
                return nextToken;
            }
        }

        Token nextToken = input.next(reusableToken);
        if (nextToken == null) return null; // EOS; iterator exhausted
        Character testC = nextToken.term().charAt(0);
        if (Character.UnicodeBlock.of(testC) != Character.UnicodeBlock.CYRILLIC) {
            return nextToken;
        }
        stack = luceneMorph.getMorhInfo(nextToken.term());
        index = 0;
        current = (Token) nextToken.clone();
        nextToken = createToken(stack.get(index++), current, reusableToken);
        return nextToken;
    }

    /**
     * Creates and returns a token for the given synonym of the current input
     * token; Override for custom (stateless or stateful) behavior, if desired.
     *
     * @param synonym       a synonym for the current token's term
     * @param current       the current token from the underlying child stream
     * @param reusableToken the token to reuse
     * @return a new token, or null to indicate that the given synonym should be
     *         ignored
     */
    protected Token createToken(String synonym, Token current, final Token reusableToken) {
        reusableToken.reinit(current, synonym);
        reusableToken.setTermBuffer(synonym);
        reusableToken.setPositionIncrement(0);
        return reusableToken;
    }
}
