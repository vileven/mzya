package com.vileven.lexical;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vileven on 12.05.17.
 */
public class TokenTest {

    @Test
    public void conctructorTest() {
        final Token token = new Token(LexicalState.CHAR_CONST, "e", 2);
        assertEquals(LexicalState.CHAR_CONST, token.getState());
        assertEquals("e",token.getName());
        assertEquals(2, token.getPosition());
    }

    @Test
    public void identifierCase() {
        Token token = new Token(LexicalState.IDENTIFIER, "abc", 1);
        assertEquals(LexicalState.IDENTIFIER, token.getState());

        token = new Token(LexicalState.IDENTIFIER, "Const",1);
        assertEquals(LexicalState.CONST, token.getState());

        token = new Token(LexicalState.IDENTIFIER, "set",1);
        assertEquals(LexicalState.SET, token.getState());

        token = new Token(LexicalState.IDENTIFIER, "set",1);
        assertEquals(LexicalState.SET, token.getState());

        token = new Token(LexicalState.IDENTIFIER, "of",1);
        assertEquals(LexicalState.OF, token.getState());

        token = new Token(LexicalState.IDENTIFIER, "Char",1);
        assertEquals(LexicalState.CHAR_TYPE, token.getState());
    }

    @Test
    public void dotsCase() {
        final Token token = new Token(LexicalState.SECOND_DOT, "..", 2);
        assertEquals(LexicalState.DOTS, token.getState());
    }

}