package com.vileven.syntax;

import com.vileven.lexical.LexicalAnalizer;
import com.vileven.lexical.ParsingException;
import com.vileven.lexical.Token;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vileven on 16.05.17.
 */
public class SyntaxAnalizerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    List<Token> out = new ArrayList<>();
    LexicalAnalizer lexicalAnalizer = new LexicalAnalizer();
    SyntaxAnalizer syntaxAnalizer = new SyntaxAnalizer();


    @Test
    public void oneConst() throws ParsingException, SyntaxException {
        lexicalAnalizer.analize("Const A7867:set of Char = ['h'..'z','a'];", out);
        assertEquals(Integer.valueOf(out.size()), Integer.valueOf(syntaxAnalizer.analize(out)));
    }

    @Test
    public void twoConst() throws ParsingException, SyntaxException {
        lexicalAnalizer.analize("Const A7867:set of Char = ['h'..'z','a']; vbg:set of 'a'..'z' = []; ", out);
        assertEquals(Integer.valueOf(out.size()), Integer.valueOf(syntaxAnalizer.analize(out)));
    }

    @Test
    public void threeConst() throws ParsingException, SyntaxException {
        lexicalAnalizer.analize("Const A7867:set of Char = ['h'..'z','a']; vbg:set of 'a'..'z' = []; abc: set of ','..'-' = ['h'..'z','a','2'..'3'];", out);
        assertEquals(Integer.valueOf(out.size()), Integer.valueOf(syntaxAnalizer.analize(out)));
    }
}