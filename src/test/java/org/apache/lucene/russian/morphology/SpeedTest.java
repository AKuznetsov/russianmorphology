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
package org.apache.lucene.russian.morphology;

import org.apache.lucene.russian.morphology.informations.Morph;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SpeedTest {

    @Test
    public void getTestOfSpeed() throws IOException {
        Morph splitter = new Morph("sep.txt");
        InputStream stream = Test.class.getResourceAsStream("/org/apache/lucene/russian/morphology/analayzer/russian-text.txt");
        BufferedReader stream1 = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String s = stream1.readLine().trim().toLowerCase();
        for (String w : s.split(" ")) {
            try {
                System.out.println(w);
                System.out.println(splitter.getMorhInfo(w));
            } catch (WrongCharaterException e) {

            }
        }
//        Long startTime = System.currentTimeMillis();
//        RussianMorphlogyAnalayzer morphlogyAnalayzer = new RussianMorphlogyAnalayzer();
//        System.out.println("To build analayzer take " + (System.currentTimeMillis() - startTime) + " ms.");
//        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/russian/morphology/text.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//
//
//        final Token reusableToken = new Token();
//
//        Token nextToken;
//
//
//        startTime = System.currentTimeMillis();
//        Integer count = 0;
//        TokenStream in = morphlogyAnalayzer.tokenStream(null, reader);
//        for (; ;) {
//            nextToken = in.next(reusableToken);
//            count++;
//            if (nextToken == null) {
//                break;
//            }
//
//        }
//        System.out.println("It takes " + (System.currentTimeMillis() - startTime) + " ms. To proccess  " + count + " words." );
    }
}
