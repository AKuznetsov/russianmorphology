package org.apache.lucene.russian.morphology.dictonary;

import org.apache.lucene.russian.morphology.dictonary.FlexiaModel;
import com.frielp.morph.automate.WordImpl;
import org.apache.lucene.russian.morphology.evristics.RussianSuffixDecoderEncoder;

import java.util.*;
import java.io.*;


public class DirtonaryReader {
    private String fileName;
    private String fileEncoding = "windows-1251";
    private List<List<FlexiaModel>> wordsFlexias = new ArrayList<List<FlexiaModel>>();
    private List<List<String>> wordPrefixes = new ArrayList<List<String>>();
    private Set<String> ingnoredForm =  new HashSet<String>();

    public DirtonaryReader(String fileName, Set<String> ingnoredForm) {
        this.fileName = fileName;
        this.ingnoredForm = ingnoredForm;
    }

    public DirtonaryReader(String fileName, String fileEncoding, Set<String> ingnoredForm) {
        this.fileName = fileName;
        this.fileEncoding = fileEncoding;
        this.ingnoredForm = ingnoredForm;
    }


    public void proccess(WordProccessor wordProccessor) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), fileEncoding));
        readFlexias(bufferedReader);
        sckipBlock(bufferedReader);
        sckipBlock(bufferedReader);
        readPrefix(bufferedReader);
        readWords(bufferedReader,wordProccessor);
    }


    private void readWords(BufferedReader reader,WordProccessor wordProccessor) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
            if (i % 10000 == 0) System.out.println("Proccess " + i + " word of " + count);

            String[] wd = s.split(" ");
            String word = wd[0].toLowerCase();
            if (word.startsWith("-")) continue;
            word = "#".equals(word) ? "" : word;
            List<FlexiaModel> models = wordsFlexias.get(Integer.valueOf(wd[1]));
            if (models.size() > 0 && !ingnoredForm.contains(models.get(0).getCode())) {
                WordCard card = new WordCard(cleanString(models.get(0).create(word)));
                for (FlexiaModel fm : models) {
                       card.addFrom(cleanString(fm.create(word)));
                }
                wordProccessor.proccess(card);
            }
        }
    }

    private String cleanString(String s){
        return s.replace((char)(34 + RussianSuffixDecoderEncoder.RUSSIAN_SMALL_LETTER_OFFSET),(char)(6 + RussianSuffixDecoderEncoder.RUSSIAN_SMALL_LETTER_OFFSET));
    }

    private void sckipBlock(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
        }
    }


    private void readPrefix(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
            wordPrefixes.add(Arrays.asList(s.toLowerCase().split(",")));
        }
    }

    private void readFlexias(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
            ArrayList<FlexiaModel> flexiaModelArrayList = new ArrayList<FlexiaModel>();
            wordsFlexias.add(flexiaModelArrayList);
            for (String line : s.split("%")) {
                addFlexia(flexiaModelArrayList, line);
            }
        }
    }

    private void addFlexia(ArrayList<FlexiaModel> flexiaModelArrayList, String line) {
        String[] fl = line.split("\\*");
      //  if (fl.length == 3)
      //      flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), fl[2].toLowerCase()));
        if (fl.length == 2) flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), ""));
    }

}
