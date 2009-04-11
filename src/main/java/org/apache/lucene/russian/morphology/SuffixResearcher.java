package org.apache.lucene.russian.morphology;

import org.apache.lucene.russian.morphology.dictonary.DirtonaryReader;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.IgnoredFormReader;
import org.apache.lucene.russian.morphology.evristics.StatiticsCollectors;
import org.apache.lucene.russian.morphology.evristics.SuffixCounter;
import org.apache.lucene.russian.morphology.evristics.Evristic;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class SuffixResearcher {
    public static void main(String[] args) throws IOException {
        IgnoredFormReader formReader = new IgnoredFormReader("igoredFrom.txt");
        Set<String> form = formReader.getIngnoredFroms();
        System.out.println(form);
        DirtonaryReader dirtonaryReader = new DirtonaryReader("morphs.mrd", form);
        StatiticsCollectors statiticsCollectors = new StatiticsCollectors();
        dirtonaryReader.proccess(statiticsCollectors);
        Collection<SuffixCounter> counterCollection = statiticsCollectors.getStatititics().values();
        Object[] objects = counterCollection.toArray();
        Arrays.sort(objects);
        System.out.println("Length " + objects.length + " ingored words " + statiticsCollectors.getIgnoredCount());
        for(int i = 0; i < 10; i++){
            System.out.println(objects[i]);
        }

        final Evristic evristic = new Evristic();
        for(int i = 0; i < objects.length; i++){
            evristic.addEvristic(((SuffixCounter) objects[i]).getSuffixEvristic());
        }
        final AtomicInteger good = new AtomicInteger(0);
        final AtomicInteger bad = new AtomicInteger(0);
        final FileWriter writer = new FileWriter("incorret.txt");
        dirtonaryReader.proccess(new WordProccessor(){
            public void proccess(WordCard wordCard) throws IOException {
                for(String wordForm:wordCard.getWordsFroms()){
                    String cf = wordCard.getCanonicalFrom();
                    if (evristic.getNormalForm(wordForm).equals(cf)){
                        good.incrementAndGet();
                    } else{
                        writer.write(wordForm + " c " + cf + " f " + evristic.getNormalForm(wordForm)  + "\n");
                        bad.incrementAndGet();
                    }
                }
            }
        });
        writer.close();

        System.out.println("Good " + good + " Bad " + bad);

        evristic.writeToFile("evriticsb");


//        Map<String, Set<String>> perehod = new HashMap<String,Set<String>>();
//        for(SuffixCounter suffixCounter:statiticsCollectors.getStatititics().values()){
//            String sf = suffixCounter.getSuffixEvristic().getFormSuffix();
//            Set<String> stringSet = perehod.get(sf);
//            if (stringSet == null){
//                stringSet = new HashSet<String>();
//                perehod.put(sf,stringSet);
//            }
//            stringSet.add(suffixCounter.getSuffixEvristic().getNormalSuffix());
//            //suffix.add(suffixCounter.getSuffixEvristic().getFormSuffix());
//            //System.out.println(suffixCounter.);
//        }
//        System.out.println("Diffirent suffix " + perehod.size());
//        int c = 0;
//        int max_size = 0;
//        int[] size_dist = new int[20];
//        for(int j = 0; j < size_dist.length; j++) size_dist[j] = 0;
//        for(Set<String> set:perehod.values()){
//            size_dist[set.size()] ++;
//            if (set.size() > 1){
//                c++;
//                //System.out.println(set);
//            }
//            if(set.size() > max_size) max_size = set.size();
//        }
//        System.out.println("max size of diffirent suffix " + max_size + " " + c);
//        for(int j = 0; j < size_dist.length; j++) System.out.println("" + j + " " + size_dist[j]);
    }
}
