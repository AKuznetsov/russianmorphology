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
        if(nextToken == null || nextToken.term().length() == 0) return nextToken;
        String word = nextToken.term();
        Character testC = word.charAt(0);
        if (Character.UnicodeBlock.of(testC) != Character.UnicodeBlock.CYRILLIC){
            return  nextToken;
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
