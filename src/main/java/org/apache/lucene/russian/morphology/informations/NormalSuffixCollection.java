package org.apache.lucene.russian.morphology.informations;

import java.io.Serializable;


public class NormalSuffixCollection implements Serializable{
    private String[] normalSuffixes;

    public NormalSuffixCollection(String[] normalSuffixes) {
        this.normalSuffixes = normalSuffixes;
    }

    public String getSuffix(Integer index){
        return normalSuffixes[index];
    }
}
