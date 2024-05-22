package itis.inf304;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class FileWorking extends Thread{
    private final File file;
    private final LogFile logger;
    private final StringBuilder text;

    public FileWorking(File file, LogFile logger, StringBuilder text) {
        this.file = file;
        this.logger = logger;
        this.text = text;
    }

    @Override
    public void run() {
        try(DataInputStream inp = new DataInputStream(new FileInputStream(file))){
            int k = inp.readInt();
            byte[] data = new byte[k];
            inp.read(data);
            String s = new String(data, StandardCharsets.UTF_8);
            int d = inp.readInt();
            int p = inp.readInt();
            int actualLength = s.length();

            logger.log("прочитали файл " + file.getName() +" кол-во байт данных: "+k+", кол-во считанных символов: "+actualLength+", контрольное число: "+d+", номер части:"+p);

            if (actualLength != d) {
                logger.log("Ошибка: контрольное число не совпадает для файла "+file.getName());
            }

            synchronized (text) {
                text.append(s);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
