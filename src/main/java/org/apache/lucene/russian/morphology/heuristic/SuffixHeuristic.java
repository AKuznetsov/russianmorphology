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

package org.apache.lucene.russian.morphology.heuristic;

/**
 * Represent evristic that assume that
 * canonical from of word is defined by word suffix.
 * It contains to suffixes from given position of
 * canonical word form and for form.
 */
public class SuffixHeuristic {
    private String formSuffix;
    private String normalSuffix;

    public SuffixHeuristic(String formSuffix, String normalSuffix) {
        this.formSuffix = formSuffix;
        this.normalSuffix = normalSuffix;
    }

    public String getFormSuffix() {
        return formSuffix;
    }

    public void setFormSuffix(String formSuffix) {
        this.formSuffix = formSuffix;
    }

    public String getNormalSuffix() {
        return normalSuffix;
    }

    public void setNormalSuffix(String normalSuffix) {
        this.normalSuffix = normalSuffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuffixHeuristic that = (SuffixHeuristic) o;

        if (!formSuffix.equals(that.formSuffix)) return false;
        if (!normalSuffix.equals(that.normalSuffix)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = formSuffix.hashCode();
        result = 31 * result + normalSuffix.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SuffixHeuristic{" +
                "formSuffix='" + formSuffix + '\'' +
                ", normalSuffix='" + normalSuffix + '\'' +
                '}';
    }
}
