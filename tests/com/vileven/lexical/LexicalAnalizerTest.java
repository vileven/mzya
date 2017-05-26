package com.vileven.lexical;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vileven on 12.05.17.
 */
public class LexicalAnalizerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    List<Token> out = new ArrayList<>();
    LexicalAnalizer analizer = new LexicalAnalizer();

    @Before
    public void beforeEach() {
        out.clear();
    }

    @Test
    public void identifierRecogn() throws ParsingException {
        analizer.analize("Const abc  ", out);
        assertEquals(2, out.size());

        assertEquals(LexicalState.CONST, out.get(0).getState());

        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals("abc", out.get(1).getName());
        out.clear();

        analizer.analize("const abd erre ", out);
        assertEquals(3, out.size());
        out.clear();

        analizer.analize("set", out);
        assertEquals("set", out.get(0).getName());
    }

    @Test
    public void charConstRecognition() throws ParsingException {
        analizer.analize("'3'", out);
        assertEquals(1, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals("3", out.get(0).getName());
        out.clear();

        analizer.analize(" '4'   '3'   ", out);
        assertEquals(2, out.size());
    }

    @Test
    public void dotsRecogn() throws ParsingException {
        analizer.analize(" .. ", out);
        assertEquals(1, out.size());
        assertEquals(LexicalState.DOTS, out.get(0).getState());
        out.clear();

        analizer.analize("..", out);
        assertEquals(1, out.size());
        assertEquals(LexicalState.DOTS, out.get(0).getState());
    }

    @Test
    public void rangeRecogn() throws ParsingException {
        analizer.analize("'1'..','", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals(LexicalState.DOTS, out.get(1).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(2).getState());
        out.clear();

        analizer.analize(" '1'  ..  ','  ", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals(LexicalState.DOTS, out.get(1).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(2).getState());
    }

    @Test
    public void bracketsRecogn() throws ParsingException {
        analizer.analize(" [ ] ", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(1).getState());
        out.clear();

        analizer.analize("[ ]", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(1).getState());
        out.clear();

        analizer.analize("[ abc ]", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(2).getState());
        out.clear();

        analizer.analize("[abc ]", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(2).getState());
        out.clear();

        analizer.analize("['1']", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(1).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(2).getState());
        out.clear();

        analizer.analize("['1'..]", out);
        assertEquals(4, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(1).getState());
        assertEquals(LexicalState.DOTS, out.get(2).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(3).getState());
        out.clear();

        analizer.analize("['1' ..]", out);
        assertEquals(4, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(1).getState());
        assertEquals(LexicalState.DOTS, out.get(2).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(3).getState());
        out.clear();

        analizer.analize("['1'..'3']", out);
        assertEquals(5, out.size());
        assertEquals(LexicalState.LEFT_BRACK, out.get(0).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(1).getState());
        assertEquals(LexicalState.DOTS, out.get(2).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(3).getState());
        assertEquals("3", out.get(3).getName());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(4).getState());
        out.clear();
    }

    @Test
    public void colonCase() throws ParsingException {
        analizer.analize("abc : ", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.COLON, out.get(1).getState());
        out.clear();

        analizer.analize("abc: ", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.COLON, out.get(1).getState());
        out.clear();
    }

    @Test
    public void beginParse() throws  ParsingException {
        analizer.analize("  Const A7867:  set  of Char", out);
        assertEquals(6, out.size());
        assertEquals(LexicalState.CONST, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals("A7867", out.get(1).getName());
        assertEquals(LexicalState.COLON, out.get(2).getState());
        assertEquals(LexicalState.SET, out.get(3).getState());
        assertEquals(LexicalState.OF, out.get(4).getState());
        assertEquals(LexicalState.CHAR_TYPE, out.get(5).getState());
        out.clear();
    }

    @Test
    public void endsWithSemicolon() throws ParsingException {
        analizer.analize("abc;", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(1).getState());
        out.clear();

        analizer.analize(" abc  ;  ", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(1).getState());
        out.clear();

        analizer.analize(" abc  ;", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(1).getState());
        out.clear();

        analizer.analize(" abc;  ", out);
        assertEquals(2, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(1).getState());
        out.clear();
    }

    @Test
    public void commaCase() throws ParsingException {
        analizer.analize("abc,Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();

        analizer.analize("abc, Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();

        analizer.analize("abc ,  Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.IDENTIFIER, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();

        analizer.analize("'1',Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();

        analizer.analize("'1', Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();

        analizer.analize("'1'  ,   Const", out);
        assertEquals(3, out.size());
        assertEquals(LexicalState.CHAR_CONST, out.get(0).getState());
        assertEquals(LexicalState.COMMA, out.get(1).getState());
        assertEquals(LexicalState.CONST, out.get(2).getState());
        out.clear();
    }

    @Test
    public void testOneFull() throws ParsingException {
        analizer.analize("Const A7867:set of Char = ['h'..'z','a'];", out);
        assertEquals(15, out.size());
        assertEquals(LexicalState.CONST, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals("A7867", out.get(1).getName());
        assertEquals(LexicalState.COLON, out.get(2).getState());
        assertEquals(LexicalState.SET, out.get(3).getState());
        assertEquals(LexicalState.OF, out.get(4).getState());
        assertEquals(LexicalState.CHAR_TYPE, out.get(5).getState());
        assertEquals(LexicalState.EQUAL, out.get(6).getState());
        assertEquals(LexicalState.LEFT_BRACK, out.get(7).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(8).getState());
        assertEquals(LexicalState.DOTS, out.get(9).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(10).getState());
        assertEquals(LexicalState.COMMA, out.get(11).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(12).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(13).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(14).getState());
        out.clear();

        analizer.analize("Const A7867: set of Char = ['h'..'z', 'a'];", out);
        assertEquals(15, out.size());
        assertEquals(LexicalState.CONST, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals("A7867", out.get(1).getName());
        assertEquals(LexicalState.COLON, out.get(2).getState());
        assertEquals(LexicalState.SET, out.get(3).getState());
        assertEquals(LexicalState.OF, out.get(4).getState());
        assertEquals(LexicalState.CHAR_TYPE, out.get(5).getState());
        assertEquals(LexicalState.EQUAL, out.get(6).getState());
        assertEquals(LexicalState.LEFT_BRACK, out.get(7).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(8).getState());
        assertEquals(LexicalState.DOTS, out.get(9).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(10).getState());
        assertEquals(LexicalState.COMMA, out.get(11).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(12).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(13).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(14).getState());
        out.clear();
    }

    @Test
    public void testFull() throws ParsingException {
        analizer.analize("Const A7867:set of Char = ['h'..'z','a']; vbg:set of 'a'..'z' = [];", out);
        assertEquals(26, out.size());
        assertEquals(LexicalState.CONST, out.get(0).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(1).getState());
        assertEquals("A7867", out.get(1).getName());
        assertEquals(LexicalState.COLON, out.get(2).getState());
        assertEquals(LexicalState.SET, out.get(3).getState());
        assertEquals(LexicalState.OF, out.get(4).getState());
        assertEquals(LexicalState.CHAR_TYPE, out.get(5).getState());
        assertEquals(LexicalState.EQUAL, out.get(6).getState());
        assertEquals(LexicalState.LEFT_BRACK, out.get(7).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(8).getState());
        assertEquals(LexicalState.DOTS, out.get(9).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(10).getState());
        assertEquals(LexicalState.COMMA, out.get(11).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(12).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(13).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(14).getState());
        assertEquals(LexicalState.IDENTIFIER, out.get(15).getState());
        assertEquals(LexicalState.COLON, out.get(16).getState());
        assertEquals(LexicalState.SET, out.get(17).getState());
        assertEquals(LexicalState.OF, out.get(18).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(19).getState());
        assertEquals(LexicalState.DOTS, out.get(20).getState());
        assertEquals(LexicalState.CHAR_CONST, out.get(21).getState());
        assertEquals(LexicalState.EQUAL, out.get(22).getState());
        assertEquals(LexicalState.LEFT_BRACK, out.get(23).getState());
        assertEquals(LexicalState.RIGHT_BRACK, out.get(24).getState());
        assertEquals(LexicalState.SEMICOLON, out.get(25).getState());
        out.clear();
    }

    @Test
    public void itShouldThrownExeption() throws ParsingException {
        exception.expect(ParsingException.class);
        analizer.analize("set 'ds'' ;", out);
    }

}