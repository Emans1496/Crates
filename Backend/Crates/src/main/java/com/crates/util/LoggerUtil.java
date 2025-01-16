package com.crates.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {

    // Percorso del file di log nella cartella temporanea del sistema
    private static final String LOG_FILE_PATH = Paths.get(System.getProperty("java.io.tmpdir"), "operazioni_crud.log").toString();

    // Metodo per scrivere un messaggio nel file di log
    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            // Aggiungiamo timestamp al log
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // Gestisce eventuali errori di scrittura
        }
    }

    // Metodo per ottenere il percorso completo del file di log (se ci serve).
    public static String getLogFilePath() {
        return LOG_FILE_PATH;
    }
}
