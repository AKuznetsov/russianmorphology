package org.apache.lucene.russian.morphology;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.apache.lucene.russian.morphology.analayzer.RussianMorphlogyAnalayzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class SpeedTest {

    @Test
    public void getTestOfSpeed() throws IOException {
        Long startTime = System.currentTimeMillis();
        RussianMorphlogyAnalayzer morphlogyAnalayzer = new RussianMorphlogyAnalayzer();
        System.out.println("To build analayzer take " + (System.currentTimeMillis() - startTime) + " ms.");        
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/russian/morphology/text.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));


        final Token reusableToken = new Token();

        Token nextToken;


        startTime = System.currentTimeMillis();
        Integer count = 0;
        TokenStream in = morphlogyAnalayzer.tokenStream(null, reader);
        for (; ;) {
            nextToken = in.next(reusableToken);
            count++;
            if (nextToken == null) {
                break;
            }

        }
        System.out.println("It takes " + (System.currentTimeMillis() - startTime) + " ms. To proccess  " + count + " words." );
    }
}
