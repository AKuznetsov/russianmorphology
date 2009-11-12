/**
 * Copyright 2009 Alexander Kuznetsov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.morphology;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class MorphologyWithPrefix extends Morphology {
    private Map<String, PrefixRule> prefixRuleMap = new HashMap<String, PrefixRule>();

    public MorphologyWithPrefix(String fileName, LetterDecoderEncoder decoderEncoder) throws IOException {
        super(fileName, decoderEncoder);
    }

    public MorphologyWithPrefix(InputStream morphFormInputStream, LetterDecoderEncoder decoderEncoder) throws IOException {
        super(morphFormInputStream, decoderEncoder);
    }

    public MorphologyWithPrefix(InputStream morphFormInputStream,InputStream prefixesInputStream, LetterDecoderEncoder decoderEncoder) throws IOException {
        super(morphFormInputStream, decoderEncoder);
        readPrefixes(prefixesInputStream);
    }

    private void readPrefixes(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        Integer prefixAmount = Integer.parseInt(bufferedReader.readLine());
        for(int i = 0; i < prefixAmount;i++){
            PrefixRule prefixRule = readPrefix(bufferedReader);
            prefixRuleMap.put(prefixRule.getHashString(),prefixRule);
        }
        bufferedReader.close();
    }

    private PrefixRule readPrefix(BufferedReader bufferedReader) throws IOException {
        PrefixRule prefixRule = new PrefixRule();
        String s = bufferedReader.readLine();
        prefixRule.setPrefix(s);
        s = bufferedReader.readLine();
        prefixRule.setLastLetter(s.charAt(0));
        HashSet<Short> morph = new HashSet<Short>();
        int formAmount = Integer.valueOf(bufferedReader.readLine());
        for(int i = 0; i < formAmount; i++){
            morph.add(Short.valueOf(bufferedReader.readLine()));
        }
        prefixRule.setForms(morph);
        return prefixRule;
    }

    public MorphologyWithPrefix(int[][] separators, short[] rulesId, Heuristic[][] rules, String[] grammaInfo) {
        super(separators, rulesId, rules, grammaInfo);
    }

    @Override
    public List<String> getMorhInfo(String s) {
        if (prefixRuleMap.size() == 0 || s.length() < 4) {
            return super.getMorhInfo(s);
        }
        String ruleIndex = "" + s.charAt(0) + s.charAt(s.length() - 1);
        PrefixRule prefixRule = prefixRuleMap.get(ruleIndex);
        if (prefixRule == null) {
            return super.getMorhInfo(s);
        }
        if (!s.startsWith(prefixRule.getPrefix())) {
            return super.getMorhInfo(s);
        }
        String sWithoutPrefix = s.substring(prefixRule.getPrefix().length());

        int[] ints = decoderEncoder.encodeToArray(revertWord(sWithoutPrefix));
        int ruleId = findRuleId(ints);
         ArrayList<String> result = new ArrayList<String>();
        for (Heuristic h : rules[rulesId[ruleId]]) {
            //String morphInfo = grammaInfo[];
            if(prefixRule.getForms().contains(h.getFormMorphInfo())){
                result.add(createForm(h.transofrmWord(sWithoutPrefix),"pr"));
            }
        }
        return result.size() > 0 ? result : super.getMorhInfo(s);
    }
}
