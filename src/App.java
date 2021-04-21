import java.util.ArrayList;
import java.util.Scanner;

import controladores.GameSetter;
import controladores.Writer;
import depedencias.Game;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Game> partidas = GameSetter.setGames(scanner);
        String resposta;
        for (Game partida : partidas) {
            System.out.println(partida + "\n");
        }

        do {
            System.out.println("deseja imprimir o relatório geral?(y/n)");
            resposta = scanner.nextLine(); 
        } while (!resposta.equals("y") && !resposta.equals("n") && !resposta.equals("Y") && !resposta.equals("N"));

        if (resposta.equals("y") || resposta.equals("Y")) {
            Writer.criarRelatórioDePartida(GameSetter.gerarRelatorio(partidas), scanner,"relatorioGeral");
        }

        do {
            System.out.println("deseja imprimir o relatório de uma partida especifica?(y/n)");
            resposta = scanner.nextLine();
        } while (!resposta.equals("y") && !resposta.equals("n") && !resposta.equals("Y") && !resposta.equals("N"));

        if (resposta.equals("y") || resposta.equals("Y")) {
            System.out.println("Qual id da partida que deseja imprimir?");
            int partidaId = Integer.parseInt(scanner.nextLine());
            Writer.criarRelatórioDePartida(GameSetter.gerarRelatorio(partidaId,partidas), scanner, ("relatorioPartida"+ partidaId));
        }
        scanner.close();
    }
}
