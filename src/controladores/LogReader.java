package controladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class LogReader {

    public static String[] reader(Scanner scanner) throws IOException {
        System.out.printf("Informe o caminho do log:\n");
        String path = scanner.nextLine();

        // TODO exception para caso não ache o arquivo
        File file = new File(path);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String logText = new String(bytes, "UTF-8");
        String[] log = logText.split("\n");
        return log;
    }

}


// /home/matheus/Área de Trabalho/games.log
    
// /home/matheus/Área de Trabalho