package org.apache.lucene.morphology.english.stemmer;

import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class EnglishStemmerTest {
    @Test
    public void testGetStemmedWord() throws Exception {
        EnglishLuceneMorphology englishLuceneMorphology = new EnglishLuceneMorphology();
        EnglishStemmer englishStemmer = new EnglishStemmer(englishLuceneMorphology);
        assertThat(englishStemmer.getStemmedWord("running"),equalTo("run"));
        assertThat(englishStemmer.getStemmedWord("run"),equalTo("run"));
        assertThat(englishStemmer.getStemmedWord("killed"),equalTo("kill"));
        assertThat(englishStemmer.getStemmedWord("kill"),equalTo("kill"));
        assertThat(englishStemmer.getStemmedWord("networking"),equalTo("network"));
        assertThat(englishStemmer.getStemmedWord("network"),equalTo("network"));
        assertThat(englishStemmer.getStemmedWord("statistics"),equalTo("statistic"));
        assertThat(englishStemmer.getStemmedWord("statistic"),equalTo("statistic"));
        assertThat(englishStemmer.getStemmedWord("stats"),equalTo("stat"));
        assertThat(englishStemmer.getStemmedWord("stat"),equalTo("stat"));
        assertThat(englishStemmer.getStemmedWord("countries"),equalTo("country"));
        assertThat(englishStemmer.getStemmedWord("country"),equalTo("country"));
        assertThat(englishStemmer.getStemmedWord("delete"),equalTo("delete"));
        assertThat(englishStemmer.getStemmedWord("ended"),equalTo("end"));
        assertThat(englishStemmer.getStemmedWord("end"),equalTo("end"));
        assertThat(englishStemmer.getStemmedWord("ends"),equalTo("end"));
        assertThat(englishStemmer.getStemmedWord("given"),equalTo("give"));
        assertThat(englishStemmer.getStemmedWord("give"),equalTo("give"));
        assertThat(englishStemmer.getStemmedWord("log4j"),equalTo("log4j"));
    }
}
