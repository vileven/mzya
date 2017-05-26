package com.vileven.lexical;

/**
 * Created by Vileven on 11.05.17.
 */
public class ParsingException extends Exception {

    public ParsingException(int pos) {
        super("Ошибка лексического анализа на позиции: " + pos);
    }
}
