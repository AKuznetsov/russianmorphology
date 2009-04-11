package org.apache.lucene.russian.morphology.evristics;


public class SuffixCounter implements Comparable{
    private SuffixEvristic suffixEvristic;
    private Double amnout = 0.0;

    public SuffixCounter(SuffixEvristic suffixEvristic) {
        this.suffixEvristic = suffixEvristic;
    }

    public void incrementAmount(){
        amnout++;
    }

    public SuffixEvristic getSuffixEvristic() {
        return suffixEvristic;
    }

    public void setSuffixEvristic(SuffixEvristic suffixEvristic) {
        this.suffixEvristic = suffixEvristic;
    }

    public Double getAmnout() {
        return amnout;
    }

    public void setAmnout(Double amnout) {
        this.amnout = amnout;
    }

    public int compareTo(Object o) {
        if(o instanceof SuffixCounter) return (int) Math.round(Math.signum(((SuffixCounter)o).amnout - amnout));
        return -1;
    }

    @Override
    public String toString() {
        return ""+amnout + " " + suffixEvristic.toString();
    }
}
