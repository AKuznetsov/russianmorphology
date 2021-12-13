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
package org.apache.lucene.morphology.english.stemmer;

import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;


public class EnglishStemmerTest {
    @Test
    public void testGetStemmedWord() throws Exception {
        EnglishLuceneMorphology englishLuceneMorphology = new EnglishLuceneMorphology();
        EnglishStemmer englishStemmer = new EnglishStemmer(englishLuceneMorphology);
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("running"),equalTo("run"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("run"),equalTo("run"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("killed"),equalTo("kill"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("kill"),equalTo("kill"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("networking"),equalTo("network"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("network"),equalTo("network"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("statistics"),equalTo("statistic"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("statistic"),equalTo("statistic"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("stats"),equalTo("stat"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("stat"),equalTo("stat"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("countries"),equalTo("country"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("country"),equalTo("country"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("delete"),equalTo("delete"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("ended"),equalTo("end"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("end"),equalTo("end"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("ends"),equalTo("end"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("given"),equalTo("give"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("give"),equalTo("give"));
        MatcherAssert.assertThat(englishStemmer.getStemmedWord("log4j"),equalTo("log4j"));
    }
}
