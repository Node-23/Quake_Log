package controladores;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Scanner;

public class LogReader {

    public static String[] reader(Scanner scanner){
        System.out.printf("Informe o caminho do log:\n");
        String path = scanner.nextLine();

        File file = new File(path);
        byte[] bytes = null;

        try {
            bytes = Files.readAllBytes(file.toPath());
        }catch (IOException e) {
            System.out.println("Erro de I/O encontrado!\n Erro: " + e + "\n O programa será encerrado.");
            System.exit(0);
        } 

        String logText="";

        try {
            logText = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("A codificação de caracteres não é compatível. O programa será encerrado. \n");
            System.exit(0);
        }

        String[] log = logText.split("\n");
        return log;
    }

}


// /home/matheus/Área de Trabalho/games.log
    
// /home/matheus/Área de Trabalho