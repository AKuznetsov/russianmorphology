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
package org.apache.lucene.morphology.russian;

import org.apache.lucene.morphology.LuceneMorphology;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class RussianLuceneMorphTest {
    private LuceneMorphology luceneMorph;

    @Before
    public void setUp() throws IOException {
        luceneMorph = new LuceneMorphology(this.getClass().getResourceAsStream("/org/apache/lucene/morphology/russian/morph.info"), new RussianLetterDecoderEncoder());
    }

    @Test
    public void shoudGetCorrentMorphInfo() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/org/apache/lucene/morphology/russian/russian-morphology-test.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String s = bufferedReader.readLine();
        while (s != null) {
            String[] qa = s.trim().split(" ");
            Set<String> result = new HashSet<String>();
            for (int i = 1; i < qa.length; i++) {
                result.add(qa[i]);
            }
            Set<String> stringList = new HashSet<String>(luceneMorph.getNormalForms(qa[0]));
            assertThat(stringList, equalTo(result));
            s = bufferedReader.readLine();
        }
    }
}
