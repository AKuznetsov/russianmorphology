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

import org.apache.lucene.russian.morphology.dictonary.DictonaryReader;
import org.apache.lucene.russian.morphology.dictonary.IgnoredFormReader;
import org.apache.lucene.russian.morphology.evristics.Evristic;
import org.apache.lucene.russian.morphology.evristics.StatiticsCollectors;
import org.apache.lucene.russian.morphology.evristics.SuffixCounter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;


public class EvristicBuilder {
    public static void main(String[] args) throws IOException {
        IgnoredFormReader formReader = new IgnoredFormReader("data/igoredFrom.txt");
        Set<String> form = formReader.getIngnoredFroms();

        DictonaryReader dictonaryReader = new DictonaryReader("dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd", form);
        StatiticsCollectors statiticsCollectors = new StatiticsCollectors();
        dictonaryReader.proccess(statiticsCollectors);
        Collection<SuffixCounter> counterCollection = statiticsCollectors.getStatititics().values();
        Object[] objects = counterCollection.toArray();
        Arrays.sort(objects);
        System.out.println("Length " + objects.length + " ingored words " + statiticsCollectors.getIgnoredCount());
        for (int i = 0; i < 10; i++) {
            System.out.println(objects[i]);
        }

        final Evristic evristic = new Evristic();
        for (int i = 0; i < objects.length; i++) {
            evristic.addEvristic(((SuffixCounter) objects[i]).getSuffixEvristic());
        }

        evristic.writeToFile("src/main/resources/org/apache/lucene/russian/morpholgy/russianSuffixesEvristics.txt");
    }
}
