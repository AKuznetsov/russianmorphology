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


import org.apache.lucene.morphology.Morph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: akuznetsov
 * Date: 15.08.2009
 * Time: 16:52:24
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //
        Morph splitter = new Morph("russian/src/main/resources/org/apache/lucene/morphology/russian/morph.info", new RussianLetterDecoderEncoder());
        FileReader fileReader = new FileReader("russian/src/main/resources/for.test.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        while (s != null) {
            System.out.println(splitter.getMorhInfo(s));
            s = bufferedReader.readLine();
        }

        fileReader.close();
        System.gc();
        System.in.read();
    }
}
