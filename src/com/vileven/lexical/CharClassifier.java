package com.vileven.lexical;

/**
 * Created by Vileven on 12.05.17.
 */
public class CharClassifier {

    public static CharType classify(char c) {
        if (c == ' ' || c == '\t' || c == '\n') return CharType.SPACE;
        if (c == ';') return CharType.SEMICOLON;
        if (c == '.') return CharType.PERIOD;
        if (c == ':') return CharType.COLON;
        if (c == '\'') return CharType.APOSTROPHE;
        if (c >= '0' && c <= '9') return CharType.DIGIT;
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return CharType.CHAR;
        if (c == '=') return CharType.EQUAL;
        if (c == ',') return CharType.COMMA;
        if (c == '[') return CharType.LEFT_BRACK;
        if (c == ']') return CharType.RIGHT_BRACK;

        return CharType.UNKNOWN;
    }

}
