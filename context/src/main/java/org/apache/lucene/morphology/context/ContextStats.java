package org.apache.lucene.morphology.context;

/**
 * Created by alexander on 16.06.15.
 */
public class ContextStats {
    String[] morphInfo;
    double prob;

    public String[] getMorphInfo() {
        return morphInfo;
    }

    public void setMorphInfo(String[] morphInfo) {
        this.morphInfo = morphInfo;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }
}
