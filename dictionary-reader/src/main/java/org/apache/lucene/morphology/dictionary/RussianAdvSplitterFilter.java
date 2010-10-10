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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


public class RussianAdvSplitterFilter extends WordFilter {
    private String code;

    public RussianAdvSplitterFilter(WordProcessor wordProcessor) throws IOException {
        super(wordProcessor);
        code = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/russian-adv-main-code.txt"), "windows-1251")).readLine();
    }

    @Override
    public List<WordCard> transform(WordCard wordCard) {
        LinkedList<WordCard> result = new LinkedList<WordCard>();
        result.add(wordCard);

        String baseWord = "";
        String canonicalForm = "";
        String canonicalSuffix = "";
        List<FlexiaModel> flexiaModels = new LinkedList<FlexiaModel>();
        for (FlexiaModel flexiaModel : wordCard.getWordsForms()) {
            if (flexiaModel.getPrefix().length() > 0) {
                flexiaModels.add(new FlexiaModel(flexiaModel.getCode(), flexiaModel.getSuffix(), ""));
            }
            if (flexiaModel.getPrefix().length() > 0 && flexiaModel.getCode().equals(code)) {
                baseWord = flexiaModel.getPrefix() + wordCard.getBase();
                canonicalForm = flexiaModel.getCode();
                canonicalSuffix = flexiaModel.getSuffix();
            }
        }

        if (baseWord.length() > 0) {
            WordCard wc = new WordCard(canonicalForm, baseWord, canonicalSuffix);
            wc.setWordsForms(flexiaModels);
            result.add(wc);
        }

        return result;
    }
}
