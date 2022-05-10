package br.edu.utfpr.logger;

import java.time.LocalDate;

public class Logger {
    public static void log(Tipo tipo, String mensagem) {
        LocalDate tempo = LocalDate.now();
        String nomeThread = Thread.currentThread().getName();
        String formato = "" +
                "%s\t [%s]\t -\t %s -\t %s\n";
        System.out.printf(
                formato, tempo, nomeThread, tipo, mensagem
        );
    }
}
