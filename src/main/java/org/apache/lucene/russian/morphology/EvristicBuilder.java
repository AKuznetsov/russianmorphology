package org.apache.lucene.russian.morphology;

import org.apache.lucene.russian.morphology.dictonary.DictonaryReader;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.IgnoredFormReader;
import org.apache.lucene.russian.morphology.evristics.StatiticsCollectors;
import org.apache.lucene.russian.morphology.evristics.SuffixCounter;
import org.apache.lucene.russian.morphology.evristics.Evristic;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


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
        for(int i = 0; i < 10; i++){
            System.out.println(objects[i]);
        }

        final Evristic evristic = new Evristic();
        for(int i = 0; i < objects.length; i++){
            evristic.addEvristic(((SuffixCounter) objects[i]).getSuffixEvristic());
        }

        evristic.writeToFile("src/main/resources/russianSuffixesEvristics.txt");
    }
}
