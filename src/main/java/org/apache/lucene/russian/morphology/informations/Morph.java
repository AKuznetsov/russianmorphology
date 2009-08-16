package org.apache.lucene.russian.morphology.informations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Morph {
    int[][] separators;
    short[] rulesId;
    Heuristic[][] rules;
    String[] grammaInfo;


    public Morph(String fileName) throws IOException {
        readFromFile(fileName);
    }

    public Morph(int[][] separators, short[] rulesId, Heuristic[][] rules, String[] grammaInfo) {
        this.separators = separators;
        this.rulesId = rulesId;
        this.rules = rules;
        this.grammaInfo = grammaInfo;
    }

    public void writeToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(separators.length + "\n");
        for (int[] i : separators) {
            writer.write(i.length + "\n");
            for (int j : i) {
                writer.write(j + "\n");
            }
        }
        for (short i : rulesId) {
            writer.write(i + "\n");
        }
        writer.write(rules.length + "\n");
        for (Heuristic[] heuristics : rules) {
            writer.write(heuristics.length + "\n");
            for (Heuristic heuristic : heuristics) {
                writer.write(heuristic.toString() + "\n");
            }
        }
        writer.write(grammaInfo.length + "\n");
        for (String s : grammaInfo) {
            writer.write(s + "\n");
        }
        writer.close();
    }

    public void readFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String s = bufferedReader.readLine();
        Integer amount = Integer.valueOf(s);
        separators = new int[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer wordLenght = Integer.valueOf(s1);
            separators[i] = new int[wordLenght];
            for (int j = 0; j < wordLenght; j++) {
                separators[i][j] = Integer.valueOf(bufferedReader.readLine());
            }
        }
        rulesId = new short[amount];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            rulesId[i] = Short.valueOf(s1);
        }
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        rules = new Heuristic[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer ruleLenght = Integer.valueOf(s1);
            rules[i] = new Heuristic[ruleLenght];
            for (int j = 0; j < ruleLenght; j++) {
                rules[i][j] = new Heuristic(bufferedReader.readLine());
            }
        }
        s = bufferedReader.readLine();
        amount = Integer.valueOf(s);
        grammaInfo = new String[amount];
        for (int i = 0; i < amount; i++) {
            grammaInfo[i] = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
}
