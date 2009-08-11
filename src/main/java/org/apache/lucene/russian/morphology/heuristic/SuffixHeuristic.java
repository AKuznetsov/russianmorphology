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
    private Integer actualSuffixLength;
    private String normalFromSuffix;
    private String morphInfoCode;

    public SuffixHeuristic(String formSuffix, Integer actualSuffixLength, String normalFromSuffix, String morphInfoCode) {
        this.formSuffix = formSuffix;
        this.actualSuffixLength = actualSuffixLength;
        this.normalFromSuffix = normalFromSuffix;
        this.morphInfoCode = morphInfoCode;
    }

    public String getFormSuffix() {
        return formSuffix;
    }

    public Integer getActualSuffixLength() {
        return actualSuffixLength;
    }

    public String getNormalFromSuffix() {
        return normalFromSuffix;
    }

    public String getMorphInfoCode() {
        return morphInfoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuffixHeuristic that = (SuffixHeuristic) o;

        if (actualSuffixLength != null ? !actualSuffixLength.equals(that.actualSuffixLength) : that.actualSuffixLength != null)
            return false;
        if (formSuffix != null ? !formSuffix.equals(that.formSuffix) : that.formSuffix != null) return false;
        if (morphInfoCode != null ? !morphInfoCode.equals(that.morphInfoCode) : that.morphInfoCode != null)
            return false;
        if (normalFromSuffix != null ? !normalFromSuffix.equals(that.normalFromSuffix) : that.normalFromSuffix != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = formSuffix != null ? formSuffix.hashCode() : 0;
        result = 31 * result + (actualSuffixLength != null ? actualSuffixLength.hashCode() : 0);
        result = 31 * result + (normalFromSuffix != null ? normalFromSuffix.hashCode() : 0);
        result = 31 * result + (morphInfoCode != null ? morphInfoCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return formSuffix + " " + actualSuffixLength + " " + normalFromSuffix + " " + morphInfoCode;
    }
}
