package com.vileven.syntax;

/**
 * Created by Vileven on 16.05.17.
 */
public class SyntaxException extends Exception {

    public SyntaxException(int pos, String message) {
        super("Ошибка синтаксичкеского анализа на позиции: " + pos +'\n' + message);
    }

}
