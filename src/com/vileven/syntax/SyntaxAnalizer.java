package com.vileven.syntax;

import com.vileven.lexical.LexicalState;
import com.vileven.lexical.Token;

import java.util.List;

/**
 * Created by Vileven on 16.05.17.
 */
public class SyntaxAnalizer {


    private List<Token> tokens;



    public int analize(List<Token> tokens) throws SyntaxException {
        this.tokens = tokens;


        if (tokens.size() <= 1) {
            throw new SyntaxException(tokens.get(1).getPosition(),"Незаконченное выражение");
        }

        if (tokens.get(0).getState() != LexicalState.CONST) {
            throw new SyntaxException(tokens.get(0).getPosition(), "Ожидается \"Const\"");
        }

        final int cursor = scanGeneral(1);

        return cursor;
    }


    public int scanGeneral(int cursor) throws SyntaxException {

        cursor = scanIdent(cursor);

        if (tokens.size() <= cursor) {
            throw new SyntaxException(tokens.get(cursor).getPosition(),"Незаконченное выражение");
        }

        if (tokens.get(cursor).getState() != LexicalState.EQUAL) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается \"=\"");
        }

        cursor = scanValue(cursor + 1);

        if (tokens.get(cursor).getState() != LexicalState.SEMICOLON) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается \";\"");
        } else {
            if (tokens.size() == cursor + 1) {
                return cursor + 1;
            } else {
                return scanGeneral(cursor + 1);
            }
        }
    }

    public int scanIdent(int cursor) throws SyntaxException {

        if (tokens.size() <= cursor + 3) {
            throw new SyntaxException(tokens.get(cursor).getPosition(),"Незаконченное выражение");
        }

        if (tokens.get(cursor).getState() != LexicalState.IDENTIFIER) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается идентификатор");
        }

        if (tokens.get(cursor + 1).getState() != LexicalState.COLON) {
            throw new SyntaxException(tokens.get(cursor + 1).getPosition(), "Ожидается: \":\"");
        }

        if (tokens.get(cursor + 2).getState() != LexicalState.SET) {
            throw new SyntaxException(tokens.get(cursor + 2).getPosition(), "Ожидается: \"set\"");
        }

        if (tokens.get(cursor + 3).getState() != LexicalState.OF) {
            throw new SyntaxException(tokens.get(cursor + 3).getPosition(), "Ожидается: \"of\"");
        }

        if (tokens.size() <= cursor + 4) {
            throw new SyntaxException(tokens.get(cursor).getPosition(),"Незаконченное выражение");
        }

        if (tokens.get(cursor + 4).getState() != LexicalState.CHAR_TYPE && tokens.get(cursor +4).getState() != LexicalState.CHAR_CONST) {
            throw new SyntaxException(tokens.get(cursor + 4).getPosition(), "Ожидается тип: \"Char\" или диапазон");
        }

        if (tokens.get(cursor + 4).getState() != LexicalState.CHAR_TYPE) {
            cursor = scanRange(cursor + 4);
        } else {
            cursor = cursor + 5;
        }


        return cursor;
    }

    public int scanRange(int cursor) throws SyntaxException {
        if (tokens.get(cursor).getState() == LexicalState.RIGHT_BRACK) {
            return cursor;
        }

        if (tokens.size() <= cursor + 2) {
            throw new SyntaxException(tokens.get(cursor).getPosition(),"Незаконченное выражение");
        }

        if (tokens.get(cursor).getState() != LexicalState.CHAR_CONST) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается строковая константа");
        }

        if (tokens.get(cursor + 1).getState() == LexicalState.RIGHT_BRACK) {
            return cursor + 1;
        }

        if (tokens.get(cursor + 1).getState() != LexicalState.DOTS) {
            if (tokens.get(cursor + 1).getState() == LexicalState.COMMA) {
                return scanRange(cursor + 2);
            } else {
                throw new SyntaxException(tokens.get(cursor + 1).getPosition(), "Ожидается запятая или \"..\"");
            }
        }

        if (tokens.get(cursor + 2).getState() != LexicalState.CHAR_CONST) {
            throw new SyntaxException(tokens.get(cursor + 2).getPosition(), "Ожидается строковая константа");
        }

        if (tokens.get(cursor + 3).getState() == LexicalState.COMMA) {
            return scanRange(cursor + 4);
        }

        return cursor + 3;
    }

    public int scanValue(int cursor) throws SyntaxException {
        if (tokens.size() <= cursor + 1) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Незаконченное выражение");
        }

        if (tokens.get(cursor).getState() != LexicalState.LEFT_BRACK) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается \"[\" для формирования мноежства");
        }

        cursor = scanRange(cursor + 1);

        if (tokens.size() <= cursor) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Незаконченное выражение");
        }

        if (tokens.get(cursor).getState() != LexicalState.RIGHT_BRACK) {
            throw new SyntaxException(tokens.get(cursor).getPosition(), "Ожидается \"]\"");
        }


        return cursor + 1;

    }
}
