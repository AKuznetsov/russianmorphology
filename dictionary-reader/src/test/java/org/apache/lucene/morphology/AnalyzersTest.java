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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.morphology.analyzer.MorphologyAnalyzer;
import org.apache.lucene.morphology.analyzer.MorphologyFilter;
import org.apache.lucene.morphology.english.EnglishAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class AnalyzersTest {

    @Test
    public void shouldGiveCorrectWordsForEnglish() throws IOException {
        Analyzer morphlogyAnalyzer = new EnglishAnalyzer();
        String answerPath = "/english/english-analyzer-answer.txt";
        String testPath = "/english/english-analyzer-data.txt";

        testAnalayzer(morphlogyAnalyzer, answerPath, testPath);
    }

    @Test
    public void shouldGiveCorrectWordsForRussian() throws IOException {
        Analyzer morphlogyAnalyzer = new RussianAnalyzer();
        String answerPath = "/russian/russian-analyzer-answer.txt";
        String testPath = "/russian/russian-analyzer-data.txt";

        testAnalayzer(morphlogyAnalyzer, answerPath, testPath);
    }

    @Test
    public void emptyStringTest() throws IOException {
        LuceneMorphology russianLuceneMorphology = new RussianLuceneMorphology();
        LuceneMorphology englishLuceneMorphology = new EnglishLuceneMorphology();

        MorphologyAnalyzer russianAnalyzer = new MorphologyAnalyzer(russianLuceneMorphology);
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream("тест пм тест".getBytes()), "UTF-8");
        TokenStream stream = russianAnalyzer.tokenStream(null, reader);
        MorphologyFilter englishFilter = new MorphologyFilter(stream, englishLuceneMorphology);

        englishFilter.reset();
        while (englishFilter.incrementToken()) {
            System.out.println(englishFilter.toString());
        }
    }

    @Test
    public void shouldProvideCorrectIndentForWordWithMelitaForm() throws IOException {
        Analyzer morphlogyAnalyzer = new RussianAnalyzer();
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream("принеси мне вина на новый год".getBytes()), "UTF-8");

        TokenStream tokenStream = morphlogyAnalyzer.tokenStream(null, reader);
        tokenStream.reset();
        Set<String> foromsOfWine = new HashSet<String>();
        foromsOfWine.add("вина");
        foromsOfWine.add("винo");
        boolean wordSeen = false;
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTerm = tokenStream.getAttribute(CharTermAttribute.class);
            PositionIncrementAttribute position = tokenStream.getAttribute(PositionIncrementAttribute.class);
            if(foromsOfWine.contains(charTerm.toString()) && wordSeen){
                assertThat(position.getPositionIncrement(),equalTo(0));
            }
            if(foromsOfWine.contains(charTerm.toString())){
                wordSeen = true;
            }
        }
    }

    private void testAnalayzer(Analyzer morphlogyAnalyzer, String answerPath, String testPath) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(answerPath);
        BufferedReader breader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String[] strings = breader.readLine().replaceAll(" +", " ").trim().split(" ");
        HashSet<String> answer = new HashSet<String>(Arrays.asList(strings));
        stream.close();

        stream = this.getClass().getResourceAsStream(testPath);

        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");

        TokenStream tokenStream = morphlogyAnalyzer.tokenStream(null, reader);
        tokenStream.reset();
        HashSet<String> result = new HashSet<String>();
        while (tokenStream.incrementToken()) {
            CharTermAttribute attribute1 = tokenStream.getAttribute(CharTermAttribute.class);
            result.add(attribute1.toString());
        }

        stream.close();

        assertThat(result, equalTo(answer));
    }
}
