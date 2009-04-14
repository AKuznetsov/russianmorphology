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
import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class RussianMorphlogyAnalayzerTest {

    @Test
    public void shouldCorrectProccessText() throws IOException {
        RussianMorphlogyAnalayzer morphlogyAnalayzer = new RussianMorphlogyAnalayzer();
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/russian/morphology/analayzer/russian-text.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        final Token reusableToken = new Token();

        Token nextToken;


        TokenStream in = morphlogyAnalayzer.tokenStream(null, bufferedReader);
        for (; ;) {
            nextToken = in.next(reusableToken);

            if (nextToken == null) {
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
