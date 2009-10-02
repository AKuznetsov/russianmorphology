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

package org.apache.lucene.morpholgy.dictionary;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * This class contain logic how read
 * dictonary and produce word with it all forms.
 */
public class DictonaryReader {
    private String fileName;
    private String fileEncoding = "windows-1251";
    private List<List<FlexiaModel>> wordsFlexias = new ArrayList<List<FlexiaModel>>();
    private List<List<String>> wordPrefixes = new ArrayList<List<String>>();
    private Set<String> ingnoredForm = new HashSet<String>();

    public DictonaryReader(String fileName, Set<String> ingnoredForm) {
        this.fileName = fileName;
        this.ingnoredForm = ingnoredForm;
    }

    public DictonaryReader(String fileName, String fileEncoding, Set<String> ingnoredForm) {
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
        readWords(bufferedReader, wordProccessor);
    }


    private void readWords(BufferedReader reader, WordProccessor wordProccessor) throws IOException {
        String s = reader.readLine();
        int count = Integer.valueOf(s);
        for (int i = 0; i < count; i++) {
            s = reader.readLine();
            if (i % 10000 == 0) System.out.println("Proccess " + i + " wordBase of " + count);

            String[] wd = s.split(" ");
            String wordBase = wd[0].toLowerCase();
            if (wordBase.startsWith("-")) continue;
            wordBase = "#".equals(wordBase) ? "" : wordBase;
            List<FlexiaModel> models = wordsFlexias.get(Integer.valueOf(wd[1]));
            FlexiaModel flexiaModel = models.get(0);
            if (models.size() > 0 && !ingnoredForm.contains(flexiaModel.getCode())) {
                WordCard card = new WordCard(flexiaModel.create(wordBase), wordBase, flexiaModel.getSuffix());
                for (FlexiaModel fm : models) {
                    card.addFlexia(fm);
                }
                wordProccessor.proccess(card);
            }
        }
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
        // we inored all forms thats
        if (fl.length == 3) {
            System.out.println(line);
            // flexiaModelArrayList.add(new FlexiaModel(fl[1], cleanString(fl[0].toLowerCase()), cleanString(fl[2].toLowerCase())));
        }
        if (fl.length == 2) flexiaModelArrayList.add(new FlexiaModel(fl[1], fl[0].toLowerCase(), ""));
    }

}
