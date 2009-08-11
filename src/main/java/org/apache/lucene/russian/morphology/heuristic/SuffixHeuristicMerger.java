package org.apache.lucene.russian.morphology.heuristic;


public class SuffixHeuristicMerger {

    public SuffixHeuristic merge(SuffixHeuristic one, SuffixHeuristic two) {
        if (!one.getMorphInfoCode().equals(two.getMorphInfoCode()))
            return null;
        SuffixHeuristic min = one.getActualSuffixLength() > two.getActualSuffixLength() ? two : one;

        return null;
    }
}
