package org.apache.lucene.russian.morphology.analayzer;

import junit.framework.TestCase;
import org.junit.Test;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class RussianMorphlogyAnalayzerTest {

    @Test
    public void shouldCorrectProccessText() throws IOException {
        RussianMorphlogyAnalayzer morphlogyAnalayzer = new RussianMorphlogyAnalayzer();
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/russian/morphology/analayzer/russian-text.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));

        final Token reusableToken = new Token();

              Token nextToken;


        TokenStream in = morphlogyAnalayzer.tokenStream(null, bufferedReader);
        for (;;)
               {
                   nextToken = in.next(reusableToken);

                   if (nextToken == null)
                   {
                       break;
                   }

                   System.out.println(nextToken.term());
//                   nextSampleToken = sample.next(reusableSampleToken);
//                   assertEquals(
//                       "Unicode",
//                       nextToken.term(),
//                       nextSampleToken == null
//                       ? null
//                       : nextSampleToken.term());
               }

    }
}
