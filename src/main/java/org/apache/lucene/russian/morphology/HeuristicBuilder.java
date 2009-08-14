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

import org.apache.lucene.russian.morphology.dictonary.*;
import org.apache.lucene.russian.morphology.heuristic.HeuristicBySuffixLegth;
import org.apache.lucene.russian.morphology.heuristic.SimpleSuffixHeuristic;
import org.apache.lucene.russian.morphology.heuristic.StatiticsCollectors;
import org.apache.lucene.russian.morphology.heuristic.SuffixCounter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


public class HeuristicBuilder {
    public static void main(String[] args) throws IOException {
        IgnoredFormReader formReader = new IgnoredFormReader("data/igoredFrom.txt");
        Set<String> form = formReader.getIngnoredFroms();

        FrequentyReader frequentyReader = new FrequentyReader("data/lemma.num");
        GrammaReader grammaInfo = new GrammaReader("dictonary/Dicts/Morph/rgramtab.tab");
        DictonaryReader dictonaryReader = new DictonaryReader("dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd", form);

        NewModel newModel = new NewModel();
        dictonaryReader.proccess(newModel);
        newModel.printInfo();


//        StatiticsCollectors statiticsCollectors = new StatiticsCollectors(frequentyReader.read());
//        dictonaryReader.proccess(statiticsCollectors);
//        Collection<SuffixCounter> counterCollection = statiticsCollectors.getStatititics().values();
//        Object[] objects = counterCollection.toArray();
//        Arrays.sort(objects);
//        System.out.println("Length " + objects.length + " ingored words " + statiticsCollectors.getIgnoredCount());
//        for (int i = 0; i < 10; i++) {
//            System.out.println(objects[i]);
//        }
//
//        final HeuristicBySuffixLegth heuristic = new HeuristicBySuffixLegth();
//        for (int i = 0; i < objects.length; i++) {
//            heuristic.addHeuristic(((SuffixCounter) objects[i]).getSuffixHeuristic());
//        }
//
//        System.out.println("Single suffix " + heuristic.getSingleSuffixes().size());
//        System.out.println("diffiren morgh " + heuristic.getWordWithMorphology().size());
//        System.out.println("Ononims " + heuristic.getOnonyms().size());
//        final Map<Long, Set<SimpleSuffixHeuristic>> map = heuristic.getUnkowns();
//        System.out.println("Unknow suffix " + map.size());
//        int cont = 0;
//        for (Set<SimpleSuffixHeuristic> st : map.values()) {
//
//            if (cont > 50) break;
//            if (st.size() < 3) {
//                System.out.println(st);
//                cont++;
//            }
//        }
//        //final RussianSuffixDecoderEncoder decoderEncoder = new RussianSuffixDecoderEncoder(6);
//        final AtomicLong c = new AtomicLong(0L);
//        final AtomicLong all = new AtomicLong(0L);
//        dictonaryReader.proccess(
//                new WordProccessor() {
//                    public void proccess(WordCard wordCard) throws IOException {
//                        for (FlexiaModel fm : wordCard.getWordsFroms()) {
//                            String form = fm.create(wordCard.getBase());
//                            if(form.startsWith("прик") && form.endsWith("ья")) System.out.println(form);
//
//
//                            int startSymbol = form.length() > RussianSuffixDecoderEncoder.suffixLength ? form.length() - RussianSuffixDecoderEncoder.suffixLength : 0;
//                            String formSuffix = form.substring(startSymbol);
//                            Long aLong = RussianSuffixDecoderEncoder.encode(formSuffix);
//                            all.incrementAndGet();
//                            if (map.containsKey(aLong)) c.incrementAndGet();
//                        }
//                    }
//                }
//        );
//
//
//        System.out.println("Ankown words " + all.longValue());
//        System.out.println("Ankown words " + c.longValue());
    }
}
