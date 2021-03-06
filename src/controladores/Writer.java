package controladores;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Writer {

    public static void criarRelatórioDePartida(String relatorio, Scanner scanner,String tipoDeRelatorio) {
        try {
           
            System.out.println("Informe o caminho para salvar o relatório:");
            String path = scanner.nextLine();
            FileWriter criadorDeArquivo = new FileWriter(path+"/"+ tipoDeRelatorio +".txt", false);
            BufferedWriter buffer = new BufferedWriter(criadorDeArquivo);
            PrintWriter escritorDeArquivo = new PrintWriter(buffer);
            escritorDeArquivo.append(relatorio);
            escritorDeArquivo.close();
            System.out.println("Relatório criado!");
        } catch (FileNotFoundException e) {
            System.out.println("INFORME UM CAMINHO VÁLIDO!\n");
            Writer.criarRelatórioDePartida(relatorio,scanner,tipoDeRelatorio);
        } catch (IOException e) {
            System.out.println("INFORME UM CAMINHO VÁLIDO!\n");            
            Writer.criarRelatórioDePartida(relatorio,scanner,tipoDeRelatorio);
        }
    }
}
