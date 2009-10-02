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

package org.apache.lucene.morphology.russian;

import org.apache.lucene.morphology.LetterDecoderEncoder;
import org.apache.lucene.morphology.SuffixToLongException;
import org.apache.lucene.morphology.WrongCharaterException;

import java.util.ArrayList;

/**
 * This helper class allow encode suffix of russian word
 * to long value and decode from it.
 * Assumed that suffix contains only small russian letters and dash.
 * Also assumed that letter � and � coinsed.
 */
public class RussianSuffixDecoderEncoder implements LetterDecoderEncoder {
    public static final int RUSSIAN_SMALL_LETTER_OFFSET = 1071;
    static public int SUFFIX_LENGTH = 6;
    public static final int EE_CHAR = 34;
    public static final int E_CHAR = 6;
    public static final int DASH_CHAR = 45;
    public static final int DASH_CODE = 33;

    public Integer encode(String string) {
        if (string.length() > 6) throw new SuffixToLongException("Suffix length should not be greater then " + 12);
        int result = 0;
        for (int i = 0; i < string.length(); i++) {
            int c = 0 + string.charAt(i) - RUSSIAN_SMALL_LETTER_OFFSET;
            if (c == 45 - RUSSIAN_SMALL_LETTER_OFFSET) {
                c = DASH_CODE;
            }
            if (c == EE_CHAR) c = E_CHAR;
            if (c < 0 || c > 33)
                throw new WrongCharaterException("Symblo " + string.charAt(i) + " is not small cirillic letter");
            result = result * 34 + c;
        }
        for (int i = string.length(); i < 6; i++) {
            result *= 34;
        }
        return result;
    }

    public int[] encodeToArray(String s) {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        while (s.length() > 6) {
            integers.add(encode(s.substring(0, 6)));
            s = s.substring(6);
        }
        integers.add(encode(s));
        int[] ints = new int[integers.size()];
        int pos = 0;
        for (Integer i : integers) {
            ints[pos] = i;
            pos++;
        }
        return ints;
    }

    public String decodeArray(int[] array) {
        String result = "";
        for (int i : array) {
            result += decode(i);
        }
        return result;
    }


    public String decode(Integer suffixN) {
        String result = "";
        while (suffixN > 33) {
            int c = suffixN % 34 + RUSSIAN_SMALL_LETTER_OFFSET;
            if (c == RUSSIAN_SMALL_LETTER_OFFSET) {
                suffixN /= 34;
                continue;
            }
            if (c == DASH_CODE + RUSSIAN_SMALL_LETTER_OFFSET) c = DASH_CHAR;
            result = (char) c + result;
            suffixN /= 34;
        }
        long c = suffixN + RUSSIAN_SMALL_LETTER_OFFSET;
        if (c == DASH_CODE + RUSSIAN_SMALL_LETTER_OFFSET) c = DASH_CHAR;
        result = (char) c + result;
        return result;
    }

    public boolean checkCharacter(char c) {
        int code = 0 + c;
        if (code == 45) return true;
        code -= RUSSIAN_SMALL_LETTER_OFFSET;
        if (code > 0 && code < 33) return true;
        return false;
    }

    public String cleanString(String s) {
        return s;
    }
}
