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

package org.apache.lucene.russian.morphology;

/**
 * This helper class allow encode suffix of russian word
 * to long value and decode from it.
 * Assumed that suffix contains only small russian letters and dash.
 * Also assumed that letter � and � coinsed.
 */
public class RussianSuffixDecoderEncoder {
    public static final int RUSSIAN_SMALL_LETTER_OFFSET = 1071;
    public static final int SUFFIX_LENGTH = 6;
    public static final int EE_CHAR = 34;
    public static final int E_CHAR = 6;
    public static final int DASH_CHAR = 45;
    public static final int DASH_CODE = 33;


    static public Long encode(String string) {
        if (string.length() > 12) throw new SuffixToLongException("Suffix length should not be greater then " + 12);
        long result = 0L;
        for (int i = 0; i < string.length(); i++) {
            int c = 0 + string.charAt(i) - RUSSIAN_SMALL_LETTER_OFFSET;
            if (c == 45 - RUSSIAN_SMALL_LETTER_OFFSET) {
                c = DASH_CODE;
            }
            if (c == EE_CHAR) c = E_CHAR;
            if (c < 0 || c > 33) throw new WrongCharaterException();
            result = result * 35L + c;
        }
        return result;
    }

    static public String decode(Long suffixN) {
        String result = "";
        while (suffixN > 35) {
            long c = suffixN % 35 + RUSSIAN_SMALL_LETTER_OFFSET;
            if (c == DASH_CODE + RUSSIAN_SMALL_LETTER_OFFSET) c = DASH_CHAR;
            result = (char) c + result;
            suffixN /= 35;
        }
        long c = suffixN + RUSSIAN_SMALL_LETTER_OFFSET;
        if (c == DASH_CODE + RUSSIAN_SMALL_LETTER_OFFSET) c = DASH_CHAR;
        result = (char) c + result;
        return result;
    }

    static public boolean checkCharacter(char c) {
        int code = 0 + c;
        if (code == 45) return true;
        code -= RUSSIAN_SMALL_LETTER_OFFSET;
        if (code == 34) return true;
        if (code > 0 && code < 33) return true;
        return false;
    }
}
