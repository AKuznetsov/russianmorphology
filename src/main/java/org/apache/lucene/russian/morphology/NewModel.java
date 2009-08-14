package org.apache.lucene.russian.morphology;

import org.apache.lucene.russian.morphology.heuristic.SimpleSuffixHeuristic;
import org.apache.lucene.russian.morphology.dictonary.WordProccessor;
import org.apache.lucene.russian.morphology.dictonary.WordCard;
import org.apache.lucene.russian.morphology.dictonary.FlexiaModel;

import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;


public class NewModel implements WordProccessor{
    private TreeMap<String, Set<Heuristic>> inversIndex = new TreeMap<String,Set<Heuristic>>();

    public void proccess(WordCard wordCard) throws IOException {
        String normalStringMorph = wordCard.getWordsFroms().get(0).getCode();
        for (FlexiaModel fm : wordCard.getWordsFroms()) {
            Heuristic heuristic = createEvristic(wordCard.getBase(), wordCard.getCanonicalSuffix(), fm, normalStringMorph);
            String form = revertWord(fm.create(wordCard.getBase()));
            Set<Heuristic> suffixHeuristics = inversIndex.get(form);
            if(suffixHeuristics == null){
                suffixHeuristics = new HashSet<Heuristic>();
                inversIndex.put(form,suffixHeuristics);
            }
            suffixHeuristics.add(heuristic);
        }
    }


    public void printInfo(){
        System.out.println("All ivers words " + inversIndex.size());
        Set<Heuristic> prevSet = null;
        int count = 0;
        for(Set<Heuristic> currentSet:inversIndex.values()){
            if(!currentSet.equals(prevSet)){
                prevSet = currentSet;
                count++;
            }
        }
        System.out.println("Word with diffirent rules " + count);
    }

    private String revertWord(String s){
        String result = "";
        for (int i = 1; i <= s.length(); i++) {
            result += s.charAt(s.length() - i);
        }
        return result;
    }


    private Heuristic createEvristic(String wordBase, String canonicalSuffix, FlexiaModel fm, String normalSuffixForm) {
        String form = fm.create(wordBase);
        String normalForm = wordBase + canonicalSuffix;
        Integer length = getCommonLength(form, normalForm);
        Integer actualSuffixLengh = form.length() - length;
        String actualNormalSuffix = normalForm.substring(length);
        return new Heuristic(actualSuffixLengh, actualNormalSuffix, fm.getCode(), normalSuffixForm);
    }

    public static Integer getCommonLength(String s1, String s2) {
        Integer length = Math.min(s1.length(), s2.length());
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return length;
    }


    private class Heuristic{
        Integer actualSuffixLengh;
        String actualNormalSuffix;
        String formMorphInfo;
        String normalSuffixForm;

        private Heuristic(Integer actualSuffixLengh, String actualNormalSuffix, String formMorphInfo, String normalSuffixForm) {
            this.actualSuffixLengh = actualSuffixLengh;
            this.actualNormalSuffix = actualNormalSuffix;
            this.formMorphInfo = formMorphInfo;
            this.normalSuffixForm = normalSuffixForm;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Heuristic heuristic = (Heuristic) o;

            if (actualNormalSuffix != null ? !actualNormalSuffix.equals(heuristic.actualNormalSuffix) : heuristic.actualNormalSuffix != null)
                return false;
            if (actualSuffixLengh != null ? !actualSuffixLengh.equals(heuristic.actualSuffixLengh) : heuristic.actualSuffixLengh != null)
                return false;
            if (formMorphInfo != null ? !formMorphInfo.equals(heuristic.formMorphInfo) : heuristic.formMorphInfo != null)
                return false;
            if (normalSuffixForm != null ? !normalSuffixForm.equals(heuristic.normalSuffixForm) : heuristic.normalSuffixForm != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = actualSuffixLengh != null ? actualSuffixLengh.hashCode() : 0;
            result = 31 * result + (actualNormalSuffix != null ? actualNormalSuffix.hashCode() : 0);
            result = 31 * result + (formMorphInfo != null ? formMorphInfo.hashCode() : 0);
            result = 31 * result + (normalSuffixForm != null ? normalSuffixForm.hashCode() : 0);
            return result;
        }
    }
}
