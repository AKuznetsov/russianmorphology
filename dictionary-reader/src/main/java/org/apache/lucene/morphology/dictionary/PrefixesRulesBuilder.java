package org.apache.lucene.morphology.dictionary;

import org.apache.lucene.morphology.PrefixRule;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;


public class PrefixesRulesBuilder extends DictonaryReader {
    private Map<FlexiaModel,Set<FlexiaModel>> rules = new HashMap<FlexiaModel,Set<FlexiaModel>>();

    public PrefixesRulesBuilder(String fileName, Set<String> ingnoredForm) {
        super(fileName, ingnoredForm);
    }

    public PrefixesRulesBuilder(String fileName, String fileEncoding, Set<String> ingnoredForm) {
        super(fileName, fileEncoding, ingnoredForm);
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
            HashSet<String> map = new HashSet<String>();
            for(FlexiaModel fm:rules.get(key)){
                map.add(fm.getCode());
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
                Set<FlexiaModel> models1 = rules.get(convert(fm));
                if(models1 == null){
                    models1 = new HashSet<FlexiaModel>();
                    rules.put(convert(fm),models1);
                }
                models1.add(convert(com));
            }
        }
    }

    private FlexiaModel convert(FlexiaModel fm){
        String suf = fm.getSuffix();
        if(suf.length() == 1) System.out.println(fm);
        return new FlexiaModel(fm.getCode(),""+ suf.charAt(suf.length()-1)+ (suf.length() > 1 ?  suf.charAt(suf.length()-2) : ""),fm.getPrefix());
    }

    protected void addFlexia(ArrayList<FlexiaModel> flexiaModelArrayList, String line) {
        String[] fl = line.split("\\*");
        if (fl.length == 3) {
            flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), fl[2].toLowerCase()));
        }
        if (fl.length == 2) flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), ""));
    }

}
