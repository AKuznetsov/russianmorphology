package org.apache.lucene.russian.morphology.heuristic;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class HeuristicBySuffixLegth {
    private Map<Long, Set<SuffixHeuristic>> heuristics = new HashMap<Long, Set<SuffixHeuristic>>();

    public void addHeuristic(SuffixHeuristic suffixHeuristic) {
        Long suffix = RussianSuffixDecoderEncoder.encode(suffixHeuristic.getFormSuffix());
        Set<SuffixHeuristic> suffixHeuristics = heuristics.get(suffix);
        if (suffixHeuristics == null) {
            suffixHeuristics = new HashSet<SuffixHeuristic>();
            heuristics.put(suffix, suffixHeuristics);
        }
        suffixHeuristics.add(suffixHeuristic);
    }

    public Map<Long, Set<SuffixHeuristic>> getHeuristics() {
        return heuristics;
    }
}
