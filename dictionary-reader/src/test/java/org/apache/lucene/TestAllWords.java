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
package org.apache.lucene;

import org.apache.lucene.morphology.*;
import org.apache.lucene.morphology.dictionary.*;
import org.apache.lucene.morphology.english.EnglishLetterDecoderEncoder;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.english.EnglishMorphology;
import org.apache.lucene.morphology.russian.RussianLetterDecoderEncoder;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianMorphology;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;


public class TestAllWords {

    String prefix = "";

    @Before
    public void setUp() {
        System.out.println(System.getProperty("user.dir"));
        prefix = System.getProperty("user.dir").endsWith("dictionary-reader") ? "../" : "";

    }

    @Test
    public void shouldEnglishMorphologyIncludeAllWordsFormsWithMorphInfo() throws IOException {
        final MorphologyImpl morphology = new EnglishMorphology();
        LetterDecoderEncoder decoderEncoder = new EnglishLetterDecoderEncoder();
        String pathToGramma = prefix + "dictonary/Dicts/Morph/egramtab.tab";
        String pathToDict = prefix + "dictonary/Dicts/SrcMorph/EngSrc/morphs.mrd";

        testFullGramma(morphology, decoderEncoder, pathToGramma, pathToDict);

    }

    @Test
    public void shouldRussianMorphologyIncludeAllWordsFormsWithMorphInfo() throws IOException {
        final MorphologyImpl morphology = new RussianMorphology();
        LetterDecoderEncoder decoderEncoder = new RussianLetterDecoderEncoder();
        String pathToGramma = prefix + "dictonary/Dicts/Morph/rgramtab.tab";
        String pathToDict = prefix + "dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd";

        testFullGramma(morphology, decoderEncoder, pathToGramma, pathToDict);
    }

    private void testFullGramma(final MorphologyImpl morphology, LetterDecoderEncoder decoderEncoder, String pathToGramma, String pathToDict) throws IOException {
        GrammarReader grammarInfo = new GrammarReader(pathToGramma);
        final List<String> morphInfo = grammarInfo.getGrammarInfo();
        final Map<String, Integer> inversIndex = grammarInfo.getGrammarInverseIndex();

        DictionaryReader dictionaryReader = new DictionaryReader(pathToDict, new HashSet<String>());

        final AtomicLong wordCount = new AtomicLong(0);
        Long startTime = System.currentTimeMillis();

        WordProcessor wordProcessor = new WordProcessor() {
            public void process(WordCard wordCard) throws IOException {
                String word = wordCard.getBase() + wordCard.getCanonicalSuffix();
                for (FlexiaModel fm : wordCard.getWordsForms()) {
                    String wordForm = wordCard.getBase() + fm.getSuffix();
                    String morph = morphInfo.get(inversIndex.get(fm.getCode()));
                    assertThat(morphology.getMorphInfo(wordForm), hasItem(word + "|" + morph));
                    assertThat(morphology.getNormalForms(wordForm), hasItem(word));
                    wordCount.set(2L + wordCount.get());
                }
            }
        };

        WordCleaner wordCleaner = new WordCleaner(decoderEncoder, wordProcessor);
        WordStringCleaner wordStringCleaner = new WordStringCleaner(decoderEncoder, wordCleaner);
        RemoveFlexiaWithPrefixes removeFlexiaWithPrefixes = new RemoveFlexiaWithPrefixes(wordStringCleaner);
        dictionaryReader.process(removeFlexiaWithPrefixes);
        long time = System.currentTimeMillis() - startTime;
        System.out.println("Done " + wordCount.get() + " in " + time + " ms. " + wordCount.get() / (time / 1000L) + " word per second");
    }

    @Test
    public void shouldEnglishLuceneMorphologyIncludeAllWords() throws IOException {
        final LuceneMorphology morphology = new EnglishLuceneMorphology();

        LetterDecoderEncoder decoderEncoder = new EnglishLetterDecoderEncoder();
        String pathToDic = prefix + "dictonary/Dicts/SrcMorph/EngSrc/morphs.mrd";

        testAllWordForLucene(morphology, decoderEncoder, pathToDic);
    }

    @Test
    public void shouldIncludeAllWordsRussianInLuceneMorophology() throws IOException {
        final LuceneMorphology morphology = new RussianLuceneMorphology();

        LetterDecoderEncoder decoderEncoder = new RussianLetterDecoderEncoder();

        String pathToDic = prefix + "dictonary/Dicts/SrcMorph/RusSrc/morphs.mrd";

        testAllWordForLucene(morphology, decoderEncoder, pathToDic);

    }

    private void testAllWordForLucene(final LuceneMorphology morphology, LetterDecoderEncoder decoderEncoder, String pathToDic) throws IOException {
        final AtomicLong wordCount = new AtomicLong(0);
        Long startTime = System.currentTimeMillis();

        DictionaryReader dictionaryReader = new DictionaryReader(pathToDic, new HashSet<String>());
        WordProcessor wordProcessor = new WordProcessor() {
            public void process(WordCard wordCard) throws IOException {
                String word = wordCard.getBase() + wordCard.getCanonicalSuffix();
                for (FlexiaModel fm : wordCard.getWordsForms()) {
                    String wordForm = wordCard.getBase() + fm.getSuffix();
                    assertThat(morphology.getNormalForms(wordForm), hasItem(word));
                    wordCount.set(1L + wordCount.get());
                }
            }
        };

        WordCleaner wordCleaner = new WordCleaner(decoderEncoder, wordProcessor);
        WordStringCleaner wordStringCleaner = new WordStringCleaner(decoderEncoder, wordCleaner);
        RemoveFlexiaWithPrefixes removeFlexiaWithPrefixes = new RemoveFlexiaWithPrefixes(wordStringCleaner);
        dictionaryReader.process(removeFlexiaWithPrefixes);

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Done " + wordCount.get() + " in " + time + " ms. " + wordCount.get() / (time / 1000.0) + " word per second");
    }


}
