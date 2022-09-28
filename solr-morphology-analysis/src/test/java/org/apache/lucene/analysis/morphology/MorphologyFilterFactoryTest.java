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
package org.apache.lucene.analysis.morphology;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.apache.lucene.util.ClasspathResourceLoader;
import org.apache.lucene.util.ResourceLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MorphologyFilterFactoryTest {

    private static final String LANGUAGE_KEY = "language";
    private ResourceLoader loader = new ClasspathResourceLoader(MorphologyFilterFactoryTest.class);
    private Map<String, String> args;

    @Before
    public void setUp() {
        args = new HashMap<>();
    }

    @Test
    public void if_RussianLanguageKey_then_CreateRussianMorphologyFilter() {

        args.put(LANGUAGE_KEY, "Russian");
        MorphologyFilterFactory morphologyFilterFactory = new MorphologyFilterFactory(args);
        morphologyFilterFactory.inform(loader);

        LuceneMorphology luceneMorphology = morphologyFilterFactory.getLuceneMorphology();

        Assert.assertTrue("Creation the MorphologyFilterFactory with a Russian language key", luceneMorphology instanceof RussianLuceneMorphology);
    }

    @Test
    public void if_EnglishLanguageKey_then_CreateEnglishMorphologyFilter() {

        args.put(LANGUAGE_KEY, "English");
        MorphologyFilterFactory morphologyFilterFactory = new MorphologyFilterFactory(args);
        morphologyFilterFactory.inform(loader);

        LuceneMorphology luceneMorphology = morphologyFilterFactory.getLuceneMorphology();

        Assert.assertTrue("Creation the MorphologyFilterFactory with a English language key", luceneMorphology instanceof EnglishLuceneMorphology);
    }

    @Test
    public void if_NoLanguageKey_then_CreateEnglishMorphologyFilter() {

        MorphologyFilterFactory morphologyFilterFactory = new MorphologyFilterFactory(args);
        morphologyFilterFactory.inform(loader);

        LuceneMorphology luceneMorphology = morphologyFilterFactory.getLuceneMorphology();

        Assert.assertTrue("Creation the MorphologyFilterFactory without any language keys", luceneMorphology instanceof EnglishLuceneMorphology);
    }
}
