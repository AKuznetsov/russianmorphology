package org.apache.lucene.russian.morphology.informations;

import org.apache.lucene.russian.morphology.RussianSuffixDecoderEncoder;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class LuceneMorph extends Morph{

    public LuceneMorph(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public List<String> getMorhInfo(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int[] ints = RussianSuffixDecoderEncoder.encodeToArray(revertWord(s));
        int ruleId = findRuleId(ints);
        for (Heuristic h : rules[rulesId[ruleId]]) {
            result.add(h.transofrmWord(s));
        }
        return result;
    }

    protected void readRules(BufferedReader bufferedReader) throws IOException {
        String s;
        Integer amount;
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        rules = new Heuristic[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer ruleLenght = Integer.valueOf(s1);
            Heuristic[] heuristics = new Heuristic[ruleLenght];
            for (int j = 0; j < ruleLenght; j++) {
                heuristics[j] = new Heuristic(bufferedReader.readLine());
            }
            rules[i] = modeifyHeuristic(heuristics);
        }
    }


    private Heuristic[] modeifyHeuristic(Heuristic[] heuristics){
        ArrayList<Heuristic> result = new ArrayList<Heuristic>();
        for(Heuristic heuristic:heuristics){
            boolean isAdded = true;
            for(Heuristic ch:result){
                isAdded = isAdded && !(ch.getActualNormalSuffix().equals(heuristic.getActualNormalSuffix()) && (ch.getActualSuffixLengh() == heuristic.getActualSuffixLengh()));
            }
            if(isAdded){
                result.add(heuristic);
            }
        }
        return result.toArray(new Heuristic[result.size()]);
    }
}
