package com.vileven.lexical;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by Vileven on 11.05.17.
 */
@SuppressWarnings("AnonymousInnerClassMayBeStatic")
public class LexicalAnalizer {

    private final Map<LexicalState, Map<CharType, LexicalState>> states = new EnumMap<>(LexicalState.class);

    private void init() {
        states.put(LexicalState.NONE, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.IDENTIFIER);
            put(CharType.APOSTROPHE, LexicalState.OPEN_APOSTR);
            put(CharType.COLON, LexicalState.COLON);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.FIRST_DOT);
            put(CharType.SPACE, LexicalState.NONE);
            put(CharType.SEMICOLON, LexicalState.SEMICOLON);
            put(CharType.EQUAL, LexicalState.EQUAL);
            put(CharType.LEFT_BRACK, LexicalState.LEFT_BRACK);
            put(CharType.RIGHT_BRACK, LexicalState.RIGHT_BRACK);
            put(CharType.COMMA, LexicalState.COMMA);
        }});

        states.put(LexicalState.IDENTIFIER, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.IDENTIFIER);
            put(CharType.DIGIT, LexicalState.IDENTIFIER);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.COLON);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.SEMICOLON);
            put(CharType.EQUAL, LexicalState.END);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.COMMA);
        }});

        states.put(LexicalState.OPEN_APOSTR, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.CHAR_CONST);
            put(CharType.UNKNOWN, LexicalState.CHAR_CONST);
            put(CharType.COLON, LexicalState.CHAR_CONST);
            put(CharType.DIGIT, LexicalState.CHAR_CONST);
            put(CharType.PERIOD, LexicalState.CHAR_CONST);
            put(CharType.SPACE, LexicalState.CHAR_CONST);
            put(CharType.SEMICOLON, LexicalState.CHAR_CONST);
            put(CharType.EQUAL, LexicalState.CHAR_CONST);
            put(CharType.LEFT_BRACK, LexicalState.CHAR_CONST);
            put(CharType.RIGHT_BRACK, LexicalState.CHAR_CONST);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.CHAR_CONST);
        }});

        states.put(LexicalState.CHAR_CONST, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.END);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.ERROR);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.CLOSE_APOSTR, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.END);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.END);
            put(CharType.COMMA, LexicalState.END);
        }});

        states.put(LexicalState.EQUAL, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.END);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.FIRST_DOT, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.SECOND_DOT);
            put(CharType.SPACE, LexicalState.ERROR);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.SECOND_DOT, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.END);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.LEFT_BRACK, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.END);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.UNKNOWN, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.RIGHT_BRACK, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.ERROR);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.SEMICOLON);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

        states.put(LexicalState.COLON, new EnumMap<CharType, LexicalState>(CharType.class) {{
            put(CharType.CHAR, LexicalState.IDENTIFIER);
            put(CharType.DIGIT, LexicalState.ERROR);
            put(CharType.APOSTROPHE, LexicalState.ERROR);
            put(CharType.COLON, LexicalState.ERROR);
            put(CharType.PERIOD, LexicalState.ERROR);
            put(CharType.SPACE, LexicalState.END);
            put(CharType.SEMICOLON, LexicalState.ERROR);
            put(CharType.EQUAL, LexicalState.ERROR);
            put(CharType.LEFT_BRACK, LexicalState.ERROR);
            put(CharType.RIGHT_BRACK, LexicalState.ERROR);
            put(CharType.COMMA, LexicalState.ERROR);
        }});

    }


    @SuppressWarnings("EnumSwitchStatementWhichMissesCases")
    public void analize(String in, List<Token> out) throws ParsingException {

        StringBuilder buffer = new StringBuilder();
        LexicalState state = LexicalState.NONE;

        for (int i = 0; i < in.length(); i++) {
            final char c = in.charAt(i);
            final CharType charType = CharClassifier.classify(c);
            final LexicalState newState = states.get(state).get(charType);


            if (newState == LexicalState.ERROR) {
                throw new ParsingException(i);
            }

            switch (newState) {
                case OPEN_APOSTR:
                    state = newState;
                    break;
                case SECOND_DOT:
                    buffer.append(c);
                    out.add(new Token(newState,buffer.toString(), i));
                    state = LexicalState.NONE;
                    buffer = new StringBuilder();
                    break;
                case LEFT_BRACK:
                    buffer.append(c);
                    out.add(new Token(newState,buffer.toString(), i));
                    state = LexicalState.NONE;
                    buffer = new StringBuilder();
                    break;
                case COLON:
                    if (state == LexicalState.IDENTIFIER) {
                        out.add(new Token(state,buffer.toString(), i));
                    }
                    out.add(new Token(newState,String.valueOf(c), i));
                    buffer = new StringBuilder();
                    state = LexicalState.NONE;
                    break;
                case SEMICOLON:
                    if (state == LexicalState.RIGHT_BRACK || state == LexicalState.IDENTIFIER) {
                        out.add(new Token(state,buffer.toString(), i));
                    }
                    out.add(new Token(newState,String.valueOf(c), i));
                    buffer = new StringBuilder();
                    state = LexicalState.NONE;
                    break;
                case COMMA:
                    if (state == LexicalState.IDENTIFIER || state == LexicalState.CHAR_CONST) {
                        out.add(new Token(state,buffer.toString(), i));
                    }
                    out.add(new Token(newState,String.valueOf(c), i));
                    buffer = new StringBuilder();
                    state = LexicalState.NONE;
                    break;
                case END:
                    out.add(new Token(state,buffer.toString(), i));
                    state = LexicalState.NONE;
                    buffer = new StringBuilder();
                    break;
                default:
                    if (charType != CharType.SPACE) {
                        buffer.append(c);
                    }
                    state = newState;
            }
        }

        if (state != LexicalState.NONE) {
            out.add(new Token(state, buffer.toString(), in.length() - 1));
        }


    }

    public LexicalAnalizer() {
        init();
    }

}
