/**
 * Copyright 2015 Alexander Kuznetsov
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
package org.apache.lucene.morphology.context;

import java.util.Arrays;

public class ContextItem implements Comparable<ContextItem> {
    String[][] morphInfo;

    public ContextItem(String[][] morphInfo) {
        this.morphInfo = morphInfo;
    }

    public String[][] getMorphInfo() {
        return morphInfo;
    }

    public void setMorphInfo(String[][] morphInfo) {
        this.morphInfo = morphInfo;
    }

    public int hashCode() {
        int h = 0;
        for (String[] m : morphInfo) {
            for (String s : m) {
                h = 31 * h + s.hashCode();
            }
        }
        return h;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextItem that = (ContextItem) o;

        if (that.morphInfo.length != this.morphInfo.length) {
            return false;
        }
        for (int i = 0; i < morphInfo.length; i++) {
            if (!Arrays.equals(morphInfo[i], that.morphInfo[i])) {
                return false;
            }
        }

        return true;
    }


    @Override
    public int compareTo(ContextItem o) {
        int i = o.morphInfo.length - morphInfo.length;
        if (i != 0) return i;
        for (int j = 0; j < morphInfo.length; j++) {
            i = o.morphInfo[j].length - morphInfo[j].length;
            if (i != 0) return i;
            for (int k = 0; k < morphInfo[j].length; k++) {
                i = morphInfo[j][k].compareTo(o.morphInfo[j][k]);
                if (i != 0) return i;
            }
        }
        return 0;
    }
}
