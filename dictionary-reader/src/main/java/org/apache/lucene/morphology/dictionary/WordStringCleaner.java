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

import org.apache.lucene.morphology.LetterDecoderEncoder;

import java.util.List;


public class WordStringCleaner implements WordFilter {

    private LetterDecoderEncoder decoderEncoder;

    public WordStringCleaner(LetterDecoderEncoder decoderEncoder) {
        this.decoderEncoder = decoderEncoder;
    }

    public WordCard transform(WordCard wordCard) {
        wordCard.setBase(cleanString(wordCard.getBase()));
        wordCard.setCanonicalForm(cleanString(wordCard.getCanonicalForm()));
        wordCard.setCanonicalSuffix(cleanString(wordCard.getCanonicalSuffix()));
        List<FlexiaModel> models = wordCard.getWordsForms();
        for (FlexiaModel m : models) {
            m.setSuffix(cleanString(m.getSuffix()));
            m.setPrefix(cleanString(m.getPrefix()));
            //made correct code
            m.setCode(m.getCode().substring(0, 2));
        }
        return wordCard;
    }


    private String cleanString(String s) {
        return decoderEncoder.cleanString(s);
    }
}
