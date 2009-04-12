package org.apache.lucene.russian.morphology;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import org.apache.lucene.russian.morphology.SuffixToLongException;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class RussianSuffixDecoderEncoderTest {

    @Test
    public void testShouldCorretDecodeEncode() throws IOException {
       InputStream stream = this.getClass().getResourceAsStream("/decoder-test-data.txt");
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
       String s = bufferedReader.readLine();
        while(s != null){
            String[] qa = s.trim().split(" ");
            Long ecodedSuffix = RussianSuffixDecoderEncoder.encode(qa[0]);
            assertThat(RussianSuffixDecoderEncoder.decode(ecodedSuffix),equalTo(qa[1]));
            s = bufferedReader.readLine();
        }
    }

    @Test(expected = SuffixToLongException.class)
    public void shouldThrownExeptionIfSuffixToLong(){
         RussianSuffixDecoderEncoder.encode("1234567890123");
    }

    @Test(expected = WrongCharaterException.class)
    public void shouldThrownExeptionIfSuffixContainWrongCharater(){
         RussianSuffixDecoderEncoder.encode("1");
    }    
    
}
