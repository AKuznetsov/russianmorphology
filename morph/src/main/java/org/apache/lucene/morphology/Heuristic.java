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

import java.io.Serializable;


public class Heuristic implements Serializable {
    byte actualSuffixLengh;
    String actualNormalSuffix;
    short formMorphInfo;
    short normalFormMorphInfo;

    public Heuristic(String s) {
        String[] strings = s.split("\\|");
        actualSuffixLengh = Byte.valueOf(strings[0]);
        actualNormalSuffix = strings[1];
        formMorphInfo = Short.valueOf(strings[2]);
        normalFormMorphInfo = Short.valueOf(strings[3]);
    }

    public Heuristic(byte actualSuffixLengh, String actualNormalSuffix, short formMorphInfo, short normalFormMorphInfo) {
        this.actualSuffixLengh = actualSuffixLengh;
        this.actualNormalSuffix = actualNormalSuffix;
        this.formMorphInfo = formMorphInfo;
        this.normalFormMorphInfo = normalFormMorphInfo;
    }

    public String transofrmWord(String w) {
        if (w.length() - actualSuffixLengh < 0) return w;
        return w.substring(0, w.length() - actualSuffixLengh) + actualNormalSuffix;
    }

    public byte getActualSuffixLengh() {
        return actualSuffixLengh;
    }

    public String getActualNormalSuffix() {
        return actualNormalSuffix;
    }

    public short getFormMorphInfo() {
        return formMorphInfo;
    }

    public short getNormalFormMorphInfo() {
        return normalFormMorphInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Heuristic heuristic = (Heuristic) o;

        if (actualSuffixLengh != heuristic.actualSuffixLengh) return false;
        if (formMorphInfo != heuristic.formMorphInfo) return false;
        if (normalFormMorphInfo != heuristic.normalFormMorphInfo) return false;
        if (actualNormalSuffix != null ? !actualNormalSuffix.equals(heuristic.actualNormalSuffix) : heuristic.actualNormalSuffix != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) actualSuffixLengh;
        result = 31 * result + (actualNormalSuffix != null ? actualNormalSuffix.hashCode() : 0);
        result = 31 * result + (int) formMorphInfo;
        result = 31 * result + (int) normalFormMorphInfo;
        return result;
    }

    @Override
    public String toString() {
        return "" + actualSuffixLengh + "|" + actualNormalSuffix + "|" + formMorphInfo + "|" + normalFormMorphInfo;
    }
}
