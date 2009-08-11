package org.apache.lucene.russian.morphology.informations;

import java.io.Serializable;


public class GrammaInfo implements Serializable{
    private String[] grammaInfo;

    public GrammaInfo(String[] grammaInfo) {
        this.grammaInfo = grammaInfo;
    }

    public String getInfo(Integer index){
        return grammaInfo[index];
    }
}
