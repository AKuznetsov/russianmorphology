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


import org.apache.lucene.morphology.Heuristic;
import org.apache.lucene.morphology.Morph;

import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

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
        Morph splitter = new Morph("sep.txt",new RussianLetterDecoderEncoder());
        TreeSet<Short> shorts = new TreeSet<Short>();
        int count = 0;
        TreeMap<Integer, Integer> rulesStat = new TreeMap<Integer, Integer>();
        for (Heuristic[] heuristics : splitter.getRules()) {
            Integer d = rulesStat.get(heuristics.length);
            rulesStat.put(heuristics.length, 1 + (d == null ? 0 : d));
            boolean flag = true;
            short actualSuffixLenght = heuristics[0].getActualSuffixLengh();
            String normalSuffix = heuristics[0].getActualNormalSuffix();
            for (Heuristic heuristic : heuristics) {
                flag = flag && (heuristic.getActualSuffixLengh() == actualSuffixLenght)
                        && normalSuffix.equals(heuristic.getActualNormalSuffix());
            }
            if (!flag) {
                System.out.println(Arrays.asList(heuristics));
                count++;
            }
        }
        System.out.println(count);
        System.out.println(rulesStat);
        System.gc();
        System.in.read();
    }
}
