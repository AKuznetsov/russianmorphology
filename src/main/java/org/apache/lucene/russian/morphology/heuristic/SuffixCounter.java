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
 * Conains information of freqency of suffix evristic
 * in dictionary.
 */
public class SuffixCounter implements Comparable {
    private SimpleSuffixHeuristic simpleSuffixHeuristic;
    private Double amnout = 0.0;

    public SuffixCounter(SimpleSuffixHeuristic simpleSuffixHeuristic) {
        this.simpleSuffixHeuristic = simpleSuffixHeuristic;
    }

    public void incrementAmount() {
        amnout++;
    }

    public void incrementAmount(Double wordFreq) {
        amnout += wordFreq;
    }

    public SimpleSuffixHeuristic getSuffixHeuristic() {
        return simpleSuffixHeuristic;
    }

    public void setSuffixEvristic(SimpleSuffixHeuristic simpleSuffixHeuristic) {
        this.simpleSuffixHeuristic = simpleSuffixHeuristic;
    }

    public Double getAmnout() {
        return amnout;
    }

    public void setAmnout(Double amnout) {
        this.amnout = amnout;
    }

    public int compareTo(Object o) {
        if (o instanceof SuffixCounter) return (int) Math.round(Math.signum(((SuffixCounter) o).amnout - amnout));
        return -1;
    }

    @Override
    public String toString() {
        return "" + amnout + " " + simpleSuffixHeuristic.toString();
    }
}
