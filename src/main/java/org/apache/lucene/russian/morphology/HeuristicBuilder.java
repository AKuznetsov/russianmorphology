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
import org.apache.lucene.russian.morphology.dictonary.FrequentyReader;
import org.apache.lucene.russian.morphology.dictonary.GrammaReader;
import org.apache.lucene.russian.morphology.dictonary.IgnoredFormReader;
import org.apache.lucene.russian.morphology.heuristic.HeuristicBySuffixLegth;
import org.apache.lucene.russian.morphology.heuristic.StatiticsCollectors;
import org.apache.lucene.russian.morphology.heuristic.SuffixCounter;
import org.apache.lucene.russian.morphology.heuristic.SuffixHeuristic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;


public class HeuristicBuilder {
    public static void main(String[] args) throws IOException {
        IgnoredFormReader formReader = new IgnoredFormReader("data/igoredFrom.txt");
        Set<String> form = formReader.getIngnoredFroms();

        FrequentyReader frequentyReader = new FrequentyReader("data/lemma.num");
        GrammaReader grammaInfo = new GrammaReader("dictonary/Dicts/Morph/rgramtab.tab");
        DictonaryReader dictonaryReader = new DictonaryReader("dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd", form);


        StatiticsCollectors statiticsCollectors = new StatiticsCollectors(frequentyReader.read(), grammaInfo);
        dictonaryReader.proccess(statiticsCollectors);
        Collection<SuffixCounter> counterCollection = statiticsCollectors.getStatititics().values();
        Object[] objects = counterCollection.toArray();
        Arrays.sort(objects);
        System.out.println("Length " + objects.length + " ingored words " + statiticsCollectors.getIgnoredCount());
        for (int i = 0; i < 10; i++) {
            System.out.println(objects[i]);
        }

        final HeuristicBySuffixLegth heuristic = new HeuristicBySuffixLegth();
        for (int i = 0; i < objects.length; i++) {
            heuristic.addHeuristic(((SuffixCounter) objects[i]).getSuffixHeuristic());
        }

        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

        int ct = 0;
        for (Set<SuffixHeuristic> s : heuristic.getHeuristics().values()) {
            Integer d = map.get(s.size());
            map.put(s.size(), 1 + (d == null ? 0 : d));
            if (s.size() == 1) {
                ct++;
                continue;
            }
            SuffixHeuristic heuristic1 = s.iterator().next();
            Integer sufixSize = heuristic1.getActualSuffixLength();
            String normalSuffix = heuristic1.getNormalFromSuffix();
            if (heuristic1.getFormSuffix().length() < 6) {
                ct++;
                continue;
            }
            Boolean flag = true;
            if (sufixSize > 3) continue;
            for (SuffixHeuristic sh : s) {
                flag = flag && (sufixSize.equals(sh.getActualSuffixLength()))
                        && (normalSuffix.equals(sh.getNormalFromSuffix()));
            }
            if (flag) {
                System.out.println(s);
                ct++;
            }
            //HashSet<String> integers = new HashSet<String>();
//            for(SuffixHeuristic sh:s){
//                integers.add(sh.getMorphInfoCode());
//            }
//            if(s.size() == integers.size()){
//                ct++;
//            }else{
//               if(s.size() == 2) System.out.println(s);
//            }
        }
        System.out.println(objects.length);
        System.out.println(heuristic.getHeuristics().size());
        System.out.println(ct);
        System.out.println(map);
        //heuristic.writeToFile("russianSuffixesHeuristic.txt");
    }
}
