package org.apache.lucene.russian.morphology.informations;

import java.io.Serializable;


public class Heuristic implements Serializable {
    byte actualSuffixLengh;
    String actualNormalSuffix;
    short formMorphInfo;
    short normalFormMorphInfo;

    public Heuristic(String s) {
        String[] strings = s.split("\\|");
        actualSuffixLengh = Byte.valueOf(strings[0]);
        actualNormalSuffix = strings[1];
        formMorphInfo = Short.valueOf(strings[2]);
        normalFormMorphInfo = Short.valueOf(strings[3]);
    }

    public Heuristic(byte actualSuffixLengh, String actualNormalSuffix, short formMorphInfo, short normalFormMorphInfo) {
        this.actualSuffixLengh = actualSuffixLengh;
        this.actualNormalSuffix = actualNormalSuffix;
        this.formMorphInfo = formMorphInfo;
        this.normalFormMorphInfo = normalFormMorphInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Heuristic heuristic = (Heuristic) o;

        if (actualSuffixLengh != heuristic.actualSuffixLengh) return false;
        if (formMorphInfo != heuristic.formMorphInfo) return false;
        if (normalFormMorphInfo != heuristic.normalFormMorphInfo) return false;
        if (actualNormalSuffix != null ? !actualNormalSuffix.equals(heuristic.actualNormalSuffix) : heuristic.actualNormalSuffix != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) actualSuffixLengh;
        result = 31 * result + (actualNormalSuffix != null ? actualNormalSuffix.hashCode() : 0);
        result = 31 * result + (int) formMorphInfo;
        result = 31 * result + (int) normalFormMorphInfo;
        return result;
    }

    @Override
    public String toString() {
        return "" + actualSuffixLengh + "|" + actualNormalSuffix + "|" + formMorphInfo + "|" + normalFormMorphInfo;
    }
}
