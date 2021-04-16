package classes.controladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LogReader {
    
    public static String[] reader(String path) throws IOException{
        File file = new File(path);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String logText = new String(bytes, "UTF-8");
        String[] log = logText.split("\n");
        return log;
    }

}
