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

package org.apache.lucene.morphology.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent word and all it forms.
 */
public class WordCard {
    private String canonicalForm;
    private String base;
    private String canonicalSuffix;
    private List<FlexiaModel> wordsForms = new ArrayList<FlexiaModel>();

    public WordCard(String canonicalForm, String base, String canonicalSuffix) {
        this.canonicalForm = canonicalForm;
        this.canonicalSuffix = canonicalSuffix;
        this.base = base;
    }

    public void addFlexia(FlexiaModel flexiaModel) {
        wordsForms.add(flexiaModel);
    }

    public void removeFlexia(FlexiaModel flexiaModel) {
        wordsForms.remove(flexiaModel);
    }

    public String getCanonicalForm() {
        return canonicalForm;
    }

    public String getCanonicalSuffix() {
        return canonicalSuffix;
    }

    public String getBase() {
        return base;
    }

    public List<FlexiaModel> getWordsForms() {
        return wordsForms;
    }

    public void setCanonicalForm(String canonicalForm) {
        this.canonicalForm = canonicalForm;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setCanonicalSuffix(String canonicalSuffix) {
        this.canonicalSuffix = canonicalSuffix;
    }

    public void setWordsForms(List<FlexiaModel> wordsForms) {
        this.wordsForms = wordsForms;
    }

    @Override
    public String toString() {
        return "WordCard{" +
                "canonicalForm='" + canonicalForm + '\'' +
                ", base='" + base + '\'' +
                ", canonicalSuffix='" + canonicalSuffix + '\'' +
                ", wordsForms=" + wordsForms +
                '}';
    }
}
