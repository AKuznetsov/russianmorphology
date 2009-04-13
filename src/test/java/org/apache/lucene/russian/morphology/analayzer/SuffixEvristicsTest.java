package org.apache.lucene.russian.morphology.analayzer;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.*;


public class SuffixEvristicsTest {

    @Test
    public void testShouldDefineCorretCononicalWordForm() throws IOException {
       SuffixEvristics suffixEvristics = new SuffixEvristics();
       InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/russian/morphology/analayzer/suffix-evristics-test-data.txt");
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
       String s = bufferedReader.readLine();
        while(s != null){
            String[] qa = s.trim().split(" ");
            assertThat(suffixEvristics.getCanonicalForm(qa[0]),equalTo(qa[1]));
            s = bufferedReader.readLine();
        }
    }
}
