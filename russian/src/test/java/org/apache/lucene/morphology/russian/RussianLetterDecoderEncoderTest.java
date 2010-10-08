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
package org.apache.lucene.morphology.russian;

import org.apache.lucene.morphology.SuffixToLongException;
import org.apache.lucene.morphology.WrongCharaterException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RussianLetterDecoderEncoderTest {
    private RussianLetterDecoderEncoder decoderEncoder;

    @Before
    public void setUp() {
        decoderEncoder = new RussianLetterDecoderEncoder();
    }


    @Test
    public void testShouldPreserverStringComporision() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/morphology/russian/decoder-test-monotonic.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String s = bufferedReader.readLine();
        while (s != null) {
            String[] qa = s.trim().split(" ");
            if (qa[0].length() <= RussianLetterDecoderEncoder.WORD_PART_LENGHT && qa[1].length() <= RussianLetterDecoderEncoder.WORD_PART_LENGHT) {
                assertThat(decoderEncoder.encode(qa[1]) > decoderEncoder.encode(qa[0]), equalTo(true));
            }
            s = bufferedReader.readLine();
        }
    }


    @Test
    public void testShouldCorrectDecodeEncode() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/morphology/russian/decoder-test-data.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String s = bufferedReader.readLine();
        while (s != null) {
            String[] qa = s.trim().split(" ");
            if (qa[0].length() <= RussianLetterDecoderEncoder.WORD_PART_LENGHT) {
                Integer encodedSuffix = decoderEncoder.encode(qa[0]);
                assertThat(decoderEncoder.decode(encodedSuffix), equalTo(qa[1]));
            }
            s = bufferedReader.readLine();
        }
    }

    @Test
    public void testShouldCorrectDecodeEncodeStringToArray() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/morphology/russian/decoder-test-data-for-array.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String s = bufferedReader.readLine();
        while (s != null) {
            String[] qa = s.trim().split(" ");
            int[] ecodedSuffix = decoderEncoder.encodeToArray(qa[0]);
            assertThat(decoderEncoder.decodeArray(ecodedSuffix), equalTo(qa[1]));
            s = bufferedReader.readLine();
        }
    }

    @Test(expected = SuffixToLongException.class)
    public void shouldThrownExeptionIfSuffixToLong() {
        decoderEncoder.encode("1234567890123");
    }

    @Test(expected = WrongCharaterException.class)
    public void shouldThrownExceptionIfSuffixContainWrongCharater() {
        decoderEncoder.encode("1");
    }
}
