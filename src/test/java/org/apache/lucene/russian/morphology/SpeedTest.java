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

import org.junit.Test;

import java.io.IOException;


public class SpeedTest {

    @Test
    public void getTestOfSpeed() throws IOException {
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
