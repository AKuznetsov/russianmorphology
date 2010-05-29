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

package org.apache.lucene.morphology.generator;

import org.apache.lucene.morphology.dictionary.DictonaryReader;
import org.apache.lucene.morphology.dictionary.GrammaReader;
import org.apache.lucene.morphology.dictionary.StatisticsCollector;
import org.apache.lucene.morphology.russian.RussianLetterDecoderEncoder;

import java.io.IOException;
import java.util.HashSet;


public class RussianHeuristicBuilder {
    public static void main(String[] args) throws IOException {
        GrammaReader grammaInfo = new GrammaReader("dictonary/Dicts/Morph/rgramtab.tab");
        DictonaryReader dictonaryReader = new DictonaryReader("dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd", new HashSet<String>());

        RussianLetterDecoderEncoder decoderEncoder = new RussianLetterDecoderEncoder();
        StatisticsCollector statisticsCollector = new StatisticsCollector(grammaInfo, decoderEncoder);
        dictonaryReader.proccess(statisticsCollector);
        statisticsCollector.saveHeuristic("russian/src/main/resources/org/apache/lucene/morphology/russian/morph.info");

    }
}
