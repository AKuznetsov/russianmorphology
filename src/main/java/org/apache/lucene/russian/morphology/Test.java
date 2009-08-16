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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        Morph splitter = new Morph("sep.txt");
        InputStream stream = Test.class.getResourceAsStream("/org/apache/lucene/russian/morphology/analayzer/russian-text.txt");
        BufferedReader stream1 = new BufferedReader(new InputStreamReader(stream));
        String s = stream1.readLine().trim().toLowerCase();
        for (String w : s.split(" ")) {
            System.out.println(splitter.getMorhInfo(w));
        }
        System.gc();
        System.out.println("Ready");
        System.in.read();
    }
}
