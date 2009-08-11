package org.apache.lucene.russian.morphology.heuristic;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class HeuristicBySuffixLegth {
    private Map<Long, Set<SimpleSuffixHeuristic>> heuristics = new HashMap<Long, Set<SimpleSuffixHeuristic>>();

    public void addHeuristic(SimpleSuffixHeuristic simpleSuffixHeuristic) {
        Long suffix = RussianSuffixDecoderEncoder.encode(simpleSuffixHeuristic.getFormSuffix());
        Set<SimpleSuffixHeuristic> simpleSuffixHeuristics = heuristics.get(suffix);
        if (simpleSuffixHeuristics == null) {
            simpleSuffixHeuristics = new HashSet<SimpleSuffixHeuristic>();
            heuristics.put(suffix, simpleSuffixHeuristics);
        }
        simpleSuffixHeuristics.add(simpleSuffixHeuristic);
    }

    public Map<Long, Set<SimpleSuffixHeuristic>> getHeuristics() {
        return heuristics;
    }

    public Map<Long, SimpleSuffixHeuristic> getSingleSuffixes() {
        HashMap<Long, SimpleSuffixHeuristic> result = new HashMap<Long, SimpleSuffixHeuristic>();
        for (Long st : heuristics.keySet()) {
            if (heuristics.get(st).size() == 1) {
                result.put(st, heuristics.get(st).iterator().next());
            }
        }
        return result;
    }


    public Map<Long, Set<SimpleSuffixHeuristic>> getWordWithMorphology() {
        HashMap<Long, Set<SimpleSuffixHeuristic>> result = new HashMap<Long, Set<SimpleSuffixHeuristic>>();
        for (Long st : heuristics.keySet()) {
            if (heuristics.get(st).size() == 1) continue;
            if (checkSetOnSuffix(heuristics.get(st))) {
                result.put(st, heuristics.get(st));
            }
        }
        return result;
    }

    public Map<Long, Set<SimpleSuffixHeuristic>> getOnonyms() {
        HashMap<Long, Set<SimpleSuffixHeuristic>> result = new HashMap<Long, Set<SimpleSuffixHeuristic>>();
        for (Long st : heuristics.keySet()) {
            if (heuristics.get(st).size() == 1) continue;
            if (checkSetOnSuffix(heuristics.get(st))) continue;
            if (heuristics.get(st).iterator().next().getFormSuffix().length() < 6) {
                result.put(st, heuristics.get(st));
            }
        }
        return result;
    }

    public Map<Long, Set<SimpleSuffixHeuristic>> getUnkowns() {
        HashMap<Long, Set<SimpleSuffixHeuristic>> result = new HashMap<Long, Set<SimpleSuffixHeuristic>>();
        for (Long st : heuristics.keySet()) {
            if (heuristics.get(st).size() == 1) continue;
            if (checkSetOnSuffix(heuristics.get(st))) continue;
            if (heuristics.get(st).iterator().next().getFormSuffix().length() >= 6) {
                result.put(st, heuristics.get(st));
            }
        }
        return result;
    }

    private Boolean checkSetOnSuffix(Set<SimpleSuffixHeuristic> sshs) {
        SimpleSuffixHeuristic heuristic = sshs.iterator().next();
        String normalSuffix = heuristic.getNormalSuffix();
        Integer suffixLenght = heuristic.getActualSuffixLength();
        Boolean result = true;
        for (SimpleSuffixHeuristic ssh : sshs) {
            result = result && ssh.getActualSuffixLength().equals(suffixLenght) && ssh.getNormalSuffix().endsWith(normalSuffix);
        }
        return result;
    }

}
