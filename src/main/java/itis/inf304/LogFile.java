package itis.inf304;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogFile {
    private final PrintWriter writer;

    public LogFile(String fileName) throws IOException {
        this.writer = new PrintWriter(new FileWriter(fileName, true));
    }

    public synchronized void log(String message) {
        writer.println(message);
    }

    public void close() {
        writer.close();
    }
}
