package org.apache.lucene.morphology.context;

/**
 * Created by alexander on 16.06.15.
 */
public class ContextItem {
    String[][] morphInfo;
    long count = 0;

    public ContextItem(String[][] morphInfo) {
        this.morphInfo = morphInfo;
    }

    public String[][] getMorphInfo() {
        return morphInfo;
    }

    public void setMorphInfo(String[][] morphInfo) {
        this.morphInfo = morphInfo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
