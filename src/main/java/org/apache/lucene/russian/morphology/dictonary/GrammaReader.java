package org.apache.lucene.russian.morphology.dictonary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//todo spleet this class on two.
public class GrammaReader {
    private String fileName;
    private String fileEncoding = "windows-1251";
    private Map<Integer, String> grammaInfo = new HashMap<Integer, String>();
    private Map<String, Integer> inversIndex = new HashMap<String, Integer>();

    public GrammaReader(String fileName) throws IOException {
        this.fileName = fileName;
        setUp();
    }

    public GrammaReader(String fileName, String fileEncoding) throws IOException {
        this.fileName = fileName;
        this.fileEncoding = fileEncoding;
        setUp();
    }

    private void setUp() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), fileEncoding));
        String line = bufferedReader.readLine();
        while (line != null) {
            line = line.trim();
            if (!line.startsWith("//") && line.length() > 0) {
                String[] strings = line.split(" ", 2);
                Integer i = grammaInfo.size();
                inversIndex.put(strings[0], i);
                grammaInfo.put(i, strings[1]);
            }
            line = bufferedReader.readLine();
        }
    }

    public Map<Integer, String> getGrammaInfo() {
        return grammaInfo;
    }

    public void setGrammaInfo(Map<Integer, String> grammaInfo) {
        this.grammaInfo = grammaInfo;
    }

    public Map<String, Integer> getInversIndex() {
        return inversIndex;
    }

    public void setInversIndex(Map<String, Integer> inversIndex) {
        this.inversIndex = inversIndex;
    }
}
