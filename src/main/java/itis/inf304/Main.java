package itis.inf304;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        String inputDirPath = "v26";
        String logFilePath = "v26.log";
        String outputFilePath = "v26.txt";

        LogFile logger;
        try {
            logger = new LogFile(logFilePath);
        } catch (IOException e) {
            System.err.println("Ошибка создания лог файла" + e.getMessage());
            return;
        }

        File inputDir = new File(inputDirPath);
        if (!inputDir.exists()) {
            logger.log("Папка не найдена" + inputDirPath);
            logger.close();
            return;
        }

        Thread[] threads = new Thread[(int) inputDir.length()];
        StringBuilder finalText = new StringBuilder();
        int i = 0;
        for(File file : inputDir.listFiles()){
            threads[i] = new FileWorking(file,logger,finalText);
            threads[i].start();
            i++;
        }
        for(Thread thread : threads){
            if(thread != null){
                thread.join();
            }
        }

        try (PrintWriter outputWriter = new PrintWriter(new FileWriter(outputFilePath))) {
            outputWriter.write(finalText.toString());
        } catch (IOException e) {
            logger.log("Ошибка при записи итогового файла: " + e.getMessage());
        }

        logger.close();
    }
}
