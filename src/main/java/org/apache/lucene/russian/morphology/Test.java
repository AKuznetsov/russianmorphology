package org.apache.lucene.russian.morphology;

import org.apache.lucene.russian.morphology.dictonary.GrammaReader;

import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException {
        GrammaReader grammaReader = new GrammaReader("dictonary/Dicts/Morph/rgramtab.tab");
        System.out.println(grammaReader.getInversIndex().size());
    }
}
