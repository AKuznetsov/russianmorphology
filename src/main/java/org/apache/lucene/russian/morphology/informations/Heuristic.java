package org.apache.lucene.russian.morphology.informations;

import java.io.Serializable;


public class Heuristic implements Serializable {
    byte actualSuffixLengh;
    String actualNormalSuffix;
    short formMorphInfo;
    short normalSuffixForm;

    public Heuristic(byte actualSuffixLengh, String actualNormalSuffix, short formMorphInfo, short normalSuffixForm) {
        this.actualSuffixLengh = actualSuffixLengh;
        this.actualNormalSuffix = actualNormalSuffix;
        this.formMorphInfo = formMorphInfo;
        this.normalSuffixForm = normalSuffixForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Heuristic heuristic = (Heuristic) o;

        if (actualSuffixLengh != heuristic.actualSuffixLengh) return false;
        if (formMorphInfo != heuristic.formMorphInfo) return false;
        if (normalSuffixForm != heuristic.normalSuffixForm) return false;
        if (actualNormalSuffix != null ? !actualNormalSuffix.equals(heuristic.actualNormalSuffix) : heuristic.actualNormalSuffix != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) actualSuffixLengh;
        result = 31 * result + (actualNormalSuffix != null ? actualNormalSuffix.hashCode() : 0);
        result = 31 * result + (int) formMorphInfo;
        result = 31 * result + (int) normalSuffixForm;
        return result;
    }
}
