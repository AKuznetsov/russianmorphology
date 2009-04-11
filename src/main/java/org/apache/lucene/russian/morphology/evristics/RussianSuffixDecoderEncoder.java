package org.apache.lucene.russian.morphology.evristics;


public class RussianSuffixDecoderEncoder {
    public static final int RUSSIAN_SMALL_LETTER_OFFSET = 1071;
    public static final int SUFFIX_LENGTH = 7;


    static public Integer encode(String string) {
        if (string.length() > 6) throw new RuntimeException("suffix to long");
        int result = 0;
        for (int i = 0; i < string.length(); i++) {
            int c = 0 + string.charAt(i) - RUSSIAN_SMALL_LETTER_OFFSET;
            if (c < 0) {
                c = 33;
            }
            if (c == 34) c = 6;
            result = result * 35 + c;
        }
        return result;
    }

    static public String decode(Integer suffixN) {
        String result = "";
        while (suffixN > 35) {
            result = (char) (suffixN % 35 + RUSSIAN_SMALL_LETTER_OFFSET) + result;
            suffixN /= 35;
        }
        result = (char) (suffixN + RUSSIAN_SMALL_LETTER_OFFSET) + result;
        return result;
    }

    static public Long encodeLong(String string) {
        if (string.length() > 12) throw new RuntimeException("suffix to long");
        long result = 0L;
        for (int i = 0; i < string.length(); i++) {
            int c = 0 + string.charAt(i) - RUSSIAN_SMALL_LETTER_OFFSET;
            if (c < 0) {
                c = 33;
            }
            if (c == 34) c = 6;
            result = result * 35L + c;
        }
        return result;
    }

    static public String decodeLong(Long suffixN) {
        String result = "";
        while (suffixN > 35) {
            long c = suffixN % 35 + RUSSIAN_SMALL_LETTER_OFFSET;
            if (c == 33 + RUSSIAN_SMALL_LETTER_OFFSET) c = 45;
            result = (char) c + result;
            suffixN /= 35;
        }
        long c = suffixN + RUSSIAN_SMALL_LETTER_OFFSET;
        if (c == 33 + RUSSIAN_SMALL_LETTER_OFFSET) c = 45;
        result = (char) c + result;
        return result;
    }
}
