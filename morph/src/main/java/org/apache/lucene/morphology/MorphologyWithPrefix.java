package org.apache.lucene.morphology;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class MorphologyWithPrefix extends Morphology {
    private Map<String, PrefixRule> prefixRuleMap = new HashMap<String, PrefixRule>();

    public MorphologyWithPrefix(String fileName, LetterDecoderEncoder decoderEncoder) throws IOException {
        super(fileName, decoderEncoder);
    }

    public MorphologyWithPrefix(InputStream inputStream, LetterDecoderEncoder decoderEncoder) throws IOException {
        super(inputStream, decoderEncoder);
    }

    public MorphologyWithPrefix(int[][] separators, short[] rulesId, Heuristic[][] rules, String[] grammaInfo) {
        super(separators, rulesId, rules, grammaInfo);
    }

    @Override
    public List<String> getMorhInfo(String s) {
        if (s.length() < 4) {
            return super.getMorhInfo(s);
        }
        String ruleIndex = "" + s.charAt(0) + s.charAt(s.length() - 1);
        PrefixRule prefixRule = prefixRuleMap.get(ruleIndex);
        if (prefixRule == null) {
            return super.getMorhInfo(s);
        }
        if (s.startsWith(prefixRule.getPrefix())) {
            return super.getMorhInfo(s);
        }
        String sWithoutPrefix = s.substring(prefixRule.getPrefix().length());

        int[] ints = decoderEncoder.encodeToArray(revertWord(sWithoutPrefix));
        int ruleId = findRuleId(ints);
         ArrayList<String> result = new ArrayList<String>();
        for (Heuristic h : rules[rulesId[ruleId]]) {
            String morphInfo = grammaInfo[h.getFormMorphInfo()];
            if(prefixRule.getForms().contains(morphInfo)){
                result.add(createForm(h.transofrmWord(sWithoutPrefix),"pr"));
            }
        }
        return result.size() > 0 ? result : super.getMorhInfo(s);
    }
}
