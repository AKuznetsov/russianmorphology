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

import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;


public class TestLuceneMorph {

    @Test
    public void englishMorphologyShouldGetCorrectNormalForm() throws IOException {
        LuceneMorphology luceneMorph = new EnglishLuceneMorphology();
        String pathToTestData = "/english/english-morphology-test.txt";
        testMorphology(luceneMorph, pathToTestData);
    }

    @Test
    public void russianMorphologyShouldGetCorrectNormalForm() throws IOException {
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();
        List<String> v = luceneMorph.getMorphInfo("вина");
        System.out.println(v);
        String pathToTestData = "/russian/russian-morphology-test.txt";
        testMorphology(luceneMorph, pathToTestData);
    }

    private void testMorphology(LuceneMorphology luceneMorph, String pathToTestData) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(pathToTestData);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String s = bufferedReader.readLine();
        while (s != null) {
            String[] qa = s.trim().split(" ");
            Set<String> result = new HashSet<>(Arrays.asList(qa).subList(1, qa.length));
            Set<String> stringList = new HashSet<>(luceneMorph.getNormalForms(qa[0]));
            MatcherAssert.assertThat(stringList, equalTo(result));
            s = bufferedReader.readLine();
        }
    }
}
