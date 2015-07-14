package org.apache.lucene.morphology.context;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleTokenizerTest {

    @Test
    public void testSimpleTokenizer() throws IOException {
        Analyzer statAnalyzer = new StatAnalyzer();
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream("принеси мне вина на новый год? - и что я жду тебя, где вино".getBytes()), "UTF-8");

        TokenStream tokenStream = statAnalyzer.tokenStream(null, reader);
        tokenStream.reset();

        boolean wordSeen = false;
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTerm = tokenStream.getAttribute(CharTermAttribute.class);
            PositionIncrementAttribute position = tokenStream.getAttribute(PositionIncrementAttribute.class);
            System.out.println(charTerm.toString());
        }
    }

}