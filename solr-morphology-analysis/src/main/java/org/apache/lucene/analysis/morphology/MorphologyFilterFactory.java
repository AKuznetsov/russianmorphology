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

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.analyzer.MorphologyFilter;

import java.util.Map;

/**
 * Factory for {@link MorphologyFilter}, with configurable language
 * <p>
 * <b>Note:</b> Two languages are available now: English (default value) and Russian.
 * <pre class="prettyprint">
 * &lt;fieldType name="content" class="solr.TextField" positionIncrementGap="100"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.StandardTokenizerFactory"/&gt;
 *     &lt;filter class="solr.LowerCaseFilterFactory"/&gt;
 *     &lt;filter class="solr.MorphologyFilterFactory" language="English"/&gt;
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;</pre>
 */
public class MorphologyFilterFactory extends TokenFilterFactory implements ResourceLoaderAware{

    private static final String LANGUAGE_KEY = "language";

    private String language;
    private LuceneMorphology luceneMorphology;

    public MorphologyFilterFactory(Map<String, String> args) {
        super(args);

        language = get(args, LANGUAGE_KEY, "English");
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Unknown parameters: " + args);
        }
    }

    public TokenStream create(TokenStream input) {
        return new MorphologyFilter(input, luceneMorphology);
    }

    public void inform(ResourceLoader loader) {

        String className = "org.apache.lucene.morphology." + language.toLowerCase() + "." + language + "LuceneMorphology";
        luceneMorphology = loader.newInstance(className, LuceneMorphology.class);
    }

    public LuceneMorphology getLuceneMorphology() {
        return luceneMorphology;
    }
}
