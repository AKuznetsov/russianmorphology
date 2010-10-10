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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class RemoveFlexiaWithPrefixes extends WordFilter {

    public RemoveFlexiaWithPrefixes(WordProcessor wordProcessor) {
        super(wordProcessor);
    }

    @Override
    public List<WordCard> transform(WordCard wordCard) {

        List<FlexiaModel> flexiaModelsToRemove = new LinkedList<FlexiaModel>();
        for (FlexiaModel fm : wordCard.getWordsForms()) {
            if (fm.getPrefix().length() > 0) {
                flexiaModelsToRemove.add(fm);
            }
        }
        for (FlexiaModel fm : flexiaModelsToRemove) {
            wordCard.removeFlexia(fm);
        }

        return new LinkedList<WordCard>(Arrays.asList(wordCard));
    }
}
