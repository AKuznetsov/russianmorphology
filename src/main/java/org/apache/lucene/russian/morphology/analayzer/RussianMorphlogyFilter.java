package org.apache.lucene.russian.morphology.analayzer;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;


public class RussianMorphlogyFilter extends TokenFilter {
    private ArrayEvristics arrayEvristics;

    protected RussianMorphlogyFilter(TokenStream tokenStream, ArrayEvristics arrayEvristics) {
        super(tokenStream);
        this.arrayEvristics = arrayEvristics;
    }

    public Token next(final Token reusableToken) throws IOException {
        assert reusableToken != null;
        return createToken(arrayEvristics.getCanonicalForm(reusableToken.term()), reusableToken, reusableToken);
    }

    protected Token createToken(String synonym, Token current, final Token reusableToken) {
        reusableToken.reinit(current, synonym);
        reusableToken.setTermBuffer(synonym);
        reusableToken.setPositionIncrement(0);
        return reusableToken;
    }
}
