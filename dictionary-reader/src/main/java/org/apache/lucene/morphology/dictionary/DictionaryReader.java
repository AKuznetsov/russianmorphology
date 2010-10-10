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


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class contain logic how read
 * dictonary and produce word with it all forms.
 */
public class DictionaryReader {
    private String fileName;
    private String fileEncoding = "windows-1251";
    private List<List<FlexiaModel>> wordsFlexias = new ArrayList<List<FlexiaModel>>();
    private Set<String> ignoredForm = new HashSet<String>();

    public DictionaryReader(String fileName, Set<String> ignoredForm) {
        this.fileName = fileName;
        this.ignoredForm = ignoredForm;
    }


    public void process(WordProcessor wordProcessor) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), fileEncoding));
        readFlexias(bufferedReader);
        skipBlock(bufferedReader);
        skipBlock(bufferedReader);
        readPrefix(bufferedReader);
        readWords(bufferedReader, wordProcessor);
    }


    private void readWords(BufferedReader reader, WordProcessor wordProcessor) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        int actual = 0;
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
            if (i % 10000 == 0) System.out.println("Proccess " + i + " wordBase of " + count);

            WordCard card = buildForm(s);

            if (card == null) {
                continue;
            }

            wordProcessor.process(card);
            actual++;

        }
        System.out.println("Finished word processing actual words " + actual);
    }

    private WordCard buildForm(String s) {
        String[] wd = s.split(" ");
        String wordBase = wd[0].toLowerCase();
        if (wordBase.startsWith("-")) return null;
        wordBase = "#".equals(wordBase) ? "" : wordBase;
        List<FlexiaModel> models = wordsFlexias.get(Integer.valueOf(wd[1]));
        FlexiaModel flexiaModel = models.get(0);
        if (models.size() == 0 || ignoredForm.contains(flexiaModel.getCode())) {
            return null;
        }

        WordCard card = new WordCard(flexiaModel.create(wordBase), wordBase, flexiaModel.getSuffix());

        for (FlexiaModel fm : models) {
            card.addFlexia(fm);
        }
        return card;
    }


    private void skipBlock(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            reader.readLine();
        }
    }


    private void readPrefix(BufferedReader reader) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            reader.readLine();
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
        // we inored all forms thats
        if (fl.length == 3) {
            //System.out.println(line);
            flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), fl[2].toLowerCase()));
        }
        if (fl.length == 2) flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), ""));
    }

}
