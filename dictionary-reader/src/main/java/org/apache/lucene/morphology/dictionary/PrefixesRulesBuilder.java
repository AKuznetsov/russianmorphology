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
package org.apache.lucene.morphology.dictionary;

import org.apache.lucene.morphology.PrefixRule;

import java.util.*;
import java.io.*;


public class PrefixesRulesBuilder extends DictonaryReader {
    private GrammaReader grammaInfo;

    private Map<FlexiaModel,Set<FlexiaModel>> rules = new HashMap<FlexiaModel,Set<FlexiaModel>>();

    public PrefixesRulesBuilder(String fileName, String fileEncoding, Set<String> ingnoredForm) throws IOException {
        super(fileName, fileEncoding, ingnoredForm);
        grammaInfo = new GrammaReader("dictonary/Dicts/Morph/rgramtab.tab");
    }

    @Override
    public void proccess(WordProccessor wordProccessor) throws IOException {
        super.proccess(wordProccessor);
        System.out.println(rules.size());
        System.out.println(rules);
    }

    public List<PrefixRule> getPrefixRules(){
        List<PrefixRule> prefixRules = new ArrayList<PrefixRule>();
        for(FlexiaModel key:rules.keySet()){
            PrefixRule prefixRule = new PrefixRule();
            prefixRule.setPrefix(key.getPrefix());
            prefixRule.setLastLetter(key.getSuffix().charAt(0));
            HashSet<Short> map = new HashSet<Short>();
            for(FlexiaModel fm:rules.get(key)){
                int gi = grammaInfo.getGrammInversIndex().get(fm.getCode());
                map.add((short) gi);
            }
            prefixRule.setForms(map);
            prefixRules.add(prefixRule);
        }
        return prefixRules;
    }

    @Override
    protected void readWords(BufferedReader reader, WordProccessor wordProccessor) throws IOException {
        sckipBlock(reader);
    }



    @Override
    protected void readPrefix(BufferedReader reader) throws IOException {
        sckipBlock(reader);
    }

    @Override
    protected void readFlexias(BufferedReader reader) throws IOException {
        super.readFlexias(reader);
        //todo research flesias
        for(List<FlexiaModel> fmList:wordsFlexias){
            research(fmList);
        }
    }

    private void research(List<FlexiaModel> models) {
        for(FlexiaModel fm:models){
            if(fm.getPrefix().length() > 0){
                testFlexia(models, fm);
            }
        }
    }

    private void testFlexia(List<FlexiaModel> models, FlexiaModel fm) {
        for(FlexiaModel com:models){
            if(com.getSuffix().equals(fm.getSuffix()) && com.getPrefix().length() == 0){
                Set<FlexiaModel> models1 = rules.get(convertForKey(fm));
                if(models1 == null){
                    models1 = new HashSet<FlexiaModel>();
                    rules.put(convertForKey(fm),models1);
                }
                models1.add(convert(com));
            }
        }
    }

    private FlexiaModel convert(FlexiaModel fm){
        String suf = fm.getSuffix();
        //if(suf.length() == 1) System.out.println(fm);
        return new FlexiaModel(fm.getCode(),""+ suf.charAt(suf.length()-1),fm.getPrefix());
    }

    private FlexiaModel convertForKey(FlexiaModel fm){
        String suf = fm.getSuffix();
        //if(suf.length() == 1) System.out.println(fm);
        return new FlexiaModel("pr",""+ suf.charAt(suf.length()-1),fm.getPrefix());
    }

    protected void addFlexia(ArrayList<FlexiaModel> flexiaModelArrayList, String line) {
        String[] fl = line.split("\\*");
        if (fl.length == 3) {
            flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), fl[2].toLowerCase()));
        }
        if (fl.length == 2) flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), ""));
    }

    public void savePrefixes(String fileName) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
        List<PrefixRule> prefixRuleList = getPrefixRules();
        writer.write(prefixRuleList.size()+"\n");
        for(PrefixRule pr: prefixRuleList){
            writePrefixRule(writer, pr);
        }
        writer.close();
    }

    private void writePrefixRule(OutputStreamWriter writer, PrefixRule pr) throws IOException {
        writer.write(pr.getPrefix()+"\n");
        writer.write(pr.getLastLetter()+"\n");
        HashSet<Short> formInfo = pr.getForms();
        writer.write(formInfo.size()+"\n");
        for(Short s:formInfo){
            writer.write(s+"\n");
        }
    }
}
