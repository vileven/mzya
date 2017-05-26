package com.vileven;

import com.vileven.lexical.LexicalAnalizer;
import com.vileven.lexical.ParsingException;
import com.vileven.lexical.Token;
import com.vileven.syntax.SyntaxAnalizer;
import com.vileven.syntax.SyntaxException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static void intro() {
        System.out.println("Домашнее задание №2");
        System.out.println("Володин Сергей, ИУ6-43");
        System.out.println("Вариант 5");
        System.out.println("2017");

        System.out.println("");
        System.out.println("Вводите выражения. Для завершения работы введите \"exit();\"");
    }

    public static void main(String[] args) {
        final List<Token> tokens = new ArrayList<>();
        final LexicalAnalizer lexicalAnalizer = new LexicalAnalizer();
        final SyntaxAnalizer syntaxAnalizer = new SyntaxAnalizer();

        Main.intro();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                final String line = scanner.nextLine();

                if (line.equals("exit();")) {
                    System.exit(0);
                }

                if (line.equals("exit();")) {
                    System.exit(0);
                }

                tokens.clear();

                try {
                    lexicalAnalizer.analize(line, tokens);
                } catch (ParsingException e) {
                    System.err.println(e.getMessage());
                    System.out.println("Конструкция не распознана!");

                    continue;
                }

                try {
                    syntaxAnalizer.analize(tokens);
                } catch (SyntaxException e) {
                    System.err.println(e.getMessage());
                    System.out.println("Конструкция не распознана!");

                    continue;
                }

                System.out.println("Конструкция распознана.");
            }
        }
    }

}
