package com.vileven.lexical;

/**
 * Created by Vileven on 11.05.17.
 */
public class Token {

    private final LexicalState state;
    private final String name;
    private final int position;

    public Token(LexicalState state, String name, int pos) {

        if (state == LexicalState.IDENTIFIER) {
            if (name.equals("Const")) state = LexicalState.CONST;
            if (name.equals("Char")) state = LexicalState.CHAR_TYPE;
            if (name.equals("set")) state = LexicalState.SET;
            if (name.equals("of")) state = LexicalState.OF;
        }

        if (state == LexicalState.SECOND_DOT) {
            state = LexicalState.DOTS;
        }

        this.state = state;
        this.name = name;
        this.position = pos;
    }

    public LexicalState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

}
